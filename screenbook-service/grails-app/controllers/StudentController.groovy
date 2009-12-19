import org.codehaus.groovy.grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class StudentController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ studentInstanceList: Student.list( params ), studentInstanceTotal: Student.count() ]
    }

    def show = {
        def studentInstance = Student.get( params.id )

        if(!studentInstance) {
            flash.message = "Student not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ studentInstance : studentInstance ] }
    }

    def delete = {
        def studentInstance = Student.get( params.id )
        if(studentInstance) {
            try {
                studentInstance.delete(flush:true)
                flash.message = "Student ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Student ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Student not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def studentInstance = Student.get( params.id )

        if(!studentInstance) {
            flash.message = "Student not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ studentInstance : studentInstance ]
        }
    }

    def update = {
        def studentInstance = Student.get( params.id )
        if(studentInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(studentInstance.version > version) {
                    
                    studentInstance.errors.rejectValue("version", "student.optimistic.locking.failure", "Another user has updated this Student while you were editing.")
                    render(view:'edit',model:[studentInstance:studentInstance])
                    return
                }
            }
            studentInstance.properties = params
            if(!studentInstance.hasErrors() && studentInstance.save()) {
                flash.message = "Student ${params.id} updated"
                redirect(action:show,id:studentInstance.id)
            }
            else {
                render(view:'edit',model:[studentInstance:studentInstance])
            }
        }
        else {
            flash.message = "Student not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def studentInstance = new Student()
        studentInstance.properties = params
        return ['studentInstance':studentInstance]
    }

    def save = {
        def studentInstance = new Student(params)
        if(!studentInstance.hasErrors() && studentInstance.save()) {
            flash.message = "Student ${studentInstance.id} created"
            redirect(action:show,id:studentInstance.id)
        }
        else {
            render(view:'create',model:[studentInstance:studentInstance])
        }
    }

	
	
}
