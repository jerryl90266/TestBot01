package org.usfirst.frc.team4201.robot;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.usfirst.frc.team4201.robot.OI;
//import org.usfirst.frc.team4201.robot.subsystems.ArmPiston;
//import org.usfirst.frc.team4201.robot.subsystems.Intake;
//import org.usfirst.frc.team4201.robot.subsystems.Shifter;
//import org.usfirst.frc.team4201.robot.subsystems.Shooter;
//import org.usfirst.frc.team4201.robot.subsystems.ShooterArm;
//import org.usfirst.frc.team4201.robot.subsystems.Vision;
import org.usfirst.frc.team4201.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	Joystick stick;
	int autoLoopCounter;
	Timer timer;
	Command autonomousCommand;

	// team294
	public static OI oi;
	// Creates the SubSystem objects
	public static DriveTrain driveTrain;
//	public static Shifter shifter;
//	public static ShooterArm shooterArm;
//	public static Shooter shooter;
//	public static Intake intake;
//	public static Vision vision;
//	public static ArmPiston armPiston;
	public static PowerDistributionPanel panel;
	// Turn on/off SmartDashboard debugging
	public static boolean smartDashboardDebug = false;		// true to print lots of stuff on the SmartDashboard
	public static FileWriter debugStream;
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void writeLog(String msg) {
		if (debugStream == null) return;
		try {
			debugStream.write(format.format(new Date()) + ": " + msg + "\n");
			debugStream.flush();
		} catch (IOException e) {
		}
	}
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
//    	myRobot = new RobotDrive(0,1);
    	stick = new Joystick(0);
    	timer = new Timer();
    	
    	// team294
		//Instantiates the subsystems
		driveTrain = new DriveTrain();
//		shifter = new Shifter();
//		shooterArm = new ShooterArm();
//		shooter = new Shooter();
//		intake = new Intake();
//		vision = new Vision();
		panel = new PowerDistributionPanel();
//		armPiston = new ArmPiston();

		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.
		oi = new OI();

		// etc team294
		// Display active commands and subsystem status on SmartDashboard
		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putData(driveTrain);

    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    	timer.reset(); // Resets the timer to 0
        timer.start(); // Start counting
        
        // team294
		writeLog("autonomousInit");
		
		// schedule the autonomous command (example)
//		autonomousCommand = oi.getAutonomousCommand();
//		
//		if (autonomousCommand != null)
//			autonomousCommand.start();

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
//			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			driveTrain.driveForward(0.2);	// 20% 
	    	driveTrain.getLeftEncoder();
	    	driveTrain.getRightEncoder();

			autoLoopCounter++;
		} else
    	if(autoLoopCounter < 200) //Check if we've completed 100 loops (approximately 2 seconds)
    	{
			driveTrain.driveBackward(0.2);	// 20% 
//    		driveTrain.driveCurve(0.2, 0.6);
			autoLoopCounter++;
    	}
    	else {
//			myRobot.drive(0.0, 0.0); 	// stop robot
			driveTrain.stop();

		}
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
		writeLog("teleopInit");

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//template 
//        myRobot.arcadeDrive(stick);
		driveTrain.getDegrees();
		if (smartDashboardDebug) {
			// Uncomment the following line to read coPanel knobs.
			oi.updateSmartDashboard();

			// Uncomment the following line for debugging shooter motors PIDs.
//			shooter.setPIDFromSmartDashboard();
			
			// Uncomment the following line for debugging the arm motor PID.
//	        shooterArm.setPIDFromSmartDashboard();
			
			// Uncomment the following lines to see drive train data
	    	driveTrain.getLeftEncoder();
	    	driveTrain.getRightEncoder();
			driveTrain.smartDashboardNavXAngles();
			
			//		SmartDashboard.putNumber("Panel voltage", panel.getVoltage());
			//		SmartDashboard.putNumber("Panel arm current", panel.getCurrent(0));
		}

    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
