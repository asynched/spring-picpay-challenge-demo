package tech.asynched.payments.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.asynched.payments.models.TransactionModel;

@Repository()
public interface TransactionRepository extends JpaRepository<TransactionModel, UUID> {

}
