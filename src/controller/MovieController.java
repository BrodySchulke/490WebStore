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
		urlPatterns = { "/movies/show_next", "/movies/show_previous", "/movies/modify" }
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
		if (requestURI.endsWith("show_next")) {
			url = "../movies/listMovies.jsp";
			MovieDB.viewMovies(20);
		} else if (requestURI.endsWith("show_previous")){
			url = "../movies/listMovies.jsp";
			MovieDB.viewMovies(-20);
		} else if (requestURI.endsWith("show_details")){
			url = "../movies/movieDetail.jsp";
			MovieDB.showAMovie(Integer.parseInt(request.getParameter("movie_view")));
		} else {
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
			System.out.println("cowabunga!");
		}
		response.sendRedirect("/listMovies.jsp");
	}
}
