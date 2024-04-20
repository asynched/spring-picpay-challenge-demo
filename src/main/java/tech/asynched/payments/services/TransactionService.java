package tech.asynched.payments.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import tech.asynched.payments.dto.CreateTransactionDto;
import tech.asynched.payments.models.TransactionModel;
import tech.asynched.payments.repositories.TransactionRepository;
import tech.asynched.payments.repositories.UserRepository;
import tech.asynched.payments.utils.Retry;

@Service()
public class TransactionService {
  private final UserRepository userRepository;
  private final TransactionRepository transactionRepository;
  private final PaymentAuthorizerService paymentAuthorizerService;
  private final NotificationService notificationService;

  @Autowired()
  public TransactionService(
      TransactionRepository transactionRepository,
      UserRepository userRepository,
      PaymentAuthorizerService paymentAuthorizerService,
      NotificationService notificationService) {
    this.transactionRepository = transactionRepository;
    this.userRepository = userRepository;
    this.paymentAuthorizerService = paymentAuthorizerService;
    this.notificationService = notificationService;
  }

  @Transactional()
  public TransactionModel createTransaction(CreateTransactionDto data) {
    var payer = userRepository.findById(data.payerId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payer does not exist"));
    var payee = userRepository.findById(data.payeeId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payee does not exist"));

    if (payer.getBalance() < data.value()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
    }

    if (payer.isShopkeeper()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shopkeepers cannot make transactions");
    }

    if (!paymentAuthorizerService.isAuthorized()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Payment not authorized");
    }

    var model = new TransactionModel();

    model.setPayerId(payer.getId());
    model.setPayeeId(payee.getId());
    model.setValue(data.value());

    payer.setBalance(payer.getBalance() - data.value());
    payee.setBalance(payee.getBalance() + data.value());

    userRepository.save(payer);
    userRepository.save(payee);

    var transaction = transactionRepository.save(model);

    var retryable = new Retry<Boolean>(() -> {
      if (!notificationService.notifyPayment(transaction)) {
        throw new RuntimeException("Failed to notify payment");
      }

      return true;
    });

    if (!retryable.run(3)) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to notify payment");
    }

    return transaction;
  }
}
