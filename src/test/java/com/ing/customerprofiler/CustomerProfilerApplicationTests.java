package com.ing.customerprofiler;

import com.ing.customerprofiler.data.domain.CustomerProfile;
import com.ing.customerprofiler.data.service.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerProfilerApplicationTests {

	@Autowired
	TransactionService transactionService;

	@Test
	public void contextLoads() {
	}
	@Test
	public void testTransactionCount() {
		Assert.assertTrue(transactionService.getTransactions().stream().count() == 2995);
	}
	@Test
	public void testInvalidPeriodHandled() {
		CustomerProfile profile = transactionService.getCustomerProfile(Long.valueOf(1), "2015");
		Assert.assertTrue(profile.getTransactions().stream().count() == 0);
	}

}
