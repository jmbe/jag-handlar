class BootStrap {

  def accountService
  def roleService

  def init = {servletContext ->

    /* Check or add roles. */
    ["ROLE_ADMIN", "ROLE_TEACHER"].each {
      if (!roleService.roleExists(it)) {
        roleService.addRole(it);
      }
    }

    /* Check or add admin account. */
    if (!accountService.mainAccountExists("admin")) {
      log.info("Could not find admin account. Adding...")
      accountService.createAdminAccount("admin");
      accountService.setNewPassword("admin", "admin");

      new Book(name:"book1").save()
            
    }

  }
  def destroy = {
  }
}