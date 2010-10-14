package se.spsm.screenbook.apikey {
public class ApiLoginResult {

    private var _valid:Boolean;
    private var _errorCode:String;

    public function ApiLoginResult(result:XML) {


        /* Neither "Boolean(s)" or "s as Boolean" worked to correctly parse the string. */
        _valid = "true" == result.valid.toLowerCase();
        _errorCode = result.errorCode;

    }

    public function get isValid():Boolean {
        return _valid;
    }

    public function get errorCode():String {
        return _errorCode;
    }

    public function get isAccountExpired():Boolean {
        return _errorCode == "error.account.expired"
    }

}
}
