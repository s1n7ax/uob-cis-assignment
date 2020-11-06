package org.s1n7ax.feedback.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Question
 */
@Entity
public class Question {

	@Id
	@GeneratedValue
	private Long id;

	private String question;

	public Question() {
	}

	public Question(String question) {
		this.question = question;
	}

	public Long getId() {
		return id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", question=" + question + "]";
	}

}
