package org.s1n7ax.feedback.repository;

import org.s1n7ax.feedback.entity.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * ProductRepository handles crud operation
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

}
