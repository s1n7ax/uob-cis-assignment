package org.s1n7ax.feedback.controller;

import java.util.List;

import org.s1n7ax.feedback.entity.Rating;
import org.s1n7ax.feedback.service.PurchaseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RatingController handles ratings
 */
@RestController
public class RatingController {

	@Autowired
	private PurchaseHistoryService service;

	/**
	 * Get ratings of a seller
	 *
	 * @param sellerId id of seller to get rating details
	 */
	@GetMapping("/rating")
	public List<Rating> getRatings(@RequestParam Long sellerId) {
		return service.getRatingBySeller(sellerId);
	}
}
