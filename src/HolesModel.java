import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;

class HolesModel implements HolesModelObservable
{

    //
    // Private Class Fields
    //

        static final private int ourDefaultNumberOfRows = 4;
        static final private int ourDefaultNumberOfColumns = 4;

    //
    // Private Fields
    //

        private ArrayList< HolesModelObserver > myObservers;

        private int myScore;
        private int myScoreIncrement;

        private ArrayList< ArrayList< Ellipse2D.Double > > myHoles;
        private int myRedHoleRow;
        private int myRedHoleColumn;

        private Random myPseudoRandomNumberGenerator;
        
        private Boolean soundIsMuted = false;
        private final String OUR_CORRECT_SOUND = "sounds/correctSound.wav";
        private final String OUR_WRONG_SOUND = "sounds/wrongSound.wav";
        
        private int myTimerDelay = 1000;

    ////
    //// Private Accessors
    ////


        private ArrayList< HolesModelObserver > observers()
        {

            return myObservers;

            }

        private ArrayList< ArrayList< Ellipse2D.Double > > holes()
        {

            return myHoles;

            }

    ////
    //// Public Accessors
    ////

        public int score()
        {

            return myScore;

            }

        public int scoreIncrement()
        {

            return myScoreIncrement;

            }

        public int redHoleRow()
        {

            return myRedHoleRow;

            }

        public int redHoleColumn()
        {

            return myRedHoleColumn;

            }

        public Ellipse2D.Double hole(int row, int column)
        {

            return holes().get(row).get(column);

            }

        public int numberOfRows()
        {

            return holes().size();

            }

        public int numberOfColumns()
        {

            return holes().get(0).size();

            }

        public Ellipse2D.Double redHole()
        {

            return holes().get(redHoleRow()).get(redHoleColumn());

            }

        public Random randomNumber()
        {

            return myPseudoRandomNumberGenerator;

            }
        
        public Boolean isMuted()
        {
            
            return soundIsMuted;
            
        }
        
        public String correctSound()
        {
            return OUR_CORRECT_SOUND;
        }
        
        public String wrongSound()
        {
            return OUR_WRONG_SOUND;
        }
        
        public int myTimerDelay()
        {
            return myTimerDelay;
        }

    ////
    //// Private Mutators
    ////

        private void setObservers(ArrayList< HolesModelObserver> otherObservers)
        {

            myObservers = otherObservers;

            }

        private void setHoles(ArrayList< ArrayList< Ellipse2D.Double > > otherHoles)
        {

            myHoles = otherHoles;

            }

    ////
    //// Public Mutators
    ////

        public void setScore(int otherScore)
        {

            myScore = otherScore;

            announceScoreChange();

            }

        public void setScoreIncrement(int otherIncrement)
        {

            myScoreIncrement = otherIncrement;

            }

        public void incrementScore()
        {

            setScore(score() + scoreIncrement());

            }

        private void setRedHolePosition(int otherRow, int otherColumn)
        {

            myRedHoleRow = otherRow;
            myRedHoleColumn = otherColumn;

            announceRedHolePositionChange();

            }

        public void setPseudoRandomNumberGenerator(Random otherGenerator)
        {

            myPseudoRandomNumberGenerator = otherGenerator;

            }

        public void randomizeRedHolePosition()
        {

            setRedHolePosition(
                randomNumber().nextInt(numberOfRows()),
                randomNumber().nextInt(numberOfColumns())
                );

            }
        
        public void toggleMute()
        {
            soundIsMuted = !soundIsMuted;
        }
        
        public void setTimerDelay(int otherDelay)
        {
            myTimerDelay = otherDelay;
        }

    ////
    //// Public Ctors
    ////

        HolesModel()
        {

            this(ourDefaultNumberOfRows, ourDefaultNumberOfColumns);

            }

        HolesModel(int initialNumberOfRowsAndColumns)
        {

            this(initialNumberOfRowsAndColumns, initialNumberOfRowsAndColumns);

            }

        HolesModel(int initialNumberOfRows, int initialNumberOfColumns)
        {

            setObservers(new ArrayList< HolesModelObserver >());

            setScore(0);
            setScoreIncrement(5);
            setPseudoRandomNumberGenerator(new Random());

            //
            // Initialize the collection of holes to a
            // initialNumberOfRows-by-initialNumberOfColumns-sized collection of
            // zero-sized ellipses
            //

                myHoles = new ArrayList< ArrayList< Ellipse2D.Double > >();

                for (int row = 0; row < initialNumberOfRows; ++ row) {

                    myHoles.add(new ArrayList< Ellipse2D.Double >());

                    for (int column = 0; column < initialNumberOfColumns; ++ column)
                        myHoles.get(row).add(new Ellipse2D.Double(0, 0, 0, 0));

                    }

            randomizeRedHolePosition();;

            }

    ////
    //// Public Observation Methods
    ////

        @Override
        public void announceScoreChange()
        {

            for (HolesModelObserver currentObserver : observers())
                currentObserver.updateScore();

            }

        @Override
        public void announceRedHolePositionChange()
        {

            for (HolesModelObserver currentObserver : observers())
                currentObserver.updateRedHolePosition();

            }

        @Override
        public void attach(HolesModelObserver anotherObserver)
        {

            if (! observers().contains(anotherObserver))
                observers().add(anotherObserver);

            }

        @Override
        public void detach(HolesModelObserver currentObserver)
        {

            observers().remove(currentObserver);

            }


    }
