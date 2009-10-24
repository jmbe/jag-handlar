package se.jaghandlar.web.subscribe


import javax.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import se.pictosys.web.api.Scope
import se.pictosys.web.api.ScopeObjectLoader

@Component("userItemLoader")
class UserItemLoader implements se.pictosys.payment.web.api.UserItemLoader {

  def userItemLoader = new ScopeObjectLoader<Username>("useritem", Scope.SESSION);

  void saveUserAsUserItem(HttpServletRequest request) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void saveUserItem(HttpServletRequest request, String username) {
    userItemLoader.set(request, new Username({username: username}));
  }

  boolean hasUserItem(HttpServletRequest request) {
    return !userItemLoader.isEmpty(request);
  }

  void removeUserItem(javax.servlet.http.HttpServletRequest request) {
    userItemLoader.remove(request);
  }

  java.lang.String getUserItemUsername(HttpServletRequest request) {
    return userItemLoader.get(request).getUsername();
  }
}
