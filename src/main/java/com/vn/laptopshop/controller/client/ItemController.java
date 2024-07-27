package com.vn.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.domain.CartDetail;
import com.vn.laptopshop.domain.Product;
import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.service.ProductService;
import com.vn.laptopshop.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {
    private ProductService productService;
    private UserService userService;

    public ItemController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
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
        this.productService.handleAddProductToCart(id, email, session, 1);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(HttpServletRequest request, Model model) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        return "client/cart/index";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartProduct(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long cartDetailId = id;
        userService.handleRemoveCartDetail(cartDetailId, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckoutpage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String postCheckoutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThankYouPage(Model model) {

        return "client/cart/thanks";
    }

    @PostMapping("/add-product-from-detail")
    public String handleAddProductFromDetail(
            @RequestParam("id") long id,
            @RequestParam("quantity") long quantity,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(id, email, session, quantity);
        return "redirect:/product/" + id;
    }

    @GetMapping("/products")
    public String getProductsPage(Model model,
            @RequestParam(defaultValue = "1", name = "page") Optional<String> pageOptional,
            @RequestParam(name = "name") Optional<String> nameOptional,
            @RequestParam(name = "min-price") Optional<String> minOptional,
            @RequestParam(name = "max-price") Optional<String> maxOptional,
            @RequestParam(name = "factory") Optional<String> factoryOptional,
            @RequestParam(name = "price") Optional<String> priceOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent())
                page = Integer.valueOf(pageOptional.get());
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        int size = 4;
        Pageable paging = PageRequest.of(page - 1, 60);

        // case 0: name
        // String name = nameOptional.isPresent() ? nameOptional.get() : "";
        // Page<Product> prs = this.productService.fetchProductsWithSpec(paging, name);

        // case 1: min price
        // double minPrice = minOptional.isPresent() ? Double.valueOf(minOptional.get())
        // : 0;
        // Page<Product> prs = this.productService.fetchProductsWithSpec(paging,
        // maxPrice);

        // case 2: max price
        // double maxPrice = maxOptional.isPresent() ? Double.valueOf(maxOptional.get())
        // : 0;
        // Page<Product> prs = this.productService.fetchProductsWithSpec(paging,
        // maxPrice);

        // case 3: factory
        // String factory = factoryOptional.isPresent() ? factoryOptional.get() : "";
        // Page<Product> prs = this.productService.fetchProductsWithSpec(paging,
        // factory);

        // case list factory
        // List<String> factory = Arrays.asList(factoryOptional.get().split(","));
        // Page<Product> prs = this.productService.fetchProductsWithSpec(paging,
        // factory);

        // case price
        // String price = priceOptional.isPresent() ? priceOptional.get() : "";
        // Page<Product> prs = this.productService.fetchProductsWithSpec(paging, price);

        // list price
        List<String> price = Arrays.asList(priceOptional.get().split(","));
        Page<Product> prs = this.productService.fetchProductsWithSpec(paging, price);
        List<Product> products = prs.getContent();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        return "client/product/total";
    }
}
