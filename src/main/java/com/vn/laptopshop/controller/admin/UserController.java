package com.vn.laptopshop.controller.admin;

import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.service.UploadService;
import com.vn.laptopshop.service.UserService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {
    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handleHello();
        model.addAttribute("test", test);
        List<User> arrusers = this.userService.getUsersByEmail("1@gmail.com");
        System.out.println(arrusers);
        return "hello";
    }

    @GetMapping("/admin/user")
    public String tableUsers(Model model, @RequestParam(defaultValue = "1", name = "page") Optional<String> s_page) {
        int page = 1;
        try {
            if(s_page.isPresent()) 
                    page = Integer.valueOf(s_page.get());
        }
            catch(Exception error) {
                System.out.println(error.getMessage());
            }
        
        int size = 2; 
        Pageable paging = PageRequest.of(page-1, size);
        Page<User> pagedResult = userService.findAll(paging);
        List<User> users = pagedResult.getContent();
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page-1);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        return "admin/user/index";
    }

    @GetMapping("/admin/user/{id}")
    public String viewUser(Model model, @PathVariable long id) {
        System.out.println("test id: " + id);
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String postCreateUser(Model model,
            @ModelAttribute("newUser") @Valid User newUser,
            BindingResult newUserBindingResult,
            @RequestParam("file") MultipartFile file) {
        // validate data input
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>> " + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }
        ///
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        System.out.println(avatar);
        String hashPassword = this.passwordEncoder.encode(newUser.getPassword());
        System.out.println("check password: " + hashPassword);
        newUser.setAvatar(avatar);
        newUser.setPassword(hashPassword);
        newUser.setRole(this.userService.getRoleByName(newUser.getRole().getName()));
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
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping(value = "/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/delete";
    }

    @PostMapping(value = "/admin/user/delete")
    public String postDeleteUser(@ModelAttribute("user") User user) {
        User currentUser = this.userService.getUserById(user.getId());
        if (currentUser != null) {
            this.userService.deleteUserById(currentUser.getId());
        }
        return "redirect:/admin/user";
    }
}
