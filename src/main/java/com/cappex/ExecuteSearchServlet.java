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
import java.util.Collections;
import java.util.Comparator;

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
		private String priority;

        SearchResult(String name, String city, String state, String priority) {
            this.name = name;
            this.city = city;
            this.state = state;
            this.priority = priority;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");

		String searchTerm = null;
        if(!StringUtils.isEmpty(request.getParameter("param"))) {
			searchTerm = request.getParameter("param");
		}

        List<SearchResult> searchResults = new ArrayList<>();
        try {
			Connection connection = DriverManager.getConnection("jdbc:h2:" + DATABASE_DIRECTORY + "cappex-test-database");
            Statement statement = connection.createStatement();

            // Get results set from DB (Case insensitive)
            // Sort results by popularity assuming that "highest" popularity is a bigger number
			ResultSet resultSet = new CollegeDao().getCollegeData(searchTerm, connection);
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult(resultSet.getString("name"), resultSet.getString("city"), resultSet.getString("state"), resultSet.getString("popularity_level"));
                searchResults.add(searchResult);
            }

			Collections.sort(searchResults, new Comparator<SearchResult>() {
				@Override
				public int compare(SearchResult o1, SearchResult o2) {
					return Integer.parseInt(o1.priority) > Integer.parseInt(o2.priority) ? 1 : 0;
				}
			});

            String json = new Gson().toJson(searchResults);

            printWriter.write(json);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}