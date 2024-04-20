package tech.asynched.payments.dto;

import java.util.UUID;

public record CreateTransactionDto(
    UUID payerId,
    UUID payeeId,
    Long value) {

}
