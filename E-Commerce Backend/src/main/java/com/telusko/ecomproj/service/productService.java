package com.telusko.ecomproj.service;
import com.telusko.ecomproj.repo.productRepo;
import com.telusko.ecomproj.model.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class productService {
    @Autowired
    private productRepo repo;

    public List<product> getAllProducts() {
           return repo.findAll();
    }

    public product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public product addProduct(product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public product updateProduct(int id, product product, MultipartFile imageFile) throws IOException {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
         return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }
}
