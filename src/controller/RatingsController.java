package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.RatingDB;
import model.Customer;
import model.Movie;

/**
 * Servlet implementation class Ratings Controller
 */
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(
		name = "RatingServlet",
		description = "A servlet for handling ratings",
		urlPatterns = { 
				"/ratings/star1", 
				"/ratings/star2", 
				"/ratings/star3",
				"/ratings/star4",
				"/ratings/star5" 
				}
		)
public class RatingsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RatingsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession userSession = request.getSession();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
//		System.out.println(requestURI);
		if (requestURI.endsWith("star1")) {
			updateRatings(1, request);
		} else if (requestURI.endsWith("star2")) {
			updateRatings(2, request);
		} else if (requestURI.endsWith("star3")) {
			updateRatings(3, request);
		} else if (requestURI.endsWith("star4")) {
			updateRatings(4, request);
		} else if (requestURI.endsWith("star5")) {
			updateRatings(5, request);
		} else {
			response.sendRedirect("../registerError.jsp");
		}
	}
	
	private void updateRatings(int rating, HttpServletRequest request) throws IOException {
		HttpSession userSession = request.getSession();
		String body = request.getReader().lines()
			    .reduce("", (accumulator, actual) -> accumulator + actual);
	    String[] movieMap = body.split(";");
	    Map<String, String> mapper = new HashMap<>();
	    for (String s : movieMap) {
	    	String[] mapOne = s.split("=");
	    	mapper.put(mapOne[0], mapOne[1]);
	    }
	    Movie m = new Movie().createMovie(mapper);
	    Customer c = (Customer)userSession.getAttribute("customer");
	    RatingDB.updateRating(rating, m, c);
	}
	
}