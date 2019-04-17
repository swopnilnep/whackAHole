// David Will

package phase1;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;


public class Phase1 {

    
    public static void main(String[] args) {
        
        
        // User dimensions input
        
        Scanner myScanner = new Scanner(System.in);
        
        System.out.print("Enter the number of rows (between 3 and 9, inclusive): ");
        int userRows = myScanner.nextInt();;
        
        while (userRows > 9 | userRows < 3){
            System.out.print("Please enter a valid number of rows: ");
            userRows = myScanner.nextInt();
        }
        
        int userInputRows = userRows;

        System.out.print("Enter the number of columns (between 3 and 9, inclusive): ");
        int userColumns = myScanner.nextInt();
        
        while (userColumns > 9 | userColumns < 3){
            System.out.print("Please enter a valid number of columns: ");
            userColumns = myScanner.nextInt();
        }
        
        int userInputColumns = userColumns;
        
        // Begin main runnable
        
        EventQueue.invokeLater(
        new Runnable()
        {

            @Override
            public void run()
            {

                ProgramFrame frame = new ProgramFrame(userInputRows, userInputColumns);

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

    public ProgramFrame(int inputRows, int inputColumns)
    {

        add(new HolesComponent(inputRows, inputColumns));
        pack();
        
    }
}


class HolesComponent extends JComponent
{   
    
    
    private final int OUR_INITIAL_DIAMETER = 100;
    private int userRows;
    private int userColumns;
    
    final private int OUR_DEFAULT_WIDTH;
    final private int OUR_DEFAULT_HEIGHT;
    
    private Ellipse2D myRedEllipse;
    private Ellipse2D myCurrentEllipse;
    
    private int myScore = 0;
    
    private ArrayList<Ellipse2D> myEllipses = new ArrayList<>();   
    
    
    private ArrayList<Ellipse2D> ellipses(){
        return myEllipses;
    }
    
    private void addToScore(){
        
        myScore += 5;
        
    }
    
    private int score(){
        
        return myScore;
        
    }
    
    private void setEllipses(ArrayList<Ellipse2D> other){
                
        myEllipses = other;
        
    }
    
    private void setCurrentEllipse( Ellipse2D other)
    {
    
        myCurrentEllipse = other;
        
    }
    
    
    private Ellipse2D currentEllipse(){
    
        return myCurrentEllipse;
        
    }
    
    private Ellipse2D redEllipse(){
        
        return myRedEllipse;
    }
    
    private void setRedEllipse(Ellipse2D other){

        myRedEllipse = other;

    }
    
    
    public void initializeEllipses(){
        
        Ellipse2D testEllipse;
        
        for (int initialX = 0; initialX < OUR_DEFAULT_WIDTH; initialX += OUR_INITIAL_DIAMETER){
            for (int initialY = 0; initialY < OUR_DEFAULT_HEIGHT; initialY += OUR_INITIAL_DIAMETER){
                testEllipse = new Ellipse2D.Double(initialX, initialY, OUR_INITIAL_DIAMETER,OUR_INITIAL_DIAMETER);
                ellipses().add(testEllipse);
                
            }
        }
    }
    
    public void setRedEllipse(){
        
        Random getRandomIndex = new Random();
        int redEllipseIndex = getRandomIndex.nextInt(ellipses().size());
        HolesComponent.this.setRedEllipse(ellipses().get(redEllipseIndex));
        
    }
    
    
    @Override
    public void paintComponent(Graphics canvas){      

        
        for (Ellipse2D ellipseOnCanvas: ellipses()){
            if (ellipseOnCanvas == redEllipse()){
                ((Graphics2D) canvas).setColor(Color.red);
                ((Graphics2D) canvas).fill(ellipseOnCanvas);
            }
            
            else {
                
                ((Graphics2D) canvas).setColor(Color.black);
                ((Graphics2D) canvas).fill(ellipseOnCanvas);
            }
            
        canvas.setFont(new Font("TimesRoman",Font.PLAIN, 100));
        canvas.setColor(Color.green);
        canvas.drawString("Score: " + score(),0,100);    
            
        }
    

    }
    
    public void resetEllipsesOnResize(int newEllipseHeight, int newEllipseWidth){
        
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
        
        return pointInEllipse ?  thisEllipse : null;
        
    }
    
    @Override
    public Dimension getPreferredSize(){
        
        initializeEllipses();
        return new Dimension(OUR_DEFAULT_WIDTH, OUR_DEFAULT_HEIGHT);
        
    }
    
    public HolesComponent(int inputRows, int inputColumns){
        
        userRows = inputRows;
        userColumns = inputColumns;
        OUR_DEFAULT_WIDTH = userColumns * OUR_INITIAL_DIAMETER;
        OUR_DEFAULT_HEIGHT = userRows * OUR_INITIAL_DIAMETER;
        
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
            int newHeight = e.getComponent().getHeight() / userRows;
            int newWidth = e.getComponent().getWidth() / userColumns;
            resetEllipsesOnResize(newHeight, newWidth);     
            setRedEllipse();
            
        }
        
        @Override
        public void componentShown(ComponentEvent e) {
            ;

        }
    }
    

    private class MouseHandler extends MouseAdapter{

        Ellipse2D previouslyClickedHole;
        
        @Override
        public void mousePressed(MouseEvent event){
            
            setCurrentEllipse(findEllipseContainingPoint(event.getPoint()));
            
            if (currentEllipse() == redEllipse()){
            
                addToScore();
                setRedEllipse();
                
            } 
            
            repaint();
            
        }
        
    }
}