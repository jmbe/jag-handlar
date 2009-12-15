import se.pictosys.country.CountryLoadFailedException
import se.pictosys.country.api.Country
import se.pictosys.country.UneceCountryRepository
import se.pictosys.country.StateRepository

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


    try {
      log.info("Loading countries...");
      Collection<Country> countries = UneceCountryRepository.getInstance().getAll();
      if (countries.size() < 10) {
        log.error("Country collection is very small, "
                + "just ${countries.size()} entries.");
      }

      servletContext.countries = countries
      log.info("Loaded ${countries.size()} countries.");
    } catch (CountryLoadFailedException e) {
      log.error("Could not load countries.");
    }


    log.info("Loading states...")
    servletContext.canadianStates = StateRepository.getCanadianStates()
    servletContext.usStates = StateRepository.getUsStates()

  }

  def destroy = {
  }
}
