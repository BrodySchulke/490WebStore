package _webstore;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CustomerController
 */
@WebServlet("/CustomerController")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerController() {
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
//		String requestURI = request.getRequestURI();
//		System.out.println(requestURI); 
//		String url = "";
//		if(requestURI.endsWith("register")){
//			url = registerCustomer(request);
//		}else if(requestURI.endsWith("modify")){
//			url = modifyCustomer(request);
//		}else if(requestURI.endsWith("delete")){
//			url = deleteCustomer(request);
//		}
		
		response.sendRedirect("/listCustomers.jsp");
	}
	
//	private String registerCustomer(HttpServletRequest request){
//		String url = "";
//		String username = request.getParameter("username");
//		String password = request.getParameter("user_password");
//		String name = request.getParameter("name");
//		String email = request.getParameter("email");
//		
//		int flag = 0;
//		flag = CustomerDB.checkCustomerAvail(Customername, email);
//		if(flag > 0){
//			url = "/registerError.jsp";
//		}else{
//			Customer Customer = new Customer();
//			
//			Customer.setCustomername(Customername);
//			Customer.setPasswd(passwd);
//			Customer.setName(name);
//			Customer.setEmail(email);
//			if(CustomerDB.insertCustomer(Customer)){
//				url = "/listCustomers.jsp";
//			}
//		}
//		return url;
//	}
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
