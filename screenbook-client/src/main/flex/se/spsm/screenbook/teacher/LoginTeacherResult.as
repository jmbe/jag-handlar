package se.spsm.screenbook.teacher {
public class LoginTeacherResult {
    private var _status:String;
    private var _username:String;
    private var _key:String;

    public function LoginTeacherResult(result:XML) {
        _username = result.username;
        _status = result.status;
        _key = result.apikey;
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

    public function get key():String {
        return _key;
    }
}
}