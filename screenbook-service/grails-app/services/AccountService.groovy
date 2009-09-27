import se.jaghandlar.exceptions.UserNotFoundException
import se.jaghandlar.exceptions.IncorrectPasswordException

class AccountService {

  def authenticateService
  def mailService

  boolean transactional = true


  def createAccountWithRole(String username, String authority) {
    log.info("Creating account " + username)

    def account = new Account(username: username, passwd: authenticateService.encodePassword("läraren"), apikey: UUID.randomUUID().toString(), enabled: true)

    account.save()

    Role.findByAuthority(authority).addToPeople(account)

    return account
  }

  def createMainAccount(String username) {
    createAccountWithRole(username, "ROLE_TEACHER");
  }

  def createAdminAccount(String username) {
    createAccountWithRole(username, "ROLE_ADMIN");
  }

  def verifyLogin(String username, String password) throws UserNotFoundException, IncorrectPasswordException {
    def account = Account.findByUsername(username);
    if (account == null) {
      log.info("Bad account")
      throw new UserNotFoundException("error.incorrect.username");
    } else if (account.passwd != authenticateService.encodePassword(password)) {
      log.info("Bad password")
      throw new IncorrectPasswordException("error.incorrect.password");
    } else {
      return account.apikey
    }
    return null
  }

  def verifyApiLogin(String accountName, String apikey) {
    if (!accountName || !apikey) {
      println "accountName or apikey was not set"
      return false
    }
    def account = Account.findByUsername(accountName)
    if (account) {
      def result = account.apikey == apikey
      println "apikey was correct? " + result
      return result
    } else {
      println "no account was found"
      return false
    }
  }

  /**
   * Change password without checking previous password. This method should not be exposed to end users.
   */
  def setNewPassword(String accountName, String newPassword) {
    if (!mainAccountExists(accountName)) {
      log.warn("Could not find account ${accountName} when setting new password.");
      return;
    }

    def account = Account.findByUsername(accountName);
    account.passwd = authenticateService.encodePassword(newPassword);
  }

  /**
   * Check if the student account exists and that it is possible to login
   */
  def verifyStudentLogin(String accountName, String studentName) {
    return Student.findByUsernameAndMainAccount(studentName, accountName) != null
  }

  def verifyFreeLicences(String accountName) {
    log.info("Verifying free licenses for " + accountName)
    def account = Account.findByUsername(accountName)

    if (account == null) {
      log.warn("Could not find account ${accountName}");
      return false;
    }

    return account.hasFreeLicenses()
  }

  def mainAccountExists(String accountName) {
    def account = Account.findByUsername(accountName)
    return account != null;
  }

  def createStudentAccount(String accountName, String studentName) {
    log.info("Creating student " + studentName)

    def account = Account.findByUsername(accountName)

    def student = new Student(username: studentName, account: account)
    student.save()

    return student
  }

  def resetPassword(String accountIdentifier) {
    def account
    if(accountIdentifier.contains("@")) {
      account = Account.findByEmail(accountIdentifier)
    } else {
      account = Account.findByUsername(accountIdentifier)
    }

    if(account == null) {
      return false
    }

    def password = "password"
    account.passwd = authenticateService.encodePassword(password)
    account.save()

    mailService.sendMail {
      to account.email
      subject "Password reset"
      body "Your new password is '" + password + "'"
    }

    log.info "Password reset mail sent."

    return true
  }

}
