package se.spsm.screenbook.network {
import flash.events.Event;

import mx.rpc.events.FaultEvent;

public class NetworkProblemEvent extends Event {

    public static const FAILURE:String = "NETWORK_FAILURE";

    private var _result:FaultEvent;

    public function NetworkProblemEvent(e:FaultEvent) {
        super(FAILURE);

        _result = e;
    }

    public function get result():FaultEvent {
        return _result;
    }

}
}