package com.vn.laptopshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.vn.laptopshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Product save(Product product);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    Product findById(long id);

    void deleteById(long id);

}