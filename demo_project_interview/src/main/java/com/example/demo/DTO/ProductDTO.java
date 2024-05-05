package com.example.demo.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {

	
	private String name;
    private BigDecimal price;
    private boolean active;
    
}
