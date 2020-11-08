package org.s1n7ax.feedback;

import org.s1n7ax.feedback.repository.CustomerRepository;
import org.s1n7ax.feedback.repository.FeedbackRepository;
import org.s1n7ax.feedback.repository.ProductRepository;
import org.s1n7ax.feedback.repository.PurchaseHistoryRepository;
import org.s1n7ax.feedback.repository.QuestionRepository;
import org.s1n7ax.feedback.repository.SellerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	// start application
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner(CustomerRepository customerRepo, FeedbackRepository feedbackRepo,
			PurchaseHistoryRepository purchaseHistoryRepo, QuestionRepository questionRepo, ProductRepository prodRepo,
			SellerRepository sellerRepo) {
		return args -> {
			DataCreation.create(customerRepo, feedbackRepo, purchaseHistoryRepo, questionRepo, prodRepo, sellerRepo);
		};
	}
}
