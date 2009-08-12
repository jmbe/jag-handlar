class BootStrap {

     def init = { servletContext ->
        def role = new Role(authority: "ROLE_ADMIN", description: "The main admin role")
        role.save()
     }
     def destroy = {
     }
}