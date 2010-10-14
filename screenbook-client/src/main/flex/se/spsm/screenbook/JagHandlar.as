package se.spsm.screenbook {
import flash.events.EventDispatcher;

import mx.controls.Alert;

import se.spsm.screenbook.account.AccountEvent;
import se.spsm.screenbook.answer.AnswerEvent;
import se.spsm.screenbook.apikey.ApiKey;
import se.spsm.screenbook.apikey.ApiKeyRequiredEvent;
import se.spsm.screenbook.apikey.ApiLoginEvent;
import se.spsm.screenbook.contact.ContactController;
import se.spsm.screenbook.lostpassword.LostPasswordEvent;
import se.spsm.screenbook.lostpassword.ChangedPasswordEvent;
import se.spsm.screenbook.network.NetworkProblemEvent;
import se.spsm.screenbook.student.StudentEvent;
import se.spsm.screenbook.student.StudentListEvent;
import se.spsm.screenbook.teacher.AuthenticationController;
import se.spsm.screenbook.teacher.LoginTeacherEvent;
import se.spsm.screenbook.teacher.Teacher;

public class JagHandlar extends EventDispatcher {

    private var settings:ConnectionSettings;

    private var message:String;
    private var status:String;
    private var student:String;
    private var studentController:StudentController;
    private var contactController:ContactController;
    private var accountController:AccountController;

    private var authenticationController:AuthenticationController;

    [Bindable]
    public var progressMessage:String;



    private var _currentTeacher:Teacher;

    private var _currentApiKey:ApiKey;

    [Event("LoginTeacherEvent.SUCCESS")]
    [Event("LoginTeacherEvent.FAILURE")]
    [Event("ApiLoginEvent.SUCCESS")]
    [Event("ApiLoginEvent.FAILURE")]
    [Event("NetworkProblemEvent.FAILURE")]
    [Event("ApiKeyRequiredEvent.REQUIRED")]
    [Event("StudentEvent.ALL_ANSWERS_LOADED")]
    [Event("StudentEvent.STUDENT_CREATED")]
    [Event("StudentEvent.BOOK_OPENED")]
    [Event("StudentEvent.ALL_ANSWERS_LOADED")]
    [Event("StudentEvent.ALL_ANSWERS_REMOVED")]
    [Event("StudentEvent.STUDENT_NAME_CHANGED")]
    [Event("AccountEvent.CONTACT_DETAILS_CHANGED")]
    [Event("AccountEvent.CONTACT_DETAILS_LOADED")]

    public function JagHandlar(newSettings:ConnectionSettings) {
        this.settings = newSettings;

        this.studentController = new StudentController(this.settings);
        this.studentController.addEventListener(StudentEvent.STUDENT_CREATED, onStudentCreated);
        this.studentController.addEventListener(StudentEvent.BOOK_OPENED, onOpenBookAsStudent);
        this.studentController.addEventListener(StudentEvent.ALL_ANSWERS_LOADED, onAnswerListLoaded);
        this.studentController.addEventListener(StudentEvent.ALL_ANSWERS_REMOVED, onAnswerListLoaded);
        this.studentController.addEventListener(StudentEvent.STUDENT_NAME_CHANGED, onAnswerListLoaded);

        this.studentController.addEventListener(LoginTeacherEvent.SUCCESS, handleLoginSuccess);
        this.studentController.addEventListener(ApiKeyRequiredEvent.REQUIRED, onApiKeyRequired);

        this.studentController.addEventListener(StudentListEvent.LOADED, onStudentListLoaded);

        this.authenticationController = new AuthenticationController(this.settings);
        this.authenticationController.addEventListener(LoginTeacherEvent.SUCCESS, onLoginTeacherSuccess);
        this.authenticationController.addEventListener(LoginTeacherEvent.FAILURE, onLoginTeacherFailure);

        this.authenticationController.addEventListener(ApiLoginEvent.SUCCESS, onApiLoginSuccess);
        this.authenticationController.addEventListener(ApiLoginEvent.FAILURE, onApiLoginFailure);

        this.authenticationController.addEventListener(LostPasswordEvent.RESULT, onLostPasswordResult);
        this.authenticationController.addEventListener(ChangedPasswordEvent.RESULT, onChangedPasswordResult);

        this.accountController = new AccountController(this.settings);
        this.accountController.addEventListener(AccountEvent.CONTACT_DETAILS_CHANGED, onContactDetailsChanged);
        this.accountController.addEventListener(AccountEvent.CONTACT_DETAILS_LOADED, onContactDetailsChanged);

        this.authenticationController.addEventListener(NetworkProblemEvent.FAILURE, onNetworkProblem);
        this.studentController.addEventListener(NetworkProblemEvent.FAILURE, onNetworkProblem);
        this.accountController.addEventListener(NetworkProblemEvent.FAILURE, onNetworkProblem);


        this.studentController.addEventListener(AnswerEvent.LOADED, handleAnswerEvent);
        this.studentController.addEventListener(AnswerEvent.SAVED, handleAnswerEvent);

        this.contactController = new ContactController(this.settings);
        this.contactController.addEventListener(NetworkProblemEvent.FAILURE, onNetworkProblem);

        this.message = "Not logged in";
        this.status = "";
    }




    public function onNetworkProblem(e:NetworkProblemEvent):void {
        dispatchEvent(new NetworkProblemEvent(e.result));
    }

    /**
     * Performs API login.
     *
     * Events:
     *
     * ApiLoginEvent.SUCCESS
     * ApiLoginEvent.FAILURE
     *
     * @param username main account username
     * @param apiKey api key which was read from URL query parameters
     */
    public function loginAsApi(username:String, apiKey:String):void {
        this.authenticationController.verifyApiLogin(username, apiKey);
        currentApiKey = new ApiKey(username, apiKey);
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


    /**
     * Determines whether the user is running in testmode, i.e. whether no one is logged in.
     */
    public function get isTestMode():Boolean {
        return currentApiKey == null && currentTeacher == null;
    }

    private function set currentTeacher(teacher:Teacher):void {
        _currentTeacher = teacher;
    }

    /**
     * Logs out the currently logged in teacher.
     */
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
        dispatchEvent(new LoginTeacherEvent(LoginTeacherEvent.FAILURE, e.result));
    }


    private function handleLoginSuccess(e:LoginTeacherEvent): void {
        this.progressMessage = e.type;

        //Alert("Student login success");
    }

    /**
     * Validates teacher login. Returns API key in the result event.
     *
     * Events:
     *
     * LoginTeacherEvent.SUCCESS
     * LoginTeacherEvent.FAILURE
     *
     * @param username main account username
     * @param password password
     */
    public function loginAsTeacher(username: String, password:String):void {
        this.progressMessage = "Logging in as teacher";
        this.authenticationController.loginTeacher(username, password);
    }


    public function clearBookmarkReminder(): void {
        this.authenticationController.clearBookmarkReminder(currentApiKey.username);
    }

    /**
     * Adds a new student to the currently logged in main account.
     *
     * @param student student name
     * @param screenKeyboard whether the student should use screen keyboard
     */
    public function createStudent(student:String, screenKeyboard:Boolean = false):void {
        if (!isTestMode) {
            this.studentController.createStudent(currentApiKey, student, screenKeyboard);
        }
    }

    private function onStudentCreated(e:StudentEvent):void {
        dispatchEvent(new StudentEvent(StudentEvent.STUDENT_CREATED, e.result));
    }

    public function openBookAsStudent(username:String):void {
        this.studentController.openBookAsStudent(currentApiKey, username);
    }

    private function onOpenBookAsStudent(e:StudentEvent):void {
        currentStudent = e.result.student;

        dispatchEvent(new StudentEvent(e.type, e.result));
    }

    public function loadAllAnswers(student:String):void {
        this.studentController.loadAllAnswers(currentApiKey, student);
    }

    private function onAnswerListLoaded(e:StudentEvent):void {
        dispatchEvent(new StudentEvent(e.type, e.result));
    }

    /**
     * Logs out the currently logged in student.
     */
    public function logoutStudent():void {
        currentStudent = null;
    }

    /**
     * Logs out all accounts; API, teacher and student.
     */
    public function logout():void {
        logoutStudent();
        logoutTeacher();
        logoutApi();

    }

    public function logoutApi():void {
        currentApiKey = null;
    }

    public function isApiLoggedIn():Boolean {
        return currentApiKey != null;
    }
    
    public function isStudentLoggedIn():Boolean {
        return currentStudent != null;
    }

    public function loadAnswer(question:String):void {
        this.studentController.loadAnswer(currentApiKey, currentStudent, question);
    }

    /**
     * Saves an answer in the database for the currently logged in student.
     *
     * Does not work in test mode - requires that an account is logged in and a student is active.
     *
     * Events:
     * AnswerEvent.SAVED
     *
     * @param question
     * @param answer
     */
    public function saveAnswer(question:String, answer:String):void {
        if (!isTestMode) {
            this.studentController.saveAnswer(currentApiKey, currentStudent, question, answer);
        }
    }

    public function handleAnswerEvent(e:AnswerEvent):void {
        dispatchEvent(new AnswerEvent(e.type, e.answer));

    }

    /**
     * @return currently logged in student.
     */
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

    private function onChangedPasswordResult(event:ChangedPasswordEvent):void {
        dispatchEvent(new ChangedPasswordEvent(event.type, event.isPasswordChanged()));
    }

    private function onApiKeyRequired(e:ApiKeyRequiredEvent):void {
        dispatchEvent(new ApiKeyRequiredEvent());
    }


    /**
     * Loads a list of all students for the currently logged in account.
     *
     * Events:
     *
     * StudentListEvent.LOADED
     */
    public function loadAllStudents():void {
        this.studentController.loadAllStudents(currentApiKey);
    }

    private function onStudentListLoaded(event:StudentListEvent):void {
        dispatchEvent(new StudentListEvent(event.type, event.studentList));
    }

    /**
     * Erases all answers in the book for the given student.
     * @param student student name
     */
    public function clearAllAnswers(student:String):void {
        this.studentController.clearAllAnswers(currentApiKey, student);
    }

    /**
     * Sets new screen keyboard setting for the given student.
     *
     * @param student student name.
     * @param useScreenKeyboard whether screen keyboard should be used.
     */
    public function changeScreenKeyboard(student:String, useScreenKeyboard:Boolean):void {
        if (!isTestMode) {
            this.studentController.changeScreenKeyboard(currentApiKey, student, useScreenKeyboard);
        }
    }

    public function changeStudentName(currentStudentName:String, newStudentName:String):void {
        this.studentController.changeStudentName(currentApiKey, currentStudentName, newStudentName);
    }

    public function changeTeacherPassword(currentPassword:String, newPassword:String):void {
        this.authenticationController.changeTeacherPassword(currentApiKey, currentPassword, newPassword);
    }

    public function changeContactDetails(contactPerson:String, email:String, phone:String, newsletterSubscribe:Boolean):void {
        this.accountController.changeContactDetails(currentApiKey, contactPerson, email, phone, newsletterSubscribe);
    }

    public function loadContactDetails():void {
        this.accountController.loadContactDetails(currentApiKey);
    }

    private function onContactDetailsChanged(event:AccountEvent):void {
        dispatchEvent(new AccountEvent(event.type,  event.result));
    }

    public function sendSupportMail(name:String, organisation:String, email:String, message:String):void {
        this.contactController.sendSupportMail(name, organisation, email, message);
    }
}
}

