package org.s1n7ax.feedback.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.s1n7ax.feedback.entity.Customer;
import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PurchaseHistoryService
 */
@Service
public class PurchaseHistoryService {

	@Autowired
	private CustomerService customerService;

	/**
	 * Returns list of purchases made by the customer
	 * 
	 * @param name name of the customer
	 * @return list of purchases
	 */
	public List<PurchaseHistory> getPurchaseHistoryList(String name) {
		return customerService.getPurchaseHistory(name);
	}

	public PurchaseHistory getCustomerPurchase(String name, Long purchaseHistoryId) {

		if (!customerService.isCustomerAvailable(name))
			throw new NoSuchElementException();

		Customer customer = customerService.getCustomer(name);

		List<PurchaseHistory> purchaseHistorieList = customer.getPurchaseHistories();

		PurchaseHistory purchaseHistory = purchaseHistorieList.stream().filter(ph -> ph.getId() == purchaseHistoryId)
				.findFirst().orElse(null);

		return purchaseHistory;

	}

	public boolean hasCustomerPurchase(String name, Long purchaseHistoryId) {

		return getCustomerPurchase(name, purchaseHistoryId) != null;

	}
}
