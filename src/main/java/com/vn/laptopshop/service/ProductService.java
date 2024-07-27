package com.vn.laptopshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.domain.CartDetail;
import com.vn.laptopshop.domain.Order;
import com.vn.laptopshop.domain.OrderDetail;
import com.vn.laptopshop.domain.Product;
import com.vn.laptopshop.domain.Product_;
import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.repository.CartDetailRepository;
import com.vn.laptopshop.repository.CartRepository;
import com.vn.laptopshop.repository.OrderDetailRepository;
import com.vn.laptopshop.repository.OrderRepository;
import com.vn.laptopshop.repository.ProductRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService, OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Product handleSaveProduct(Product product) {
        return productRepository.save(product);
    }

    // case name
    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }
    // public Page<Product> fetchProductsWithSpec(Pageable pageable, String name) {
    // return productRepository.findAll(nameLike(name), pageable);
    // }

    // case min price

    public static Specification<Product> filterMinPrice(double minPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE), minPrice);
    }

    // public Page<Product> fetchProductsWithSpec(Pageable pageable, double
    // minPrice) {
    // return productRepository.findAll(this.filterMinPrice(minPrice), pageable);
    // }

    // case max price
    public static Specification<Product> filterMaxPrice(double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(Product_.PRICE), maxPrice);
    }

    // public Page<Product> fetchProductsWithSpec(Pageable pageable, double
    // maxPrice) {
    // return productRepository.findAll(this.filterMaxPrice(maxPrice), pageable);
    // }

    // case factory
    public static Specification<Product> matchListFactory(List<String> factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(factory);
    }

    public static Specification<Product> filterFactory(String factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.FACTORY), factory);
    }

    // public Page<Product> fetchProductsWithSpec(Pageable pageable, List<String>
    // factory) {
    // return productRepository.findAll(this.matchListFactory(factory), pageable);
    // }

    // case price 10 to 15
    public static Specification<Product> matchPrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.gt(root.get(Product_.PRICE), min),
                criteriaBuilder.le(root.get(Product_.PRICE), max));
    }

    // public Page<Product> fetchProductsWithSpec(Pageable pageable, String price) {
    // if (price.equals("10-toi-15-trieu")) {
    // double min = 10000000;
    // double max = 15000000;
    // return this.productRepository.findAll(matchPrice(min, max), pageable);
    // }
    // if (price.equals("15-toi-20-trieu")) {
    // double min = 15000000;
    // double max = 20000000;
    // return this.productRepository.findAll(matchPrice(min, max), pageable);
    // }
    // if (price.equals("20-toi-30-trieu")) {
    // double min = 20000000;
    // double max = 30000000;
    // return this.productRepository.findAll(matchPrice(min, max), pageable);
    // }

    // return productRepository.findAll(pageable);
    // }

    public Specification<Product> matchMultiPrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Product_.PRICE), min, max);
    }

    public Page<Product> fetchProductsWithSpec(Pageable pageable, List<String> price) {
        Specification<Product> combinedSpec = (root, query, criteriaBuilder) -> criteriaBuilder.disjunction();
        int count = 0;
        for (String p : price) {
            double min = 0;
            double max = 0;
            switch (p) {
                case "10-toi-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    count++;
                    break;

                case "15-toi-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    count++;
                    break;

                case "20-toi-30-trieu":
                    min = 20000000;
                    max = 30000000;
                    count++;
                    break;
            }
            if (min != 0 && max != 0) {
                Specification<Product> rangeSpec = matchMultiPrice(min, max);
                combinedSpec = combinedSpec.or(rangeSpec);
            }
        }
        if (count == 0) {
            return this.productRepository.findAll(pageable);
        }
        return this.productRepository.findAll(combinedSpec, pageable);
    }
    //////

    public Product findById(long id) {
        return productRepository.findById(id);
    }

    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    public void handleAddProductToCart(long productId, String email, HttpSession session, long quantity) {
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
            newCartDetail.setQuantity(quantity);
            newCartDetail.setPrice(product.getPrice());
            cartDetailRepository.save(newCartDetail);
            int sum = cart.getSum() + 1;
            cart.setSum(sum);
            cart = this.cartRepository.save(cart);
            session.setAttribute("sum", sum);
        } else {
            cart_detail.setQuantity(cart_detail.getQuantity() + quantity);
        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> optional = this.cartDetailRepository.findById(cartDetail.getId());
            if (optional.isPresent()) {
                CartDetail currenCartDetail = optional.get();
                currenCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currenCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {
        // lưu vaof hoá đơn sau ó xoá gở hàng
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {
                Order order = new Order();
                order.setReceiverAddress(receiverAddress);
                order.setReceiverName(receiverName);
                order.setReceiverPhone(receiverPhone);
                order.setUser(user);
                order.setStatus("PENDING");

                double sum = 0;
                for (CartDetail cartDetail : cartDetails) {
                    sum += cartDetail.getPrice() * cartDetail.getQuantity();
                }

                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);
                for (CartDetail cartDetail : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setPrice(cartDetail.getPrice());
                    orderDetail.setQuantity(cartDetail.getQuantity());
                    orderDetail.setProduct(cartDetail.getProduct());
                    this.orderDetailRepository.save(orderDetail);
                }

                for (CartDetail cartDetail : cartDetails) {
                    this.cartDetailRepository.delete(cartDetail);
                }
                this.cartRepository.delete(cart);
                session.setAttribute("sum", 0);
            }

        }

    }
}
