package frc.robot;

import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class VisionPanel extends JPanel
{
    public static ArrayList<MatOfPoint> points;
    public static ArrayList<Target> validTargets;
    public static ArrayList<Point> pt = null;
    public static BufferedImage image = null;
    public static final int purpleColor = -15340065;
    public static Target biggestTarget = null;

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
        Point[] contours = (Point[])points.toArray();
        BufferedImage contoursImage = new BufferedImage(320, 240, BufferedImage.TYPE_4BYTE_ABGR);

        
        Point[] points;
        Target target;
        for(Object currentContour : contours)
        {
            points = ((MatOfPoint)currentContour).toArray();
            target = new Target(points);
            if(target.getWidthRatio()> 1.5 && target.getWidthRatio()<2.5)
            {
                validTargets.add(target);
            }

            for(Point p : points)
                contoursImage.setRGB((int)p.x, (int)p.y, purpleColor);
            
        }

        if(!validTargets.isEmpty())
        {
            biggestTarget = validTargets.get(0);
            System.out.println("No Valid Targets");
        }
            
        for(Target t : validTargets)
        {
            if(t.gettopWidth()>biggestTarget.gettopWidth())
                biggestTarget=t;
        }
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.RED);

        double tlx = biggestTarget.getTopLeftPoint().x;
        double trx = biggestTarget.getTopRightPoint().x;
        double blx = biggestTarget.getBottomLeftPoint().x;
        double brx = biggestTarget.getBottomRightPoint().x;
        double tly = biggestTarget.getTopLeftPoint().y;
        double tryy = biggestTarget.getTopRightPoint().y;
        double bly = biggestTarget.getBottomLeftPoint().y;
        double bry = biggestTarget.getBottomRightPoint().y;
        double topWidth = biggestTarget.gettopWidth();

        g.drawLine((int)tlx,(int)tly,(int)(tlx+topWidth),(int)tly);
        g.drawLine((int)tlx,(int)tly,(int)(blx),(int)(bly));
        g.drawLine((int)blx,(int)bly,(int)(brx),(int)(bry));
        g.drawLine((int)brx,(int)bry,(int)(trx),(int)(tryy));
        g.drawLine((int)trx,(int)tryy,(int)(trx-topWidth),(int)(tryy));
        g.drawLine((int)(tlx+topWidth),(int)tly,(int)(blx),(int)(bly+topWidth));
        g.drawLine((int)(blx),(int)(bly+topWidth),(int)(brx),(int)(bry+topWidth));
        repaint();

    }
    public void setPoints(ArrayList<MatOfPoint> points)
    {
        this.points.clear();
        for(MatOfPoint mp : points)
        {
            this.points.add(mp);
        }
        
    }
}