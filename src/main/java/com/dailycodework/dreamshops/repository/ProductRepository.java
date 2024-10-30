package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String categoryName);
    List<Product> findByBrand(String brandName);
    List<Product> findByCategoryAndBrand(String categoryName, String brandNAme);
    List<Product> findByName(String name);
    List<Product> findByBrandAndName(String brandName, String name);
    Long countByBrandAndName(String brandName, String name);
}
