package com.epam.hospital.dao.builder.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBuilder implements EntityBuilder<User> {
    public User build(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt(Column.USER_ID));
        user.setUserRoleId(resultSet.getInt(Column.USER_ROLE_ID));
        user.setEmail(resultSet.getString(Column.USER_EMAIL));
        user.setFirstName(resultSet.getString(Column.USER_FIRS_NAME));
        user.setLastName(resultSet.getString(Column.USER_LAST_NAME));
        user.setHashedPassword(resultSet.getString(Column.USER_PASSWORD));
        user.setNumber(resultSet.getString(Column.USER_NUMBER));
        user.setIsBanned(resultSet.getBoolean(Column.USER_IS_BANNED));
        return user;
    }
}
