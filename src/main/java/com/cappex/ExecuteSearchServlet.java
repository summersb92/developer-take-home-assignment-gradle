package com.cappex;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import org.apache.commons.lang3.StringUtils;
import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;

/**
 * This servlet is responsible for performing the college search.
 */

@WebServlet(urlPatterns = {"/execute-search"})
public class ExecuteSearchServlet extends HttpServlet {

    private final static String DATABASE_DIRECTORY = "./";

    private class SearchResult {
        private String name;
        private String city;
        private String state;

        SearchResult(String name, String city, String state) {
            this.name = name;
            this.city = city;
            this.state = state;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");

        String searchTerm = "";

        String parameterValue = request.getParameter("param");
        if (StringUtils.isNotBlank(parameterValue)) {
            searchTerm = StringUtils.trim(parameterValue);
        }

        List<SearchResult> searchResults = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:h2:" + DATABASE_DIRECTORY + "cappex-test-database");
            Statement statement = connection.createStatement()) {

            // Get results set from DB (Case insensitive)
            // Assumed "highest" priority is a bigger number
            ResultSet resultSet = statement.executeQuery("SELECT name, city, state, popularity_level FROM colleges where LOWER(name) LIKE LOWER('%" + searchTerm + "%') ORDER BY popularity_level desc, name");
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult(resultSet.getString("name"), resultSet.getString("city"), resultSet.getString("state"));
                searchResults.add(searchResult);
            }

            String json = new Gson().toJson(searchResults);

            printWriter.write(json);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}