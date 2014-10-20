/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team1076.robot.year2014;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author supereater14
 *
 * This drivetrain uses one sideways omniwheel in the front for steering and two tank drive wheels in the back. The front omniwheel is controlled using one motor and the rear wheels each have two.
 */
public class DriveTrain implements Runnable {

    Joystick controller;
    Jaguar frontMotor = new Jaguar(FRVars.frontMotor);
    Jaguar leftMotorOne = new Jaguar(FRVars.leftMotorOne);
    Jaguar leftMotorTwo = new Jaguar(FRVars.leftMotorTwo);
    Jaguar rightMotorOne = new Jaguar(FRVars.rightMotorOne);
    Jaguar rightMotorTwo = new Jaguar(FRVars.rightMotorTwo);
    Solenoid upShifter = new Solenoid(FRVars.upShifterPort);
    Solenoid downShifter = new Solenoid(FRVars.downShifterPort);
    final int shiftButton = FRVars.shiftButton;
    final int lowGearButton = FRVars.lowGearButton;
    final int highGearButton = FRVars.highGearButton;
    final double fbRatio = FRVars.fbRatio;
    boolean currentGear = false;
    boolean herding = false;
    double y1;
    double y2;

    public DriveTrain(Joystick c) {
        controller = c;
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

    public final void setLeft(double val) {
        leftMotorOne.set(reduceToOne(val));
        leftMotorTwo.set(reduceToOne(val));
    }

    public final void setRight(double val) {
        rightMotorOne.set(reduceToOne(-val));
        rightMotorTwo.set(reduceToOne(-val));
    }

    public final void setSteering(double val) {
        frontMotor.set(reduceToOne(val));
    }

    public final void setMotors(double l, double r) {
        setSteering((r - l));
        setLeft(l);
        setRight(r);
    }

    public final void upShift() {
        downShifter.set(false);
        upShifter.set(true);
        currentGear = true;
    }

    public final void downShift() {
        upShifter.set(false);
        downShifter.set(true);
        currentGear = false;
    }

    public final void shift() {
        if (currentGear) {
            downShift();
        } else {
            upShift();
        }
    }

    public final void run() {
        downShift();
        while (true) {
            y1 = controller.getRawAxis(2);
            y2 = controller.getRawAxis(5);
            
           
            if (herding) {
                setSteering((y1-y2)*0.75);
                setLeft(y1);
                setRight(y2);
            } else {
                setSteering(y2-y1);
                setLeft(y2);
                setRight(y1);
            }
                    /*
            x = 0 - controller.getX();
            y = 0 - controller.getY();
            if (herding) {
                setSteering(x);
                setLeft((y + (x * fbRatio)));
                setRight((y - (x * fbRatio)));
            } else {
                setSteering(x);
                setLeft(-(y - (x * fbRatio)));
                setRight(-(y + (x * fbRatio)));
            }
                            */
            if (controller.getRawButton(shiftButton)) {
                herding = !herding;
                Timer.delay(.3);
            }
            if (controller.getRawButton(lowGearButton)) {
                downShift();
            }
            if (controller.getRawButton(highGearButton)) {
                upShift();
            }
        }
    }
}
