package com.hcl.mortgage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.hcl.mortgage.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	Account findByCustomerId(Integer customerId);

}
