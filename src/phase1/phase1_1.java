// David Will

package phase1;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;


public class phase1_1 {

    
    public static void main(String[] args) {
        
        
//        Scanner myScanner = new Scanner(System.in);
//        
//        System.out.print("Enter the number of rows: ");
//        int userRows = myScanner.nextInt();
//
//        System.out.print("Enter the number of columns: ");
//        int userColumns = myScanner.nextInt();

        int userRows=0;
        int userColumns=0;
        
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

        add(new MouseComponent());
        pack();
        
    }
}


class MouseComponent extends JComponent
{   final private int initialDiameter = 100;
    private int userRows = 7;
    private int userColumns = 5;
    
    final private int defaultWidth = userColumns * initialDiameter;
    final private int defaultHeight = userRows * initialDiameter;
    
    private Ellipse2D myCurrentEllipse;
    private ArrayList< Ellipse2D > myEllipses = new ArrayList< Ellipse2D >();
    
    private ArrayList< Ellipse2D > ellipses(){
        
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
    
    
    public void findEllipseContainingPoint(Point2D clickPoint)
    {
//    
//        int ellipseNumber = 0;
//        Boolean pointInEllipse = false;
//        
        System.out.println(ellipses().size());
//        
//        for(; ellipseNumber < ellipses().size(); ++ellipseNumber ){
//            System.out.println(
//            
//            ellipses().get(ellipseNumber).contains(clickPoint)
//            
//            );
//            if (pointInEllipse) break;
//        }
        
//        return pointInEllipse ?  ellipses().get(ellipseNumber) : null;
    
    }
    
    @Override
    public Dimension getPreferredSize(){
        
        return new Dimension(defaultWidth, defaultHeight);
        
    }
    
    public MouseComponent(){
        
        addMouseListener(new MouseHandler());
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
        
        int myPresses = 0;
        int myClicks = 0;
        Ellipse2D previouslyClickedHole;
        
        @Override
        public void mousePressed(MouseEvent event){
            
            ++myPresses;
            System.out.println(
                    "Mouse press " + myPresses + " at (" 
                    + event.getX() + ", " + event.getY() + ")"
            );
            
//            setCurrentEllipse(findEllipseContainingPoint(event.getPoint()));
        findEllipseContainingPoint(event.getPoint());
            
        }
        
        @Override
        public void mouseClicked(MouseEvent event){
            
            ++myClicks;
            if (event.getClickCount() > 0)
                System.out.println(
                        "Mouse click(s) " + myClicks + " at ("
                        + event.getX() + ", " + event.getY() + ")"
                );
            
        }
        
    }
}
