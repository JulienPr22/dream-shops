package com.julienprr.dreamshops.service.product;

import com.julienprr.dreamshops.dto.ImageDto;
import com.julienprr.dreamshops.dto.ProductDto;
import com.julienprr.dreamshops.exceptions.AlreadyExistsException;
import com.julienprr.dreamshops.exceptions.ResourceNotFoundException;
import com.julienprr.dreamshops.model.Category;
import com.julienprr.dreamshops.model.Image;
import com.julienprr.dreamshops.model.Product;
import com.julienprr.dreamshops.repository.CategoryRepository;
import com.julienprr.dreamshops.repository.ImageRepository;
import com.julienprr.dreamshops.repository.ProductRepository;
import com.julienprr.dreamshops.request.ProductAddRequest;
import com.julienprr.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(ProductAddRequest request) {
        if (productExists(request.getName(), request.getBrand())) {
            throw new AlreadyExistsException("Product " + request.getBrand() + " - " + request.getName() + " already exists");
        }

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName())).orElseGet(() -> {
            Category newCategory = new Category(request.getCategory().getName());
            return categoryRepository.save(newCategory);
        });

        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private boolean productExists(String productName, String brand) {
        return productRepository.existsByNameAndBrand(productName, brand);
    }

    private Product createProduct(ProductAddRequest request, Category category) {
        return new Product(request.getName(), request.getBrand(), request.getDescription(), request.getPrice(), request.getInventory(), request.getCategory());
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new ResourceNotFoundException("Product not found.");
        });
    }

    @Override
    public Product updateProduct(Long productId, ProductUpdateRequest request) {
        return productRepository.findById(productId).map(existingProduct -> updateExistingProduct(existingProduct, request)).map(productRepository::save).orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(request.getCategory());
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> geConvertedProduct(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
