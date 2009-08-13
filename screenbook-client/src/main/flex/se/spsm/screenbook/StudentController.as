package se.spsm.screenbook {
import mx.controls.Alert;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.rpc.http.HTTPService;

public class StudentController {

    private var settings:ConnectionSettings;

    public function StudentController(settings:ConnectionSettings) {
        this.settings = settings;
    }

    private function httpServiceResult(e:ResultEvent):void {
        //Alert.show("Received a result ");
    }

    private function httpServiceFault(e:FaultEvent):void {
        throw new Error("There was a problem sending the request due to: " + e.message);
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