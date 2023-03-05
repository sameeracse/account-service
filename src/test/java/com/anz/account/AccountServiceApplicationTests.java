package com.anz.account;

import com.anz.account.dto.AccountResponse;
import com.anz.account.dto.AccountTransactionResponse;
import com.anz.account.dto.ApiError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = AccountServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountServiceApplicationTests {

	private static final String BASE_URL = "http://localhost:";
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void testGetUserAccounts() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "1");
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		ResponseEntity<AccountResponse> accountResponse = testRestTemplate.exchange(
				BASE_URL + port + "/accounts", HttpMethod.GET,entity, AccountResponse.class);
		assertEquals(HttpStatus.OK, accountResponse.getStatusCode());
		assertNotNull(accountResponse.getBody());
		assertNotNull(accountResponse.getBody().getAccounts());
		assertEquals(11,accountResponse.getBody().getAccounts().size());
	}

	@Test
	void testGetUserAccountsWhenTokenIsNull() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		ResponseEntity<ApiError> apiError = testRestTemplate.exchange(BASE_URL + port + "/accounts", HttpMethod.GET,entity, ApiError.class);
		assertNotNull(apiError);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,apiError.getStatusCode());
	}

	@Test
	void testGetAccountTransactionsByAccountId() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "1");
		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		ResponseEntity<AccountTransactionResponse> accountTransactionResponse = testRestTemplate.exchange(
				BASE_URL + port + "/account/1/transactions?pageNo=0&pageSize=10",HttpMethod.GET, entity, AccountTransactionResponse.class);

		assertEquals(HttpStatus.OK,accountTransactionResponse.getStatusCode());
		assertNotNull(accountTransactionResponse.getBody());
		assertNotNull(accountTransactionResponse.getBody().getAccountTransactions());
		assertEquals(10,accountTransactionResponse.getBody().getAccountTransactions().size());

		accountTransactionResponse = testRestTemplate.exchange(
				BASE_URL + port + "/account/1/transactions?pageNo=1&pageSize=10",HttpMethod.GET, entity, AccountTransactionResponse.class);

		assertEquals(HttpStatus.OK,accountTransactionResponse.getStatusCode());
		assertNotNull(accountTransactionResponse.getBody());
		assertNotNull(accountTransactionResponse.getBody().getAccountTransactions());
		assertEquals(2,accountTransactionResponse.getBody().getAccountTransactions().size());
	}

	@Test
	void testGetAccountTransactionsByAccountIdWhenInvalidAccountId() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "1");
		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		ResponseEntity<ApiError> apiError = testRestTemplate.exchange(BASE_URL + port + "/account/(%/transactions?pageNo=0&pageSize=2"
				,HttpMethod.GET, entity,ApiError.class);
		assertNotNull(apiError);
		assertEquals(HttpStatus.BAD_REQUEST,apiError.getStatusCode());

	}

	@Test
	void testGetAccountTransactionsByAccountIdWhenRequestParams() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "1");
		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		ResponseEntity<ApiError> apiError = testRestTemplate.exchange(BASE_URL + port + "/account/1/transactions?pageNo=-1&pageSize=2",
				HttpMethod.GET, entity,ApiError.class);
		assertNotNull(apiError);
		assertEquals(HttpStatus.BAD_REQUEST,apiError.getStatusCode());

		apiError = testRestTemplate.getForEntity(BASE_URL + port + "/account/1/transactions?pageNo=0&pageSize=-1", ApiError.class);
		assertNotNull(apiError);
		assertEquals(HttpStatus.BAD_REQUEST,apiError.getStatusCode());
	}

}
