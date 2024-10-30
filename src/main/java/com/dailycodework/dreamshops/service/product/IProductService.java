package com.dailycodework.dreamshops.service.product;

import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    Product getProductById(Long id);
    void deleteProduct(Long id);
    List<Product> getProductsByCategoryName(String categoryName);
    List<Product> getProductsByBrandName(String brandName);
    List<Product> getAllProducts();
    List<Product> getAllProductsByCategoryNameAndBrandName(String categoryName,String brandName);
    List<Product> getAllProductsByName(String name);
    List<Product> getAllProductsByBrandNameAndName(String brandName,String name);
    Long countProductsByBrandNameAndName(String brandName,String name);

}
