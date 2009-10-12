package se.spsm.screenbook.lostpassword {
import flash.events.Event;

public class LostPasswordEvent extends Event {
    public static const RESULT:String = "LOST_PASSWORD_RESULT";

    public function LostPasswordEvent(type:String, success:Boolean, bubbles:Boolean = false, cancelable:Boolean = false) {
        super(type, bubbles, cancelable);
        _success = success;

    }

    private var _success:Boolean;


    public function isNewPasswordSent():Boolean {
        return _success;
    }

}
}