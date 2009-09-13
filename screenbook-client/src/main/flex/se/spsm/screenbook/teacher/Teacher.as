package se.spsm.screenbook.teacher {
public class Teacher {
    private var _username:String;

    public function Teacher(username:String) {
        _username = username;
    }

    public function get username():String {
        return _username;
    }
}
}