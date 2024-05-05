package com.example.demo.utility;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="products")
@EntityListeners(AuditingEntityListener.class)
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productID;
	
	@Column(nullable = false)
	 private String productName;
	
	@Column(nullable = false)
	 private BigDecimal price;
	    
	    @Column(nullable = false)
	    private boolean active;
	 
	    @CreatedDate
	    @Column(name = "created_at", nullable = true, updatable = false)
	    private LocalDateTime createdAt;

	   
	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	

	

		@Override
	public String toString() {
		return "Product [productID=" + productID + ", productName=" + productName + ", price=" + price + ", active="
				+ active + ", createdAt=" + createdAt + "]";
	}

		public void setStatus(ApprovalStatus pending) {
			// TODO Auto-generated method stub
			
		}
	 

}
