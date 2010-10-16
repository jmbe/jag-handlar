package se.jaghandlar.exceptions

public class IncorrectPasswordException extends Exception {

  public IncorrectPasswordException() {
    this("error.incorrect.password");
  }

  public IncorrectPasswordException(String message) {
    super(message)
  }
}
