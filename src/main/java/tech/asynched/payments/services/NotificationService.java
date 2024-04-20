package tech.asynched.payments.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import tech.asynched.payments.models.TransactionModel;

@Slf4j()
@Service()
public class NotificationService {
  public boolean notifyPayment(
      TransactionModel data) {
    var client = HttpClient.newHttpClient();
    var request = HttpRequest.newBuilder().uri(
        URI.create("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")).build();

    try {
      var response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        return false;
      }

      var mapper = new ObjectMapper();

      var message = mapper.readValue(response.body(), NotificationResponse.class);

      return message.message();
    } catch (Exception err) {
      log.error("Failed to notify payment: {}", err.getMessage());
      return false;
    }
  }
}

record NotificationResponse(
    boolean message) {
}