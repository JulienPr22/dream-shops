package com.julienprr.dreamshops.controller;

import com.julienprr.dreamshops.dto.ProductDto;
import com.julienprr.dreamshops.exceptions.ResourceNotFoundException;
import com.julienprr.dreamshops.model.Product;
import com.julienprr.dreamshops.request.ProductAddRequest;
import com.julienprr.dreamshops.request.ProductUpdateRequest;
import com.julienprr.dreamshops.response.ApiResponse;
import com.julienprr.dreamshops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.geConvertedProduct(products);
        return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
    }

    @GetMapping("product/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Success", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/name/{productName}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String productName) {
        try {
            List<Product> products = productService.getProductsByName(productName);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found!", null));
            }
            List<ProductDto> convertedProducts = productService.geConvertedProduct(products);
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brandName) {
        try {
            List<Product> products = productService.getProductsByBrand(brandName);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }
            List<ProductDto> convertedProducts = productService.geConvertedProduct(products);
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/category")
    public ResponseEntity<ApiResponse> getProductsByCategory(@RequestParam String categoryName) {
        try {
            List<Product> products = productService.getProductsByCategory(categoryName);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }
            List<ProductDto> convertedProducts = productService.geConvertedProduct(products);
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }
            List<ProductDto> convertedProducts = productService.geConvertedProduct(products);
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/category-and-name")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndName(@RequestParam String categoryName, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, productName);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }
            List<ProductDto> convertedProducts = productService.geConvertedProduct(products);
            return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PostMapping("product/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductAddRequest product) {
        try {
            Product newProduct = productService.addProduct(product);
            ProductDto productDto = productService.convertToDto(newProduct);
            return ResponseEntity.ok(new ApiResponse("Product added successfully", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("product/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            ProductDto productDto = productService.convertToDto(updatedProduct);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("product/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count/by/category-and-name")
    public ResponseEntity<ApiResponse> countsProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            Long productCount = productService.countProductsByBrandAndName(brandName, productName);
            return ResponseEntity.ok(new ApiResponse("Success", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
