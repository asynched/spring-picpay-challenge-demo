package tech.asynched.payments.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tech.asynched.payments.dto.CreateShopkeeperDto;
import tech.asynched.payments.dto.CreateUserDto;
import tech.asynched.payments.models.UserModel;
import tech.asynched.payments.repositories.UserRepository;

@Service()
public class UserService {
  private final UserRepository userRepository;
  private final HashService hashService;

  @Autowired()
  public UserService(UserRepository userRepository,
      HashService hashService) {
    this.userRepository = userRepository;
    this.hashService = hashService;
  }

  public UserModel createUser(CreateUserDto data) {
    if (userRepository.findByEmail(data.email()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
    }

    if (userRepository.findByDocument(data.cpf()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
    }

    var user = new UserModel();

    user.setName(data.name());
    user.setEmail(data.email());
    user.setPassword(hashService.hash(data.password()));
    user.setDocument(data.cpf());
    user.setShopkeeper(false);
    user.setBalance(100_000);

    return userRepository.save(user);
  }

  public UserModel createShopkeeper(CreateShopkeeperDto data) {
    var shopkeeper = new UserModel();

    shopkeeper.setName(data.name());
    shopkeeper.setEmail(data.email());
    shopkeeper.setPassword(data.password());
    shopkeeper.setDocument(data.cnpj());
    shopkeeper.setShopkeeper(true);
    shopkeeper.setBalance(100_000);

    return userRepository.save(shopkeeper);
  }

  public Optional<UserModel> findById(UUID id) {
    return userRepository.findById(id);
  }
}
