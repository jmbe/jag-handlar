package se.spsm.screenbook.teacher {
import flash.events.EventDispatcher;

import mx.controls.Alert;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.rpc.http.HTTPService;

import se.spsm.screenbook.*;

public class AuthenticationController extends EventDispatcher{
    private var _settings:ConnectionSettings;

    public function AuthenticationController(connectionSettings:ConnectionSettings) {
        _settings = connectionSettings;
    }

    [Event("LoginTeacherEvent.SUCCESS")]
    [Event("LoginTeacherEvent.FAILURE")]

    public function loginTeacher(username:String, password:String):void {
        var service:HTTPService = _settings.createService("authentication/loginAsTeacher");

        var params:Object = {
            username: username,
            password : password
        };

        service.addEventListener(ResultEvent.RESULT, loginTeacherResult);
        service.addEventListener(FaultEvent.FAULT, httpServiceFault);

        service.requestTimeout;

        service.send(params);

    }

    private function loginTeacherResult(e:ResultEvent):void {

        var xml:XML = XML(e.result);
        var result = new LoginTeacherResult(xml);

        var eventName:String = result.isFailure ? LoginTeacherEvent.FAILURE : LoginTeacherEvent.SUCCESS;
        var loginEvent:LoginTeacherEvent = new LoginTeacherEvent(eventName, result);

        dispatchEvent(loginEvent);
    }

    private function httpServiceFault(e:ResultEvent):void {
        Alert.show("HTTP Failure");
    }

}
}