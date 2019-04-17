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

                ProgramFrame frame = new ProgramFrame();

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

    public ProgramFrame()
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
    private ArrayList<Ellipse2D> myEllipses = new ArrayList<Ellipse2D>();
    
    private ArrayList< Ellipse2D > ellipses(){
        
        return myEllipses;
        
    }
    
    private ArrayList<Ellipse2D> setEllipses(ArrayList<Ellipse2D> other){
        myEllipses = other;
        return myEllipses;
    }
    
    
    public void setUpEllipses(){
        Ellipse2D testEllipse;
        
        for (int initialX = 0; initialX < defaultWidth; initialX += 100){
            for (int initialY = 0; initialY < defaultHeight; initialY += 100){
                testEllipse = new Ellipse2D.Double(initialX, initialY, 100,100);
                ellipses().add(testEllipse);
                
            }
        }
        
        
        for (Ellipse2D itemInArrayList: ellipses())
            System.out.println(itemInArrayList.getX());
    }
    
    
    public void pickRedEllipse(){
        
    }
    
    
    @Override
    public void paintComponent(Graphics canvas){      

        for (Ellipse2D ellipseOnCanvas: ellipses())
            ((Graphics2D) canvas).fill(ellipseOnCanvas);
    

    }
    
    public void redoEllipse(int newEllipseHeight, int newEllipseWidth){
        
        ArrayList<Ellipse2D> newEllipseArrayList = new ArrayList<Ellipse2D>();

        Ellipse2D redoneEllipse = new Ellipse2D.Double(0,0,0,0);
        
        int initialX = 0;
        int initialY = 0;
        
        for (int numColumns = 0; numColumns < userColumns; ++ numColumns){
            initialY =0;
            for (int numRows = 0; numRows < userRows; ++ numRows){
                redoneEllipse = new Ellipse2D.Double(initialX, initialY, newEllipseWidth, newEllipseHeight);
                newEllipseArrayList.add(redoneEllipse);
                initialY += newEllipseHeight;
            }
            initialX += newEllipseWidth;
        }
        
        setEllipses(newEllipseArrayList);
        repaint();
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
        setUpEllipses();
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
            int newHeight = e.getComponent().getHeight() / userRows;
            int newWidth = e.getComponent().getWidth() / userColumns;
            redoEllipse(newHeight, newWidth);     
            
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
