package se.jaghandlar.web.subscribe

import javax.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import se.pictosys.account.api.Username
import se.pictosys.web.api.Scope
import se.pictosys.web.api.ScopeObjectLoader

@Component("userItemLoader")
class UserItemLoader implements se.pictosys.payment.web.api.UserItemLoader {

  def log = LoggerFactory.getLogger(UserItemLoader.class)

  def userItemLoader = new ScopeObjectLoader<Username>("useritem", Scope.SESSION);

  void saveUserAsUserItem(HttpServletRequest request) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void saveUserItem(HttpServletRequest request, Username username) {
    log.debug "Saving user item ${username}"
    userItemLoader.set(request, username);
  }

  boolean hasUserItem(HttpServletRequest request) {
    log.debug "Checking if user item exists in session."
    return !userItemLoader.isEmpty(request);
  }

  void removeUserItem(javax.servlet.http.HttpServletRequest request) {
    log.debug "Removing user item."
    userItemLoader.remove(request);
  }

  Username getUserItemUsername(HttpServletRequest request) {
    log.debug "Returning user item as username."
    return userItemLoader.get(request);
  }
}
