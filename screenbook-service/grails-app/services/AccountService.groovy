import se.jaghandlar.exceptions.UserNotFoundException
import se.jaghandlar.exceptions.IncorrectPasswordException

class AccountService {

    def authenticateService

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

    def verifyApiLogin(String username, String apikey) {
      if(!username || !apikey) {
        println "username or apikey was not set"
        return false
      }
      def account = Account.findByUsername(username)
      if(account) {
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
	def verifyStudentLogin (String mainAccountName, String studentAccountName) {
		return Student.findByUsernameAndMainAccount(studentAccountName, mainAccountName) != null
	}

	def verifyFreeLicences (String mainAccountName) {
		log.info("Verifying free licenses for " + mainAccountName)
		def account = Account.findByUsername(mainAccountName)

        if (account == null) {
            log.warn("Could not find account ${mainAccountName}");
            return false;
        }

		return account.hasFreeLicenses()
	}

    def mainAccountExists(String mainAccountName) {
        def account = Account.findByUsername(mainAccountName)
        return account != null;
    }

    def createStudentAccount(String mainAccountName, String studentAccountName) {
      log.info("Creating student " + studentAccountName)

	  def account = Account.findByUsername(mainAccountName)

      def student = new Student(username: studentAccountName, account: account)
      student.save()

      return student
    }

}
