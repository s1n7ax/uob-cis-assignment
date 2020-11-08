package org.s1n7ax.feedback.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Seller
 */
@Entity
public class Seller {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

//	@OneToMany
//	private List<Product> productList;

	public Seller() {
	}

	public Seller(String name) {
		this.name = name;
	}

//	public Seller(String name, List<Product> productList) {
//		this.name = name;
//		this.productList = productList;
//	}

	public Long getId() {
		return id;
	}

//	public List<Product> getProductList() {
//		return productList;
//	}
//
//	public void setProductList(List<Product> productList) {
//		this.productList = productList;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", name=" + name + "]";
	}

//	@Override
//	public String toString() {
//		return "Seller [id=" + id + ", name=" + name + ", productList=" + productList + "]";
//	}

	
}
