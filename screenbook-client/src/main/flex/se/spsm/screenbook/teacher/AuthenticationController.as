package se.spsm.screenbook.teacher {
import flash.events.EventDispatcher;

import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.rpc.http.HTTPService;

import se.spsm.screenbook.*;
import se.spsm.screenbook.apikey.ApiKey;
import se.spsm.screenbook.apikey.ApiLoginEvent;
import se.spsm.screenbook.apikey.ApiLoginResult;
import se.spsm.screenbook.lostpassword.ChangedPasswordEvent;
import se.spsm.screenbook.lostpassword.LostPasswordEvent;
import se.spsm.screenbook.network.NetworkProblemEvent;

public class AuthenticationController extends EventDispatcher {
    private var _settings:ConnectionSettings;

    public function AuthenticationController(connectionSettings:ConnectionSettings) {
        _settings = connectionSettings;
    }

    [Event("LoginTeacherEvent.SUCCESS")]
    [Event("LoginTeacherEvent.FAILURE")]
    [Event("ApiLoginEvent.SUCCESS")]
    [Event("ApiLoginEvent.FAILURE")]
    [Event("LostPasswordEvent.RESULT")]

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

    private function httpServiceFault(event:FaultEvent):void {
        dispatchEvent(new NetworkProblemEvent(event));
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

        var loginEvent:ApiLoginEvent = new ApiLoginEvent(eventName, result);
        dispatchEvent(loginEvent);
    }

    public function lostPassword(accountIdentifier:String):void {
        var service:HTTPService = _settings.createService("authentication/lostPassword");

        var params:Object = {
            accountIdentifier: accountIdentifier
        };

        service.addEventListener(ResultEvent.RESULT, lostPasswordResult);
        service.addEventListener(FaultEvent.FAULT, httpServiceFault);

        service.send(params);

    }

    private function lostPasswordResult(e:ResultEvent):void {

        var success:Boolean = "true" == XML(e.result).toString();

        var resultEvent:LostPasswordEvent = new LostPasswordEvent(LostPasswordEvent.RESULT, success);

        dispatchEvent(resultEvent);

    }


    public function changeTeacherPassword(apiKey:ApiKey, currentPassword:String, newPassword:String):void {


        var service:HTTPService = _settings.createService("authentication/changeTeacherPassword");

        var params:Object = {
            account : apiKey.username,
            oldPassword : currentPassword,
            newPassword : newPassword
        };

        service.addEventListener(ResultEvent.RESULT, onTeacherPasswordChanged);
        service.addEventListener(FaultEvent.FAULT, httpServiceFault);

        service.send(params);


    }

    private function onTeacherPasswordChanged(event:ResultEvent):void {
        var success:Boolean = "true" == XML(event.result).toString();
        var resultEvent:ChangedPasswordEvent = new ChangedPasswordEvent(ChangedPasswordEvent.RESULT, success);
        dispatchEvent(resultEvent);
    }

    public function clearBookmarkReminder(username:String):void {
        var service:HTTPService = _settings.createService("authentication/clearBookmarkReminder");

        var params:Object = {
            username : username
        };


        service.addEventListener(FaultEvent.FAULT, httpServiceFault);

        service.send(params);
    }
}
}
