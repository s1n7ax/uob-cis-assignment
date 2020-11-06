package org.s1n7ax.feedback.repository;

import org.s1n7ax.feedback.entity.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * CustomerRepository
 */
public interface CustomerRepository extends CrudRepository<Customer, String> {

}
