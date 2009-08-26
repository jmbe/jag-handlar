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

    def createStudentAccount(String accountUsername, String studentUsername) {
      log.info("Creating student " + studentUsername)

	  def account = Account.findByUsername(accountUsername)

      def student = new Student(username: studentUsername, account: account)
      student.save()

      return student
    }

}
