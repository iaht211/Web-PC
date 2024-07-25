package com.vn.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vn.laptopshop.domain.Order;
import com.vn.laptopshop.domain.OrderDetail;
import com.vn.laptopshop.repository.OrderDetailRepository;
import com.vn.laptopshop.repository.OrderRepository;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
    private final OrderRepository orderRepository;
    public final OrderDetailRepository orderDetailRepository;

    public OrderController(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @GetMapping()
    public String getOrderPage(Model model) {
        List<Order> orders = this.orderRepository.findAll();
        model.addAttribute("orders", orders);

        return "admin/order/index";
    }

    @GetMapping("/{id}")
    public String getUpdatePage(Model model, @PathVariable long id) {
        List<OrderDetail> orderDetails = this.orderRepository.findById(id).getOrderDetails();
        model.addAttribute("orderDetails", orderDetails);
        return "admin/order/detail";
    }

    @GetMapping("/update/{id}")
    public String getUpdateOrder(Model model, @PathVariable long id) {
        Order order = orderRepository.findById(id);
        model.addAttribute("order", order);
        return "admin/order/udpate";
    }

    @PostMapping("/update")
    public String postUpdateOrders(@ModelAttribute("order") Order order) {
        long id = order.getId();
        Order currOrder = orderRepository.findById(id);
        currOrder.setStatus(order.getStatus());
        orderRepository.save(currOrder);
        return "redirect:/admin/order";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteOrder(Model model, @PathVariable(value = "id") long id) {
        Order order = orderRepository.findById(id);
        model.addAttribute("order", order);
        return "admin/order/delete";
    }

    @PostMapping("/delete") 
    public String postDeleteOrder(@RequestParam(value = "id") long id) {
        Order currOrder = orderRepository.findById(id);
        List<OrderDetail> orderDetails = currOrder.getOrderDetails();
        for(OrderDetail orderDetail: orderDetails) {
            orderDetailRepository.delete(orderDetail);
        }
        orderRepository.deleteById(id);

        return "redirect:/admin/order";
    }

}
