package tech.asynched.payments.models;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data()
@Entity()
@Table(name = "users")
public class UserModel {
  @Id()
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false, length = 255)
  private String name;

  @Column(nullable = false, length = 32, unique = true)
  private String document;

  @Column(nullable = false, length = 255, unique = true)
  private String email;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(nullable = false)
  private boolean shopkeeper;

  @Column(nullable = false)
  private long balance;

  @Column(name = "created_at")
  @CreationTimestamp()
  private Timestamp createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp()
  private Timestamp updatedAt;
}
