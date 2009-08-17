import grails.converters.XML

class AnswerController {

    def answerService

    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ answerInstanceList: Answer.list( params ), answerInstanceTotal: Answer.count() ]
    }

    def show = {
        def answerInstance = Answer.get( params.id )

        if(!answerInstance) {
            flash.message = "Answer not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ answerInstance : answerInstance ] }
    }

    def delete = {
        def answerInstance = Answer.get( params.id )
        if(answerInstance) {
            try {
                answerInstance.delete(flush:true)
                flash.message = "Answer ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Answer ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Answer not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def answerInstance = Answer.get( params.id )

        if(!answerInstance) {
            flash.message = "Answer not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ answerInstance : answerInstance ]
        }
    }

    def update = {
        def answerInstance = Answer.get( params.id )
        if(answerInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(answerInstance.version > version) {
                    
                    answerInstance.errors.rejectValue("version", "answer.optimistic.locking.failure", "Another user has updated this Answer while you were editing.")
                    render(view:'edit',model:[answerInstance:answerInstance])
                    return
                }
            }
            answerInstance.properties = params
            if(!answerInstance.hasErrors() && answerInstance.save()) {
                flash.message = "Answer ${params.id} updated"
                redirect(action:show,id:answerInstance.id)
            }
            else {
                render(view:'edit',model:[answerInstance:answerInstance])
            }
        }
        else {
            flash.message = "Answer not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def answerInstance = new Answer()
        answerInstance.properties = params
        return ['answerInstance':answerInstance]
    }

    def save = {
        def answerInstance = new Answer(params)
        if(!answerInstance.hasErrors() && answerInstance.save()) {
            flash.message = "Answer ${answerInstance.id} created"
            redirect(action:show,id:answerInstance.id)
        }
        else {
            render(view:'create',model:[answerInstance:answerInstance])
        }
    }

  
    /**
     * @param username
     * @param question_key
     */
    def getAnswer = {
      def answerInstance = answerService.getAnswer(params.username, params.question_key)
      render answerInstance as XML
    }

    /**
     * @param username
     * @param question_key
     * @param answer 
     */
    def setAnswer = {
      //username, question_key, answer
      def answerInstance = answerService.setAnswer(username: params.username, questionkey: params.question_key, answer: params.answer)
      render answerInstance as XML
    }

}
