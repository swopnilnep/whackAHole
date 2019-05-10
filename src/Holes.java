// 
// Authors: David Will and Swopnil N. Shrestha
// Purpose: Develop a Simple whack a mole game with Java Awt and Swing Libraries
// Instructor: Dr. Alan K Zaring
// Class: CS252 Object Oriented Programming with Java (Spring 2019)
// Date: 2019/18/04
// Phase: 03
// 

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import javax.swing.*;

import java.util.Scanner;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
public class Holes {
        public static void main(String[] commandLineArguments)
        {

            // 
            // Get user input from System.in using the Scanner object and verify with
            // predeterminded conditions.
            // 

            Scanner input = new Scanner(System.in);

            // 
            // userInputRows is the desired number of rows of holes, entered by the user
            // 

            int userInputRows = -1;

            // 
            // userInputColumns is the desired number of rows of columns, entered by the user
            // 

            int userInputColumns = -1;

            // 
            // rowInputIsFulfilled is true if an integer is available for input and the input integer
            // is between 3 and 9, inclusive
            // 

            boolean rowInputIsFulfilled = false;

            // 
            // Invariants: Until the input has an integer which is within the range of 3-9 inclusive,
            //  rowInputIsFulfilled will be false
            // 

            while (!rowInputIsFulfilled)
            {

                System.out.print("Enter number of rows (between 3 and 9, inclusive): ");
                
                if (input.hasNext() && !input.hasNextInt()){

                    System.err.println("*** Error: Input must be an integer");
                    input.next();

                }

                else if (input.hasNextInt()) {

                    userInputRows = input.nextInt();

                    if (userInputRows < 3 || userInputRows > 9){

                        System.err.println("*** Error: Input must be between 3 and 9, inclusive");

                    }

                    else {
    
                        rowInputIsFulfilled = true;

                    }

                }
                
            }

            //
            // columnInputIsFulfilled is true if an integer is available for input and the input integer
            // is between 3 and 9, inclusive
            //
            boolean columnInputIsFulfilled = false;

            //
            // Invariants: Until the input has an integer which is within the range of 3-9 inclusive,
            //  columnInputIsFulfilled will be false
            //

            while (!columnInputIsFulfilled)
            {

                System.out.print("Enter number of columns (between 3 and 9, inclusive): ");

                if (input.hasNext() && !input.hasNextInt()){

                    System.err.println("*** Error: Input must be an integer");
                    input.next();

                }

                else if (input.hasNextInt()) {

                    userInputColumns = input.nextInt();

                    if (userInputColumns < 3 || userInputColumns > 9){

                        System.err.println("*** Error: Input must be between 3 and 9, inclusive");
    
                    }
    
                    else {
    
                        columnInputIsFulfilled = true;
    
                    }

                }
                
            }

            final int OUR_NUMBER_OF_ROWS = userInputRows;
            final int OUR_NUMBER_OF_COLUMNS = userInputColumns;
            
            // 
            // Run the program frame that displays a score and a userInputRows by userInputColumns
            // grid of holes (Ellipses), of which one hole is pseudo-randomly chosen to be colored
            // in red while all the other holes are colored in black. Clicking on the red hole adds
            // 5 points to the user
            // 
            
            EventQueue.invokeLater(
                new Runnable()
                {

                    public void run() {

                        ProgramFrame frame = new ProgramFrame(OUR_NUMBER_OF_ROWS, OUR_NUMBER_OF_COLUMNS);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);

                    }

                }
            );

        }
    
    }
class ProgramFrame extends JFrame
{

    // 
    // Private Fields
    // 

    private final String OUR_TITLE = "Holes";
    private final int OUR_INITIAL_HOLE_DIAMETER = 200;
    private JPanel myMainPanel;

    // 
    // Private Accessors
    // 

    private JPanel mainPanel(){
        return myMainPanel;
    }

    // 
    // Private Mutators
    // 

    private void setMainPanel(JPanel other){
        myMainPanel = other;
    }

    // 
    // Public Constructors
    // 

