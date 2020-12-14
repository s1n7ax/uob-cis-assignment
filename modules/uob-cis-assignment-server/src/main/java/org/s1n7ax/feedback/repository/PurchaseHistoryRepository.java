package org.s1n7ax.feedback.repository;

import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.springframework.data.repository.CrudRepository;

/**
 * PurchaseHistoryRepositorProducty handles crud operation
 */
public interface PurchaseHistoryRepository extends CrudRepository<PurchaseHistory, Long> {

}
