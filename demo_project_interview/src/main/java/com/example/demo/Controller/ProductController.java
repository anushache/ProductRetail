package com.example.demo.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.ExceptionHandler.ValidationException;
import com.example.demo.Service.ApprovalQueueService;
import com.example.demo.Service.ProductService;
import com.example.demo.utility.ApprovalQueue;
import com.example.demo.utility.Product;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	ProductService productService;

	@Autowired
	ApprovalQueueService approvalQueueService;

	@PostMapping("/saveproduct")
	public ResponseEntity<?> saveProduct(@RequestBody Product product) {

			Product prod = productService.saveProductDetails(product);
		
		return new ResponseEntity<Product>(prod,HttpStatus.CREATED);
	}

	@GetMapping("/listActiveProductsByLatestFirst")
	public ResponseEntity<?> listActiveProducts() {
		try {
			List<Product> products = productService.listActiveProductsByLatestFirst();
			return ResponseEntity.ok(products);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve active products");
		}
	}

	@DeleteMapping("/ProductId/{productID}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer productID) {
		
			productService.deleteProduct(productID);
			return ResponseEntity.ok("Product deleted successfully");
		

	}

	
	  
	  @GetMapping("/search")
	  public ResponseEntity<?> searchProducts(
	  
	  @RequestParam(required = true) String productName,
	  
	  @RequestParam(required = false) BigDecimal minPrice,
	  
	  @RequestParam(required = false) BigDecimal maxPrice,
	  
	  @RequestParam(required = false) LocalDateTime minPostedDate,
	  
	  @RequestParam(required = false) LocalDateTime maxPostedDate) { 
		  try {
	  List<Product> products = productService.searchProducts(productName, minPrice,
	  maxPrice, minPostedDate, maxPostedDate); 
	  
	  return ResponseEntity.ok(products);
	  } catch (Exception e) { return
	  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
	  body("Failed to search products"); } }
	  
	 
	@PutMapping("/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody Product productDTO) {
		try {
			Product updatedProduct = productService.updateProduct(productId, productDTO);
			return ResponseEntity.ok(updatedProduct);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update product");
		}
	}

	@GetMapping("/approval-queue")
	public ResponseEntity<?> getProductsInApprovalQueue() {
		try {
			List<ApprovalQueue> approvalQueueItems = approvalQueueService.getProductsInApprovalQueue();
			return ResponseEntity.ok(approvalQueueItems);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to get products in approval queue");
		}
	}

	@PutMapping("/approval-queue/{id}")
	public ResponseEntity<?> approveProduct(@PathVariable Integer id) {
		try {
			approvalQueueService.approveProduct(id);
			return ResponseEntity.ok("Product approved successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to approve product");
		}
	}

	@PutMapping("/approval-queue/{id}/reject")
	public ResponseEntity<?> rejectProduct(@PathVariable Integer id) {
		try {
			approvalQueueService.rejectProduct(id);
			return ResponseEntity.ok("Product rejected successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reject product");
		}
	}
}