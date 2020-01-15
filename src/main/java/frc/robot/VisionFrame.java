package frc.robot;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.opencv.core.MatOfPoint;

public class VisionFrame extends JFrame
{
    VisionPanel vp;
    public VisionFrame()
    {
        vp = new VisionPanel(1080, 720);
    }

    public VisionPanel getVisionPanel()
    {
        return vp;
    }
    
}