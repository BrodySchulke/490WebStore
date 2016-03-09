package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.CustomerDB;
import db.MovieDB;
import db.OrderDB;
import db.TransactionDB;
import model.Customer;
import model.Movie;
import model.Order;
import model.Transaction;

/**
 * Servlet implementation class CustomerController
 */
@WebServlet(
		name = "CustomerServlet",
		description = "A servlet for handling customers",
		urlPatterns = { "/signup/customer/*", "/home/customer/*", "/customers/logout" }
		)
//@WebServlet("/customerController")
public class customerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public customerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI();
		String url = "";
		if(requestURI.endsWith("redirect")){
			url = loginOrSignup(request);
		} else if (requestURI.endsWith("register")) {
			url = registerCustomer(request);
		} else if (requestURI.endsWith("logout")) {
			clearCustomerSession(request);
			url = "../home/loginForm.html";
		} else {
			url = "../../customers/registerError.jsp";
		}
		response.sendRedirect(url);
	}
	
	private String loginOrSignup(HttpServletRequest request){
		String url = "";
		String username = request.getParameter("username");
		String user_password = request.getParameter("password");
		Customer flag = CustomerDB.checkValidUser(username, user_password);
		if (flag != null) {
			makeCustomerSession(request, flag);
			url = "../../movies/listMovies.jsp";
		} else {
			url = "../../signup/signUpForm.html";
		}
		return url;
	}
	
	
	private String registerCustomer(HttpServletRequest request){
 		String url = "";
		String username = request.getParameter("username");
		String user_password = request.getParameter("passwd");
		String email = request.getParameter("email");
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		int flag = 0;
		flag = CustomerDB.checkUserAvail(username, email);
		if(flag > 0){
			url = "../../customers/registerError.jsp";
		}else{
			Customer customer = new Customer();
			customer.setUsername(username);
			customer.setUser_password(user_password);
			customer.setEmail(email);
			customer.setFirst_name(first_name);
			customer.setLast_name(last_name);
			if(CustomerDB.insertCustomer(customer)){
				TransactionDB.setUpTransactionForNewCustomer(customer);
				url = "../../home/loginForm.html";
			}
		}
		
		return url;
	}
	
	private static void makeCustomerSession(HttpServletRequest request, Customer customer) {
		HttpSession userSession = request.getSession();
		userSession.setAttribute("narrow","");
		userSession.setAttribute("search_value", "");
		userSession.setAttribute("filter_value", new String[2]);
		userSession.setAttribute("sort_value", "");
		Transaction transaction = TransactionDB.initializeCart(customer);
		Map<Movie, Order> cart = new HashMap<>();
		OrderDB.getOrdersAssociatedWithCustomer(cart, transaction);
		userSession.setAttribute("customer", customer);
		userSession.setAttribute("transaction", transaction);
		userSession.setAttribute("cart", cart);

	}
	
	@SuppressWarnings("unchecked")
	private static void clearCustomerSession(HttpServletRequest request) {
		HttpSession userSession = request.getSession();
		Map<Movie, Order> grabOrdersToWriteBack = (Map<Movie, Order>)userSession.getAttribute("cart");
	    OrderDB.clearUserOrders((Transaction)userSession.getAttribute("transaction"));
	    grabOrdersToWriteBack.entrySet().forEach(entry -> {
	    	OrderDB.writeBackUserOrders(grabOrdersToWriteBack.get(entry.getKey()));
	    });
	    Transaction t = (Transaction)userSession.getAttribute("transaction");
	    TransactionDB.writeTransactionToDB(t);
		if (userSession != null) {
			userSession.invalidate();
		}
		System.out.println("session cleared");
	}
	
}
