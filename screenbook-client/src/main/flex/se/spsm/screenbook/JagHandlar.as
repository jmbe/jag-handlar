package se.spsm.screenbook {
import flash.events.EventDispatcher;

import mx.controls.Alert;

import se.spsm.screenbook.apikey.ApiKey;
import se.spsm.screenbook.apikey.ApiLoginEvent;
import se.spsm.screenbook.lostpassword.LostPasswordEvent;
import se.spsm.screenbook.network.NetworkProblemEvent;
import se.spsm.screenbook.student.StudentCreatedEvent;
import se.spsm.screenbook.teacher.AuthenticationController;
import se.spsm.screenbook.teacher.LoginTeacherEvent;
import se.spsm.screenbook.teacher.Teacher;

public class JagHandlar extends EventDispatcher {

    private var settings:ConnectionSettings;

    private var message:String;
    private var status:String;
    private var student:String;
    private var studentController:StudentController;

    private var authenticationController:AuthenticationController;

    [Bindable]
    public var progressMessage:String;

    [Bindable]
    public var studentCreatedResult;


    private var _currentTeacher:Teacher;

    private var _currentApiKey:ApiKey;

    [Event("LoginTeacherEvent.SUCCESS")]
    [Event("LoginTeacherEvent.FAILURE")]
    [Event("ApiLoginEvent.SUCCESS")]
    [Event("ApiLoginEvent.FAILURE")]
    [Event("NetworkProblemEvent.FAILURE")]

    public function JagHandlar(newSettings:ConnectionSettings) {
        this.settings = newSettings;

        this.studentController = new StudentController(this.settings);
        this.studentController.addEventListener(StudentCreatedEvent.STUDENT_CREATED, handleStudentCreated);

        this.studentController.addEventListener(LoginTeacherEvent.SUCCESS, handleLoginSuccess);

        this.authenticationController = new AuthenticationController(this.settings);
        this.authenticationController.addEventListener(LoginTeacherEvent.SUCCESS, onLoginTeacherSuccess);
        this.authenticationController.addEventListener(LoginTeacherEvent.FAILURE, onLoginTeacherFailure);

        this.authenticationController.addEventListener(ApiLoginEvent.SUCCESS, onApiLoginSuccess);
        this.authenticationController.addEventListener(ApiLoginEvent.FAILURE, onApiLoginFailure);

        this.authenticationController.addEventListener(LostPasswordEvent.RESULT, onLostPasswordResult);

        this.authenticationController.addEventListener(NetworkProblemEvent.FAILURE, onNetworkProblem);

        this.message = "Not logged in";
        this.status = "";
    }




    public function onNetworkProblem(e:NetworkProblemEvent):void {
        dispatchEvent(new NetworkProblemEvent(e.result));
    }

    public function doApiLogin(username:String, apiKey:String):void {
        this.authenticationController.verifyApiLogin(username, apiKey);

    }

    private function onApiLoginSuccess(e:ApiLoginEvent):void {
        //Alert.show("api login success");
        dispatchEvent(new ApiLoginEvent(ApiLoginEvent.SUCCESS, e.result));
    }

    private function onApiLoginFailure(e:ApiLoginEvent):void {
        //Alert.show("api login failure");

        currentApiKey = null;

        dispatchEvent(new ApiLoginEvent(ApiLoginEvent.FAILURE, e.result));
    }

    [Bindable]
    public function get currentApiKey():ApiKey {
        return _currentApiKey;
    }

    private function set currentApiKey(apiKey:ApiKey):void {
        _currentApiKey = apiKey;
    }

    public function get isTeacherAuthenticated():Boolean {
        return currentTeacher != null;
    }

    [Bindable]
    public function get currentTeacher():Teacher {
        return _currentTeacher;
    }

    [Bindable]
    public function get isTestMode():Boolean {
        return currentApiKey == null && currentTeacher == null;
    }

    private function set currentTeacher(teacher:Teacher):void {
        _currentTeacher = teacher;
    }

    public function logoutTeacher():void {
        currentTeacher = null;
    }

    private function onLoginTeacherSuccess(e:LoginTeacherEvent):void {
        // Alert.show("Jag handlar: teacher logged in successfully");
        currentTeacher = e.teacher;

        currentApiKey = e.apiKey;

        /* Seem that a new event has to be created, if the event should be re-dispatched. */
        dispatchEvent(new LoginTeacherEvent(LoginTeacherEvent.SUCCESS, e.result));
    }

    private function onLoginTeacherFailure(e:LoginTeacherEvent):void {
        //Alert.show("Jag handlar: Login failed for teacher");

        logoutTeacher();
        dispatchEvent(new LoginTeacherEvent(LoginTeacherEvent.FAILURE, null));
    }


    private function handleLoginSuccess(e:LoginTeacherEvent): void {
        this.progressMessage = e.type;

        //Alert("Student login success");
    }


    public function loginAsTeacher(username: String, password:String):void {
        this.progressMessage = "Logging in as teacher";
        this.authenticationController.loginTeacher(username, password);
    }

    private function handleStudentCreated(e:StudentCreatedEvent):void {
        //Alert.show("Student created success");
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


    public function lostPassword(accountIdentifier:String):void {
        this.authenticationController.lostPassword(accountIdentifier);        
    }

    private function onLostPasswordResult(e:LostPasswordEvent):void {
        dispatchEvent(new LostPasswordEvent(LostPasswordEvent.RESULT, e.isNewPasswordSent()));
    }

}
}

