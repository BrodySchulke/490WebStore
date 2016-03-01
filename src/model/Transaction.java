package model;

import java.io.Serializable;
import java.util.Date;

import org.postgresql.util.PGmoney;

public class Transaction implements Serializable {
	private int user_id;
	private boolean status;
	private double total_price;
	private Date close_date;
	private int transaction_id;
	public int getUser_id() {
		return user_id;
	}
	@Override
	public String toString() {
		return "Transaction [user_id=" + user_id + ", status=" + status + ", total_price=" + total_price
				+ ", close_date=" + close_date + ", transaction_id=" + transaction_id + "]";
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public double getTotal_price() {
		return total_price;
	}
	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}
	public Date getClose_date() {
		return close_date;
	}
	public void setClose_date(Date close_date) {
		this.close_date = close_date;
	}
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	
}
