
package whackAHole;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import java.util.Scanner;

/**
 * @author Swopnil N. Shrestha, David Will
 */

public class whackAHole {
    public static void main(String [] commandLineArguments)
    {
        
//        Scanner scan = new Scanner(System.in);
//        
//        System.out.print("Enter the number of holes in each row: ");
//        int holesPerRow = scan.nextInt();
//        System.out.print("Enter the number of holes in each column: ");
//        int holesPerColumn = scan.nextInt();
//        
        EventQueue.invokeLater(() -> {
            ProgramFrame frame = new ProgramFrame();
            
            frame.setTitle("Whack A Hole");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    
    }
}


class InputHelper
{
    
    Scanner scan = new Scanner(System.in);
    private int holesInRow;
    private int holesInColumn;
    
    
    private void setHolesInRow()
    {
    
        System.out.print("Enter the number of holes in a row: ");
        holesInRow = scan.nextInt();
        
    }
    
    private void setColumnInRow()
    {
    
        System.out.print("Enter the number of holes in a column: ");
        holesInColumn = scan.nextInt();
                    
    }
    
    public int holesInRow(){
    
        return holesInRow;
        
    }
    
    public int holesInColumn(){
    
        return holesInColumn;
        
    }
    
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
    private static final int OUR_DEFAULT_RADUIS = 50;
    private static final int OUR_DEFAULT_WIDTH = OUR_DEFAULT_RADUIS * 2 * 3; // 3 initialized as holes per row (need to change that later)
    private static final int OUR_DEFAULT_HEIGHT = OUR_DEFAULT_RADUIS * 2 * 3; // 3 initialized as holes per column (need to change that later)
    
    private ArrayList< Ellipse2D >  myHoles;
    private Ellipse2D myCurrentHole;
    
    private ArrayList< Ellipse2D > holes()
    {
        
        return myHoles;
        
    }
    
    private Ellipse2D currentHole()
    {
    
        return myCurrentHole;
        
    }        
    
    private void setHoles(ArrayList< Ellipse2D > otherHoles)
    {
    
        myHoles = otherHoles;
        
    }
    
    private void setCurrentHole(Ellipse2D otherHole)
    {
    
        myCurrentHole = otherHole;
    
    }
    
    public MouseComponent()
    {
    
        setHoles(new ArrayList< Ellipse2D >());
        setCurrentHole(null);
        
        // Add mouse click listener here
        
    }
    
    @Override
    public Dimension getPreferredSize() // This is an inbuilt method
    {
    
        return new Dimension(OUR_DEFAULT_WIDTH, OUR_DEFAULT_HEIGHT);
        
    }
    
    
    @Override
    public void paintComponent(Graphics canvas)
    {
        
        for (Ellipse2D holeOnCanvas : holes())
            ((Graphics2D) canvas).draw(holeOnCanvas);

    }
    
}