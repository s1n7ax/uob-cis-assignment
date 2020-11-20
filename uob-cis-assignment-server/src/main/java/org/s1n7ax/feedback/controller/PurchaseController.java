package org.s1n7ax.feedback.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.s1n7ax.feedback.entity.Feedback;
import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.PurchaseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * QuestionResource
 */
@RestController
public class PurchaseController {
	@Autowired
	private PurchaseHistoryService purchaseHistoryService;

	@Autowired
	private FeedbackService feedbackService;

	/**
	 * Returns list of purchase history records of the current user
	 * 
	 * @return list of purchase history records
	 */
	@GetMapping("/purchase/history")
	public ResponseEntity<?> getPurchaseHistory() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<PurchaseHistory> history;

		try {

			history = purchaseHistoryService.getPurchaseHistoryList(auth.getName());

		} catch (NoSuchElementException ex) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return ResponseEntity.ok(history);

	}

	/**
	 * Add feedback for a purchased item
	 *
	 * @param purchaseHistoryId id of the purchase record
	 * @param feedbackList list of feedback
	 */
	@PostMapping("/purchase/feedback")
	public ResponseEntity<?> addFeedback(@RequestParam Long purchaseHistoryId,
			@RequestBody List<Feedback> feedbackList) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		try {

			feedbackService.addFeedbackList(auth.getName(), purchaseHistoryId, feedbackList);

		} catch (NoSuchElementException ex) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return ResponseEntity.ok(null);

	}

	/**
	 * Get feedbacks for purchased item
	 *
	 * @param purchaseHistoryId it of the purchase item
	 */
	@GetMapping("/purchase/feedback")
	public ResponseEntity<?> getFeedback(@RequestParam Long purchaseHistoryId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Feedback> feedbackList;

		try {

			feedbackList = feedbackService.getFeedbackList(auth.getName(), purchaseHistoryId);

		} catch (NoSuchElementException ex) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return ResponseEntity.ok(feedbackList);

	}
}
