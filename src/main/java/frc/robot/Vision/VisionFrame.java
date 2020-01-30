package frc.robot.Vision;

import java.awt.*;
import javax.swing.*;

public class VisionFrame extends JFrame
{
	
    public VisionFrame(String frameName)
    {
        super(frameName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        VisionPanel p = new VisionPanel(800,600);
        Insets frameInsets = getInsets();
        int frameWidth = p.getWidth()
                + (frameInsets.left + frameInsets.right);
        int frameHeight = p.getHeight()
                + (frameInsets.top + frameInsets.bottom);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setLayout(null);
        add(p);
        pack();
        setVisible(true);
	}
	
}
