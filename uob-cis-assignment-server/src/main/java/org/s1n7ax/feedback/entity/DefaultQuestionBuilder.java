package org.s1n7ax.feedback.entity;

import java.util.List;
import java.util.Vector;

/**
 * DefaultQuestionBuilder
 */
public class DefaultQuestionBuilder implements QuestionBuilder {
	private Vector<Question> questions = new Vector<>();

	public static DefaultQuestionBuilder getInstance() {
		return new DefaultQuestionBuilder();
	}

	@Override
	public List<Question> build() {
		return questions;
	}

	public void addQuestion(String question) {
		questions.add(new Question(question));
	}

}
