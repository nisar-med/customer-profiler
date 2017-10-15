package com.ing.customerprofiler.controller;

import com.ing.customerprofiler.data.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class TransactionController {
    @Autowired
    private TransactionRepository tranactionRepository;

    @RequestMapping(method= RequestMethod.GET)
    public String index() {
        return "statement";
    }

}
