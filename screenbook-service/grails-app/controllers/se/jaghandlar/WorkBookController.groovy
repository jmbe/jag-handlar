package se.jaghandlar

import se.jaghandlar.WorkBook;

//@Secured(['ROLE_ADMIN'])
class WorkBookController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ workBookInstanceList: WorkBook.list( params ), workBookInstanceTotal: WorkBook.count() ]
    }

    def show = {
        def workBookInstance = WorkBook.get( params.id )

        if(!workBookInstance) {
            flash.message = "WorkBook not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ workBookInstance : workBookInstance ] }
    }

    def delete = {
        def workBookInstance = WorkBook.get( params.id )
        if(workBookInstance) {
            try {
                workBookInstance.delete(flush:true)
                flash.message = "WorkBook ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "WorkBook ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "WorkBook not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def workBookInstance = WorkBook.get( params.id )

        if(!workBookInstance) {
            flash.message = "WorkBook not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ workBookInstance : workBookInstance ]
        }
    }

    def update = {
        def workBookInstance = WorkBook.get( params.id )
        if(workBookInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(workBookInstance.version > version) {
                    
                    workBookInstance.errors.rejectValue("version", "workBook.optimistic.locking.failure", "Another user has updated this WorkBook while you were editing.")
                    render(view:'edit',model:[workBookInstance:workBookInstance])
                    return
                }
            }
            workBookInstance.properties = params
            if(!workBookInstance.hasErrors() && workBookInstance.save()) {
                flash.message = "WorkBook ${params.id} updated"
                redirect(action:show,id:workBookInstance.id)
            }
            else {
                render(view:'edit',model:[workBookInstance:workBookInstance])
            }
        }
        else {
            flash.message = "WorkBook not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def workBookInstance = new WorkBook()
        workBookInstance.properties = params
        return ['workBookInstance':workBookInstance]
    }

    def save = {
        def workBookInstance = new WorkBook(params)
        if(!workBookInstance.hasErrors() && workBookInstance.save()) {
            flash.message = "WorkBook ${workBookInstance.id} created"
            redirect(action:show,id:workBookInstance.id)
        }
        else {
            render(view:'create',model:[workBookInstance:workBookInstance])
        }
    }
}
