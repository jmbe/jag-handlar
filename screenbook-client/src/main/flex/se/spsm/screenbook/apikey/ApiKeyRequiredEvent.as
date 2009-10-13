package se.spsm.screenbook.apikey {
import flash.events.Event;

public class ApiKeyRequiredEvent extends Event {
    public static const REQUIRED:String = "API_KEY_REQUIRED";

    public function ApiKeyRequiredEvent() {
        super(REQUIRED, false);
    }

}
}