package com.vn.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.vn.laptopshop.domain.Product;
import com.vn.laptopshop.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(id, email, session);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage() {
        return "client/cart/index";
    }
}
