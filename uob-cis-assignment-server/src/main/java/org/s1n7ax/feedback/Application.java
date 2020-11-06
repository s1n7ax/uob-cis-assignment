package org.s1n7ax.feedback;

import java.util.ArrayList;
import java.util.List;

import org.s1n7ax.feedback.entity.*;
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

	private void dataCreation(CustomerRepository customerRepo, FeedbackRepository feedbackRepo,
			PurchaseHistoryRepository purchaseHistoryRepo, QuestionRepository questionRepo, ProductRepository prodRepo,
			SellerRepository sellerRepo, String username) {
		// products
		List<Product> productList = new ArrayList<>();
		Product iphone = new Product("iPhone 12", 799);
		Product canon = new Product("Canon 80D", 899);
		productList.add(iphone);
		productList.add(canon);
		prodRepo.saveAll(productList);

		// seller
		Seller john = new Seller("John", productList);
		sellerRepo.save(john);

		// questions
		Question q1 = new Question("Sellers communication");
		Question q2 = new Question("Shipping time");
		Question q3 = new Question("Packaging");
		Question q4 = new Question("Accuracy of the product description");

		questionRepo.save(q1);
		questionRepo.save(q2);
		questionRepo.save(q3);
		questionRepo.save(q4);

		// feedback
		Feedback f1 = new Feedback(q1);
		Feedback f2 = new Feedback(q2);
		Feedback f3 = new Feedback(q3);
		Feedback f4 = new Feedback(q4);

		List<Feedback> flist = new ArrayList<>();
		flist.add(f1);
		flist.add(f2);
		flist.add(f3);
		flist.add(f4);

		feedbackRepo.saveAll(flist);

		// purchase history
		PurchaseHistory purchaseHistory = new PurchaseHistory(iphone, flist);

		List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();
		purchaseHistoryList.add(purchaseHistory);
		purchaseHistoryRepo.saveAll(purchaseHistoryList);

		Customer customer1 = new Customer(username, purchaseHistoryList);

		customerRepo.save(customer1);
	}

	// add data to in memory database
	@Bean
	CommandLineRunner runner(CustomerRepository customerRepo, FeedbackRepository feedbackRepo,
			PurchaseHistoryRepository purchaseHistoryRepo, QuestionRepository questionRepo, ProductRepository prodRepo,
			SellerRepository sellerRepo) {
		return args -> {
			dataCreation(customerRepo, feedbackRepo, purchaseHistoryRepo, questionRepo, prodRepo, sellerRepo,
					"srinesh@gmail.com");

			dataCreation(customerRepo, feedbackRepo, purchaseHistoryRepo, questionRepo, prodRepo, sellerRepo,
					"srineshnisala@gmail.com");

		};
	}
}
