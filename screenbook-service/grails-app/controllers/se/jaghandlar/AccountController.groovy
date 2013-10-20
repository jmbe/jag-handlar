package se.jaghandlar
import org.apache.commons.lang.StringUtils

import se.jaghandlar.Account;

//@Secured(['ROLE_ADMIN'])
class AccountController {

    def authenticateService
    def accountService
    def purchaseService

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']


    def index = {
      log.info "Index method called"
      redirect(action:list,params:params)
    }


    def activatePurchase = {
        log.info "Activating purchase ${params.purchaseId} for account ${params.accountId}"

        purchaseService.activatePurchase(params.purchaseId as Long)

        def accountInstance = Account.get( params.id )
        flash.message = "Kontouppgifter har mailats till kunden."
        redirect(action:show, id:params.accountId)
    }

    def list = {
        log.info "Listing all accounts"

        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ title:'Alla konton', accountInstanceList: Account.list( params ), accountInstanceTotal: Account.count() ]
    }

    def listNew = {
      log.info "Listing new accounts"

      params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
      def accounts = Account.findAllByNewAccount(true)
      render (view:'list', model:[title: 'Nya konton', accountInstanceList: accounts, accountInstanceTotal: accounts.size()])
    }

    def listNewsletter = {
      log.info "Listing accounts interested in newsletter"

      params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
      def accounts = Account.findAllByNewsletterSubscribe(true)
      render (view:'list', model:[newsletter:true, title: 'Intresserade av nyhetsbrev', accountInstanceList: accounts, accountInstanceTotal: accounts.size()])
    }

    def exportSubscriptionList = {

      log.info "Exporting newsletter subscription list"

      StringWriter writer = new StringWriter();
      def accounts = Account.findAllByNewsletterSubscribe(true)

      accounts.collect {it.email}.unique().sort().eachWithIndex { email, index ->
        writer.print "${email};"
        if ((index + 1) % 5 == 0) {
          writer.println()
        }
      }

      response.setHeader "Content-disposition", "attachment; filename=jag-handlar-nyhetsbrev.txt"
      render contentType : "text/plain", text: writer.toString()
    }

    def show = {


        def accountInstance = Account.get( params.id )

        if(!accountInstance) {
            flash.message = "Account not found with id ${params.id}"
            redirect(action:list)
        } else {
          log.info "Displaying account ${accountInstance.username}."
          return [ accountInstance : accountInstance ]
        }
    }

    def delete = {
        def accountInstance = Account.get( params.id )

        log.info "Deleting account ${params.id}"

        if (accountInstance) {
            try {
                log.info "Removing current roles"
                accountInstance.authorities.each {
                  it.removeFromPeople accountInstance
                }

                accountInstance.delete(flush:true)
                def message = "Account ${params.id} deleted"
                flash.message = message
                log.info message
                redirect(action:list)
            } catch(org.springframework.dao.DataIntegrityViolationException e) {
                def message = "Account ${params.id} could not be deleted"
                flash.message = message
                log.warn message
                redirect(action:show, id:params.id)
            }
        } else {
            def message = "Account not found with id ${params.id}"
            flash.message = message
            log.warn message
            redirect(action:list)
        }
    }

    def edit = {
        def accountInstance = Account.get( params.id )

        if(!accountInstance) {
            flash.message = "Account not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ accountInstance : accountInstance ]
        }
    }

    def update = {
        def accountInstance = Account.get( params.id )
        if(accountInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(accountInstance.version > version) {
                    
                    accountInstance.errors.rejectValue("version", "account.optimistic.locking.failure", "Another user has updated this Account while you were editing.")
                    render(view:'edit',model:[accountInstance:accountInstance])
                    return
                }
            }

            if (StringUtils.isEmpty(params.passwd)) {
              log.info "Passwd was empty. Will not change password."
              params.remove("passwd")
            } else {
              log.info "Changing password for ${accountInstance.id}."
              params.passwd = authenticateService.encodePassword(params.passwd)
            }

            accountInstance.properties = params

            if(!accountInstance.hasErrors() && accountInstance.save()) {
                flash.message = "Account ${params.id} updated"
                redirect(action:show,id:accountInstance.id)
            }
            else {
                render(view:'edit',model:[accountInstance:accountInstance])
            }
        }
        else {
            flash.message = "Account not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def accountInstance = new Account()
        accountInstance.properties = params
        return ['accountInstance':accountInstance]
    }

    def save = {
        def accountInstance = new Account(params)
        accountInstance.passwd = authenticateService.encodePassword(params.passwd)
        if(!accountInstance.hasErrors() && accountInstance.save()) {
            flash.message = "Account ${accountInstance.id} created"
            redirect(action:show,id:accountInstance.id)
        }
        else {
            render(view:'create',model:[accountInstance:accountInstance])
        }
    }
}
