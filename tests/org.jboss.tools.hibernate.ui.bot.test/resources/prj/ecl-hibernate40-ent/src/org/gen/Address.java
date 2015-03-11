package org.gen;

// Generated Mar 6, 2015 3:29:43 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Address generated by hbm2java
 */
public class Address implements java.io.Serializable {

	private short addressId;
	private City city;
	private String address;
	private String address2;
	private String district;
	private String postalCode;
	private String phone;
	private Date lastUpdate;
	private Set customers = new HashSet(0);
	private Set staffs = new HashSet(0);
	private Set staffs_1 = new HashSet(0);
	private Set stores = new HashSet(0);
	private Set stores_1 = new HashSet(0);
	private Set customers_1 = new HashSet(0);

	public Address() {
	}

	public Address(short addressId, City city, String address, String district,
			String phone, Date lastUpdate) {
		this.addressId = addressId;
		this.city = city;
		this.address = address;
		this.district = district;
		this.phone = phone;
		this.lastUpdate = lastUpdate;
	}

	public Address(short addressId, City city, String address, String address2,
			String district, String postalCode, String phone, Date lastUpdate,
			Set customers, Set staffs, Set staffs_1, Set stores, Set stores_1,
			Set customers_1) {
		this.addressId = addressId;
		this.city = city;
		this.address = address;
		this.address2 = address2;
		this.district = district;
		this.postalCode = postalCode;
		this.phone = phone;
		this.lastUpdate = lastUpdate;
		this.customers = customers;
		this.staffs = staffs;
		this.staffs_1 = staffs_1;
		this.stores = stores;
		this.stores_1 = stores_1;
		this.customers_1 = customers_1;
	}

	public short getAddressId() {
		return this.addressId;
	}

	public void setAddressId(short addressId) {
		this.addressId = addressId;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set getCustomers() {
		return this.customers;
	}

	public void setCustomers(Set customers) {
		this.customers = customers;
	}

	public Set getStaffs() {
		return this.staffs;
	}

	public void setStaffs(Set staffs) {
		this.staffs = staffs;
	}

	public Set getStaffs_1() {
		return this.staffs_1;
	}

	public void setStaffs_1(Set staffs_1) {
		this.staffs_1 = staffs_1;
	}

	public Set getStores() {
		return this.stores;
	}

	public void setStores(Set stores) {
		this.stores = stores;
	}

	public Set getStores_1() {
		return this.stores_1;
	}

	public void setStores_1(Set stores_1) {
		this.stores_1 = stores_1;
	}

	public Set getCustomers_1() {
		return this.customers_1;
	}

	public void setCustomers_1(Set customers_1) {
		this.customers_1 = customers_1;
	}

}