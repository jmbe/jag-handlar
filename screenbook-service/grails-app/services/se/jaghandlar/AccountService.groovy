package se.jaghandlar
import cr.co.arquetipos.password.PasswordTools
import org.apache.commons.lang.StringUtils

import se.jaghandlar.Account;
import se.jaghandlar.Role;
import se.jaghandlar.Student;
import se.jaghandlar.exceptions.IncorrectPasswordException
import se.jaghandlar.exceptions.UserNotFoundException

class AccountService {

    def authenticateService
    def mailService

    static transactional = true


    def accountRemotingTest() {
        log.info "Account remoting test"
        def account = new Account(username: 'user', passwd: 'password', apikey: UUID.randomUUID().toString(), enabled: true, email: 'email@t.se')
        return account
    }

    def helloRemotingTest() {
        log.info "Hello remoting test"
        "Hello world!"
    }

    def parameterRemotingTest(String name) {
        log.info "Received parameter ${name}"

        def t = "Hi ${name}";
        // must use actual String so that Flex can read it.
        return t as String

    }


    def createAccountWithRole(String username, String email, String authority) {
        log.info("Creating account " + username)

        def account = new Account(username: username, passwd: authenticateService.encodePassword("läraren"), apikey: UUID.randomUUID().toString(), enabled: true, email: email)

        account.save()


        log.info "Adding role ${authority} to account ${username}."

        Role role = Role.findByAuthority(authority)
        log.debug "Found role ${role}"
        role.addToPeople(account)

        return account
    }

    def createMainAccount(String username, String email, boolean newsletterSubscribe = false) {
        def account = createAccountWithRole(username, email, "ROLE_TEACHER");
        account.newsletterSubscribe = newsletterSubscribe
    }

    def createAdminAccount(String username, String email) {
        def account = createAccountWithRole(username, email, "ROLE_ADMIN");
        account.newAccount = false
    }

    def verifyLogin(String username, String password) throws UserNotFoundException, IncorrectPasswordException {
        if (StringUtils.isEmpty(username)) {
            log.warn "Username was empty"
            throw new UserNotFoundException();
        }

        if (StringUtils.isEmpty(password)) {
            log.warn "Password was empty for user ${username}"
            throw new IncorrectPasswordException();
        }

        def account = Account.findByUsername(username);
        if (account == null) {
            log.info("Bad account ${username}")
            throw new UserNotFoundException();
        } else if (account.passwd != authenticateService.encodePassword(password)) {
            log.info("Bad password for ${username}")
            throw new IncorrectPasswordException();
        } else {
            return account.apikey
        }
    }

    def isValidLogin(String username, String password) {
        try {
            verifyLogin(username, password)
            return true
        } catch (UserNotFoundException e) {
            /* fall through */
        } catch (IncorrectPasswordException e) {
            /* fall through */
        }

        return false
    }

    def changePassword(accountName, oldPassword, newPassword) {
        def account = Account.findByUsername(accountName);
        if (account == null) {
            log.warn("Tried to change password for ${accountName}, but that account cannot be found.");
            return false
        }

        if (account.passwd != authenticateService.encodePassword(oldPassword)) {
            log.warn("Tried to change password for ${accountName}, but given password doesn't match stored password.")
            return false
        }

        log.info("Changing password for ${accountName}.")
        account.passwd = authenticateService.encodePassword(newPassword)
        return true
    }

    def verifyApiLogin(String accountName, String apikey) {
        if (!accountName || !apikey) {
            println "accountName or apikey was not set"
            return false
        }
        def account = Account.findByUsername(accountName)
        if (account) {
            def result = account.apikey == apikey
            log.info "apikey was correct for ${accountName}: " + result
            return result
        } else {
            log.info "no account ${accountName} was found while verifying API key"
            return false
        }
    }

    /**
     * Change password without checking previous password. This method should not be exposed to end users.
     */
    def setNewPassword(String accountName, String newPassword) {
        if (!mainAccountExists(accountName)) {
            log.warn("Could not find account ${accountName} when setting new password.");
            return;
        }

        def account = Account.findByUsername(accountName);
        account.passwd = authenticateService.encodePassword(newPassword);
    }

    /**
     * Check if the student account exists and that it is possible to login
     */
    def verifyStudentLogin(String accountName, String studentName) {
        return Student.findByUsernameAndMainAccount(studentName, accountName) != null
    }

    def verifyFreeLicences(String accountName) {
        log.info("Verifying free licenses for " + accountName)
        def account = Account.findByUsername(accountName)

        if (account == null) {
            log.warn("Could not find account ${accountName}");
            return false;
        }

        return account.hasFreeLicenses()
    }

    def mainAccountExists(String accountName) {
        def account = Account.findByUsername(accountName)
        return account != null;
    }

    def createStudentAccount(String accountName, String studentName, Boolean screenKeyboard = false) {
        log.info("Creating student " + studentName)

        def account = Account.findByUsername(accountName)

        def student = new Student(username: studentName, account: account, screenKeyboard: screenKeyboard)
        student.save()

        return student
    }

    def emailExists(String email) {
        return Account.findByEmail(email) != null
    }

    def resetPassword(String accountIdentifier) {
        def account
        if (accountIdentifier.contains("@")) {
            account = Account.findByEmail(accountIdentifier)
        } else {
            account = Account.findByUsername(accountIdentifier)
        }

        if (account == null) {
            return false
        }


        def password = generateNewPassword()
        account.passwd = authenticateService.encodePassword(password)
        account.save()

        mailService.sendMail {
            to account.email
            subject "Jag handlar, lösenordsåterställning"
            body """\
Bäste kund hos Jag handlar,

Detta är dina nya inloggningsuppgifter:

Användarnamn: ${account.username}
Lösenord: ${password}
      """
        }

        log.info "Password reset mail sent to " + account.email + "."

        return true
    }

    def clearBookmarkReminder(String username) {
        def account = Account.findByUsername(username)

        if (account != null) {
            account.showBookmarkReminder = false;
        }
    }

    /**
     * Generates a new password
     *
     * @return password in plain text
     */
    def generateNewPassword() {
        PasswordTools.generateRandomPassword(8, "abcdefghjkmnpqrstuwxz23456789")
    }

    def getNumberOfLicenses(def username) {
        def account = Account.findByUsername(username)
        account.getNumberOfLicenses()
    }

    /**
     * @return true if changes were successfully saved
     */
    def changeContactDetails(def username, def contactName, def email, def phone, boolean newsletterSubscribe) {
        def account = Account.findByUsername(username)

        account.contactPerson = contactName
        account.email = email;
        account.phoneNumber = phone;
        account.newsletterSubscribe = newsletterSubscribe

        if (!account.save()) {
            log.warn "Problems saving contact details for account ${username}"
            account.errors.allErrors.each { log.warn it }

            return false
        }

        return true
    }
}
