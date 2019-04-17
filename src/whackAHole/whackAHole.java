package whackAHole;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class whackAHole{

    public static void main(String[] commandLineArguments)
    {
    
        EventQueue.invokeLater(
        new Runnable()
        {

            @Override
            public void run()
            {

                ProgramFrame frame = new ProgramFrame();

                frame.setTitle("Holes");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                }

            }
        );
    
    }
    
    class ProgramFrame extends JFrame
    {
    
        public ProgramFrame()
        {
        
            add(new MouseComponent());
            pack();
        
        }
    
    }
    
    class MouseComponent extends JComponent
    {
    
        final private int initialDiameter = 100;
        private int userRows = 7;
        private int userColumns = 5;
        
        private int defaultWidth = userColumns * initialDiameter;
        private int defaultHeight = userRows * initialDiameter;
        
        private Ellipse2D myCurrentEllipse;
        private ArrayList < Ellipse2D > myEllipses = 
                new ArrayList< Ellipse2D >();
        
    }

}