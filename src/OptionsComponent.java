
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

class OptionsComponent extends JPanel implements HolesModelObserver
{
    // 
    // Private Fields
    // 

    private HolesModel myModel;
    private MuteLabel myMuteStatusLabel;
    private MuteButton myMuteButton;
    private SaveButton mySaveStateButton;
    private LoadButton myLoadStateButton;
    private ResetButton myResetButton;


    // 
    // Private Accessors
    // 

    private ResetButton resetButton()
    {
        return myResetButton;
    }

    private HolesModel model()
    {

        return myModel;

    }

    private MuteLabel muteStatus()
    {

        return myMuteStatusLabel;

    }

    private MuteButton muteButton()
    {

        return myMuteButton;

    }

    private SaveButton saveStateButton()
    {

        return mySaveStateButton;

    }

    private LoadButton loadStateButton()
    {

        return myLoadStateButton;

    }


    // 
    // Private Mutators
    // 

    private void setResetButton(ResetButton otherButton)
    {
        myResetButton = otherButton;
    }

    private void setModel(HolesModel otherModel)
    {

        myModel = otherModel;

    }

    private void setMuteStatusLabel(MuteLabel otherMuteStatusLabel)
    {

        myMuteStatusLabel = otherMuteStatusLabel;

    }

    private void setMuteButton(MuteButton otherMuteButton)
    {

        myMuteButton = otherMuteButton;

    }

    private void setSaveStateButton(SaveButton otherSaveStateButton)
    {

        mySaveStateButton = otherSaveStateButton;

    }

    private void setLoadStateButton(LoadButton otherLoadStateButton)
    {

        myLoadStateButton = otherLoadStateButton;

    }


    //
    // Public Constructors
    // 

