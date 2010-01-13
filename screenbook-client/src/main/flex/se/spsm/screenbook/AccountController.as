package se.spsm.screenbook {
import flash.events.Event;
import flash.events.EventDispatcher;

import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.rpc.http.HTTPService;

import se.spsm.screenbook.account.AccountEvent;
import se.spsm.screenbook.account.AccountResult;
import se.spsm.screenbook.apikey.ApiKey;
import se.spsm.screenbook.apikey.ApiKeyRequiredEvent;
import se.spsm.screenbook.network.NetworkProblemEvent;

public class AccountController extends EventDispatcher {


    private var settings:ConnectionSettings;

    [Event("ApiKeyRequiredEvent.REQUIRED")]

    public function AccountController(settings:ConnectionSettings) {
        this.settings = settings;
    }


    private function httpServiceFault(e:FaultEvent):void {
        dispatchEvent(new NetworkProblemEvent(e));
    }

    public function loadContactDetails(apiKey:ApiKey):void {
        if (!checkApiKey(apiKey)) {
            return;
        }

        var service:HTTPService = settings.createService("api/loadContactDetails");
        var params:Object = apiKey.toParameters();

        service.addEventListener(ResultEvent.RESULT, onContactDetailsLoaded);
        service.addEventListener(FaultEvent.FAULT, httpServiceFault);

        service.send(params);
    }


    public function changeContactDetails(apiKey:ApiKey, contactPerson:String, email:String, phone:String):void {
        if (!checkApiKey(apiKey)) {
            return;
        }


        var service:HTTPService = settings.createService("api/changeContactDetails");

        var params:Object = apiKey.toParameters();
        params.contactPerson = contactPerson;
        params.email = email;
        params.phone = phone;


        service.addEventListener(ResultEvent.RESULT, onContactDetailsChanged);
        service.addEventListener(FaultEvent.FAULT, httpServiceFault);

        service.send(params);

    }

    private function onContactDetailsLoaded(e:ResultEvent):void {
        var result:AccountResult = new AccountResult().fromXml(XML(e.result));
        var event:Event = new AccountEvent(AccountEvent.CONTACT_DETAILS_LOADED, result);
        dispatchEvent(event);
    }


    private function onContactDetailsChanged(e:ResultEvent):void {
        var result:AccountResult = new AccountResult().fromXml(XML(e.result));
        var event:Event = new AccountEvent(AccountEvent.CONTACT_DETAILS_CHANGED, result);
        dispatchEvent(event);
    }


    private function checkApiKey(apiKey:ApiKey):Boolean {
        if (apiKey == null) {
            dispatchEvent(new ApiKeyRequiredEvent());
            return false;
        }

        return true;
    }

}

}
