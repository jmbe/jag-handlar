package se.jaghandlar.web.subscribe

import javax.servlet.http.HttpServletRequest
import se.pictosys.country.api.Country
import se.pictosys.payment.web.api.DetectedCountryLoader
import se.pictosys.web.api.ScopeObjectLoader
import org.springframework.stereotype.Component
import se.pictosys.web.api.Scope

@Component("detectedCountryLoader")
class JagHandlarDetectedCountryLoader implements DetectedCountryLoader {

  def countryCodeLoader = new ScopeObjectLoader<String>("detectedCountryCode", Scope.SESSION)

  def eesCodeLoader = new ScopeObjectLoader("ees", Scope.SESSION)

  void saveDetectedCountry(HttpServletRequest request, java.lang.String countryCode) {
    countryCodeLoader.set(request, countryCode);
  }

  void saveEesCountry(HttpServletRequest request, Country country) {
    eesCodeLoader.set(request, country.isEes());
  }

  String loadDetectedCountryCode(HttpServletRequest request) {
    return countryCodeLoader.get(request);
  }
}