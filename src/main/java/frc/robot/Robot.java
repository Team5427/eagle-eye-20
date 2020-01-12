/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
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
    // public static CvSink cvSink;
    // public static CvSource outputStream;

    // public static Mat image;
    // public static Mat output;

    // //GripPipeline pipeline = new GripPipeline();
    // public static UsbCamera camera;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    
    m_robotContainer = new RobotContainer();

      
    new Thread(() -> {
      //Initializes Camera from RoboRio and starts capture
      UsbCamera camera =CameraServer.getInstance().startAutomaticCapture();
      camera.setResolution(640, 480); //sets resolution
      
      //Gets video from RoboRio CameraServer [accessible via SmrtDshbrd]
      CvSink cvSink = CameraServer.getInstance().getVideo();
      CvSource outputStream = CameraServer.getInstance().putVideo("Processed", 640, 480);

      Mat source = new Mat(); //Mats are essentially video frame Objects
      Mat output = new Mat();
      GripPipeline pipeline = new GripPipeline();
      camera.setExposureManual(0);

      while(!Thread.interrupted()){
          //source is changed by reference with vid frame (Mat)
          if(cvSink.grabFrame(source) == 0)
            System.out.print("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
          else
          {
            Mat hslThresholdInput = source;
		double[] hue = {70.14388592123126, 90.9215075652347};
		double[] sat = {243.0755510497436, 255.0};
    double[] lum = {65.73742173558516, 169.41978968857904};
    Mat hslThresholdOutput = new Mat();
    Imgproc.cvtColor(hslThresholdInput, hslThresholdOutput, Imgproc.COLOR_BGR2HLS);
		Core.inRange(hslThresholdOutput, new Scalar(hue[0], lum[0], sat[0]),
      new Scalar(hue[1], lum[1], sat[1]), hslThresholdOutput);
      




      // Mat findContoursInput = hslThresholdOutput;
      // boolean findContoursExternalOnly = true;
      // Mat hierarchy = new Mat();
      // ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
      // findContoursOutput.clear();
      // int mode;
      // if (findContoursExternalOnly) {
      //   mode = Imgproc.RETR_EXTERNAL;
      // }
      // else {
      //   mode = Imgproc.RETR_LIST;
      // }
      // int method = Imgproc.CHAIN_APPROX_SIMPLE;
      // Imgproc.findContours(findContoursInput, findContoursOutput, hierarchy, mode, method);
  
  
  
  
      // ArrayList<MatOfPoint> filterContoursContours = findContoursOutput;
      // ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<MatOfPoint>();
  
      // double filterContoursMinArea = 12.0;
      // double filterContoursMinPerimeter = 45.0;
      // double filterContoursMinWidth = 38.0;
      // double filterContoursMaxWidth = 1000.0;
      // double filterContoursMinHeight = 53.0;
      // double filterContoursMaxHeight = 1000.0;
      // double[] filterContoursSolidity = {0, 100};
      // double filterContoursMaxVertices = 1000000.0;
      // double filterContoursMinVertices = 0.0;
      // double filterContoursMinRatio = 0.0;
      // double filterContoursMaxRatio = 1000.0;
      
      
      // MatOfInt hull = new MatOfInt();
      // filterContoursOutput.clear();
      // //operation
      // for (int i = 0; i < filterContoursContours.size(); i++) {
      //   final MatOfPoint contour = filterContoursContours.get(i);
      //   final Rect bb = Imgproc.boundingRect(contour);
      //   if (bb.width < filterContoursMinWidth || bb.width > filterContoursMaxWidth) continue;
      //   if (bb.height < filterContoursMinHeight || bb.height > filterContoursMaxHeight) continue;
      //   final double area = Imgproc.contourArea(contour);
      //   if (area < filterContoursMinArea) continue;
      //   if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < filterContoursMinPerimeter) continue;
      //   Imgproc.convexHull(contour, hull);
      //   MatOfPoint mopHull = new MatOfPoint();
      //   mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
      //   for (int j = 0; j < hull.size().height; j++) {
      //     int index = (int)hull.get(j, 0)[0];
      //     double[] point = new double[] { contour.get(index, 0)[0], contour.get(index, 0)[1]};
      //     mopHull.put(j, 0, point);
      //   }
      //   final double solid = 100 * area / Imgproc.contourArea(mopHull);
      //   if (solid < filterContoursSolidity[0] || solid > filterContoursSolidity[1]) continue;
      //   if (contour.rows() < filterContoursMinVertices || contour.rows() > filterContoursMaxVertices)	continue;
      //   final double ratio = bb.width / (double)bb.height;
      //   if (ratio < filterContoursMinRatio || ratio > filterContoursMaxRatio) continue;
      //   filterContoursOutput.add(contour);
      // }



    
      //   pipeline.process(hslThresholdOutput);
          //OpenCV turns vid frame image to grayscale and changes output    
          //by reference
          //Imgproc.cvtColor(source,output,Imgproc.COLOR_BGR2GRAY);      
          outputStream.putFrame(hslThresholdOutput); //outputs frame to CameraServer
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


















