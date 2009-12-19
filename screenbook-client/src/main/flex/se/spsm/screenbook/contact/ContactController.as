package se.spsm.screenbook.contact {
import flash.events.EventDispatcher;

import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;

import se.spsm.screenbook.ConnectionSettings;
import mx.rpc.http.HTTPService;

import se.spsm.screenbook.network.NetworkProblemEvent;

public class ContactController extends EventDispatcher {
    private var _settings:ConnectionSettings;

    public function ContactController(connectionSettings:ConnectionSettings) {
        _settings = connectionSettings;
    }

    public function sendSupportMail(name:String, organisation:String, email:String, message:String):void {
        var service:HTTPService = _settings.createService("contact/support");


        var params:Object = {
            name: name,
            organisation: organisation,
            email: email,
            message : message
        };

        service.addEventListener(ResultEvent.RESULT, supportMailSentResult);
        service.addEventListener(FaultEvent.FAULT, httpServiceFault);

        service.requestTimeout = 5;
        service.send(params);
    }

    private function supportMailSentResult(event:ResultEvent):void {
        
    }

    private function httpServiceFault(event:FaultEvent):void {
        dispatchEvent(new NetworkProblemEvent(event));
    }

}
}
