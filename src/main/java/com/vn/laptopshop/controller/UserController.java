package com.vn.laptopshop.controller;

import org.springframework.web.bind.annotation.RestController;

import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.repository.UserRepository;
import com.vn.laptopshop.service.UserService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handleHello();
        model.addAttribute("test", test);
        List<User> arrusers = this.userService.getUsersByEmail("1@gmail.com");
        System.out.println(arrusers);
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String tableUsers(Model model) {
        List<User> arr = this.userService.getAllUsers();
        model.addAttribute("users", arr);
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/{id}")
    public String viewUser(Model model, @PathVariable long id) {
        System.out.println("test id: " + id);
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/view";
    }

    @RequestMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String postCreateUser(@ModelAttribute("newUser") User newUser, Model model) {
        this.userService.handleSaveUser(newUser);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/update";
    }

    @RequestMapping(value = "/admin/user/update", method = RequestMethod.POST)
    public String postUpdateUser(Model model, @ModelAttribute("user") User user) {
        User currentUser = this.userService.getUserById(user.getId());
        if (currentUser != null) {
            currentUser.setFullName(user.getFullName());
            currentUser.setAddress(user.getAddress());
            currentUser.setPhone(user.getPhone());
            this.userService.handleSaveUser(user);
        }
        return "redirect:/admin/user";
    }
}
