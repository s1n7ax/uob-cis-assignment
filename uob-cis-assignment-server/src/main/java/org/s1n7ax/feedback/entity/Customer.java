package org.s1n7ax.feedback.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Customer
 */
@Entity
public class Customer {

	@Id
	private String name;

	@OneToMany
	private List<PurchaseHistory> purchaseHistories;

	public Customer() {
	}

	public Customer(String name, List<PurchaseHistory> purchaseHistories) {
		this.name = name;
		this.purchaseHistories = purchaseHistories;
	}

	public String getName() {
		return name;
	}

	public List<PurchaseHistory> getPurchaseHistories() {
		return purchaseHistories;
	}

	public void setPurchaseHistories(List<PurchaseHistory> purchaseHistories) {
		this.purchaseHistories = purchaseHistories;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", purchaseHistories=" + purchaseHistories + "]";
	}

}