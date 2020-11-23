package org.s1n7ax.feedback.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.s1n7ax.feedback.Main;
import org.s1n7ax.feedback.component.StarButton;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UITest extends ApplicationTest {
	@Before
	public void setup() throws Exception {
		ApplicationTest.launch(Main.class);
	}

	@Override
	public void start(Stage stage) {
		stage.show();
	}

	@Test
	public void test() throws Exception {
		{
			TextField email = findElement("#txt_Email");
			TextField password = findElement("#txt_Password");
			Button login = findElement("#btn_Login");
			ImageView socialLogin = findElement("#btn_GoogleSignin");

			// check the availability of components
			assertThat(email).isNotNull();
			assertThat(password).isNotNull();
			assertThat(login).isNotNull();
			assertThat(socialLogin).isNotNull();

			// basic login validation
			email.setText("srinesh@gmail.com");
			password.setText("123");
			clickOn(login);
		}

		{
			Label email = findElement("#lbl_Email");
			Button logout = findElement("#btn_Logout");
			VBox container = lookup("#ele_Container").queryAs(VBox.class);

			Set<Label> sellers = findElements("#lbl_Seller");
			Set<Label> products = findElements("#lbl_Product");
			Set<Label> prices = findElements("#lbl_Price");

			assertThat(email).isNotNull();
			assertThat(logout).isNotNull();
			assertThat(container).isNotNull();
			assertThat(sellers.size()).isEqualTo(1);
			assertThat(products.size()).isEqualTo(1);
			assertThat(prices.size()).isEqualTo(1);

			assertThat(sellers.iterator().next().getText()).isNotEmpty();
			assertThat(products.iterator().next().getText()).isNotEmpty();
			assertThat(prices.iterator().next().getText()).isNotEmpty();

			Button feedback = findElement("#btn_Feedback");
			clickOn(feedback);
		}

		{
			Thread.sleep(200);
			Label seller = findElement("#lbl_Seller");
			Label product = findElement("#lbl_Product");
			Label price = findElement("#lbl_Price");
			Button submit = findElement("#btn_Submit");

			assertThat(seller).isNotNull();
			assertThat(product).isNotNull();
			assertThat(price).isNotNull();

			assertThat(seller.getText()).isEqualTo("John");
			assertThat(product.getText()).isEqualTo("iPhone 12");
			assertThat(price.getText()).isEqualTo("799.0");

			List<Set<StarButton>> stars = new ArrayList<Set<StarButton>>();
			for (int i = 0; i < 5; i++) {
				stars.add(findElements("#btn_Star" + (i + 1)));
			}

			List<Iterator<StarButton>> starIter = new ArrayList<Iterator<StarButton>>();
			for (int i = 0; i < 5; i++) {
				starIter.add(stars.get(i).iterator());
			}

			for (int i = 0, j = 0; i < 4; i++) {
				List<StarButton> btns = new ArrayList<>();

				for (int x = 0; x < 4; x++) {
					btns.add(starIter.get(i).next());
				}

				for (StarButton btn : btns) {
					assertThat(btn.getImage().getUrl()).endsWith("/deselected_star.png");
				}

				clickOn(btns.get(j++));
			}

			clickOn(submit);

			Button ok = targetWindow("Message").lookup("OK").query();
			assertThat(ok).isNotNull();
			clickOn(ok);
		}
	}

	@After
	public void after() throws Exception {
		FxToolkit.hideStage();
		release(KeyCode.ALL_CANDIDATES);
		release(MouseButton.FORWARD);
		release(MouseButton.PRIMARY);
		release(MouseButton.SECONDARY);
		release(MouseButton.BACK);
	}

	@SuppressWarnings("unchecked")
	public <T extends Node> T findElement(String query) {
		return (T) lookup(query).query();
	}

	@SuppressWarnings("unchecked")
	public <T extends Node> Set<T> findElements(String query) {
		return (Set<T>) lookup(query).queryAll();
	}
}
