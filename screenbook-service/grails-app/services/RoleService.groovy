import se.jaghandlar.Role;

class RoleService {

  static transactional = true



  def addRole(String name, String description = "") {
    if (roleExists(name)) {
      throw new IllegalStateException("Role ${name} already exists.");
    }

    def role = new Role(authority: name, description: description)
    role.save()
    return role;
  }

  def roleExists(String name) {
    return Role.findByAuthority(name) != null;
  }

}