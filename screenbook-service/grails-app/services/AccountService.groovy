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

    def createStudentAccount(String username) {
      log.info("Creating student " + username)

      def student = new Student(username: username)
      student.save()

      return student
    }

}
