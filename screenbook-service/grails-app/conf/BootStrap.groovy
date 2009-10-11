class BootStrap {

  def accountService
  def roleService

  def init = {servletContext ->

    /* Check or add roles. */
    ["ROLE_ADMIN", "ROLE_TEACHER"].each {
      if (!roleService.roleExists(it)) {
        roleService.addRole(it)
      }
    }

    /* Check or add admin account. */
    if (!accountService.mainAccountExists("admin")) {
      log.info("Could not find admin account. Adding...")
      def account = accountService.createAdminAccount("admin", "jm.bergqvist@gmail.com")
      account.apikey = "a1984cf7-c2eb-400c-abe6-a1ee9e151ee1"
      account.save(flush:true)
      accountService.setNewPassword("admin", "admin")

      new Book(name:"book1").save()
            
    }

  }
  def destroy = {
  }
}