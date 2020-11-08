package org.s1n7ax.feedback;

import java.util.ArrayList;
import java.util.List;

import org.s1n7ax.feedback.entity.Customer;
import org.s1n7ax.feedback.entity.Feedback;
import org.s1n7ax.feedback.entity.Product;
import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.s1n7ax.feedback.entity.Question;
import org.s1n7ax.feedback.entity.Seller;
import org.s1n7ax.feedback.repository.CustomerRepository;
import org.s1n7ax.feedback.repository.FeedbackRepository;
import org.s1n7ax.feedback.repository.ProductRepository;
import org.s1n7ax.feedback.repository.PurchaseHistoryRepository;
import org.s1n7ax.feedback.repository.QuestionRepository;
import org.s1n7ax.feedback.repository.SellerRepository;

/**
 * DataCreation - just a data creation script
 */
public class DataCreation {

	public static void create(CustomerRepository customerRepo, FeedbackRepository feedbackRepo,
			PurchaseHistoryRepository purchaseHistoryRepo, QuestionRepository questionRepo, ProductRepository prodRepo,
			SellerRepository sellerRepo) {
		// seller
		Seller john = new Seller("John");
		sellerRepo.save(john);

		// products
		List<Product> productList = new ArrayList<>();
		Product iphone = new Product("iPhone 12", 799, john);
		Product canon = new Product("Canon 80D", 899, john);
		productList.add(iphone);
		productList.add(canon);
		prodRepo.saveAll(productList);

		// question
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

		Feedback f5 = new Feedback(q1);
		Feedback f6 = new Feedback(q2);
		Feedback f7 = new Feedback(q3);
		Feedback f8 = new Feedback(q4);

		List<Feedback> flist1 = new ArrayList<>();
		flist1.add(f1);
		flist1.add(f2);
		flist1.add(f3);
		flist1.add(f4);
		feedbackRepo.saveAll(flist1);

		List<Feedback> flist2 = new ArrayList<>();
		flist2.add(f5);
		flist2.add(f6);
		flist2.add(f7);
		flist2.add(f8);
		feedbackRepo.saveAll(flist2);

		// purchase history
		PurchaseHistory purchaseHistory1 = new PurchaseHistory(iphone, flist1);
		PurchaseHistory purchaseHistory2 = new PurchaseHistory(canon, flist2);

		List<PurchaseHistory> purchaseHistoryList1 = new ArrayList<>();
		purchaseHistoryList1.add(purchaseHistory1);
		purchaseHistoryRepo.saveAll(purchaseHistoryList1);

		List<PurchaseHistory> purchaseHistoryList2 = new ArrayList<>();
		purchaseHistoryList2.add(purchaseHistory2);
		purchaseHistoryRepo.saveAll(purchaseHistoryList2);

		Customer customer1 = new Customer("srinesh@gmail.com", purchaseHistoryList1);
		Customer customer2 = new Customer("srineshnisala@gmail.com", purchaseHistoryList2);
		customerRepo.save(customer1);
		customerRepo.save(customer2);
	}
}