    public ProgramFrame(int initialNumberOfRows, int initialNumberOfColumns){

        setSize(
            initialNumberOfColumns * OUR_INITIAL_HOLE_DIAMETER,
            getInsets().top + initialNumberOfRows * OUR_INITIAL_HOLE_DIAMETER
            );
        setTitle(OUR_TITLE);

        // 
        // Create the Model
        // 

        HolesModel model = new HolesModel(initialNumberOfRows, initialNumberOfColumns);
    
        // 
        // Set Main Panel to Hold all Sub-Panels
        // 
    
        setMainPanel(new JPanel());
        BorderLayout borderLayout = new BorderLayout();
        mainPanel().setLayout(borderLayout);
        
        // 
        // Set Sub-panels to Hold all the Components
        // 

        JPanel holesPanel = new JPanel();
        JPanel sidebarPanel = new JPanel();

        // Setup the Holes Panel
        holesPanel.setLayout(new BoxLayout(holesPanel, BoxLayout.Y_AXIS));
        holesPanel.add(new HolesComponent(model));
        holesPanel.setBackground(model.randomBackgroundColor());

        // Setup the Sidebar Panel Layout and Components
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.add(new ScoreComponent(model));
        sidebarPanel.add(new LevelsComponent(model));
        sidebarPanel.add(new LivesComponent(model));
        sidebarPanel.add(new OptionsComponent(model));
        
        // 
        // Add Components and Sub-Components to the Panels
        // 

        mainPanel().add(holesPanel, BorderLayout.CENTER);
        mainPanel().add(sidebarPanel, BorderLayout.EAST);

        // 
        // Add the Panel to the Program Frame
        // 

        add(mainPanel());
    }
}
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
                this.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        model().resetModel();
                        playSound("sounds/resetGame.wav");

                    }

                });
            }

            public void playSound(String soundName){
                try{

                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();

                }

                catch(Exception ex){
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
                    this.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {

                            ;

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
                    this.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {

                            model().saveProperties();

                        }

                    });
                }
        }
        class MuteButton extends JButton
        {
                public MuteButton()
                {
                    
                    this.setText("Mute/Unmute");
                    this.addActionListener(new ActionListener () {
                        
                        @Override
                        public void actionPerformed(ActionEvent e){

                            model().toggleMute();

                        }

                    });

                }

        }

    //
    // Public Observation Methods
    //
    
        @Override
        public void updateScore()
        {

        }
        
        @Override
        public void updateRedHolePosition()
        {

        }
        
        @Override
        public void updateSoundStatus()
        {
            if (model().isMuted())
                muteStatus().mute();
            else
                muteStatus().unmute();
        }

        @Override
        public void updateLevel()
        {
            
        }
        
        @Override
        public void updateLivesRemaining()
        {
            
        }


}
class ScoreComponent extends JLabel implements HolesModelObserver
{
    //
    // Private Fields
    //

        private HolesModel myModel;

    //
    // Private Accessors
    //

        private HolesModel model()
        {

            return myModel;

            }

    //
    // Private Mutators
    //

        private void setModel(HolesModel otherModel)
        {

            myModel = otherModel;

            }
        
    //
    // Public Ctors
    //
        
        public ScoreComponent(HolesModel initialModel)
        {
            setModel(initialModel);
            model().attach(this);
            
            setFont(new Font("Times New Roman", Font.BOLD, 30));
            setForeground(Color.BLACK);
            
            setText("Score: " + model().score());
        }
        
    //
    // Public Observation Methods
    //
        @Override
        public void updateScore()
        {
            setText("Score: " + model().score());
            
            repaint();
        }
        
        @Override
        public void updateRedHolePosition()
        {

            }
        
        @Override
        public void updateSoundStatus()
        {
            
        }

        @Override
        public void updateLevel()
        {
            
        }
        
        @Override
        public void updateLivesRemaining()
        {
            
        }
    
}
class LivesComponent extends JLabel implements HolesModelObserver
{
    //
    // Private Fields
    //

        private HolesModel myModel;

    //
    // Private Accessors
    //

        private HolesModel model()
        {

            return myModel;

            }

    //
    // Private Mutators
    //

        private void setModel(HolesModel otherModel)
        {

            myModel = otherModel;

        }
        
    //
    // Public Ctors
    //
        
        public LivesComponent(HolesModel initialModel)
        {
            setModel(initialModel);
            model().attach(this);
            
            setFont(new Font("Times New Roman", Font.BOLD, 30));
            setForeground(Color.BLACK);
            
            setText("Lives: " + model().livesRemaining());
        }
        
    //
    // Public Observation Methods
    //
        @Override
        public void updateScore()
        {
         
        }
        
        @Override
        public void updateRedHolePosition()
        {

            }
        
        @Override
        public void updateSoundStatus()
        {
            
        }
        
        @Override
        public void updateLivesRemaining()
        {
            setText("Lives: "+ model().livesRemaining());
            repaint();
        }
        
        @Override
        public void updateLevel()
        {
            
        }
    
}
class LevelsComponent extends JLabel implements HolesModelObserver
{
    //
    // Private Fields
    //

        private HolesModel myModel;

    //
    // Private Accessors
    //

