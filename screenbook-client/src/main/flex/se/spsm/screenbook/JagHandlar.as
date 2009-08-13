package se.spsm.screenbook {
import flash.events.Event;

public class JagHandlar {

    private var settings:ConnectionSettings;
    private var message:String;
    private var status:String;
    private var student:String;
    private var studentController:StudentController;

    public function JagHandlar(host:String) {
        this.settings = new ConnectionSettings();
        this.settings.host = host;

        this.studentController = new StudentController(this.settings);

        this.message = "Not logged in";
        this.status = "";
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

