import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;

import java.io.File;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Properties;
import java.io.OutputStream;
import java.io.FileOutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
        
        private int myLivesRemaining = 3;

        private int myLevel = 1;
        private int myCorrectClicks = 0;

        private ArrayList< ArrayList< Ellipse2D.Double > > myHoles;
        private int myRedHoleRow;
        private int myRedHoleColumn;

        private Random myPseudoRandomNumberGenerator;
        
        private Boolean soundIsMuted = false;
        private final String OUR_CORRECT_SOUND = "sounds/correctSound.wav";
        private final String OUR_WRONG_SOUND = "sounds/wrongSound.wav";
        private final String OUR_LEVEL_UP_SOUND = "sounds/levelUp.wav";
        
        private int myTimerDelay = 1000;
        
        private Boolean gameIsOver = false;
        
        ActionListener taskPerformer = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent evt) {  
            randomizeRedHolePosition();
            }

        };
        private Timer myTimer = new Timer(myTimerDelay, taskPerformer);
        
        private int[] myHighScoreArray = new int[16];

    //
    // Private Accessors
    //


        private ArrayList< HolesModelObserver > observers()
        {

            return myObservers;

        }

        private ArrayList< ArrayList< Ellipse2D.Double > > holes()
        {

            return myHoles;

        }

    //
    // Public Accessors
    //

        public int level()
        {
            return myLevel;
        }
        
        public int correctClicks()
        {
            return myCorrectClicks;
        }

        public int score()
        {

            return myScore;

        }

        public int scoreIncrement()
        {

            return myScoreIncrement;

        }
        
        public int livesRemaining()
        {
            return myLivesRemaining;
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
        
        public String levelUpSound()
        {
            return OUR_LEVEL_UP_SOUND;
        }
        
        public int timerDelay()
        {
            return myTimerDelay;
        }
        
        public Timer timer()
        {
            return myTimer;
        }
        
        public boolean gameIsOver()
        {
            return gameIsOver;
        }
        
        public int[] highScores()
        {
            return myHighScoreArray;
        }
        
        

    //
    // Private Mutators
    //

        private void setObservers(ArrayList< HolesModelObserver> otherObservers)
        {

            myObservers = otherObservers;

            }

        private void setHoles(ArrayList< ArrayList< Ellipse2D.Double > > otherHoles)
        {

            myHoles = otherHoles;

            }

        private void setLevel(int other)
        {
            myLevel = other;
        }

    //
    // Public Mutators
    //

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
        
        public void setLivesRemaining(int otherLives)
        {
            myLivesRemaining = otherLives;
            announceLivesRemainingChange();
            
        }
        
        public void decrementLivesRemaining()
        {
            if (livesRemaining() > 0)
                setLivesRemaining(livesRemaining() - 1);
            else
            {
                if (!gameIsOver())
                {    
                    gameOverProcedure();
                }
            }
                
                
        }
        
        public void setCorrectClicks(int otherCorrectClicks)
        {
            myCorrectClicks = otherCorrectClicks;
        }
        
        public void incrementCorrectClicks()
        {
            setCorrectClicks(correctClicks() + 1);
        }

        public void incrementLevel()
        {
           
            setLevel(level() + 1);
            setScoreIncrement(scoreIncrement() + 5);
            announceLevelChange();
            
        }
        
        public void setTimerDelay(int otherDelay)
        {
            myTimerDelay = otherDelay;
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
            announceSoundStatusChange();
        }
        
        public Color randomBackgroundColor()
        {
            float red = randomNumber().nextFloat();
            float green = randomNumber().nextFloat();
            float blue = randomNumber().nextFloat();
            Color randomColor = new Color(red, green, blue);
            return randomColor;
        }
        
        public void detachAll()
        {
            for (HolesModelObserver currentObserver : observers())
                detach(currentObserver);
        }
        
        public void setGameOver(boolean otherGameStatus)
        {
            gameIsOver = otherGameStatus;
        }
        
        public void gameOverProcedure()
        {
            detachAll();
            setGameOver(true);
            timer().stop();
            updateHighScores();
        }
        
        public void updateHighScores()
        {
            int lowestHighScore = highScores()[0];
            if (score() > lowestHighScore)
            {
                highScores()[0] = score();
                Arrays.sort(highScores());
            }
                
        }
        

    // 
    // Public Writing Methods
    // 

        public void saveProperties() {
            try {
                
                // 
                // Create a properties file
                // 

                Properties props = new Properties();

                // 
                // Set Properties
                // 

                props.setProperty("level", Integer.toString(level()));
                props.setProperty("correctClicks", Integer.toString(correctClicks()));
                props.setProperty("score", Integer.toString(score()));
                props.setProperty("scoreIncrement", Integer.toString(scoreIncrement()));
                props.setProperty("livesRemaining", Integer.toString(livesRemaining()));
                props.setProperty("redHoleRow", Integer.toString(redHoleRow()));
                props.setProperty("redHoleColumn", Integer.toString(redHoleColumn()));
                props.setProperty("numberOfRows", Integer.toString(numberOfRows()));
                props.setProperty("numberOfColumns", Integer.toString(numberOfColumns()));
                props.setProperty("timerDelay", Integer.toString(timerDelay()));
                
                props.setProperty("gameIsOver", Boolean.toString(gameIsOver()));
                props.setProperty("isMuted", Boolean.toString(isMuted()));
                
                // 
                // Create and Export to a new file
                // 
                
                File f = new File("properties.txt");
                OutputStream out = new FileOutputStream( f );
                
                props.store(out, "User preferences for loading and saving game states");
            }
            catch (Exception e ) {
                e.printStackTrace();
            }
        }

    //
    // Public Ctors
    //

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

    // 
    // Public Serialization Methods
    // 

        

    //
    // Public Observation Methods
    //

        @Override
        public void announceScoreChange()
        {

            for (HolesModelObserver currentObserver : observers())
                currentObserver.updateScore();

            }

        @Override
        public void announceLevelChange()
        {

            for (HolesModelObserver currentObserver : observers())
                currentObserver.updateLevel();
        }

        @Override
        public void announceRedHolePositionChange()
        {

            for (HolesModelObserver currentObserver : observers())
                currentObserver.updateRedHolePosition();

            }
            
        
        @Override
        public void announceLivesRemainingChange()
        {
            for (HolesModelObserver currentObserver : observers())
                currentObserver.updateLivesRemaining();
        }

        @Override
        public void announceSoundStatusChange()
        {
            for (HolesModelObserver currentObserver : observers())
            currentObserver.updateSoundStatus();
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
