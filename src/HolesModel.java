import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private ArrayList < HolesModelObserver > myObservers;

    private String myUserName;
    
    private int myScore;
    private int myScoreIncrement;

    private int myLivesRemaining;

    private int myLevel;
    private int myCorrectClicks;

    private ArrayList < ArrayList < Ellipse2D.Double > > myHoles;
    private int myRedHoleRow;
    private int myRedHoleColumn;

    private Random myPseudoRandomNumberGenerator;

    private Boolean soundIsMuted = false;
    
    private final String OUR_CORRECT_SOUND = "sounds/correctSound.wav";
    private final String OUR_WRONG_SOUND = "sounds/wrongSound.wav";
    private final String OUR_LEVEL_UP_SOUND = "sounds/levelUp.wav";
    private final String OUR_RESET_SOUND = "sounds/resetGame.wav";

    
    private final String OUR_HIGHSCORES_FILE_LOCATION = "data/highscores.dat";
    
    private int myTimerDelay = 1000;

    private Boolean gameIsOver = false;

    ActionListener taskPerformer = new ActionListener()
    {

        @
        Override
        public void actionPerformed(ActionEvent evt)
        {
            randomizeRedHolePosition();
        }

    };
    
    private Timer myTimer = new Timer(myTimerDelay, taskPerformer);

    TreeMap < Integer, String > myHighScores = new TreeMap < > ();

    //
    // Private Accessors
    //


    private ArrayList < HolesModelObserver > observers()
    {

        return myObservers;

    }

    private ArrayList < ArrayList < Ellipse2D.Double > > holes()
    {

        return myHoles;

    }
    

    //
    // Public Accessors
    //

    public void setHoles(ArrayList < ArrayList < Ellipse2D.Double > > otherHoles)
    {

        myHoles = otherHoles;

    }

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

    public TreeMap < Integer, String > highScores()
    {
        return myHighScores;
    }

    public String userName()
    {
        return myUserName;
    }

    //
    // Private Mutators
    //

    private void setObservers(ArrayList < HolesModelObserver > otherObservers)
    {

        myObservers = otherObservers;

    }


    public void setLevel(int other)
    {
        myLevel = other;
        announceLevelChange();
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
        if (livesRemaining() >= 1)
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
        timer().setDelay(otherDelay);
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

    public void setMuteStatus(Boolean other)
    {
        soundIsMuted = other;
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

    public void setGameOver(boolean otherGameStatus)
    {
        gameIsOver = otherGameStatus;
    }

    public void gameOverProcedure()
    {
        setGameOver(true);
        timer().stop();
        // updateHighScores();
        System.out.println("Game Over");
        // Instead of detaching, announce the change and show game over text


        readHighScores();
        updateHighScores();
        writeHighScores();

        System.out.println(highScores());


    }
    
    public void stopTimer()
    {
        timer().stop();
    }

    public void updateHighScores()
    {

        highScores().put(score(), userName() );

        while (highScores().size() > 16)
        {
            highScores().remove(highScores().firstKey());
        }

    }

    public void setHighScores(TreeMap < Integer, String > other)
    {
        myHighScores = other;
        announceHighScoresChange();
    }

    public void readHighScores()
    {

        try
        {

            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream(
                            new File(OUR_HIGHSCORES_FILE_LOCATION)
                    )
            );
            setHighScores(
                (TreeMap < Integer, String > ) inputStream.readObject()
            );

        }
        catch (FileNotFoundException ex)
        {
            writeHighScores();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(HolesModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void writeHighScores()
    {


        try
        {

            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream(
                            new File(OUR_HIGHSCORES_FILE_LOCATION)
                    )
            );
            outputStream.writeObject(highScores());


        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(HolesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(HolesModel.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    
    public void setUserName(String other)
    {
        myUserName = other;
    }

    // 
    // Public Methods
    // 


    public void resetModel()
    {

        
        writeHighScores();
        
        setGameOver(false);
        setLevel(0);
        setCorrectClicks(0);
        setScore(0);
        setScoreIncrement(5);
        setLivesRemaining(3);
        setTimerDelay(1000);
        
        readHighScores();
        
        timer().start();

    }

    //
    // Public Constructors
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

        setObservers(new ArrayList < HolesModelObserver > ());
        resetModel();
        setPseudoRandomNumberGenerator(new Random());

        //
        // Initialize the collection of holes to a
        // initialNumberOfRows-by-initialNumberOfColumns-sized collection of
        // zero-sized ellipses
        //

        myHoles = new ArrayList < ArrayList < Ellipse2D.Double > > ();

        for (int row = 0; row < initialNumberOfRows; ++row)
        {

            myHoles.add(new ArrayList < Ellipse2D.Double > ());

            for (int column = 0; column < initialNumberOfColumns; ++column)
                myHoles.get(row).add(new Ellipse2D.Double(0, 0, 0, 0));

        }

        randomizeRedHolePosition();;

    }

    //
    // Public Observation Methods
    //

    @
    Override
    public void announceScoreChange()
    {

        for (HolesModelObserver currentObserver: observers())
            currentObserver.updateScore();

    }

    @
    Override
    public void announceLevelChange()
    {

        for (HolesModelObserver currentObserver: observers())
            currentObserver.updateLevel();
    }

    @
    Override
    public void announceRedHolePositionChange()
    {

        for (HolesModelObserver currentObserver: observers())
            currentObserver.updateRedHolePosition();

    }

    @
    Override
    public void announceLivesRemainingChange()
    {
        for (HolesModelObserver currentObserver: observers())
            currentObserver.updateLivesRemaining();
    }

    @
    Override
    public void announceSoundStatusChange()
    {
        for (HolesModelObserver currentObserver: observers())
            currentObserver.updateSoundStatus();
    }
    
    @
    Override
    public void announceHighScoresChange()
    {
        for (HolesModelObserver currentObserver: observers())
            currentObserver.updateHighScores();
    }

    @
    Override
    public void attach(HolesModelObserver anotherObserver)
    {

        if (!observers().contains(anotherObserver))
            observers().add(anotherObserver);

    }

    @
    Override
    public void detach(HolesModelObserver currentObserver)
    {

        observers().remove(currentObserver);

    }



}