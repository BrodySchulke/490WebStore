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
		urlPatterns = { "/signup/customer/*", "/home/customer/*" }
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
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI();
		String url = "";
		//System.out.println(requestURI);
		if(requestURI.endsWith("redirect")){
			url = loginOrSignup(request);
		} else if (requestURI.endsWith("register")) {
			url = registerCustomer(request);
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
//		System.out.println(userSession.getId());
		Transaction transaction = TransactionDB.initializeCart(customer);
		Map<Movie, Order> cart = new HashMap<>();
		OrderDB.getOrdersAssociatedWithCustomer(cart, transaction);
		userSession.setAttribute("customer", customer);
		userSession.setAttribute("transaction", transaction);
		userSession.setAttribute("cart", cart);
//		Enumeration e = userSession.getAttributeNames();
//		while (e.hasMoreElements()) {
//			String elem = (String)e.nextElement();
//			System.out.println(elem + ":" + userSession.getAttribute(elem));
//		}
/*		ArrayList<Order> cartExistsAndIsOpen = TransactionDB.checkForOpenCart(customer);
		if (cartExistsAndIsOpen != null) {
			userSession.setAttribute("OrderList", cartExistsAndIsOpen);
		} else {
			userSession.setAttribute("OrderList", new ArrayList<Order>());
		}*/
	}
	
//	
//	private String modifyCustomer(HttpServletRequest request){
//		String url = "";
//		String Customername = request.getParameter("Customername");
//		Customer Customer = new Customer();
//		Customer.setCustomername(Customername);
//		Customer.setPasswd(request.getParameter("passwd"));
//		Customer.setName(request.getParameter("name"));
//		Customer.setEmail(request.getParameter("email"));
//		int flag = 0;
//		flag = CustomerDB.modifyCustomer(Customer);
//		if(flag > 0){
//			url = "/listCustomers.jsp";
//		}
//		return url;
//	}
//	
//	private String deleteCustomer(HttpServletRequest request){
//		String url = "";
//		String Customername = request.getParameter("Customername");
//		int flag = 0;
//		flag = CustomerDB.deleteCustomer(Customername);
//		if(flag > 0){
//			url = "/listCustomers.jsp";
//		}
//		return url;
//	}
}
