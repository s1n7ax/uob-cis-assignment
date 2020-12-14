package org.s1n7ax.feedback.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Seller
 */
@Entity
public class Seller {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	public Seller() {
	}

	public Seller(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

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
}
