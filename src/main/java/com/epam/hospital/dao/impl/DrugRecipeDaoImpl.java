package com.epam.hospital.dao.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.constant.database.Table;
import com.epam.hospital.dao.DrugRecipeDao;
import com.epam.hospital.dao.builder.BuilderFactory;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.DrugRecipe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DrugRecipeDaoImpl extends AbstractDaoImpl<DrugRecipe> implements DrugRecipeDao {
    private final static String SAVE_DRUG_RECIPE_QUERY = String.format(
            "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
            Table.DRUG_RECIPES_TABLE,
            Column.DRUG_RECIPES_DESCRIPTION,
            Column.DRUG_RECIPES_DOSE,
            Column.DRUG_RECIPES_TREATMENT_COURSE_ID,
            Column.DRUG_RECIPES_DRUG_ID
    );
    private final static String UPDATE_DRUG_RECIPE_QUERY = String.format(
            "UPDATE %s SET %s=?, %s=? WHERE %s=? AND %s=?",
            Table.DRUG_RECIPES_TABLE,
            Column.DRUG_RECIPES_DESCRIPTION,
            Column.DRUG_RECIPES_DOSE,
            Column.DRUG_RECIPES_TREATMENT_COURSE_ID,
            Column.DRUG_RECIPES_DRUG_ID
    );

    public DrugRecipeDaoImpl() {
        super(BuilderFactory.getDrugRecipeBuilder(), Table.DRUG_RECIPES_TABLE, Column.DRUG_RECIPES_TREATMENT_COURSE_ID);
    }

    @Override
    public void save(DrugRecipe entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_DRUG_RECIPE_QUERY);) {
            setParams(statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save drug recipe.", e);
        }
    }

    @Override
    public int saveAndGetId(DrugRecipe entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_DRUG_RECIPE_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            setParams(statement, entity);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Can't save drug recipe.", e);
        }
    }

    @Override
    public void update(DrugRecipe entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(UPDATE_DRUG_RECIPE_QUERY);) {
            setParams(statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update drug recipe.", e);
        }
    }

    private void setParams(PreparedStatement statement, DrugRecipe drugRecipe) throws SQLException {
        statement.setString(1, drugRecipe.getDescription());
        statement.setFloat(2, drugRecipe.getDose());
        statement.setInt(3, drugRecipe.getTreatmentCourseId());
        statement.setInt(4, drugRecipe.getDrugId());
    }
}
