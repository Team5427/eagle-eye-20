package frc.robot;
import org.opencv.core.*;

public class Target {

    /**
     * The left and right pieces of retroreflective tape
     */
    /**
     * The average of both halves' centers
     */
    
    public boolean isHatch;

    /**
     * From Manual - In inches
     */

    Point[] points;

    public static final double HEIGHT_TARGET = 0;
    private double height;
    private double topWidth;
    private double bottomWidth;
    private Point center = new Point();
    private double widthRatio = 0;
    private boolean isTarget = false;


    public Target(Point[] points, boolean isTarget) 
    {
        this.points = points;
        this.isTarget = isTarget;
        Point topLeftPoint = points[0];
        Point topRightPoint = points[0];
        Point bottomLeftPoint = points[0];
        Point bottomRightPoint = points[0];
    
        for (Point p : points)
        {
            if(p.x < topLeftPoint.x)
                topLeftPoint = p;
            else if(p.x > topRightPoint.x)
                topRightPoint = p;
            if(p.y >= bottomLeftPoint.y && p.x < bottomLeftPoint.x)
                bottomLeftPoint = p;
            else if(p.y >= bottomRightPoint.y && p.x > bottomRightPoint.y)
                bottomRightPoint = p;
        }

        center.x = (topLeftPoint.x + topLeftPoint.x) / 2;
        center.y = (topLeftPoint.y + bottomLeftPoint.y) / 2;

        height = bottomLeftPoint.y - topLeftPoint.y;
        topWidth = topRightPoint.x - topLeftPoint.x;
        bottomWidth = bottomRightPoint.x - bottomLeftPoint.x;
        widthRatio = (double)topWidth / bottomWidth;

    }

    public double getWidthRatio()
    {
        return widthRatio;
    }

    public void setIsTarget(boolean isTarget)
    {
        this.isTarget = isTarget;
    }

    public double getTapeDist()
    {
        double diffX = right.topLeft.x - left.topLeft.x;
        double diffY = right.topLeft.y - left.topLeft.y;
        double pixDist = (right.topLeft.y != left.topLeft.y)? Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2)) : diffX;
        return pixDist;
    }

    public double getXOverZ() {
        return (center.x - GraphicsPanel.imageCenterX)/GraphicsPanel.focalLen;
    }
    public double getYOverZ() {
        return (center.y - GraphicsPanel.imageCenterY)/GraphicsPanel.focalLen;
    }
    public double getConstant3() {
        return getYOverZ()/getXOverZ();
    }
    public double getConstant4() {
        return 45; //inches, height of target - height of camera
    }
    public double solveForX() {
        return getConstant4()/getConstant3();
    }
    public double solveForZ() {
        return solveForX()/getXOverZ();
    }
    public double getHorAngle() {
        return Math.atan(solveForX());
    }

}