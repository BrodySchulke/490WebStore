package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CustomerController
 */
@WebServlet(
		name = "OrderServlet",
		description = "A servlet for handling orders",
		urlPatterns = { "/orders/sync", "/orders/cart" }
		)
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderController() {
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
		// need to do a lot of work on initializing usersession
		HttpSession userSession = request.getSession();
		String body = request.getReader().lines()
			    .reduce("", (accumulator, actual) -> accumulator + actual);
	    String[] movieMap = body.split(";");
	    for (String s : movieMap) {
	    	String[] mapAttribute = s.split("=");
	    	userSession.setAttribute(mapAttribute[0], mapAttribute[1]);
	    }
	    Enumeration e = userSession.getAttributeNames();
	    while (e.hasMoreElements()) {
	    	String elem = (String)e.nextElement();
	    	System.out.println(elem + ":" + userSession.getAttribute(elem));
	    }
		
	}
}
