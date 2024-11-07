package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.category.name) LIKE LOWER(CONCAT('%', :categoryName, '%'))")
    List<Product> findByCategory_Name(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p WHERE LOWER(p.brand) LIKE LOWER(CONCAT('%', :brandName, '%'))")
    List<Product> findByBrand(@Param("brandName") String brandName);

    @Query("SELECT p FROM Product p WHERE LOWER(p.category.name) LIKE LOWER(CONCAT('%', :categoryName, '%')) AND LOWER(p.brand) LIKE LOWER(CONCAT('%', :brandName, '%'))")
    List<Product> findByCategory_NameAndBrand_Name(@Param("categoryName") String categoryName, @Param("brandName") String brandName);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByName(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.brand) LIKE LOWER(CONCAT('%', :brandName, '%')) AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByBrandAndName(@Param("brandName") String brandName, @Param("name") String name);

    @Query("SELECT COUNT(p) FROM Product p WHERE LOWER(p.brand) LIKE LOWER(CONCAT('%', :brandName, '%')) AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Long countByBrandAndName(@Param("brandName") String brandName, @Param("name") String name);

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(p.brand) = LOWER(:brand)")
    boolean isExistsByNameAndBrand(String name, String brand);



}
