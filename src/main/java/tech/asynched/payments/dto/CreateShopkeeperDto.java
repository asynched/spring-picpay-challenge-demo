package tech.asynched.payments.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateShopkeeperDto(
    @NotBlank() @NotNull() String name,
    @Email() @NotNull() String email,
    @NotBlank() @NotNull() @Length(min = 8, max = 32) String password,
    @NotBlank() String cnpj) {

}
