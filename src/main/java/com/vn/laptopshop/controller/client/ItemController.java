package com.vn.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vn.laptopshop.domain.Product;
import com.vn.laptopshop.service.ProductService;

@Controller
public class ItemController {
    private ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getDetailItemPage(Model model, @PathVariable int id) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "client/product/index";
    }
}
