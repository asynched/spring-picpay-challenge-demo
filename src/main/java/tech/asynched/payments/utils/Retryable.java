package tech.asynched.payments.utils;

public interface Retryable<T> {
  public T run();
}
