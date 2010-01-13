package se.spsm.screenbook.account {
import flash.events.Event;

public class AccountEvent extends Event {

    public function AccountEvent(type:String, result:AccountResult, bubbles:Boolean = false) {
        super(type, bubbles);
        this.result = result;
    }

    public var result:AccountResult;

    public static const CONTACT_DETAILS_CHANGED:String = "ACCOUNT_CONTACT_DETAILS_CHANGED";
    public static const CONTACT_DETAILS_LOADED:String = "ACCOUNT_CONTACT_DETAILS_CHANGED";

}
}
