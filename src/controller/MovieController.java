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
		urlPatterns = { "/movies/show_next", "/movies/show_previous", "/movies/modify", "/movies/sort", "/movies/search", "/movies/create" }
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
		System.out.println(requestURI);
		String url = "";
		String sort_value = (String)request.getParameter("sort_value");

		String search_value = (String)request.getParameter("search_value");
		if (sort_value == null) {
			sort_value = (String)request.getSession().getAttribute("sort_value");
		}
		if (search_value == null) {
			search_value = (String)request.getSession().getAttribute("search_value");
			System.out.println("search value was null");
			System.out.println(search_value);
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
			System.out.println("the actual search_value in movie controller " + search_value);
			HttpSession userSession = request.getSession();
			userSession.setAttribute("narrow", "search");
			updateSessionSearchValue(userSession, search_value);
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
			MovieDB.updateMovie(request.getParameter("product_id"),
					request.getParameter("price"),
					request.getParameter("inventory"));
		} else if (requestURI.endsWith("create")) {
			if (!MovieDB.createMovie(request.getParameter("product_id"),
					request.getParameter("price"),
					request.getParameter("inventory"),
					request.getParameter("title"),
					request.getParameter("genre"),
					request.getParameter("year"),
					request.getParameter("length"),
					request.getParameter("actor"),
					request.getParameter("actress"),
					request.getParameter("director"))) {
				response.sendRedirect("../movies/createMovieError.jsp");
				return;
			}
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
}
