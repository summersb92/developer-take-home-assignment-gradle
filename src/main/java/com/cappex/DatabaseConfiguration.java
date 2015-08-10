package com.cappex;

import org.h2.tools.DeleteDbFiles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * This class is responsible for setting up the in-memory H2 database.
 *
 * PLEASE DO NOT MODIFY THIS FILE
 */

public class DatabaseConfiguration {

    private final static String DATABASE_DIRECTORY = "./";

    public static void main(String[] args) throws Exception {
        DeleteDbFiles.execute(DATABASE_DIRECTORY, "cappex-test-database", true);

        Class.forName("org.h2.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:h2:" + DATABASE_DIRECTORY + "cappex-test-database");
            Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE colleges(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), city VARCHAR(255), state VARCHAR(2), zip CHAR(5), popularity_level INT)");
            statement.execute("INSERT INTO colleges(name, city, state, zip, popularity_level) values('University of Illinois at Chicago', 'Chicago', 'IL', '60607', 4)");
            statement.execute("INSERT INTO colleges(name, city, state, zip, popularity_level) values('Northern Illinois University', 'DeKalb', 'IL', '60115', 2)");
            statement.execute("INSERT INTO colleges(name, city, state, zip, popularity_level) values('Lake Forest College', 'Lake Forest', 'IL', '60045', 3)");
            statement.execute("INSERT INTO colleges(name, city, state, zip, popularity_level) values('Anderson University', 'Anderson', 'IN', '46012', 1)");

            statement.execute("CREATE TABLE majors(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255))");
            statement.execute("INSERT INTO majors(name) values('Computer Science')");
            statement.execute("INSERT INTO majors(name) values('Business Administration & Management')");
            statement.execute("INSERT INTO majors(name) values('Biology')");
            statement.execute("INSERT INTO majors(name) values('Criminal Justice')");
            statement.execute("INSERT INTO majors(name) values('Accounting')");
            statement.execute("INSERT INTO majors(name) values('History')");
            statement.execute("INSERT INTO majors(name) values('Political Science')");

            statement.execute("CREATE TABLE college_majors(id INT AUTO_INCREMENT PRIMARY KEY, college_id INT, major_id INT)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(1, 1)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(1, 3)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(1, 4)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(1, 5)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(2, 2)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(2, 3)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(2, 6)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(3, 1)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(3, 2)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(3, 3)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(3, 4)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(4, 2)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(4, 3)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(4, 5)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(4, 6)");
            statement.execute("INSERT INTO college_majors(college_id, major_id) values(4, 7)");

            ResultSet resultSet = statement.executeQuery("SELECT id, name, city, state, zip, popularity_level FROM colleges");
            System.out.println("Printing out contents of the colleges table:");
            while (resultSet.next()) {
                System.out.println("id: " + resultSet.getInt("id"));
                System.out.println("name: " + resultSet.getString("name"));
                System.out.println("city: " + resultSet.getString("city"));
                System.out.println("state: " + resultSet.getString("state"));
                System.out.println("zip: " + resultSet.getString("zip"));
                System.out.println("popularity_level: " + resultSet.getInt("popularity_level") + "\n");
            }

            resultSet = statement.executeQuery("SELECT id, name FROM majors");
            System.out.println("\nPrinting out contents of the majors table:");
            while (resultSet.next()) {
                System.out.println("id: " + resultSet.getInt("id"));
                System.out.println("name: " + resultSet.getString("name") + "\n");
            }

            resultSet = statement.executeQuery("SELECT id, college_id, major_id FROM college_majors");
            System.out.println("\nPrinting out contents of the college_majors table:");
            while (resultSet.next()) {
                System.out.println("id: " + resultSet.getInt("id"));
                System.out.println("college_id: " + resultSet.getString("college_id"));
                System.out.println("major_id: " + resultSet.getString("major_id") + "\n");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
