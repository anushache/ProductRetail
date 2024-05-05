package com.example.demo.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.utility.Product;

@Repository
@Component
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product,Integer>{

	List<Product> findByActiveOrderByCreatedAtDesc(boolean active);

	
	/*
	 * List<Product> searchProducts(String productName, BigDecimal minPrice,
	 * BigDecimal maxPrice, LocalDateTime minPostedDate, LocalDateTime
	 * maxPostedDate);
	 * 
	 */
	
	 
	
	  @Query("SELECT p FROM Product p WHERE " +
	  "(:productName IS NULL OR p.productName LIKE %:productName%) " +
	  "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
	  "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
	  "AND (:minPostedDate IS NULL OR p.createdAt >= :minPostedDate) " +
	  "AND (:maxPostedDate IS NULL OR p.createdAt <= :maxPostedDate)")
	  List<Product> searchProducts(@Param("productName") String productName,
	  
	  @Param("minPrice") BigDecimal minPrice,
	  
	  @Param("maxPrice") BigDecimal maxPrice,
	  
	  @Param("minPostedDate") LocalDateTime minPostedDate,
	  
	  @Param("maxPostedDate") LocalDateTime maxPostedDate);
	 
	
	

}