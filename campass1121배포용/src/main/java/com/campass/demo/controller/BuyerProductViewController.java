package com.campass.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BuyerProductViewController {
   // 용품 메인
   @GetMapping("/buyer/product")
   public ModelAndView list() {
      return new ModelAndView("/buyer/product/product");
   }

  
  	
}