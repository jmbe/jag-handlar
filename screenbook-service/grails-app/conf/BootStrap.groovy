class BootStrap {

  def accountService
  def roleService
  def bookService

  def init = {servletContext ->

    /* Check or add roles. */
    ["ROLE_ADMIN", "ROLE_TEACHER"].each {
      if (!roleService.roleExists(it)) {
        log.info("Adding role ${it}")
        roleService.addRole(it)
      }
    }

    /* Check or add admin account. */
    if (!accountService.mainAccountExists("admin")) {
      log.info("Could not find admin account. Adding...")
      def account = accountService.createAdminAccount("admin", "jm.bergqvist@gmail.com")
      log.info("Created account ${account}")

      accountService.setNewPassword("admin", "admin")
    }

    /* Check or add book for Jag handlar */
    bookService.findOrCreate("jag-handlar")

  }

  def destroy = {
  }
}