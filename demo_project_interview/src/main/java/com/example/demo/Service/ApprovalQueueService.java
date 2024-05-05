package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.ApprovalQueueRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.utility.Product;
import com.example.demo.utility.ApprovalQueue;
import com.example.demo.utility.ApprovalStatus;

@Service
public class ApprovalQueueService {

	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private ApprovalQueueRepository approvalQueueRepository;
    
    public void addToApprovalQueue(Product product) {
    	
    	ApprovalQueue approvalQueue = new ApprovalQueue();
        approvalQueue.setProduct(product);
        approvalQueue.setRequestDate(LocalDateTime.now());
        approvalQueue.setStatus(ApprovalStatus.PENDING);
        approvalQueueRepository.save(approvalQueue);
    }

	public List<ApprovalQueue> getProductsInApprovalQueue() {
		 List<ApprovalQueue> approvalQueueItems = approvalQueueRepository.findAllByOrderByRequestDateAsc();
	        List<ApprovalQueue> approvalQueueItemDTOs = new ArrayList<>();

	        for (ApprovalQueue approvalQueueItem : approvalQueueItems) {
	            ApprovalQueue approvalQueueItemDTO = new ApprovalQueue();
	            approvalQueueItemDTO.setId(approvalQueueItem.getId());
	            approvalQueueItemDTO.setProduct(approvalQueueItem.getProduct().getProductID());
	            approvalQueueItemDTO.setProductName(approvalQueueItem.getProduct().getPrice());
	            approvalQueueItemDTO.setRequestDate(approvalQueueItem.getRequestDate());
	            approvalQueueItemDTO.setStatus(approvalQueueItem.getStatus());
	            approvalQueueItemDTOs.add(approvalQueueItemDTO);
	        }

	        return approvalQueueItemDTOs;
	}

	
	
	public void approveProduct(Integer id) {
		ApprovalQueue approvalQueue=null;
		try {
			approvalQueue = approvalQueueRepository.findById(id)
			        .orElseThrow(() -> new NotFoundException());
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Product product = approvalQueue.getProduct();
        product.setStatus(ApprovalStatus.APPROVED);
        productRepository.save(product);

        approvalQueueRepository.delete(approvalQueue);

		
	}

	public void rejectProduct(Integer approvalId) {
		ApprovalQueue approvalQueue=null;
		try {
			approvalQueue = approvalQueueRepository.findById(approvalId)
			        .orElseThrow(() -> new NotFoundException());
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        approvalQueueRepository.delete(approvalQueue);
		
	}
}
