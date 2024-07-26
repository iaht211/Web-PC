package com.vn.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.domain.CartDetail;
import com.vn.laptopshop.domain.Order;
import com.vn.laptopshop.domain.Role;
import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.domain.dto.RegisterDTO;
import com.vn.laptopshop.repository.CartDetailRepository;
import com.vn.laptopshop.repository.OrderRepository;
import com.vn.laptopshop.repository.ProductRepository;
import com.vn.laptopshop.repository.RoleRepository;
import com.vn.laptopshop.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            CartDetailRepository cartDetailRepository, OrderRepository orderRepository,
            ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public String handleHello() {
        return "hello form serveice";
    }

    public User handleSaveUser(User user) {

        return this.userRepository.save(user);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public List<User> getUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> optional = this.cartDetailRepository.findById(cartDetailId);
        if (optional.isPresent()) {
            CartDetail cartDetail = optional.get();

            Cart currentCart = cartDetail.getCart();

            // delete cart-detail
            this.cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if (currentCart.getSum() >= 1) {
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartDetailRepository.save(currentCart);
            } else {
                this.cartDetailRepository.deleteById(cartDetailId);
                session.setAttribute("sum", 0);
                this.cartDetailRepository.save(currentCart);
            }
        }
    }

    public long countUsers() {
        long countUsers = userRepository.count();
        return countUsers;
    }

    public long countOrders() {
        long countOrders = orderRepository.count();
        return countOrders;
    }
    
    public long countProducts() {
        long countProducts = productRepository.count();
        return countProducts;
    }

    public List<Order> getAllOrders() {
       return orderRepository.findAll();
    }
    
}
