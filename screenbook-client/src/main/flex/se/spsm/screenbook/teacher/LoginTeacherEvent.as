package se.spsm.screenbook.teacher {


import flash.events.Event;

public class LoginTeacherEvent extends Event {
    public static const FAILURE:String = "FAILURE";

    public static const SUCCESS:String = "SUCCESS";

    private var _result:LoginTeacherResult;

    public function LoginTeacherEvent(type:String, result:LoginTeacherResult, bubbles:Boolean = false, cancelable:Boolean = false) {
        super(type, bubbles, cancelable);
        _result = result
    }

    public function get result():LoginTeacherResult {
        return _result;
    }

    public function get teacher():Teacher {
        return new Teacher(result.username);
    }

}
}