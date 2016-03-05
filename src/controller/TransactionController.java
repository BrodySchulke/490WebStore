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

import db.MovieDB;
import db.OrderDB;
import db.RatingDB;
import db.TransactionDB;
import model.Customer;
import model.Movie;
import model.Order;
import model.Transaction;

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
		name = "TransactionServlet",
		description = "A servlet for handling transactions",
		urlPatterns = { "/transactions/purchase"
				}
		)
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionController() {
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
		
		if (requestURI.endsWith("purchase")) {
			updateCartInventoryCounts(request, response);
				//purchase successful
		} else {
			
		}
	}
	
	private void updateCartInventoryCounts(HttpServletRequest request, HttpServletResponse response) throws IOException {
		purchaseSyncDB(request);
		HttpSession userSession = request.getSession();
		Map<Movie, Order> cart = (Map<Movie, Order>)userSession.getAttribute("cart");
		boolean purchaseStatus = true;
		for (Map.Entry e : cart.entrySet()) {	
			Movie m = (Movie)e.getKey();
			Order o = (Order)cart.get(e.getKey());
			if (o.getQuantity() > m.getInventory()) {
				purchaseStatus = false;
				response.getWriter().append(m.getTitle() + " only " + m.getInventory() + " in stock");
			}
		}
		if (purchaseStatus) {
			completePurchase(userSession);
			response.getWriter().append("purchase successful!");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void purchaseSyncDB(HttpServletRequest request) {
		HttpSession userSession = request.getSession();
//		System.out.println(request.getSession().getId());
	    Map<Movie, Order> grabOrdersToWriteBack = (Map<Movie, Order>)userSession.getAttribute("cart");
	    OrderDB.clearUserOrders((Transaction)userSession.getAttribute("transaction"));
	    grabOrdersToWriteBack.entrySet().forEach(entry -> {
	    	OrderDB.writeBackUserOrders(grabOrdersToWriteBack.get(entry.getKey()));
	    });
	    Transaction t = (Transaction)userSession.getAttribute("transaction");
	    TransactionDB.writeTransactionToDB(t);
	    writeDBBackToSession(userSession, (Customer)userSession.getAttribute("customer"));
	}
	
	//after syncing with db, re-update session
	private void writeDBBackToSession(HttpSession userSession, Customer customer) {
		Transaction transaction = TransactionDB.initializeCart(customer);
		Map<Movie, Order> cart = new HashMap<>();
		OrderDB.getOrdersAssociatedWithCustomer(cart, transaction);
		userSession.setAttribute("customer", customer);
		userSession.setAttribute("transaction", transaction);
		userSession.setAttribute("cart", cart);	
	}
	
	private void completePurchase(HttpSession userSession) {
		Map<Movie, Order> cart = (Map<Movie, Order>)userSession.getAttribute("cart");
		Transaction trans = (Transaction)userSession.getAttribute("transaction");
		Customer customer = (Customer)userSession.getAttribute("customer");
		OrderDB.purchaseCloseOrders(cart, trans);
		TransactionDB.closeTransaction(trans);
		TransactionDB.setUpTransactionForNewCustomer(customer);
		writeDBBackToSession(userSession, customer);
	}
	
}	