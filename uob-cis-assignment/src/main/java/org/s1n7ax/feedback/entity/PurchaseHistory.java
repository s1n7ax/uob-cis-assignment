package org.s1n7ax.feedback.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Order
 */
@Entity
public class PurchaseHistory {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Product product;

	@OneToMany
	private List<Feedback> feedbackList;

	public PurchaseHistory() {
	}

	public PurchaseHistory(Product product, List<Feedback> feedbackList) {
		this.product = product;
		this.feedbackList = feedbackList;
	}

	@Override
	public String toString() {
		return "PurchaseHistory [feedbackList=" + feedbackList + ", id=" + id + ", product=" + product + "]";
	}

	public Long getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<Feedback> feedbackList) {
		this.feedbackList = feedbackList;
	}

}
