package se.spsm.screenbook.apikey {
import flash.events.Event;

public class ApiLoginEvent extends Event{

    public static const SUCCESS:String = "API_LOGIN_SUCCESS";
    public static const FAILURE:String = "API_LOGIN_FAILURE";

    private var _result:ApiLoginResult;

    public function ApiLoginEvent(type:String, apiLoginResult:ApiLoginResult, bubbles:Boolean = false) {
        super(type, bubbles);
        _result = apiLoginResult;
    }

    public function get result():ApiLoginResult {
        return _result;
    }
}
}