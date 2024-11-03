package com.dailycodework.dreamshops.service.product;

import com.dailycodework.dreamshops.criteria.ProductFilterCriteria;
import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    ProductDto addProduct(AddProductRequest request);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    Product getProductById(Long id);
    void deleteProduct(Long id);
    List<ProductDto> filterProducts(ProductFilterCriteria criteria);
    List<ProductDto> getAllProducts();
}
