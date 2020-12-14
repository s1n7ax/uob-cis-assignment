package org.s1n7ax.feedback.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.s1n7ax.feedback.entity.Customer;
import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.s1n7ax.feedback.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClientService
 */
@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	/**
	 * Return all the purchase history records
	 * 
	 * @param name name of the customer to get purchase history records of
	 * @return purchase history list of the customer
	 * @throws NoSuchElementException throws when customer is not found
	 */
	public List<PurchaseHistory> getPurchaseHistory(String name) {
		if (!isCustomerAvailable(name))
			throw new NoSuchElementException();

		return customerRepo.findById(name).get().getPurchaseHistories();
	}

	public boolean isCustomerAvailable(String name) {
		return customerRepo.existsById(name);
	}

	/**
	 * Find and return customer details in the database
	 * 
	 * @param name name of the customer to get information
	 * @return details of the customer
	 * @throws NoSuchElementException throws when customer is not found
	 */
	public Customer getCustomer(String name) {
		if (!isCustomerAvailable(name))
			throw new NoSuchElementException();

		return customerRepo.findById(name).get();
	}
}
