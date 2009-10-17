package se.spsm.screenbook.student {
import mx.controls.Alert;
import mx.utils.ObjectUtil;

import se.spsm.screenbook.answer.Answer;

public class StudentResult {
    private var _student:String;
    private var _book:String;
    private var _answers:Object;

    public function StudentResult() {
    }

    public function fromXml(xml:XML):StudentResult {
        _student = xml.student;
        _book = xml.book;

        _answers = new Object();

        for each (var xmlAnswer:XML in xml.answers.children()) {
            var answer:Answer = new Answer().fromXml(xmlAnswer);
            _answers[answer.questionKey] = answer;
        }

        return this;
    }


    public function toString():String {


        return "Student: " + student + ", Book: " + book + ", Answers: " + ObjectUtil.toString(_answers);
    }

    public function get student():String {
        return _student;
    }

    public function get book():String {
        return _book;
    }

    /**
     * A map of answers, question_key:String -> Answer.
     */
    public function get answers():Object {
        return _answers;
    }
}
}