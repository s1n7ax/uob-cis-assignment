# Dependencies

This document list and detail all the dependencies of the current project

## uob-cis-assignment-client

### TestFX

Constructing & running tests. Being used in
org.s1n7ax.feedback.controller.UITest to setup & run the tests. Wrapping junit 4
and few other libraries for dispatching actions such as "clickOn" and traversing
FX UI components API (lookup())

Ex:-

```java
clickOn(login);
lookup("#eleContainer").queryAs(VBox.class);

@Test
public void test() throws Exception {}
```

### Assertj

Assertion library for asserting.

Ex:-

``` java
assertThat(email).isNotNull();
assertThat(sellers.size()).isEqualTo(1);
```

### Gson
Serialization and de-serialization of Json

Ex:-

```java
Gson gson = new Gson();
PurchaseHistory[] purchaseHistoryArr = gson.fromJson(payload, PurchaseHistory[].class);
```

### commons-validator
Validating email format in login window

Ex:-

```java
@FXML
void emailKeypressed(KeyEvent event) {
	if (EmailValidator.getInstance().isValid(txtEmail.getText())) {
		textValiditySwitcher.setControl(txtEmail).changeClass("valid");
	} else {
		textValiditySwitcher.setControl(txtEmail).changeClass("invalid");
	}
}
```

### log4j
Application information logging. It's important to have logger in client side
because developer may not be able to debug in client. Using log files developer
can get an idea what is going on incase of an issue.

Ex:-

```java
private final Logger logger = LogManager.getLogger(DefaultBrowserOpener.class);

logger.info("Social login canceled");
logger.debug("opening default browser");
logger.error("unable to connect to host", e);
```

### httpclient
Sending HTTP requests. Additionally apache http client handles cookies
automatically.

Ex:-

```java
cookieStore = new BasicCookieStore();
client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
HttpGet get = new HttpGet(uri);
response = (CloseableHttpResponse) client.execute(get);
```


## uob-cis-assignment-server

### spring-boot-starter-web
For creating Spring web service project

Ex:-

```java
@RestController
public class HomeController {

	@GetMapping("/")
	public ResponseEntity<?> home() { }

	@GetMapping("/restricted")
	public String restricted() { }
}
```



### spring-boot-starter-security
Handling authentication in spring. Setting endpoint security for example.

Ex:-

```java
http
	.csrf().disable()
	.headers().frameOptions().disable()
	.and()
	.authorizeRequests()
	.antMatchers("/").permitAll()
	.antMatchers("/login").permitAll()
	.antMatchers("/login/social").permitAll()
	.antMatchers("/login/social/google").permitAll()
	.antMatchers("/oauth/google").permitAll()
	.antMatchers("/h2-console").permitAll()
	.anyRequest().authenticated()
	.and().httpBasic();
```

### spring-boot-starter-data-jpa
Interact databases using ORM

Ex:-
```java
List<PurchaseHistory> purchaseHistoryList2 = new ArrayList<>();
purchaseHistoryList2.add(purchaseHistory2);
purchaseHistoryRepo.saveAll(purchaseHistoryList2);
```

### h2
H2 in memory database to store data in memory database. H2 doesn't store data
locally. So data creation should be done at start up.

### spring-boot-starter-test
Fro testing spring boot applications

Ex:-

```java
@Test
public void restrictedResourceCheck() throws Exception {
	int status = restTemplate
			.exchange(format(getUrl(), "/restricted"), HttpMethod.GET, getValidUserEntity(), String.class)
			.getStatusCode().value();

	assertThat(status).isEqualTo(200).describedAs("user should be able to access EP");
}
```

### google-api-client
Backend Google OAuth 2 authorization validation

Ex:-

```java
res = new GoogleAuthorizationCodeTokenRequest(
		new NetHttpTransport(),
		JacksonFactory.getDefaultInstance(),
		"https://oauth2.googleapis.com/token",
		getClientSecrets().getDetails().getClientId(),
		getClientSecrets().getDetails().getClientSecret(),
		authCode,
		redirectURI)
	.execute();
```
