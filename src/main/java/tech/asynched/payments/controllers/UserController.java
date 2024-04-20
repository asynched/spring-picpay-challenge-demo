package tech.asynched.payments.controllers;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tech.asynched.payments.dto.CreateShopkeeperDto;
import tech.asynched.payments.dto.CreateUserDto;
import tech.asynched.payments.models.UserModel;
import tech.asynched.payments.services.UserService;

@RestController()
@RequestMapping("/v1/users")
public class UserController {
  private final UserService userService;

  @Autowired()
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping()
  public ResponseEntity<UserResponse> createUser(@RequestBody() @Valid() CreateUserDto data) {
    return ResponseEntity.status(HttpStatus.CREATED).body(
        UserResponse.fromUser(userService.createUser(data)));
  }

  @PostMapping("/shopkeeper")
  public ResponseEntity<UserResponse> createShopkeeper(@RequestBody() @Valid() CreateShopkeeperDto data) {

    return ResponseEntity.status(HttpStatus.CREATED).body(
        UserResponse.fromUser(userService.createShopkeeper(data)));
  }

  record UserResponse(
      UUID id,
      String name,
      String email,
      boolean shopkeeper,
      Timestamp createdAt,
      Timestamp updatedAt) {
    static UserResponse fromUser(UserModel user) {
      return new UserResponse(
          user.getId(),
          user.getName(),
          user.getEmail(),
          user.isShopkeeper(),
          user.getCreatedAt(),
          user.getUpdatedAt());
    }
  }
}
