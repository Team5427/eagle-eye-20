package frc.robot;

import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

import java.util.ArrayList;

public class VisionPanel extends JPanel
{
    public static ArrayList<MatOfPoint> points = null;
    public static  BufferedImage image;
    public static final int purpleColor = -15340065;

    public VisionPanel(int w, int h)
    {
        super();
        setSize(w,h);

    }
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(1000/20);
                
                imageToContours();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public void imageToContours()
    {
        Object[] contours = points.toArray();
        BufferedImage contoursImage = new BufferedImage(320, 240, BufferedImage.TYPE_4BYTE_ABGR);
        
        for(Object currentContour : contours)
        {
            Point[] points = ((MatOfPoint)currentContour).toArray();

            for(Point p : points)
                contoursImage.setRGB((int)p.x, (int)p.y, purpleColor);
        }
    }
    public void setPoints(ArrayList<MatOfPoint> points)
    {
        this.points = points;
    }
}