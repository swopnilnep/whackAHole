import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;

class HolesModel implements HolesModelObservable
{

    // 
    // Private Fields
    // 

    private ArrayList< HolesModelObserver > myObservers;
    private ArrayList < Ellipse2D.Double > myHoles;

    // private int myScore;
    // private int myScoreChangeCoefficient;

    private int numRows;
    private int numColumns;

    private Ellipse2D.Double myRedHole;
    private int myRedHoleIndex;

    private Random myRandomNumberGenerator;

    // 
    // Private Accessors
    // 

    private ArrayList< HolesModelObserver > observers()
    {

        return myObservers;

    }

    private ArrayList< Ellipse2D.Double > holes()
    {

        return myHoles;

    }



    // 
    // Public Accessors
    // 

    public int redHoleIndex()
    {

        return myRedHoleIndex;

    }

    public Ellipse2D redHole()
    {

        return myRedHole;

    }

    public int rows()
    {
        return numRows;
    }

    public int columns()
    {
        return numColumns;
    }

    //
    // Private Mutators
    // 

    private void setObservers(ArrayList < HolesModelObserver > otherObservers)
    {

        myObservers = otherObservers;

    }

    private void setHoles(ArrayList < Ellipse2D.Double > otherHoles)
    {

        myHoles = otherHoles;

    }



    // 
    // Public Mutators
    // 

    public void setRandomNumberGenerator(Random otherGenerator)
    {
        
        myRandomNumberGenerator = otherGenerator;

    }

    public void setRedHolePosition(int otherRedHoleIndex)
    {

        myRedHoleIndex = otherRedHoleIndex;
        announceRedHolePositionChange();

    }

    public void randomizeRedHolePosition()
    {

        setRedHolePosition(myRandomNumberGenerator.nextInt(myHoles.size()));

    }

    public void setRows(int otherRows)
    {
        numRows = otherRows;
    }

    public void setColumns(int otherColumns)
    {
        numColumns = otherColumns;
    }

    // 
    // Constructors
    // 

    public HolesModel(int initialNumberOfColumns, int initialNumberOfRows){

        setObservers(new ArrayList< HolesModelObserver >());
        setRandomNumberGenerator(new Random());

        setRows(initialNumberOfRows);
        setColumns(initialNumberOfColumns);
        
        for (int holeIndex = 0; holeIndex < initialNumberOfColumns * initialNumberOfRows; ++holeIndex)
        {
            myHoles.add(new Ellipse2D.Double(0,0,0,0));
        }


    }

    // 
    //  Public Observation Methods
    // 

    @Override
    public void announceRedHolePositionChange()
    {

        for (HolesModelObserver currentObserver : observers())
            currentObserver.updateRedHolePosition();

        }

}
