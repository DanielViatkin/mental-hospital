package com.epam.hospital.controller;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.Page;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.provider.CommandProvider;
import com.epam.hospital.controller.request.HttpRequestFiller;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.controller.request.RequestContextMapper;
import com.epam.hospital.dao.pool.ConnectionPool;
import com.epam.hospital.dao.pool.DBParameter;
import com.epam.hospital.dao.pool.DBResourceManager;
import com.epam.hospital.dao.pool.exception.ConnectionPoolException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//https://github.com/enhorse/java-interview
//https://www.youtube.com/c/EugeneSuleimanov/videos
//https://www.youtube.com/channel/UCRxBK1uUONfrU7roM36zuTQ

@Slf4j
//http://localhost:8080/MentalHospital
public class Controller extends HttpServlet {
    private static final String HOME_PAGE_COMMAND = "MentalHospital?command=" + CommandName.HOME_PAGE +
            "&" + RequestParameters.PAGE + "=1";

    @Override
    public void init() throws ServletException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();

        String url = dbResourceManager.getValue(DBParameter.DB_URL);
        String user = dbResourceManager.getValue(DBParameter.DB_USER);
        String password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        String driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        int poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));

        int repeatCounter = 0;
        boolean isDone = false;
        log.info("[DB] Starting connection pool init logic");
        while (!isDone) {
            try {
                log.info("[DB] try №{}: initialising connection pool", repeatCounter + 1);
                connectionPool.init(url, user, password, driverName, poolSize);
                isDone = true;
            } catch (ConnectionPoolException e) {
                log.error("Error while initialising connection pool.", e);
                if (++repeatCounter > 4) {
                    log.error("[DB] Connection pool failed to init connection pool {} times. Exiting...", repeatCounter);
                    System.exit(1);
                }
            }
        }
        log.info("[DB] Connection pool init successfully done");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(RequestParameters.COMMAND);
        CommandProvider commandProvider = new CommandProvider();
        Command command = commandProvider.getCommand(commandName);
        try {
            RequestContextMapper requestContextMapper = new RequestContextMapper();
            RequestContext requestContext = requestContextMapper.map(request);
            CommandResult commandResult = command.execute(requestContext);
            HttpRequestFiller requestFiller = new HttpRequestFiller();
            requestFiller.fillData(request, requestContext);
            dispatch(commandResult, request, response);
        } catch (Exception e) {
            log.error("Error while processing request.", e);
            handleException(request, response, e.getMessage());
        }
    }

    private void dispatch(CommandResult commandResult,
                          HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = commandResult.getPage();
        if (page == null) {
            response.sendRedirect(HOME_PAGE_COMMAND);
        } else {
            if (commandResult.isRedirect()) {
                response.sendRedirect(page);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                dispatcher.forward(request, response);
            }
        }
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, String errorMessage)
            throws IOException {
        req.setAttribute(RequestAttributes.ERROR_MESSAGE, errorMessage);
        RequestDispatcher dispatcher = req.getRequestDispatcher(Page.ERROR);
        try {
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            log.error("Error while handling exception...", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connectionPool.dispose();
        } catch (Exception e) {
            log.error("Error while disposing connection pool.", e);
        }
        super.destroy();
    }
}
