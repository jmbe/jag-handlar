package se.spsm.screenbook.account {
public class AccountResult {

    private var _email:String;
    private var _contactPerson:String;
    private var _phone:String;
    private var _status:String;
    private var _newsletterSubscribe:Boolean;

    public function AccountResult() {
    }


    public function fromXml(xml:XML):AccountResult {
        _contactPerson = xml.contactPerson;
        _email = xml.email;
        _phone = xml.phone;
        _status = xml.status;

        _newsletterSubscribe = xml.newsletterSubscribe == "true";

        return this;
    }


    public function get email():String {
        return _email;
    }

    public function get contactPerson():String {
        return _contactPerson;
    }

    public function get phone():String {
        return _phone;
    }

    public function get newsletterSubscribe():Boolean {
        return _newsletterSubscribe;
    }

    private function get status():String {
        return _status;
    }

    public function get success():Boolean {
        return "OK" == status;
    }
}
}
