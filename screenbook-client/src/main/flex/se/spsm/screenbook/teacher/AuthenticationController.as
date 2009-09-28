package se.spsm.screenbook.teacher {
import flash.events.EventDispatcher;

import mx.controls.Alert;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.rpc.http.HTTPService;

import se.spsm.screenbook.*;
import se.spsm.screenbook.apikey.ApiLoginEvent;
import se.spsm.screenbook.apikey.ApiLoginResult;
import se.spsm.screenbook.network.NetworkProblemEvent;

public class AuthenticationController extends EventDispatcher{
    private var _settings:ConnectionSettings;

    public function AuthenticationController(connectionSettings:ConnectionSettings) {
        _settings = connectionSettings;
    }

    [Event("LoginTeacherEvent.SUCCESS")]
    [Event("LoginTeacherEvent.FAILURE")]
    [Event("ApiLoginEvent.SUCCESS")]
    [Event("ApiLoginEvent.FAILURE")]

    public function loginTeacher(username:String, password:String):void {
        var service:HTTPService = _settings.createService("authentication/loginAsTeacher");

        var params:Object = {
            username: username,
            password : password
        };

        service.addEventListener(ResultEvent.RESULT, loginTeacherResult);
        service.addEventListener(FaultEvent.FAULT, httpServiceFault);

        service.requestTimeout = 5;

        service.send(params);

    }

    private function loginTeacherResult(e:ResultEvent):void {

        var xml:XML = XML(e.result);
        var result:LoginTeacherResult = new LoginTeacherResult(xml);

        var eventName:String = result.isFailure ? LoginTeacherEvent.FAILURE : LoginTeacherEvent.SUCCESS;
        var loginEvent:LoginTeacherEvent = new LoginTeacherEvent(eventName, result);

        dispatchEvent(loginEvent);
    }

    private function httpServiceFault(e:ResultEvent):void {
        Alert.show("HTTP Failure");
        dispatchEvent(new NetworkProblemEvent(e.result));
    }

    public function verifyApiLogin(username:String, apiKey:String):void {
        var service:HTTPService = _settings.createService("authentication/verifyApiLogin");

        var params:Object = {
            username: username,
            apikey : apiKey
        };

        service.addEventListener(ResultEvent.RESULT, apiLoginResult);
        service.addEventListener(FaultEvent.FAULT, httpServiceFault);

        service.requestTimeout = 5;

        service.send(params);
    }

    private function apiLoginResult(e:ResultEvent):void {
        var xml:XML = XML(e.result);
        var result:ApiLoginResult = new ApiLoginResult(xml);

        var eventName:String = result.isValid ? ApiLoginEvent.SUCCESS : ApiLoginEvent.FAILURE;

        var loginEvent:ApiLoginEvent = new se.spsm.screenbook.apikey.ApiLoginEvent(eventName, result);
        dispatchEvent(loginEvent);
    }

}
}