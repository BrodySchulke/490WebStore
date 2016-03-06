package controller;

import java.io.IOException;
import java.util.Enumeration;

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
		urlPatterns = { "/movies/show_next", "/movies/show_previous", "/movies/modify", "/movies/sort", "/movies/search" }
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
		//if request does not have a sort value
		if (sort_value == null) {
			sort_value = (String)request.getSession().getAttribute("sort_value");
		}
		if (requestURI.endsWith("show_next")) {
			url = "../movies/listMovies.jsp";
			MovieDB.setOffset(MovieDB.getOffset() + 20);
		} else if (requestURI.endsWith("show_previous")) {
			url = "../movies/listMovies.jsp";
			MovieDB.setOffset(MovieDB.getOffset() -20);
//		}
//		else if (requestURI.endsWith("show_details")) {
//			url = "../movies/movieDetail.jsp";
//			MovieDB.showAMovie(Integer.parseInt(request.getParameter("movie_view")));
		} else if (requestURI.endsWith("sort")) {
			HttpSession userSession = request.getSession();
			userSession.setAttribute("narrow", "sort");
			updateSessionSortValue(userSession, sort_value);
			url = "../movies/listMovies.jsp";
		} else if (requestURI.endsWith("search")) {
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
}
