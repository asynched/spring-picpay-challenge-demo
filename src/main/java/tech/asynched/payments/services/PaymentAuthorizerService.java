package tech.asynched.payments.services;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Service()
@Slf4j()
public class PaymentAuthorizerService {
  public boolean isAuthorized() {
    var client = HttpClient.newHttpClient();

    var request = HttpRequest.newBuilder()
        .uri(URI.create("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc"))
        .header("Accept", "application/json")
        .build();

    try {
      var response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() != HttpURLConnection.HTTP_OK) {
        return false;
      }

      var body = response.body();

      var mapper = new ObjectMapper();

      var message = mapper.readValue(body, AuthorizerResponse.class);

      if (message.message().equals("Autorizado")) {
        return true;
      }

      return false;
    } catch (Exception err) {
      log.error("Failed to authorize payment: {}", err.getMessage());
      return false;
    }

  }
}

record AuthorizerResponse(
    String message) {
}