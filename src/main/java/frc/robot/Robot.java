/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
    public static CvSink cvSink;
    public static CvSource outputStream;

    public static Mat image;
    public static Mat output;
    public static Mat contoursInput;
    public static Mat contoursOutput;

    GripPipeline pipeline = new GripPipeline();
    public static UsbCamera camera;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    
    m_robotContainer = new RobotContainer();

      
    new Thread(() -> {

      camera = CameraServer.getInstance().startAutomaticCapture();
      camera.setResolution(1280, 720);
      
      camera.setExposureManual(10);
      
      cvSink = CameraServer.getInstance().getVideo();
      outputStream = CameraServer.getInstance().putVideo("Processed", 1280, 720);

      System.out.println("IMAGE CREATED");
      image = new Mat();
      output = new Mat();
      
      while(!Thread.interrupted())
      {
       

          System.out.println("THREAD WAS NOT INTERRUPTED");

          
          if(cvSink.grabFrame(image)==0)
            System.out.println("i just lost the game");
          else
          {
            System.out.println("RAW FRAME WAS GRABBED");

          //  pipeline.process(image);
           Imgproc.cvtColor(image, output, Imgproc.COLOR_BGR2GRAY);
          // Imgproc.cvtColor(image, output, Imgproc.COLOR_BGR2HLS);
		      // Core.inRange(output, new Scalar(70.14388592123126, 65.73742173558516, 243.0755510497436),
          //     new Scalar(90.9215075652347, 169.41978968857904, 255.0), output);
          
          



          System.out.println("IMAGE WAS PROCESSED");

          outputStream.putFrame(output);
          System.out.println("IMAGE WAS OUTPUTTED");
          }


      }
    }).start();
  
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() 
  {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    
    
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}


















