package se.spsm.screenbook.account {
public class AccountResult {

    private var _email:String;
    private var _contactPerson:String;
    private var _phone:String;
    private var _status:String;

    public function AccountResult() {
    }


    public function fromXml(xml:XML):AccountResult {
        _contactPerson = xml.contactPerson;
        _email = xml.email;
        _phone = xml.phone;
        _status = xml.status;
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

    private function get status():String {
        return _status;
    }

    public function get success():Boolean {
        return "OK" == status;
    }
}
}
