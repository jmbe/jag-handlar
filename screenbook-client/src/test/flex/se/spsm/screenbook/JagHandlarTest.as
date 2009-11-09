package se.spsm.screenbook {
import flexunit.framework.TestCase;

import se.spsm.screenbook.teacher.LoginTeacherEvent;

/**
 * Note: this test requires that Grails is running.
 */
public class JagHandlarTest extends TestCase {

    private var jagHandlar:JagHandlar;

    public function JagHandlarTest() {
        
    }

    override public function setUp():void {
        jagHandlar = new JagHandlar(new ConnectionSettings("localhost:8080", "/"));
    }


    public function testLoginAsTeacherWithIncorrectPassword():void {
         function handleTeacherLogin(e:LoginTeacherEvent):void {
         }

         jagHandlar.addEventListener(LoginTeacherEvent.FAILURE, addAsync(handleTeacherLogin, 3000));
         jagHandlar.loginAsTeacher("incorrect", "incorrect");
    }

}
}
