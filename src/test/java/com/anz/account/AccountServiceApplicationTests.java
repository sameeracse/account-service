package com.anz.account;

import com.anz.account.dto.AccountResponse;
import com.anz.account.dto.AccountTransactionResponse;
import com.anz.account.dto.ApiError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
	void testGetAccountsByUserId() {
		ResponseEntity<AccountResponse> accountResponse = testRestTemplate.getForEntity(BASE_URL + port + "/user/1/accounts", AccountResponse.class);
		assertEquals(HttpStatus.OK, accountResponse.getStatusCode());
		assertNotNull(accountResponse.getBody());
		assertNotNull(accountResponse.getBody().getAccounts());
		assertEquals(11,accountResponse.getBody().getAccounts().size());
	}

	@Test
	void testGetAccountsByUserIdWhenInvalidUserId() {
		ResponseEntity<ApiError> apiError = testRestTemplate.getForEntity(BASE_URL + port + "/user/*!/accounts", ApiError.class);
		assertNotNull(apiError);
		assertEquals(HttpStatus.BAD_REQUEST,apiError.getStatusCode());
	}

	@Test
	void testGetAccountTransactionsByAccountId() {
		ResponseEntity<AccountTransactionResponse> accountTransactionResponse = testRestTemplate.getForEntity(BASE_URL + port + "/account/1/transactions?pageNo=0&pageSize=10", AccountTransactionResponse.class);

		assertEquals(HttpStatus.OK,accountTransactionResponse.getStatusCode());
		assertNotNull(accountTransactionResponse.getBody());
		assertNotNull(accountTransactionResponse.getBody().getAccountTransactions());
		assertEquals(10,accountTransactionResponse.getBody().getAccountTransactions().size());

		accountTransactionResponse = testRestTemplate.getForEntity(BASE_URL + port + "/account/1/transactions?pageNo=1&pageSize=10", AccountTransactionResponse.class);

		assertEquals(HttpStatus.OK,accountTransactionResponse.getStatusCode());
		assertNotNull(accountTransactionResponse.getBody());
		assertNotNull(accountTransactionResponse.getBody().getAccountTransactions());
		assertEquals(2,accountTransactionResponse.getBody().getAccountTransactions().size());
	}

	@Test
	void testGetAccountTransactionsByAccountIdWhenInvalidAccountId() {
		ResponseEntity<ApiError> apiError = testRestTemplate.getForEntity(BASE_URL + port + "/account/(%/transactions?pageNo=0&pageSize=2", ApiError.class);
		assertNotNull(apiError);
		assertEquals(HttpStatus.BAD_REQUEST,apiError.getStatusCode());

	}

	@Test
	void testGetAccountTransactionsByAccountIdWhenRequestParams() {
		ResponseEntity<ApiError> apiError = testRestTemplate.getForEntity(BASE_URL + port + "/account/1/transactions?pageNo=-1&pageSize=2", ApiError.class);
		assertNotNull(apiError);
		assertEquals(HttpStatus.BAD_REQUEST,apiError.getStatusCode());

		apiError = testRestTemplate.getForEntity(BASE_URL + port + "/account/1/transactions?pageNo=0&pageSize=-1", ApiError.class);
		assertNotNull(apiError);
		assertEquals(HttpStatus.BAD_REQUEST,apiError.getStatusCode());
	}

}
