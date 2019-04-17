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
{   private final int initialDiameter = 100;
    private int userRows;
    private int userColumns;
    
    private int defaultWidth;
    private int defaultHeight;
    
    private Ellipse2D myCurrentEllipse;
    private Ellipse2D myRedEllipse;
    
    private ArrayList<Ellipse2D> myEllipses = new ArrayList<Ellipse2D>();   
    
    
    private ArrayList<Ellipse2D> ellipses(){
        return myEllipses;
    }
    
    private ArrayList<Ellipse2D> setEllipses(ArrayList<Ellipse2D> other){
        myEllipses = other;
        return myEllipses;
    }
    
    private Ellipse2D redEllipse(){
        return myRedEllipse;
    }
    
    private Ellipse2D setRedEllipse(Ellipse2D other){
        myRedEllipse = other;
        return myRedEllipse;
    }
    
    
    public void setUpEllipses(){
        Ellipse2D testEllipse;
        
        for (int initialX = 0; initialX < defaultWidth; initialX += initialDiameter){
            for (int initialY = 0; initialY < defaultHeight; initialY += initialDiameter){
                testEllipse = new Ellipse2D.Double(initialX, initialY, initialDiameter,initialDiameter);
                ellipses().add(testEllipse);
                
            }
        }
    }
    
    
    public void pickRedEllipse(){
        Random getRandomIndex = new Random();
        int redEllipseIndex = getRandomIndex.nextInt(ellipses().size());
        setRedEllipse(ellipses().get(redEllipseIndex));
    }
    
    
    @Override
    public void paintComponent(Graphics canvas){      

        for (Ellipse2D ellipseOnCanvas: ellipses()){
            if (ellipseOnCanvas == redEllipse()){
                ((Graphics2D) canvas).setColor(Color.red);
                ((Graphics2D) canvas).fill(ellipseOnCanvas);
            }
            else{
                ((Graphics2D) canvas).setColor(Color.black);
                ((Graphics2D) canvas).fill(ellipseOnCanvas);
            }
            
        }
    

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
    
    @Override
    public Dimension getPreferredSize(){
        setUpEllipses();
        return new Dimension(defaultWidth, defaultHeight);
    }
    
    public HolesComponent(int inputRows, int inputColumns){
        userRows = inputRows;
        userColumns = inputColumns;
        defaultWidth = userColumns * initialDiameter;
        defaultHeight = userRows * initialDiameter;
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
            pickRedEllipse();
            
        }
        
        @Override
        public void componentShown(ComponentEvent e) {
            ;

        }
    }
    
    private class MouseHandler extends MouseAdapter{
        
        @Override
        public void mousePressed(MouseEvent event){

        }
    }
}