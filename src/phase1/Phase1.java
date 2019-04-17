// David Will

package phase1;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;


public class Phase1 {

    
    public static void main(String[] args) {
        
        
        Scanner myScanner = new Scanner(System.in);
        
        System.out.print("Enter the number of rows: ");
        int userRows = myScanner.nextInt();

        System.out.print("Enter the number of columns: ");
        int userColumns = myScanner.nextInt();
        
        EventQueue.invokeLater(
        new Runnable()
        {

            @Override
            public void run()
            {

                ProgramFrame frame = new ProgramFrame(userRows, userColumns);

                frame.setTitle("Holes");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                }

            }
        );
        
    }
    
}


class ProgramFrame extends JFrame
{

    public ProgramFrame(int userInputRows, int userInputColumns)
    {

        add(new HolesComponent());
        pack();
        
    }
}


class HolesComponent extends JComponent
{   private int initialDiameter = 100;
    private int userRows = 7;
    private int userColumns = 5;
    
    private int defaultWidth = userColumns * initialDiameter;
    private int defaultHeight = userRows * initialDiameter;
    
    private Ellipse2D myCurrentEllipse;
    private ArrayList<Ellipse2D> myEllipses;
    
    private ArrayList<Ellipse2D> ellipses(){
        return myEllipses;
    }
    
    private void setCurrentEllipse(Ellipse2D other){
        myCurrentEllipse = other;
    }
    
    private Ellipse2D currentEllipse(){
        return myCurrentEllipse;
    }
    
    
    @Override
    public void paintComponent(Graphics canvas){      
        Graphics2D g2 = (Graphics2D) canvas;
        Ellipse2D testEllipse;
        
        
        int initialX;
        int initialY;
        
        for (initialX = 0; initialX < defaultWidth; initialX += 100){
            for (initialY = 0; initialY < defaultHeight; initialY += 100){
                testEllipse = new Ellipse2D.Double(initialX, initialY, initialDiameter, initialDiameter);
                g2.fill(testEllipse);
            }
        }     
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(defaultWidth, defaultHeight);
    }
    
    public HolesComponent(){
        addComponentListener(new windowComponentListener());
    }
    
    
    private class windowComponentListener implements ComponentListener{
        
        @Override
        public void componentHidden(ComponentEvent e) {
            ;
        }
        
        @Override
        public void componentMoved(ComponentEvent e) {
            ;
        }

        @Override
        public void componentResized(ComponentEvent e) {
            System.out.println("Window Resized");            
        }
        
        @Override
        public void componentShown(ComponentEvent e) {
            ;

        }
    }
    
    private class MouseHandler extends MouseAdapter{
        
        @Override
        public void mouseClicked(MouseEvent event){
            
        }
    }
}


