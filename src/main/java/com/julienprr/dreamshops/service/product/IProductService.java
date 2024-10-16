package com.julienprr.dreamshops.service.product;

import com.julienprr.dreamshops.dto.ProductDto;
import com.julienprr.dreamshops.model.Product;
import com.julienprr.dreamshops.request.AddProductRequest;
import com.julienprr.dreamshops.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProduct(Long productId, UpdateProductRequest request);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> geConvertedProduct(List<Product> products);

    ProductDto convertToDto(Product product);
}
