package se.spsm.screenbook {
import mx.controls.Alert;
import mx.rpc.http.HTTPService;

public class ConnectionSettings {
    private var _host:String = "localhost:8080";
    private var _context:String = "/screenbook-service"

    public function ConnectionSettings() {
    }

    public function get host():String {
        return this._host;
    }

    public function set host(newHost:String):void {
        this._host = newHost;
    }

    public function get baseUrl() :String{
        return "http://" + _host + _context;
    }


    public function createService(partialUrl:String, method:String = "GET"):HTTPService {
        // Alert.show("Creating service in connection settings");
        var url:String = baseUrl + partialUrl;
        var service:HTTPService = new HTTPService();

        service.url = url;
        service.method = method;

        return service;
    }

}
}