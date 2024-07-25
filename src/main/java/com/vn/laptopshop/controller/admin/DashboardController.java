package com.vn.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vn.laptopshop.service.UserService;

@Controller
public class DashboardController {
    private final UserService userService;
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getDashboard(Model model) {
        long countProducts = userService.countProducts();
        long countOrders = userService.countOrders();
        long countUsers = userService.countUsers();

        model.addAttribute("countProducts", countProducts);
        model.addAttribute("countOrders", countOrders);
        model.addAttribute("countUsers", countUsers);
        return "admin/dashboard/view";
    }
}
