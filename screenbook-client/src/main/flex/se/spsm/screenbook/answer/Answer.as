package se.spsm.screenbook.answer {

public class Answer {
    private var _question_key:String;
    private var _answer:String;
    private var _book:String;

    public function Answer() {
    }

    public function fromXml(xml:XML):Answer {
        this._question_key = xml.question_key;
        this._answer = xml.answer;
        this._book = xml.book;

        return this;
    }

    public function get questionKey():String {
        return this._question_key;
    }

    public function get answerAsString():String {
        return this._answer;
    }

    public function get book():String {
        return this._book;
    }


    public function toString():String {
        return questionKey + ": " + answerAsString;
    }

}
}