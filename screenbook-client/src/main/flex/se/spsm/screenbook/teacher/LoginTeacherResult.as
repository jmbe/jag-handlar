package se.spsm.screenbook.teacher {
public class LoginTeacherResult {
    private var _status:String;
    private var _username:String;

    public function LoginTeacherResult(result:XML) {

        _username = result.username;
        _status = result.status;

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
}
}