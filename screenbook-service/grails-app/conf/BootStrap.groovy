class BootStrap {

  def accountService

  def init = {servletContext ->
    def role = new Role(authority: "ROLE_ADMIN", description: "The main admin role")
    role.save()


    if (!accountService.mainAccountExists("admin")) {
      log.info("Could not find admin account. Adding...")
      accountService.createMainAccount("admin");
      accountService.setNewPassword("admin", "admin");

    }

  }
  def destroy = {
  }
}