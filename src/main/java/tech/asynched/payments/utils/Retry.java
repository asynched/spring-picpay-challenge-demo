package tech.asynched.payments.utils;

public class Retry<T> {
  private final Retryable<T> retryable;

  public Retry(Retryable<T> retryable) {
    this.retryable = retryable;
  }

  public T run(int times) {
    for (int i = 0; i < times; i++) {
      try {
        return retryable.run();
      } catch (Exception e) {
        if (i == times - 1) {
          throw e;
        }
      }
    }

    throw new RuntimeException("Unreachable code");
  }
}
