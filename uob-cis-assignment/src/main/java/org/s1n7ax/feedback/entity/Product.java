package org.s1n7ax.feedback.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Product
 */
@Entity
public class Product {

	@Id
	@GeneratedValue
	private Long id;
	private String productName;
	private double price;
	@OneToOne
	private Seller seller;

	public Product() {
	}

	public Product(String productName, double price) {
		this.productName = productName;
		this.price = price;
	}

	public Product(String productName, double price, Seller seller) {
		this.productName = productName;
		this.price = price;
		this.seller = seller;
	}

	public Long getId() {
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", price=" + price + ", productName=" + productName + "]";
	}

}
