package tech.asynched.payments.services;

import java.security.MessageDigest;

import org.springframework.stereotype.Service;

@Service()
public class HashService {
  public String hash(String password) {
    try {
      var digest = MessageDigest.getInstance("SHA-256");

      var bytes = digest.digest(password.getBytes());

      var hexString = new StringBuilder();

      for (var byt : bytes) {
        var hex = Integer.toHexString(0xff & byt);

        if (hex.length() == 1) {
          hexString.append('0');
        }

        hexString.append(hex);
      }

      return hexString.toString();

    } catch (Exception exception) {
      throw new RuntimeException("Unreachable code");
    }
  }
}
