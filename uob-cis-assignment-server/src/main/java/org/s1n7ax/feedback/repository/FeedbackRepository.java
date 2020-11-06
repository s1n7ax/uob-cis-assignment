package org.s1n7ax.feedback.repository;

import org.s1n7ax.feedback.entity.Feedback;
import org.springframework.data.repository.CrudRepository;

/**
 * FeedbackRepository
 */
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

}
