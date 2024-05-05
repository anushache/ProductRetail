package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.utility.ApprovalQueue;

@Repository
public interface ApprovalQueueRepository extends JpaRepository<ApprovalQueue,Integer> {

	List<ApprovalQueue> findAllByOrderByRequestDateAsc();

	

	

}
