package org.pregen.hsqldb;

// Generated Mar 30, 2012 12:35:44 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Payments generated by hbm2java
 */
public class Payments implements java.io.Serializable {

	private Integer paymentnumber;
	private Customers customers;
	private double amount;
	private String checknumber;
	private Date paymentdate;

	public Payments() {
	}

	public Payments(Customers customers, double amount, String checknumber,
			Date paymentdate) {
		this.customers = customers;
		this.amount = amount;
		this.checknumber = checknumber;
		this.paymentdate = paymentdate;
	}

	public Integer getPaymentnumber() {
		return this.paymentnumber;
	}

	public void setPaymentnumber(Integer paymentnumber) {
		this.paymentnumber = paymentnumber;
	}

	public Customers getCustomers() {
		return this.customers;
	}

	public void setCustomers(Customers customers) {
		this.customers = customers;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getChecknumber() {
		return this.checknumber;
	}

	public void setChecknumber(String checknumber) {
		this.checknumber = checknumber;
	}

	public Date getPaymentdate() {
		return this.paymentdate;
	}

	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}

}
