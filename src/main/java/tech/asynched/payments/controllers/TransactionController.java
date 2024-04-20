package tech.asynched.payments.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.asynched.payments.dto.CreateTransactionDto;
import tech.asynched.payments.models.TransactionModel;
import tech.asynched.payments.services.TransactionService;

@RestController()
@RequestMapping("/v1/transactions")
public class TransactionController {
  private final TransactionService transactionService;

  @Autowired()
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping()
  public TransactionModel createTransaction(
      @RequestBody() CreateTransactionDto data) {
    return transactionService.createTransaction(data);
  }
}
