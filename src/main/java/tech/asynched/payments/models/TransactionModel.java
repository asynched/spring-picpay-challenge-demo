package tech.asynched.payments.models;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data()
@Entity()
@Table(name = "transactions")
public class TransactionModel {
  @Id()
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "payer_id")
  private UUID payerId;

  @Column(name = "payee_id")
  private UUID payeeId;

  private Long value;

  @Column(name = "created_at")
  @CreationTimestamp()
  private Timestamp createdAt;
}
