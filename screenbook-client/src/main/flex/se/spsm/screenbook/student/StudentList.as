package se.spsm.screenbook.student {
public class StudentList {
    public function StudentList() {
    }

    public function fromXml(xml:XML):StudentList {

        _licenses = xml.numberOfLicenses;

        _students = [];

        for each (var xmlStudent:XML in xml.students.children()) {
            _students.push(new StudentInfo().fromXml(xmlStudent));

        }

        return this;
    }

    private var _students:Array;

    private var _licenses:int;

    public function get licenses():int {
        return _licenses;
    }

    public function get students():Array {
        return _students;
    }

    public function toString():String {
        return licenses + " licenses, students: " + students.toString();
    }

}
}