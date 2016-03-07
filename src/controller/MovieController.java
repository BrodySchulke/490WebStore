package controller;

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

import db.MovieDB;

/**
 * Servlet implementation class CustomerController
 */
@WebServlet(
		name = "MovieServlet",
		description = "A servlet for handling movies",
		urlPatterns = { "/movies/show_next", "/movies/show_previous", "/movies/modify", "/movies/sort", "/movies/search",
				"/movies/filter_genre", "/movies/filter_years", "/movies/filter_price", "/movies/filter_rating"}
		)
public class MovieController extends HttpServlet {
	private static final long serialVersionUID = 1L;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url = "";
		String sort_value = (String)request.getParameter("sort_value");
		String search_value = (String)request.getParameter("search_value");
		String filter_value = (String)request.getParameter("filter");
		Enumeration<String> parameterNames = request.getParameterNames();
	    while (parameterNames.hasMoreElements()) {
	    	String paramName = parameterNames.nextElement();
	    	System.out.println(paramName);
	    	System.out.println((String)request.getParameter(paramName));
	    }
		if (sort_value == null) {
			sort_value = (String)request.getSession().getAttribute("sort_value");
		}
		if (search_value == null) {
			search_value = (String)request.getSession().getAttribute("search_value");
		}
		if (requestURI.endsWith("show_next")) {
			url = "../movies/listMovies.jsp";
			MovieDB.setOffset(MovieDB.getOffset() + 20);
		} else if (requestURI.endsWith("show_previous")) {
			url = "../movies/listMovies.jsp";
			MovieDB.setOffset(MovieDB.getOffset() -20);
		} else if (requestURI.endsWith("sort")) {
			HttpSession userSession = request.getSession();
			userSession.setAttribute("narrow", "sort");
			updateSessionSortValue(userSession, sort_value);
			url = "../movies/listMovies.jsp";
		} else if (requestURI.endsWith("search")) {
			HttpSession userSession = request.getSession();
			userSession.setAttribute("narrow", "search");
			updateSessionSearchValue(userSession, search_value);
			url ="../movies/listMovies.jsp";
		} 
		
		else if (requestURI.endsWith("filter_genre")) {
			HttpSession userSession = request.getSession();
			userSession.setAttribute("narrow", "filter");
			updateSessionFilter(userSession, "genre", filter_value);
			url ="../movies/listMovies.jsp";
		} 
		else if (requestURI.endsWith("filter_years")) {
			HttpSession userSession = request.getSession();
			userSession.setAttribute("narrow", "filter");
			updateSessionFilter(userSession, "years", filter_value);
			url ="../movies/listMovies.jsp";
		}
		else if (requestURI.endsWith("filter_price")) {
			HttpSession userSession = request.getSession();
			userSession.setAttribute("narrow", "filter");
			updateSessionFilter(userSession, "price", filter_value);
			url ="../movies/listMovies.jsp";
		} else if(requestURI.endsWith("filter_rating")) {
			HttpSession userSession = request.getSession();
			userSession.setAttribute("narrow", "filter");
			updateSessionFilter(userSession, "rating", filter_value);
			url ="../movies/listMovies.jsp";
		}
		else {
			url = "../movies/moviesError.jsp";
		}
		response.sendRedirect(url);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url = "";
		String body = request.getReader().lines()
			    .reduce("", (accumulator, actual) -> accumulator + actual);
		System.out.println(body);
		if (requestURI.endsWith("modify")) {
			MovieDB.updateMovie(request.getParameter("product_id"), request.getParameter("price"), request.getParameter("inventory"));
		}
		response.sendRedirect("../movies/listMovies.jsp");
	}
	
	private void updateSessionSortValue(HttpSession userSession, String sort_value) {
		if (!sort_value.equals(userSession.getAttribute("sort_value"))) {
			MovieDB.setOffset(0);
		}
		userSession.setAttribute("sort_value", sort_value);
	}
	
	private void updateSessionSearchValue(HttpSession userSession, String search_value) {
		if (!search_value.equals(userSession.getAttribute("search_value"))) {
			MovieDB.setOffset(0);
		}
		userSession.setAttribute("search_value", search_value);
	}
	
	private void updateSessionFilter(HttpSession userSession, String filterOn, String filter_value) {
		String[] filterMap = new String[2];
		filterMap[0] = filterOn;
		filterMap[1] = filter_value;
		userSession.setAttribute("filter_value", filterMap);
		MovieDB.setOffset(0);
	}
	
	
}
