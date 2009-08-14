package se.spsm.screenbook {
import flash.events.Event;
import flash.events.EventDispatcher;

import mx.controls.Alert;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.rpc.http.HTTPService;

import se.spsm.screenbook.student.StudentCreatedEvent;

public class StudentController extends EventDispatcher {



    private var settings:ConnectionSettings;

    public function StudentController(settings:ConnectionSettings) {
        this.settings = settings;
    }

    private function httpServiceResult(e:ResultEvent):void {
        var event:Event = new StudentCreatedEvent(e.result);
        dispatchEvent(event);
    }

    private function httpServiceFault(e:FaultEvent):void {
        throw new Error("There was a problem sending the request due to: " + e.toString());
    }

    public function createStudentAccount(username:String):String {


        var service:HTTPService = settings.createService("/student/createStudentAccount");


        var params:Object = {
            username: username
        };


        service.addEventListener(ResultEvent.RESULT, httpServiceResult);
        service.addEventListener(FaultEvent.FAULT, httpServiceFault);

        service.requestTimeout;

        service.send(params);

        return username;
    }


}
}