package Vision;

import java.util.ArrayList;

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

    private double height;
    private double topWidth;
    private double bottomWidth;
    private Point center = new Point();
    private double widthRatio = 0;
    private ArrayList<Point> outerPoints;
    private double leftMinAngle = 0;
    private double rightMinAngle = 0;
    private double bottomPointVal = 0;
    private Point topLeftPoint = null;
    private Point topRightPoint = null;
    private Point bottomLeftPoint = null;
    private Point bottomRightPoint = null;

    public Target(Point[] pts) 
    {
        points = new Point[pts.length];
        for(int x = 0; x<pts.length;x++)
        {
            points[x] = pts[x];
        }
        Point topLeftPoint = points[0];
        Point topRightPoint = points[0];
        Point bottomLeftPoint = null;
        Point bottomRightPoint = null;

        for(int i = 0; i<points.length; i++)
        {
            for(int j = 0; j<points.length; j++)
            {
                if(points[j].x == points[i].x && points[i].y  < points[j].y)
                    break;
            }
            outerPoints.add(points[i]);
        }
    
        bottomPointVal = points[0].y;
        for (Point p : points)
        {
            if(p.x < topLeftPoint.x)
                topLeftPoint = p;
            else if(p.x > topRightPoint.x)
                topRightPoint = p;

            if(p.y < bottomPointVal)
                bottomPointVal = p.y;
        }

        center.x = (topLeftPoint.x + topRightPoint.x) / 2;
        center.y = (topLeftPoint.y + bottomPointVal) / 2;

        for(int x = 1; x<outerPoints.size()-1; x++)
        {
            double initialpx = outerPoints.get(x-1).x;
            double initialpy = outerPoints.get(x-1).y;
            double currentpx = outerPoints.get(x).x;
            double currentpy = outerPoints.get(x).y;
            double nextpx = outerPoints.get(x+1).x;
            double nextpy = outerPoints.get(x+1).y;

            if(initialpy >= center.y && currentpy >= center.y && nextpy >= center.y)
            {
                if(initialpx <= center.x && currentpx <= center.x && nextpx <= center.x)
                {
                    double curToInitial = Math.sqrt(Math.pow(currentpx-initialpx,2)+ Math.pow(currentpy-initialpy,2));
                    double curToNext = Math.sqrt(Math.pow(currentpx-nextpx,2)+ Math.pow(currentpy-nextpy,2));
                    double initialToNext = Math.sqrt(Math.pow(initialpx-nextpx,2)+ Math.pow(initialpy-nextpy,2));

                    double angle = Math.acos((Math.pow(curToInitial,2)+Math.pow(curToNext,2)- Math.pow(initialToNext,2))/(2 * curToInitial * curToNext));

                    if(angle < leftMinAngle)
                    {
                        leftMinAngle = angle;
                        bottomLeftPoint = outerPoints.get(x);
                    }
                }
            }
        }

        for(int x = 1; x<outerPoints.size()-1; x++)
        {
            double initialpx = outerPoints.get(x-1).x;
            double initialpy = outerPoints.get(x-1).y;
            double currentpx = outerPoints.get(x).x;
            double currentpy = outerPoints.get(x).y;
            double nextpx = outerPoints.get(x+1).x;
            double nextpy = outerPoints.get(x+1).y;

            if(initialpy >= center.y && currentpy >= center.y && nextpy >= center.y)
            {
                if(initialpx >= center.x && currentpx >= center.x && nextpx >= center.x)
                {
                    double curToInitial = Math.sqrt(Math.pow(currentpx-initialpx,2)+ Math.pow(currentpy-initialpy,2));
                    double curToNext = Math.sqrt(Math.pow(currentpx-nextpx,2)+ Math.pow(currentpy-nextpy,2));
                    double initialToNext = Math.sqrt(Math.pow(initialpx-nextpx,2)+ Math.pow(initialpy-nextpy,2));

                    double angle = Math.acos((Math.pow(curToInitial,2)+Math.pow(curToNext,2)- Math.pow(initialToNext,2))/(2 * curToInitial * curToNext));

                    if(angle < rightMinAngle)
                    {
                        rightMinAngle = angle;
                        bottomRightPoint = outerPoints.get(x);
                    }
                }
            }
        }

        height = Math.abs(bottomLeftPoint.y - topLeftPoint.y);
        topWidth = Math.abs(topRightPoint.x - topLeftPoint.x);
        bottomWidth = Math.abs(bottomRightPoint.x - bottomLeftPoint.x);
        widthRatio = (double)topWidth / bottomWidth;

    }

    public double getWidthRatio()
    {
        return widthRatio;
    }
    public double gettopWidth()
    {
        return topWidth;
    }
    public Point getTopLeftPoint()
    {
        return topLeftPoint;
    }
    public Point getTopRightPoint()
    {
        return topRightPoint;
    }
    public Point getBottomLeftPoint()
    {
        return bottomLeftPoint;
    }
    public Point getBottomRightPoint()
    {
        return bottomRightPoint;
    }
   
    // public double getTapeDist()
    // {
    //     double diffX = right.topLeft.x - left.topLeft.x;
    //     double diffY = right.topLeft.y - left.topLeft.y;
    //     double pixDist = (right.topLeft.y != left.topLeft.y)? Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2)) : diffX;
    //     return pixDist;
    // }

    // public double getXOverZ() {
    //     return (center.x - GraphicsPanel.imageCenterX)/GraphicsPanel.focalLen;
    // }
    // public double getYOverZ() {
    //     return (center.y - GraphicsPanel.imageCenterY)/GraphicsPanel.focalLen;
    // }
    // public double getConstant3() {
    //     return getYOverZ()/getXOverZ();
    // }
    // public double getConstant4() {
    //     return 45; //inches, height of target - height of camera
    // }
    // public double solveForX() {
    //     return getConstant4()/getConstant3();
    // }
    // public double solveForZ() {
    //     return solveForX()/getXOverZ();
    // }
    // public double getHorAngle() {
    //     return Math.atan(solveForX());
    //}
    

}