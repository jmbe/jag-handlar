package se.spsm.screenbook {
import mx.controls.Alert;
import mx.rpc.http.HTTPService;

public class ConnectionSettings {
    private var _host:String;
    private var _context:String;

    public function ConnectionSettings(hostSetting:String="jaghandlar.pictosys.se", contextSetting:String="/") {
        host = hostSetting;
        context = contextSetting;
    }

    public function get host():String {
        return this._host;
    }

    public function set host(newHost:String):void {
        this._host = newHost;
    }

    public function get context():String {
        return this._context;
    }

    public function set context(newContext:String):void {
        this._context = newContext;
    }

    public function get baseUrl() :String{
        return "http://" + _host + _context;
    }


    public function createService(partialUrl:String, method:String = "GET", resultFormat:String = "xml"):HTTPService {
        // Alert.show("Creating service in connection settings");
        var url:String = baseUrl + partialUrl;
        var service:HTTPService = new HTTPService();

        service.resultFormat = resultFormat;

        service.url = url;
        service.method = method;

        return service;
    }

}
}