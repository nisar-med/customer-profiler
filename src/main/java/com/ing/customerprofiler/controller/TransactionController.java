package com.ing.customerprofiler.controller;

import com.ing.customerprofiler.data.domain.CustomerProfile;
import com.ing.customerprofiler.data.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(method= RequestMethod.GET)
    public String index(@RequestParam(value="customerid", required=false)Long customerId,
                        @RequestParam(value="period", required=false)String period,
                        Model model) {
        CustomerProfile profile = transactionService.getCustomerProfile(customerId, period);
        model.addAttribute("transactions", profile.getTransactions());
        model.addAttribute("classifications", profile.getClassifications());
        model.addAttribute("balance", profile.getBalance());
        return "statement";
    }

}
