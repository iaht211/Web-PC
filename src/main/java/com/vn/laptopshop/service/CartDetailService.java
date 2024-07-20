package com.vn.laptopshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vn.laptopshop.repository.CartDetailRepository;

@Service
public class CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    public void deleteById(long id) {
        cartDetailRepository.deleteById(id);
    }
}
