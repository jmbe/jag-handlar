package se.spsm.screenbook.student {
import flash.events.Event;

import mx.controls.Alert;

import se.spsm.screenbook.apikey.ApiKey;

public class StudentEvent extends Event{
    
    public static const STUDENT_CREATED:String = "studentCreated";
    public static const BOOK_OPENED:String = "STUDENT_BOOK_OPENED";
    public static const ALL_ANSWERS_LOADED:String = "STUDENT_ALL_ANSWERS_LOADED";
    public static const ALL_ANSWERS_REMOVED:String = "STUDENT_ALL_ANSWERS_REMOVED";
    public static const SCREEN_KEYBOARD_CHANGED:String = "STUDENT_SCREEN_KEYBOARD_CHANGED";

    public function StudentEvent(type:String, result:StudentResult, bubbles:Boolean=false) {
        super(type, bubbles);
        this.result = result;
    }

    public var result:StudentResult;




}
}