        private HolesModel model()
        {

            return myModel;

            }

    //
    // Private Mutators
    //

        private void setModel(HolesModel otherModel)
        {

            myModel = otherModel;

            }
        
    //
    // Public Ctors
    //
        
        public LevelsComponent(HolesModel initialModel)
        {
            setModel(initialModel);
            model().attach(this);
            
            setFont(new Font("Times New Roman", Font.BOLD, 30));
            setForeground(Color.BLACK);
            
            setText("Level: " + model().level());
        }
        
    //
    // Public Observation Methods
    //
        @Override
        public void updateScore()
        {

        }
        
        @Override
        public void updateRedHolePosition()
        {

            }
        
        @Override
        public void updateSoundStatus()
        {
            
        }

        @Override
        public void updateLevel()
        {
            setText("Level: " + model().level());
            repaint();
        }
        
        @Override
        public void updateLivesRemaining()
        {
            
        }
    
}
class HolesComponent extends JComponent implements HolesModelObserver
{

    //
    // Private Fields
    

        private HolesModel myModel;
        

    //
    // Private Accessors
    //

        private HolesModel model()
        {

            return myModel;

            }

    //
    // Private Mutators
    //

        private void setModel(HolesModel otherModel)
        {

            myModel = otherModel;

            }
       
    //
    // Methods
    //
        
        
        public void playSound(String soundName){
                try{

                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();

                }

                catch(Exception ex){
                    System.out.println("Error playing sound.");
                }
            }

    //
    // Public Ctors
    //

        public HolesComponent(HolesModel initialModel)
        {

            setModel(initialModel);
            model().attach(this);

            //
            // Click handler listenes to clicks on the holes
            //

                addMouseListener(new MouseClickHandler());

            //
            // Component listener listenes to screen resize
            //

                addComponentListener(new ResizeHandler());

            }

    //
    // Public Observation Methods
    //
        @Override
        public void updateScore()
        {

            }
        @Override
        public void updateRedHolePosition()
        {

            repaint();

            }
        
        @Override
        public void updateSoundStatus()
        {
            
        }
        
        @Override
        public void updateLivesRemaining()
        {
            
        }

        @Override
        public void updateLevel()
        {
            if (!model().isMuted())
                playSound(model().levelUpSound());
            setBackground(model().randomBackgroundColor());
            repaint();
        }

    //
    // Public Overrides
    //

        //
        // Interacts with the model to set the position and sizes of the holes
        //

            @Override
            public void paintComponent(Graphics canvas)
            {
                model().timer().start();
                final Graphics2D canvas2D = ((Graphics2D) canvas);

                    final double holeWidth =
                        ((double) getWidth()) / model().numberOfColumns();
                    final double holeHeight =
                        ((double) getHeight()) / model().numberOfRows();

                    
                    for (int row = 0; row < model().numberOfRows(); ++ row)
                        for (int column = 0; column < model().numberOfColumns(); ++ column) {


                                canvas2D.setPaint(
                                    row == model().redHoleRow()
                                            && column == model().redHoleColumn()
                                        ? Color.RED
                                        : Color.BLACK
                                    );

                                model().hole(row, column).setFrame(
                                    column * holeWidth,
                                    row * holeHeight,
                                    holeWidth,
                                    holeHeight
                                    );


                                canvas2D.fill(model().hole(row, column));

                            }

            }
            
            
            private class ResizeHandler extends ComponentAdapter
            {

                @Override
                public void componentResized(ComponentEvent event)
                {

                    //
                    // Pseudo-randomly move the red hole
                    //

                        model().randomizeRedHolePosition();

                    }

                }

            private class MouseClickHandler extends MouseAdapter
            {

                @Override
                public void mouseClicked(MouseEvent event)
                {

                    if (model().hole(
                            model().redHoleRow(),
                            model().redHoleColumn()
                            ).contains(event.getPoint()) && !model().gameIsOver()) {

                        //
                        // Update the score with the additional points
                        //
                            model().timer().restart();
                            model().setScore(model().score() + model().scoreIncrement());
                            
                            if (!model().isMuted()){
                                playSound(model().correctSound());
                            }
                        //
                        // Pseudo-randomly move the red hole
                        //
                        
                            model().incrementCorrectClicks();
                            if (model().correctClicks() % 10 == 0){
                                model().incrementLevel();
                               
                            }

                            model().randomizeRedHolePosition();

                        }
                    
                    else if (!model().gameIsOver()){
                        if (!model().isMuted()){
                            playSound(model().wrongSound());
                        }
                        model().decrementLivesRemaining();
                    }

                    }

                }


    }