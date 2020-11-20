package org.s1n7ax.feedback.repository;

import org.s1n7ax.feedback.entity.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * CustomerRepository handles crud operation
 */
public interface CustomerRepository extends CrudRepository<Customer, String> {

}
