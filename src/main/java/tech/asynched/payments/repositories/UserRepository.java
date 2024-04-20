package tech.asynched.payments.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.asynched.payments.models.UserModel;

@Repository()
public interface UserRepository extends JpaRepository<UserModel, UUID> {
  public Optional<UserModel> findByEmail(String email);

  public Optional<UserModel> findByDocument(String document);
}
