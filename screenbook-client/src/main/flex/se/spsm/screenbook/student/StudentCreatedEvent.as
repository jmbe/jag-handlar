package se.spsm.screenbook.student {
import flash.events.Event;

public class StudentCreatedEvent extends Event{
    
    public static var STUDENT_CREATED:String = "studentCreated";

    public function StudentCreatedEvent(info, bubbles:Boolean=false) {
        super(STUDENT_CREATED, bubbles);
        this.info = info;
    }

    public var info;



}
}