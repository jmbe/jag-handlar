class AccountService {

    def authenticateService

    boolean transactional = true

    def createMainAccount(String username) {
      log.info("Creating account " + username)

      //TODO Set correct password
      def account = new Account(username: username, passwd: authenticateService.encodePassword("aaa"), enabled: true)
      account.save()

      Role.findByAuthority("ROLE_ADMIN").addToPeople(account)
      
      return account
    }

	def verifyLogin(String username, String password) {
		def account = Account.findByUsername(username);
		return account != null && account.passwd == authenticateService.encodePassword(password)
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
		return account.hasFreeLicenses()
	}

    def createStudentAccount(String mainAccountName, String studentAccountName) {
      log.info("Creating student " + studentAccountName)

	  def account = Account.findByUsername(mainAccountName)

      def student = new Student(username: studentAccountName, account: account)
      student.save()

      return student
    }

}
