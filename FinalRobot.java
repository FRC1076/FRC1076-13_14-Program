/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team1076.robot.year2014;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Compressor;

/**
 *
 * @author super0eater14
 */
public class FinalRobot extends SimpleRobot {

    Compressor bigAl = new Compressor(FRVars.compressorPort, FRVars.pressureSensor);
    Joystick controller = new Joystick(1);
    DriveTrain wheelbase = new DriveTrain(controller);
    Payload thrower = new Payload(controller);
    Grabber arms = new Grabber(controller);
    Thread driving = new Thread(wheelbase);
    Thread throwing = new Thread(thrower);
    Thread grabbing = new Thread(arms);
    
    public void autonomous(){
        bigAl.start();
        wheelbase.downShift();
        wheelbase.setMotors(.5, .5);
        Timer.delay(AutonomousVars.driveTime);
        wheelbase.setMotors(0, 0);
        //thrower.fire(AutonomousVars.throwPower);
    }

    public void operatorControl() {
        bigAl.start();
        driving.start();
        throwing.start();
        grabbing.start();
    }
    
    public void test(){
        bigAl.start();
        while(true){
            wheelbase.setMotors(1, -1);
        }
    }
}
