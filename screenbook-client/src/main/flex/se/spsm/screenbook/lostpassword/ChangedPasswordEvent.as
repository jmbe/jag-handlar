package se.spsm.screenbook.lostpassword {
import flash.events.Event;

public class ChangedPasswordEvent extends Event {
    public static const RESULT:String = "PASSWORD_CHANGED_RESULT";

    public function ChangedPasswordEvent(type:String, success:Boolean, bubbles:Boolean = false, cancelable:Boolean = false) {
        super(type, bubbles, cancelable);
        _success = success;
    }


    private var _success:Boolean;


    public function isPasswordChanged():Boolean {
        return _success;
    }
}
}