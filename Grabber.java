/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package team1076.robot.year2014;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author supereater14
 */
public class Grabber implements Runnable {
    
    Joystick controller;
    Solenoid opener = new Solenoid(FRVars.openingPort);
    Solenoid closer = new Solenoid(FRVars.closingPort);
    boolean armPos = false;
    
    
    public Grabber(Joystick c){
        controller = c;
    }
    
    public final void openArms(){
        closer.set(false);
        opener.set(true);
        armPos = false;
    }
    
    public final void closeArms(){
        opener.set(false);
        closer.set(true);
        armPos = true;
    }
    
    public final void run(){
        while(true){
            if(controller.getRawButton(FRVars.openButton))openArms();
            if(controller.getRawButton(FRVars.closeButton))closeArms();
        }
    }
    
}
