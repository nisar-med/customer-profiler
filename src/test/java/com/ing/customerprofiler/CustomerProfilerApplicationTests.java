package com.ing.customerprofiler;

import com.ing.customerprofiler.data.domain.CustomerProfile;
import com.ing.customerprofiler.data.entity.Transaction;
import com.ing.customerprofiler.data.service.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerProfilerApplicationTests {

	@Autowired
	private TransactionService transactionService;

	@Test
	public void contextLoads() {
	}
	@Test
	public void testTransactionCount() {
		Assert.assertTrue((long) transactionService.getTransactions(null).size() == 2995);
	}
	@Test
	public void testInvalidPeriodHandled() {
		CustomerProfile profile = transactionService.getCustomerProfile(1L, "2015");
		Assert.assertTrue((long) profile.getTransactions().size() == 0);
	}
	@Test
	public void testCustomer1Balance() {
		List<Transaction> transactions = transactionService.getTransactions(1L);
		double balance = transactions.stream().mapToDouble(Transaction::getAmount).sum();
		Assert.assertTrue(Double.compare(balance,421.27)==0);
	}
	@Test
	public void testCustomer1IsPotentialSaver() {
		CustomerProfile profile = transactionService.getCustomerProfile(1L, "2016-05");
		Assert.assertTrue(profile.getClassifications().contains("Potential Saver"));
	}

}
