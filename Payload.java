/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team1076.robot.year2014;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author supereater14
 *
 * This payload consists of two arms which can open and close (this motion is handled in another class) and also rotate in a throwing motion. There is also a brake to halt the motion of the arm up and
 * down.
 */
public class Payload implements Runnable {

    Joystick controller;
    Jaguar firingMotorOne = new Jaguar(FRVars.firingMotorOne);
    Jaguar firingMotorTwo = new Jaguar(FRVars.firingMotorTwo);
    Solenoid engageBrake = new Solenoid(FRVars.brakeEngagePort);
    //Solenoid releaseBrake = new Solenoid(FRVars.brakeReleasePort);
    AnalogPotentiometer armRotation = new AnalogPotentiometer(FRVars.armRotation);
    final int analogFireButton = FRVars.analogFireButton;
    final int fireButton = FRVars.fireButton;
    final double armUp = FRVars.armUp;
    final double armDown = FRVars.armUp;
    final double armCatch = FRVars.armCatch;

    public Payload(Joystick c) {
        controller = c;
    }

    public final void lockArm() {
        //releaseBrake.set(false);
        engageBrake.set(false);
        Timer.delay(FRVars.brakeDelay);
    }

    public final void releaseArm() {
        engageBrake.set(true);
        //releaseBrake.set(true);
        Timer.delay(FRVars.brakeDelay);
    }

    public final double reduceToOne(double in) {
        if (in > 1) {
            return 1;
        }
        if (in < -1) {
            return -1;
        }
        return in;
    }

    public final void fire(double power) {
        releaseArm();
        firingMotorOne.set(reduceToOne(power));
        firingMotorTwo.set(reduceToOne(power));
        while (armRotation.get() < armUp);
        firingMotorOne.set(0);
        firingMotorTwo.set(0);
        lockArm();
        Timer.delay(.25);
        releaseArm();
        firingMotorOne.set(-.25);
        firingMotorTwo.set(-.25);
        while (armRotation.get() > armDown);
        firingMotorOne.set(0);
        firingMotorTwo.set(0);
    }

    public final void run() {
        while (true) {
            /*if (controller.getRawButton(fireButton)) {
             fire(1);
             } else if (controller.getRawButton(analogFireButton) && Math.abs(controller.getRawAxis(3)) > .15) {
             fire(Math.abs(controller.getRawAxis(3)));
             }*/
            while ((controller.getRawAxis(5) > .1 && armRotation.get() < armUp) || (controller.getRawAxis(5) < -.1 && armRotation.get() > armDown)) {
                releaseArm();
                firingMotorOne.set(controller.getRawAxis(5) * .25);
                firingMotorTwo.set(controller.getRawAxis(5) * .25);
            }
            while (armRotation.get() < armDown) {
                releaseArm();
                firingMotorOne.set(.25);
                firingMotorTwo.set(.25);
            }
            while (armRotation.get() > armUp) {
                releaseArm();
                firingMotorOne.set(-.25);
                firingMotorTwo.set(-.25);
            }
            firingMotorOne.set(0);
            firingMotorTwo.set(0);
            lockArm();
        }
    }
}
