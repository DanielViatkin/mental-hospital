package com.epam.hospital.dao.builder;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityBuilder<T> {
    T build(ResultSet resultSet) throws SQLException;
}
