package se.spsm.screenbook.network {
import flash.events.Event;

import mx.rpc.events.ResultEvent;

public class NetworkProblemEvent extends Event{

    public static const FAILURE:String = "NETWORK_FAILURE";

    private var _result:*;

    public function NetworkProblemEvent(e:*) {
        super(FAILURE);

        _result = e;
    }

    public function get result():* {
        return _result;
    }

}
}