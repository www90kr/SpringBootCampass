package com.campass.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ComProductViewController {
   // 용품 메인
   @GetMapping("/com/product")
   public ModelAndView list() {
      return new ModelAndView("/com/product/product");
   }


}