package org.s1n7ax.feedback.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.s1n7ax.feedback.entity.Customer;
import org.s1n7ax.feedback.entity.Feedback;
import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.s1n7ax.feedback.entity.Question;
import org.s1n7ax.feedback.entity.Rating;
import org.s1n7ax.feedback.repository.PurchaseHistoryRepository;
import org.s1n7ax.feedback.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PurchaseHistoryService handles services related to purchases
 */
@Service
public class PurchaseHistoryService {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PurchaseHistoryRepository purchaseHistoryRepo;

	@Autowired
	private QuestionRepository questionRepo;

	/**
	 * Returns list of purchases made by the customer
	 * 
	 * @param name name of the customer
	 * @return list of purchases
	 */
	public List<PurchaseHistory> getPurchaseHistoryList(String name) {
		return customerService.getPurchaseHistory(name);
	}

	/**
	 * Returns list of ratings of a seller
	 *
	 * @param id of the seller
	 * @return list of ratings
	 */
	public List<Rating> getRatingBySeller(Long sellerId) {

		Map<Long, RatingCount> ratings = new HashMap<>();

		for (Question q : questionRepo.findAll()) {
			RatingCount r = new RatingCount();
			r.setQuestion(q.getQuestion());

			ratings.put(q.getId(), r);
		}

		for (PurchaseHistory ph : purchaseHistoryRepo.findAll()) {
			if (ph.getProduct().getSeller().getId() == sellerId) {
				for (Feedback fb : ph.getFeedbackList()) {
					RatingCount r = ratings.get(fb.getQuestion().getId());
					r.addToTotal(fb.getRate());
				}
			}
		}

		List<Rating> list = new ArrayList<>();

		ratings.forEach((questionId, model) -> {
			list.add(new Rating(model.getQuestion(), model.getAverage()));
		});

		return list;
	}

	/**
	 * Returns purchase history object
	 * @param name name of the customer
	 * @param purchaseHistoryId id of the purchase
	 * @return purchase history object
	 */
	public PurchaseHistory getCustomerPurchase(String name, Long purchaseHistoryId) {

		if (!customerService.isCustomerAvailable(name))
			throw new NoSuchElementException();

		Customer customer = customerService.getCustomer(name);

		List<PurchaseHistory> purchaseHistorieList = customer.getPurchaseHistories();

		PurchaseHistory purchaseHistory = purchaseHistorieList.stream().filter(ph -> ph.getId() == purchaseHistoryId)
				.findFirst().orElse(null);

		return purchaseHistory;

	}

	/**
	 * Checks if the given customer has a purchase by given id
	 * 
	 * @param name name of the customer
	 * @param purchaseHistoryId id of the purchase
	 */
	public boolean hasCustomerPurchase(String name, Long purchaseHistoryId) {

		return getCustomerPurchase(name, purchaseHistoryId) != null;

	}
}

/**
 * RatingCount is only used for counting ratings
 * Class is defined internally because this will not be used elsewhere
 */
final class RatingCount {
	private String question;
	private int count;
	private int total;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void addToTotal(int value) {
		if (value == 0)
			return;

		total += value;
		count++;
	}

	public double getAverage() {
		if (total == 0)
			return 0;

		return (double) total / count;
	}
}
