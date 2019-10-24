package com.hcl.mortgage.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mortgage.entity.LoanDetails;


@Repository
public interface LoanRepository extends JpaRepository<LoanDetails, Integer> {
		LoanDetails findByCustomerId(Integer customerId);

		List<LoanDetails> findByEmiDate(LocalDate emiDate);
}
