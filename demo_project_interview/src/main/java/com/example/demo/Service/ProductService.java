package com.example.demo.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.ExceptionHandler.ValidationException;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.utility.ApprovalStatus;
import com.example.demo.utility.Product;

@Service
public class ProductService {
	
	 @Autowired
	    private ProductRepository productRepository;
	    
	   
	    
	    @Autowired
	    private ApprovalQueueService approvalQueueService;
	    
	    public Product saveProductDetails(Product product) throws ValidationException {
	        BigDecimal price =  product.getPrice();
	        if (price.compareTo(BigDecimal.valueOf(10000)) > 0) {
	            throw new ValidationException("601","The product price should not exceed 10000");
	        }
	        
	        
	      
	        Product prod = new Product();
	        product.setProductName(product.getProductName());
	        product.setPrice(price);
	        product.setActive(true);
	        
	        
	        if (price.compareTo(BigDecimal.valueOf(5000)) > 0) {
	            prod.setStatus(ApprovalStatus.PENDING);
	            approvalQueueService.addToApprovalQueue(product);
	        } else {
	            product.setStatus(ApprovalStatus.APPROVED);
	        }
	        
	        return productRepository.save(product);
	        
	        
	    }

		public void deleteProduct(Integer productID) {
			Product product1=null;
			
				product1 = productRepository.findById(productID)
				        .orElseThrow(() -> new NoSuchElementException());
				productRepository.delete(product1);
		       
			
		}

		public Product updateProduct(Integer productId, Product productDTO) {


			 Product existingProduct=null;
			try {
				existingProduct = productRepository.findById(productId)
				            .orElseThrow(() -> new NotFoundException());
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		        BigDecimal newPrice = productDTO.getPrice();
		        BigDecimal previousPrice = existingProduct.getPrice();

		        if (newPrice.compareTo(previousPrice.multiply(BigDecimal.valueOf(1.5))) > 0) {
		            existingProduct.setStatus(ApprovalStatus.PENDING);
		            approvalQueueService.addToApprovalQueue(existingProduct);
		        }

		        existingProduct.setProductName(productDTO.getProductName());
		        existingProduct.setPrice(newPrice);
			return productRepository.save(existingProduct);
		}

		
		
		  public List<Product> listActiveProductsByLatestFirst() {
		  
		  return productRepository.findByActiveOrderByCreatedAtDesc(true);
		  }

		
			
			  public List<Product> searchProducts(String productName, BigDecimal minPrice,
			  BigDecimal maxPrice, LocalDateTime minPostedDate, LocalDateTime
			  maxPostedDate)
			  
			  { 
				  return productRepository.searchProducts(productName, minPrice, maxPrice,
			  minPostedDate, maxPostedDate); 
			  }
			 
		 

		
}
