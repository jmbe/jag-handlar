package se.spsm.screenbook.student {
import flash.events.Event;

public class StudentListEvent extends Event {
    public static const LOADED:String = "STUDENT_LIST_LOADED";
    public function StudentListEvent(type:String, result:StudentList, bubbles:Boolean=false) {
        super(type, bubbles);
        this.result = result;
    }


    private var result:StudentList;

    public function get studentList():StudentList {
        return result;
    }

}
}