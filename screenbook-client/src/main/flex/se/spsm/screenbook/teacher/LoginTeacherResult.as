package se.spsm.screenbook.teacher {
import mx.controls.Alert;

public class LoginTeacherResult {
    private var _status:String;
    private var _username:String;
    private var _key:String;
    private var _errorCode:String;
    private var _showBookmarkReminder:Boolean;

    public function LoginTeacherResult(result:XML) {
        _username = result.username;
        _status = result.status;
        _key = result.apikey;
        _errorCode = result.errorCode;
        _showBookmarkReminder = result.showBookmarkReminder == "true";
    }



    public function get isFailure():Boolean {
        return "failure" == status;
    }

    public function get isSuccess():Boolean {
        return "success" == status;
    }

    public function get status():String {
        return _status;
    }

    public function get username():String {
        return _username;
    }

    public function get errorCode():String {
        return _errorCode;
    }

    public function get isIncorrectCredentials():Boolean {
        return "error.incorrect.username" == errorCode || "error.incorrect.password" == errorCode;
    }

    public function get isExpiredAccount():Boolean {
        return "error.account.expired" == errorCode;
    }

    /**
     * API key. 
     */
    public function get key():String {
        return _key;
    }

    public function get showBookmarkReminder():Boolean {
        return _showBookmarkReminder;
    }
}
}