    public OptionsComponent(HolesModel initialModel)
    {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setModel(initialModel);
        model().attach(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // 
        // Set Components
        // 

        setMuteStatusLabel(new MuteLabel());
        setMuteButton(new MuteButton());
        setSaveStateButton(new SaveButton());
        setLoadStateButton(new LoadButton());
        setResetButton(new ResetButton());

        // 
        // Add buttons to the options pane
        // 

        // Add Spacer
        add(Box.createRigidArea(new Dimension(4, 1)));

        // Buttons
        add(muteButton());
        add(saveStateButton());
        add(loadStateButton());
        add(resetButton());

        // Add Spacer
        add(Box.createVerticalGlue());

        // Status Element(s)
        add(muteStatus());

        add(Box.createRigidArea(new Dimension(4, 1)));

    }

    // 
    // Internal Classes
    // 

    class ResetButton extends JButton
    {
        // 
        // Public Constructors
        // 

        public ResetButton()
        {

            this.setText("Reset");
            this.addActionListener(new ActionListener()
            {

                @
                Override
                public void actionPerformed(ActionEvent e)
                {

                    model().resetModel();
                    playSound("sounds/resetGame.wav");

                }

            });
        }

        public void playSound(String soundName)
        {
            try
            {

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();

            }
            catch (Exception ex)
            {
                System.out.println("Error playing sound.");
            }
        }
    }
    class MuteLabel extends JLabel
    {

        // 
        // Private Fields
        // 

        final String OUR_DEFAULT_UNMUTED_STRING = "Sound is not muted";
        final String OUR_DEFAULT_MUTED_STRING = "Sound is muted";

        // 
        // Public Methods
        // 

        public void mute()
        {

            setText(OUR_DEFAULT_MUTED_STRING);

        }

        public void unmute()
        {

            setText(OUR_DEFAULT_UNMUTED_STRING);

        }

        // 
        // Public Constructors
        // 

        public MuteLabel()
        {

            setText(OUR_DEFAULT_UNMUTED_STRING);

        }

    }
    class LoadButton extends JButton
    {

        // 
        // Public Constructors
        // 

        public LoadButton()
        {

            this.setText("Load");
            this.addActionListener(new ActionListener()
            {

                @
                Override
                public void actionPerformed(ActionEvent e)
                {

                    try
                    {

                        Properties props = new Properties();
                        File f = new File("data/preferences.txt");
                        InputStream in = new FileInputStream(f);
                        props.load( in );

                        model().setLevel(Integer.parseInt(props.getProperty("level")));
                        model().setScore(Integer.parseInt(props.getProperty("score")));
                        model().setScoreIncrement(Integer.parseInt(props.getProperty("scoreIncrement")));
                        model().setLivesRemaining(Integer.parseInt(props.getProperty("livesRemaining")));
                        model().setTimerDelay(Integer.parseInt(props.getProperty("timerDelay")));

                        int initialRows = Integer.parseInt(props.getProperty("numberOfRows"));
                        int initialColumns = Integer.parseInt(props.getProperty("numberOfColumns"));

                        ArrayList < ArrayList < Ellipse2D.Double > > myHoles = new ArrayList < ArrayList < Ellipse2D.Double > > ();

                        for (int row = 0; row < initialRows; ++row)
                        {

                            myHoles.add(new ArrayList < Ellipse2D.Double > ());

                            for (int column = 0; column < initialColumns; ++column)
                                myHoles.get(row).add(new Ellipse2D.Double(0, 0, 0, 0));

                        }

                        model().setGameOver(Boolean.parseBoolean(props.getProperty("gameIsOver")));
                        model().setMuteStatus(Boolean.parseBoolean(props.getProperty("isMuted")));

                        model().writeHighScores();
                        model().readHighScores();

                    }
                    catch (FileNotFoundException ex)
                    {
                        System.err.println("File not found");
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(OptionsComponent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }

                }

            });
        }
    }

    class SaveButton extends JButton
    {

        // 
        // Public Constructors
        // 

        public SaveButton()
        {

            this.setText("Save");
            this.addActionListener(new ActionListener()
            {

                @
                Override
                public void actionPerformed(ActionEvent e)
                {

                    try
                    {

                        // 
                        // Create a properties file
                        // 

                        Properties props = new Properties();

                        // 
                        // Set Properties
                        // 

                        props.setProperty("level", Integer.toString(model().level()));
                        props.setProperty("correctClicks", Integer.toString(model().correctClicks()));
                        props.setProperty("score", Integer.toString(model().score()));
                        props.setProperty("scoreIncrement", Integer.toString(model().scoreIncrement()));
                        props.setProperty("livesRemaining", Integer.toString(model().livesRemaining()));
                        props.setProperty("timerDelay", Integer.toString(model().timerDelay()));

                        props.setProperty("redHoleRow", Integer.toString(model().redHoleRow()));
                        props.setProperty("redHoleColumn", Integer.toString(model().redHoleColumn()));
                        props.setProperty("numberOfRows", Integer.toString(model().numberOfRows()));
                        props.setProperty("numberOfColumns", Integer.toString(model().numberOfColumns()));

                        props.setProperty("gameIsOver", Boolean.toString(model().gameIsOver()));
                        props.setProperty("isMuted", Boolean.toString(model().isMuted()));

                        model().writeHighScores();
                        
                        // 
                        // Create and Export to a new file
                        // 

                        File f = new File("data/preferences.txt");
                        OutputStream out = new FileOutputStream(f);

                        props.store(out, "User properties for loading and saving game states");
                    }
                    catch (IOException ex)
                    {
                        System.err.println("File not found");
                    }

                }

            });
        }
    }
    class MuteButton extends JButton
    {
        public MuteButton()
        {

            this.setText("Mute/Unmute");
            this.addActionListener(new ActionListener()
            {

                @
                Override
                public void actionPerformed(ActionEvent e)
                {

                    model().toggleMute();

                }

            });

        }

    }


    //
    // Public Observation Methods
    //

    @
    Override
    public void updateScore()
    {

    }

    @
    Override
    public void updateRedHolePosition()
    {

    }

    @
    Override
    public void updateSoundStatus()
    {
        if (model().isMuted())
            muteStatus().mute();
        else
            muteStatus().unmute();
    }

    @
    Override
    public void updateLevel()
    {

    }

    @
    Override
    public void updateLivesRemaining()
    {

    }
    
    @Override
    public void updateHighScores()
    {
        
    }


}
