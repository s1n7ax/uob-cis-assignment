package org.s1n7ax.feedback.service;

import java.util.ArrayList;
import java.util.List;

import org.s1n7ax.feedback.entity.Question;
import org.s1n7ax.feedback.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * QuestionService
 */
@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepo;

	List<Question> getDefultQuestionList() {
		Iterable<Question> questionIterable = questionRepo.findAll();
		List<Question> questionList = new ArrayList<>();
		questionIterable.forEach(questionList::add);

		return questionList;
	}

}
