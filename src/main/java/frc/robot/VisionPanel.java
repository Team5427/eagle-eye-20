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
    public static final int PURPLE_COLOR = -15340065;
    
    public int width;
    public int height;
    private ArrayList<MatOfPoint> points;
    private ArrayList<Point> pt;
    private ArrayList<Target> targets;
    private Target largestTarget;
    private BufferedImage image;
    private Object[] contours;
    private BufferedImage contoursImage;

    public VisionPanel(int w, int h)
    {
        super();
        setSize(w,h);
        contoursImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
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
        contours = points.toArray();
        
        Point[] points;
        Target target;
        for(int i = contours.length-1; i>0; i--)
        {
            points = ((MatOfPoint)contours[i]).toArray();
            target = new Target(points);

            

            for(Point p : points)
                contoursImage.setRGB((int)p.x, (int)p.y, PURPLE_COLOR);
            
        }
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.RED);
        g.drawLine(0, 120, 320, 120);
        g.drawLine(160, 0, 160, 240);

        g.setColor(Color.WHITE);
        Point [] p = points.get(0).toArray();
        for(int x = 0;x<p.length; x++)
        {
            System.out.println(p[x].x);
        }
    }

    public void setPoints(ArrayList<MatOfPoint> points)
    {
        this.points = points;
    }
}