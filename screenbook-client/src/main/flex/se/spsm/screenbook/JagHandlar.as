package se.spsm.screenbook {
import flash.events.EventDispatcher;

import se.spsm.screenbook.student.StudentCreatedEvent;

public class JagHandlar extends EventDispatcher {

    private var settings:ConnectionSettings;
    private var message:String;
    private var status:String;
    private var student:String;
    private var studentController:StudentController;

    [Bindable]
    public var studentCreatedResult;

    public function JagHandlar(newSettings:ConnectionSettings) {
        this.settings = newSettings;

        this.studentController = new StudentController(this.settings);
        this.studentController.addEventListener(StudentCreatedEvent.STUDENT_CREATED, handleStudentCreated);

        this.message = "Not logged in";
        this.status = "";
    }

    private function handleStudentCreated(e:StudentCreatedEvent):void {
        this.studentCreatedResult = e.info;
    }

    public function fakeLoginAsStudent(username:String):void {
        lastMessage = "Logged in as " + username;
        lastStatus = "OK";
        currentStudent = username;
    }

    public function networkLoginAsStudent(username:String):void {
        this.studentController.createStudentAccount(username);
        lastMessage = "Created student " + username;
        currentStudent = username;
    }

    [Bindable]
    public function get lastMessage():String {
        return this.message;
    }

    private function set lastMessage(newMessage:String):void {
        this.message = newMessage;
    }


    [Bindable]
    public function get lastStatus():String {
        return this.status;
    }

    private function set lastStatus(newStatus:String):void {
        this.status = newStatus;
    }


    [Bindable]
    public function get currentStudent():String {
        return this.student;
    }

    private function set currentStudent(student:String):void {
        this.student = student;
    }


}
}

