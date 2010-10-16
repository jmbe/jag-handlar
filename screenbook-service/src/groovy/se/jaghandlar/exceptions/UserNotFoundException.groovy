package se.jaghandlar.exceptions

class UserNotFoundException extends Exception {

  public UserNotFoundException() {
    this("error.incorrect.username");
  }

  public UserNotFoundException(String message) {
    super(message)
  }
}
