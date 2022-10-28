package com.epam.hospital.dao.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.constant.database.Table;
import com.epam.hospital.dao.UserDao;
import com.epam.hospital.dao.builder.BuilderFactory;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.user.User;
import com.epam.hospital.model.user.info.DoctorInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {
    public static final String SAVE_USER_QUERY = String.format(
            "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?)",
            Table.USER_TABLE,
            Column.USER_FIRS_NAME,
            Column.USER_LAST_NAME,
            Column.USER_NUMBER,
            Column.USER_EMAIL,
            Column.USER_PASSWORD,
            Column.USER_ROLE_ID,
            Column.USER_IS_BANNED
    );
    public static final String SAVE_DOCTOR_INFO_QUERY = String.format(
            "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
            Table.DOCTOR_INFO_TABLE,
            Column.DOCTOR_INFO_CLASSIFICATION,
            Column.DOCTOR_INFO_SPECIALIZATION,
            Column.DOCTOR_INFO_WORK_EXPERIENCE,
            Column.DOCTOR_INFO_PRICE,
            Column.DOCTOR_INFO_ID
    );
    public final static String UPDATE_USER_QUERY = String.format(
            "UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?",
            Table.USER_TABLE,
            Column.USER_FIRS_NAME,
            Column.USER_LAST_NAME,
            Column.USER_NUMBER,
            Column.USER_EMAIL,
            Column.USER_PASSWORD,
            Column.USER_ROLE_ID,
            Column.USER_IS_BANNED,
            Column.USER_ID
    );
    public final static String FIND_BY_FULLNAME_QUERY = String.format(
            "SELECT * FROM %s WHERE %s=? AND %s=?",
            Table.USER_TABLE,
            Column.USER_FIRS_NAME,
            Column.USER_LAST_NAME
    );
    public final static String LOGIN_QUERY = String.format(
            "SELECT * FROM %s WHERE %s=? AND %s=?",
            Table.USER_TABLE,
            Column.USER_EMAIL,
            Column.USER_PASSWORD
    );
    public final static String FIND_ROLE_BY_ID_QUERY = String.format(
            "SELECT * FROM %s WHERE %s=?",
            Table.USER_ROLES_TABLE,
            Column.USER_ROLES_ID
    );
    public final static String IS_USER_EXIST_QUERY = String.format(
            "SELECT * FROM %s WHERE %s=?",
            Table.USER_TABLE,
            Column.USER_EMAIL
    );
    public final static String FIND_DOCTOR_INFO_BY_ID_QUERY = String.format(
            "SELECT * FROM %s WHERE %s=?",
            Table.DOCTOR_INFO_TABLE,
            Column.DOCTOR_INFO_ID
    );
    public final static String FIND_ROLE_ID_QUERY = String.format(
            "SELECT %s FROM %s WHERE %s=?",
            Column.USER_ROLES_ID,
            Table.USER_ROLES_TABLE,
            Column.USER_ROLES_NAME
    );


    public UserDaoImpl() {
        super(BuilderFactory.getUserBuilder(), Table.USER_TABLE, Column.USER_ID);
    }

    @Override
    public void save(User entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_USER_QUERY);) {
            setParams(statement, entity, SAVE_USER_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save user.", e);
        }
    }

    @Override
    public int saveAndGetId(User entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            setParams(statement, entity, SAVE_USER_QUERY);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Can't save user.", e);
        }
    }

    @Override
    public void update(User entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(UPDATE_USER_QUERY);) {
            setParams(statement, entity, UPDATE_USER_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update user.", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        List<User> list = findByField(Column.USER_EMAIL, email);

        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    //tested
    public Optional<User> findByFullName(String firstName, String lastName) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(FIND_BY_FULLNAME_QUERY);) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = BuilderFactory.getUserBuilder().build(resultSet);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find user by full name.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailPassword(String email, String password) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(LOGIN_QUERY);) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = BuilderFactory.getUserBuilder().build(resultSet);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find user by email and password.", e);
        }
        return Optional.empty();
    }

    @Override
    public String findUserRole(int id) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(FIND_ROLE_BY_ID_QUERY);) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(Column.USER_ROLES_NAME);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find user role by role id.", e);
        }
        return null;
    }

    @Override
    public boolean isUserExist(String login) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(IS_USER_EXIST_QUERY);) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find user by email.", e);
        }
        return false;
    }

    @Override
    public Optional<DoctorInfo> findDoctorInfoById(int id) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(FIND_DOCTOR_INFO_BY_ID_QUERY);) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                DoctorInfo doctorInfo = BuilderFactory.getDoctorInfoBuilder().build(resultSet);
                return Optional.of(doctorInfo);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find doctor info by doctor id.", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAllDoctors(int id) throws DaoException {
        return findByField(Column.USER_ROLE_ID, id);
    }

    @Override
    public Integer getUserRoleId() throws DaoException {
        Integer roleId = null;
        try (PreparedStatement statement = pooledConnection.prepareStatement(FIND_ROLE_ID_QUERY);) {
            statement.setString(1, "USER");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                roleId = resultSet.getInt(Column.USER_ROLES_ID);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find user role id.", e);
        }
        return roleId;
    }

    @Override
    public Integer getDoctorRoleId() throws DaoException {
        Integer roleId = null;
        try (PreparedStatement statement = pooledConnection.prepareStatement(FIND_ROLE_ID_QUERY);) {
            statement.setString(1, "DOCTOR");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                roleId = resultSet.getInt(Column.USER_ROLES_ID);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find doctor role id.", e);
        }
        return roleId;
    }

    @Override
    public void saveDoctorInfo(DoctorInfo entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_DOCTOR_INFO_QUERY);) {
            setParams(statement, entity, SAVE_DOCTOR_INFO_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save doctor info.", e);
        }
    }

    private void setParams(PreparedStatement statement, User user, String action) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getNumber());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getHashedPassword());
        statement.setInt(6, user.getUserRoleId());
        statement.setBoolean(7, user.getIsBanned());
        if (action.equals(UPDATE_USER_QUERY)) {
            statement.setInt(8, user.getUserId());
        }
    }

    private void setParams(PreparedStatement statement, DoctorInfo doctorInfo, String action) throws SQLException {
        statement.setInt(1, doctorInfo.getClassification());
        statement.setString(2, doctorInfo.getSpecialization());
        statement.setInt(3, doctorInfo.getWorkExperience());
        statement.setDouble(4, doctorInfo.getPrice());
        statement.setInt(5, doctorInfo.getDoctorId());
    }
}
