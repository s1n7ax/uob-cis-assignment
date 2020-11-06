package org.s1n7ax.feedback.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Answer
 */
@Entity
public class Feedback {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Question question;

	private int rate = 0;

	public Feedback() {
	}

	public Feedback(Question question) {
		this.question = question;
	}

	public Feedback(Question question, int rate) {
		this.question = question;
		this.rate = rate;
	}

	public Long getId() {
		return id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {

		return "Feedback [id=" + id + ", question=" + question + ", rate=" + rate + "]";
	}

}
