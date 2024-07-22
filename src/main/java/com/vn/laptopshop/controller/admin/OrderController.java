package com.vn.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vn.laptopshop.domain.Order;
import com.vn.laptopshop.repository.OrderRepository;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping()
    public String getOrderPage(Model model) {
        List<Order> orders = this.orderRepository.findAll();
        model.addAttribute("orders", orders);

        return "admin/order/index";
    }

    @GetMapping("/{id}")
    public String getUpdatePage() {
        return "admin/order/detail";
    }

}
