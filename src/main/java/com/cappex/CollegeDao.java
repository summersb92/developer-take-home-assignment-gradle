package com.cappex;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * This class is responsible for obtaining college data.
 */
public class CollegeDao {
	public ResultSet getCollegeData(String searchTerm, Connection conn) throws Exception {
		Statement statement = conn.createStatement();
		return statement.executeQuery("SELECT name, city, state, popularity_level FROM colleges where LOWER(name) LIKE LOWER('%" + searchTerm + "%')");
	}
}