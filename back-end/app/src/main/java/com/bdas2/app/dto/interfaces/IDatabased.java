package com.bdas2.app.dto.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDatabased<T> {
    T fetchMe(ResultSet data) throws SQLException;
}
