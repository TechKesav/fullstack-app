package com.telusko.ecomproj.controller;

import com.telusko.ecomproj.model.product;
import com.telusko.ecomproj.service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class productController {
    @Autowired  //advised to use setter or constructor injection other than filled injection(like this)
    private productService service;

    @GetMapping("/products")
    public ResponseEntity<List<product>> getAllProducts(){  // returns data as well as server http response like 404
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<product> getproduct(@PathVariable int id){
        product Product = service.getProductById(id);
        if(Product!=null)
            return new ResponseEntity<>(Product,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart product product, @RequestPart MultipartFile imageFile){
        try {
            product product1 = service.addProduct(product,imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        product product1= service.getProductById(productId);
        byte[] imageFile = product1.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product1.getImageType()))
                .body(imageFile);
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart product product, @RequestPart MultipartFile imageFile) throws IOException {
       product product1= service.updateProduct(id,product,imageFile);
       if(product1!=null)
           return new ResponseEntity<>("updated",HttpStatus.OK);
       else return new ResponseEntity<>("failed to update",HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        product product1 = service.getProductById(id);
        if(product1!=null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("product not found",HttpStatus.NOT_FOUND);
    }
}
