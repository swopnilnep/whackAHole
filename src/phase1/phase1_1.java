package phase1;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;


public class phase1_1 {

    
    public static void main(String[] args) {
        
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

        Component add = add(new MouseComponent());
        pack();
        
    }
}


class MouseComponent extends JComponent
{   

    // Setting Final Instance Variables
    
    final private int OUR_INITIAL_DIAMETER = 100;
    
    final private int OUR_USER_ROWS = 7; // TODO take input from the user as initial input
    final private int OUR_USER_COLUMNS = 5;
    
    final private int OUR_DEFAULT_WIDTH = OUR_USER_COLUMNS * OUR_INITIAL_DIAMETER;
    final private int OUR_DEFAULT_HEIGHT = OUR_USER_ROWS * OUR_INITIAL_DIAMETER;
    
    // Non-Instance Variable Types
    
    private Ellipse2D myCurrentEllipse;
    private ArrayList< Ellipse2D > myEllipses = new ArrayList<>();
    
    private ArrayList< Ellipse2D > ellipses(){
        
        return myEllipses;
        
    }
    
    private void setEllipses(ArrayList <Ellipse2D> other){
        
        myEllipses = other;
        
    }
    
    private void setCurrentEllipse( Ellipse2D other)
    {
        
        myCurrentEllipse = other;
        
    }
    
    public void setupEllipses(){
        
        Ellipse2D testEllipse;
        
        for (int initialX = 0; initialX < OUR_DEFAULT_WIDTH; initialX += 100){
            for (int initialY = 0; initialY < OUR_DEFAULT_HEIGHT; initialY += 100){
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
        
        for (int numColumns = 0; numColumns < OUR_USER_COLUMNS; ++ numColumns){
            initialY =0;
            for (int numRows = 0; numRows < OUR_USER_ROWS; ++ numRows){
                redoneEllipse = new Ellipse2D.Double(initialX, initialY, newEllipseWidth, newEllipseHeight);
                newEllipseArrayList.add(redoneEllipse);
                initialY += newEllipseHeight;
            }
            initialX += newEllipseWidth;
        }
        
        MouseComponent.this.setEllipses(newEllipseArrayList);
        repaint();
    }
    
    
    public Ellipse2D findEllipseContainingPoint(Point2D clickPoint)
    {
        int ellipseNumber = 0;
        Boolean pointInEllipse = false;     
        Ellipse2D thisEllipse = null;
        
        for(; ellipseNumber < ellipses().size(); ++ellipseNumber){
            
            thisEllipse = ellipses().get(ellipseNumber);
            
            if (thisEllipse.contains(clickPoint)) 
                pointInEllipse = true;
            
            if (pointInEllipse) break;
        }
        
        System.out.println(pointInEllipse);
        
        return pointInEllipse ?  thisEllipse : null;
        
    }
    
    @Override
    public Dimension getPreferredSize(){
        
        setupEllipses();
        return new Dimension(OUR_DEFAULT_WIDTH, OUR_DEFAULT_HEIGHT);
        
    }
    
    public MouseComponent(){
        
        setEllipses(new ArrayList<Ellipse2D>());
        setCurrentEllipse(null);
        
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
            int newHeight = e.getComponent().getHeight() / OUR_USER_ROWS;
            int newWidth = e.getComponent().getWidth() / OUR_USER_COLUMNS;
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
            
            setCurrentEllipse(findEllipseContainingPoint(event.getPoint()));
            
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
