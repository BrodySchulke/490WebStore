package model;

import java.io.Serializable;
import java.sql.Date;

//This is the Customer JavaBean 
public class Customer implements Serializable {
	
	private int customer_id;
	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", username=" + username + ", user_password=" + user_password
				+ ", email=" + email + ", first_name=" + first_name + ", last_name=" + last_name + ", date_joined="
				+ date_joined + "]";
	}

	private String username;
	private String user_password;
	private String email;
	private String first_name;
	private String last_name;
	private Date date_joined;
	
	public Customer(){
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String password) {
		this.user_password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Date getDate_joined() {
		return date_joined;
	}

	public void setDate_joined(Date date_joined) {
		this.date_joined = date_joined;
	}
	
}
