package com.hcl.mortgage.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.entity.LoanDetails;
import com.hcl.mortgage.entity.Transaction;
import com.hcl.mortgage.repository.AccountRepository;
import com.hcl.mortgage.repository.LoanRepository;
import com.hcl.mortgage.repository.TransactionRepository;

/**
 * @author Shilendra
 *
 */
@Component
public class Scheduler {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private AccountRepository accountRepository;
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
	/**
	 * Transaction Scheduler
	 *
	 */

	
	@Scheduled(cron = "0 0/1 * * * *")
	public void emiScheduler() {
		
		logger.info("Scheduler for deductiong amount");
		List<LoanDetails> loans = loanRepository.findByEmiDate(LocalDate.now());
		loans.stream().forEach(loan -> {
			String status="paid";
			Boolean loanCleared=false;
			Account accounts = accountRepository.findByCustomerId(loan.getCustomerId());

			if (loan.getOutStandingBalance() == 0) {
				logger.info("Loan Cleared");
				loanCleared=true;
			}
			else {
				if (accounts.getAccountBalance() < loan.getEmi()) {
					loan.setOutStandingBalance(loan.getOutStandingBalance()+loan.getEmi()*0.5);
					status="unpaid";
					logger.info("Loan remains unpaid due to insufficient balance. You have been penalised 5% of your EMI");
								
				}
				if(status.equals("paid")&& !loanCleared) {
					loan.setOutStandingBalance(loan.getOutStandingBalance() - loan.getEmi());
					loanRepository.save(loan);
					accounts.setAccountBalance(accounts.getAccountBalance() - loan.getEmi());
					accountRepository.save(accounts);
					Transaction transaction = new Transaction();
					transaction.setAmountDeducted(loan.getEmi());
					transaction.setCustomerId(loan.getCustomerId());
					transaction.setDescription("Emi of " + loan.getEmi() + " has been deducted");
					transaction.setTransactionDate(LocalDateTime.now());
					transactionRepository.save(transaction);

				}
				

			}
					});

	}

}
