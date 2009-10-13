package se.spsm.screenbook.answer {
import flash.events.Event;

public class AnswerEvent  extends Event{
    public static const LOADED:String = "ANSWER_LOADED";
    public static const SAVED:String = "ANSWER_SAVED";


    public function AnswerEvent(type:String, answer:Answer) {
        super(type, false, false);

        this._answer = answer;
    }


    private var _answer:Answer;


    public function get answer():Answer {
        return this._answer;
    }


}
}