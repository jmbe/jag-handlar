package se.spsm.screenbook.student {
import mx.controls.Alert;

public class StudentResult {
    private var _student:String;
    private var _book:String;
    private var _answers:Object;

    public function StudentResult(xml:XML) {
        _student = xml.student;
        _book = xml.book;

        //Alert.show("Creating student result");

        _answers = new Object();

        Alert.show(xml.toString());

        var answersXml = xml.answers;
        Alert.show(answersXml.toString());


        for each (var answer:XML in xml.answers.children()) {
            Alert.show(answer.toString());
        }


        //Alert.show("Done creating student result")
    }




    public function toString():String {
        return "Student: " + student + ", Book: " + book;
    }

    public function get student():String {
        return _student;
    }

    public function get book():String {
        return _book;
    }
}
}