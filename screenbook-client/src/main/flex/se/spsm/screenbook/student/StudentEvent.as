package se.spsm.screenbook.student {
import flash.events.Event;

import mx.controls.Alert;

import se.spsm.screenbook.apikey.ApiKey;

public class StudentEvent extends Event{
    
    public static const STUDENT_CREATED:String = "studentCreated";
    public static const BOOK_OPENED:String = "STUDENT_BOOK_OPENED";

    public function StudentEvent(type:String, result:StudentResult, bubbles:Boolean=false) {
        super(type, bubbles);
        this.result = result;
    }

    public var result:StudentResult;




}
}