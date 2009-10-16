package se.spsm.screenbook.student {
public class StudentInfo {
    public function StudentInfo() {
    }

    public function fromXml(xmlStudent:XML):StudentInfo {

        _student = xmlStudent["name"];
        _screenKeyboard = "true" == xmlStudent["screenKeyboard"];

        return this;
    }

    private var _screenKeyboard:Boolean;

    private var _student:String;

    public function get student():String {
        return _student;
    }

    public function get screenKeyboard():Boolean {
        return _screenKeyboard;
    }

    public function toString():String {
        return student + " (keyboard: " + screenKeyboard + ")";
    }

}
}