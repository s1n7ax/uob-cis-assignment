package org.s1n7ax.feedback.repository;

import org.s1n7ax.feedback.entity.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * SellerRepository handles crud operation
 */
@Repository
public interface SellerRepository extends CrudRepository<Seller, Long> {

}
