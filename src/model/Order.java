package model;

import java.io.Serializable;
import java.util.Date;

import org.postgresql.util.PGmoney;

public class Order implements Serializable {
	
	@Override
	public String toString() {
		return "Order [product_id=" + product_id + ", quantity=" + quantity + ", order_date=" + order_date + ", price="
				+ price + ", transaction_id=" + transaction_id + ", order_id=" + order_id + "]";
	}

	private int product_id;
	private int quantity;
	private Date order_date;
	private double price;
	private int transaction_id;
	private int order_id;
	
	public Order(){
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
	
}
