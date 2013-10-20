package se.jaghandlar

import se.jaghandlar.Book;

//@Secured(['ROLE_ADMIN'])
class BookController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ bookInstanceList: Book.list( params ), bookInstanceTotal: Book.count() ]
    }

    def show = {
        def bookInstance = Book.get( params.id )

        if(!bookInstance) {
            flash.message = "Book not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ bookInstance : bookInstance ] }
    }

    def delete = {
        def bookInstance = Book.get( params.id )
        if(bookInstance) {
            try {
                bookInstance.delete(flush:true)
                flash.message = "Book ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Book ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Book not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def bookInstance = Book.get( params.id )

        if(!bookInstance) {
            flash.message = "Book not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ bookInstance : bookInstance ]
        }
    }

    def update = {
        def bookInstance = Book.get( params.id )
        if(bookInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bookInstance.version > version) {
                    
                    bookInstance.errors.rejectValue("version", "book.optimistic.locking.failure", "Another user has updated this Book while you were editing.")
                    render(view:'edit',model:[bookInstance:bookInstance])
                    return
                }
            }
            bookInstance.properties = params
            if(!bookInstance.hasErrors() && bookInstance.save()) {
                flash.message = "Book ${params.id} updated"
                redirect(action:show,id:bookInstance.id)
            }
            else {
                render(view:'edit',model:[bookInstance:bookInstance])
            }
        }
        else {
            flash.message = "Book not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def bookInstance = new Book()
        bookInstance.properties = params
        return ['bookInstance':bookInstance]
    }

    def save = {
        def bookInstance = new Book(params)
        if(!bookInstance.hasErrors() && bookInstance.save()) {
            flash.message = "Book ${bookInstance.id} created"
            redirect(action:show,id:bookInstance.id)
        }
        else {
            render(view:'create',model:[bookInstance:bookInstance])
        }
    }
}
