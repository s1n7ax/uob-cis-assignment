package org.s1n7ax.feedback.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.s1n7ax.feedback.entity.Customer;
import org.s1n7ax.feedback.entity.Feedback;
import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.s1n7ax.feedback.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FeedbackService
 */
@Service
public class FeedbackService {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FeedbackRepository feedbackRepo;

	/**
	 * Returns feedback made for the purchase by the customer
	 * 
	 * @param username          name of the customer
	 * @param purchaseHistoryId id of the purchase history whose feedback is
	 *                          required
	 * @return list of feedback
	 * @throws NoSuchElementException throws when customer or purchase history is
	 *                                not found
	 */
	public List<Feedback> getFeedbackList(String name, Long purchaseHistoryId) {

		Customer customer = customerService.getCustomer(name);

		PurchaseHistory purchaseHistory = customer.getPurchaseHistories().stream()
				.filter(history -> history.getId() == purchaseHistoryId).findFirst().orElse(null);

		// client doesn't have matching purchase order
		if (purchaseHistory == null) {
			throw new NoSuchElementException();
		}

		return purchaseHistory.getFeedbackList();

	}

	public void addFeedbackList(String name, Long purchaseHistoryId, List<Feedback> feedbackList) {

		List<Feedback> updatedFeedbackList = new ArrayList<>();

		feedbackList.forEach(feedback -> {

			Feedback orgFeedback = getFeedback(name, purchaseHistoryId, feedback.getQuestion().getId());

			// feedback not found with the given id
			// this will be ignored
			if (orgFeedback == null)
				return;

			// feedback ids are not equal
			// user is sending invalid data
			if (orgFeedback.getId() != feedback.getId())
				throw new NoSuchElementException();

			orgFeedback.setRate(feedback.getRate());
			updatedFeedbackList.add(orgFeedback);

		});

		feedbackRepo.saveAll(updatedFeedbackList);

	}

	public Feedback getFeedback(String name, Long purchaseHistoryId, Long questionId) {
		List<Feedback> feedbackList = getFeedbackList(name, purchaseHistoryId);

		return feedbackList.stream().filter(feed -> feed.getQuestion().getId() == questionId).findFirst().orElse(null);
	}
}
