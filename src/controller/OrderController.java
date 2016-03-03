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

import db.OrderDB;
import model.Movie;
import model.Order;
import model.Transaction;

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
		String requestURI = request.getRequestURI();
		if (requestURI.endsWith("cart")) {
			updateSession(request);
		} else if (requestURI.endsWith("sync")) {
			syncDatabase(request);
		} else {
			response.sendRedirect("../registerError.jsp");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void syncDatabase(HttpServletRequest request) {
		HttpSession userSession = request.getSession();
		System.out.println("SYNCING DB");
		System.out.println(request.getSession().getId());
	    Map<Movie, Order> grabOrdersToWriteBack = (Map<Movie, Order>)userSession.getAttribute("cart");
	    grabOrdersToWriteBack.entrySet().forEach(entry -> {
	    	OrderDB.writeBackUserOrders(grabOrdersToWriteBack.get(entry.getKey()));
	    });
	}
	
	@SuppressWarnings("unchecked")
	private void updateSession(HttpServletRequest request) throws IOException {
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
	    Order o = null;
	    Map<Movie, Order> grabOrder = (Map<Movie, Order>)userSession.getAttribute("cart");
	    if (orderExistsOnThisMovie(m, userSession)) {
	    	o = grabOrder.get(m);
	    	o.setQuantity(o.getQuantity() + 1);
	    	o.setPrice(o.getPrice() + m.getPrice());
	    } else {
	    	o = new Order();
	    	o.setTransaction_id(((Transaction)userSession.getAttribute("transaction")).getTransaction_id());
	    	o.setProduct_id(m.getProduct_id());
	    	o.setQuantity(1);
	    	o.setPrice(m.getPrice());
	    	grabOrder.put(m, o);
	    }
	    Enumeration<String> e = userSession.getAttributeNames();
	    while (e.hasMoreElements()) {
	    	String elem = (String)e.nextElement();
	    	System.out.println(elem + ":" + userSession.getAttribute(elem));
	    }
	}
	
	@SuppressWarnings("unchecked")
	private boolean orderExistsOnThisMovie(Movie m, HttpSession userSession) {
		if (userSession.getAttribute("cart") != null && userSession.getAttribute("cart") instanceof Map<?, ?>) {
			if (((Map<Movie, Order>)userSession.getAttribute("cart")).containsKey(m)) {
				return true;
			}
		}
		return false;
	}
}
