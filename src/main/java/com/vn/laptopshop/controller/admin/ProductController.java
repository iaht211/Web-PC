package com.vn.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vn.laptopshop.domain.Product;
import com.vn.laptopshop.service.ProductService;
import com.vn.laptopshop.service.UploadService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/product")
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping()
    public String getProduct(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/product/index";
    }

    @GetMapping("/create")
    public String getProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/create")
    public String postCreateProduct(@ModelAttribute("newProduct") @Valid Product newProduct,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file) {

        /// validate data
        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError error : errors) {
            System.out.println(">>> ERROR: " + error);
        }

        if (bindingResult.hasErrors()) {
            return "admin/product/create";
        }
        ///
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        newProduct.setImage(avatar);
        productService.handleSaveProduct(newProduct);
        return "redirect:/admin/product";
    }

    @GetMapping("/{id}")
    public String getDetailProductPage(@PathVariable long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/update/{id}")
    public String getUpdateProductPage(@PathVariable long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/product/update";
    }

    @PostMapping("/update")
    public String postUpdateProduct(@ModelAttribute("product") Product product) {
        Product currProduct = productService.findById(product.getId());
        System.out.printf(">> check id: ", product.getId());
        currProduct.setName(product.getName());
        currProduct.setPrice(product.getPrice());
        currProduct.setFactory(product.getFactory());
        currProduct.setQuantity(product.getQuantity());
        currProduct.setSold(product.getSold());
        currProduct.setshort_desc(product.getshort_desc());
        currProduct.setdetail_desc(product.getdetail_desc());
        return "redirect:/admin/product";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteProductPage(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "/admin/product/delete";
    }

    @PostMapping("/delete")
    public String postDeleteProduct(@ModelAttribute("product") Product product) {
        productService.deleteById(product.getId());
        return "redirect:/admin/product";
    }

}
