package se.spsm.screenbook.apikey {
public class ApiLoginResult {

    private var _valid:Boolean;

    public function ApiLoginResult(result:XML) {


        /* Neither "Boolean(s)" or "s as Boolean" worked to correctly parse the string. */
        _valid = "true" == result.valid.toLowerCase();


    }

    public function get isValid():Boolean {
        return _valid;
    }
}
}