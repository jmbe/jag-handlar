package se.spsm.screenbook.apikey {
public class ApiKey {

    private var _username:String;
    private var _key:String;

    public function ApiKey(username:String, key:String) {
        _username = username;
        _key = key;
    }


    public function get key():String {
        return _key;
    }

    public function get username():String {
        return _username;
    }

    public function toParameters():Object {
        return {
            account : username,
            apikey : key
        }
    }
}
}