package org.s1n7ax.feedback.repository;

import org.s1n7ax.feedback.entity.Question;
import org.springframework.data.repository.CrudRepository;

/**
 * QuestionRepository handles crud operation
 */
public interface QuestionRepository extends CrudRepository<Question, Long> {

}
