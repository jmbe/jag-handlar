package se.spsm.screenbook {
import flexunit.framework.TestCase;

import se.spsm.screenbook.teacher.LoginTeacherEvent;

public class JagHandlarTest extends TestCase {

    private var jagHandlar:JagHandlar;

    override public function setUp():void {
        jagHandlar = new JagHandlar(new ConnectionSettings("localhost:8080", "/screenbook-service/"));
    }


    public function testLoginAsTeacherWithIncorrectPassword():void {
         function handleTeacherLogin(e:LoginTeacherEvent):void {
         }

         jagHandlar.addEventListener(LoginTeacherEvent.FAILURE, addAsync(handleTeacherLogin, 3000));
         jagHandlar.loginAsTeacher("incorrect", "incorrect");
    }

}
}
