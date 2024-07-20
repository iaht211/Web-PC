package com.vn.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.domain.CartDetail;
import com.vn.laptopshop.domain.Product;
import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.repository.CartDetailRepository;
import com.vn.laptopshop.repository.CartRepository;
import com.vn.laptopshop.repository.ProductRepository;
import com.vn.laptopshop.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public Product handleSaveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(long id) {
        return productRepository.findById(id);
    }

    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    public void handleAddProductToCart(long productId, String email, HttpSession session) {
        User user = userService.getUserByEmail(email);
        // check user have cart ? not -> create

        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setSum(0);
            newCart.setUser(user);
            cart = this.cartRepository.save(newCart);
        }

        Product product = this.productRepository.findById(productId);
        CartDetail cart_detail = this.cartDetailRepository.findByCartAndProduct(cart, product);
        if (cart_detail == null) {
            CartDetail newCartDetail = new CartDetail();
            newCartDetail.setCart(cart);
            newCartDetail.setProduct(product);
            newCartDetail.setQuantity(1);
            newCartDetail.setPrice(product.getPrice());
            cartDetailRepository.save(newCartDetail);
            int sum = cart.getSum() + 1;
            cart.setSum(sum);
            cart = this.cartRepository.save(cart);
            session.setAttribute("sum", sum);
        } else {
            cart_detail.setQuantity(cart_detail.getQuantity() + 1);
        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

}
