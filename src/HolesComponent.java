
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;

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

        addMouseListener((MouseListener) new MouseClickHandler());

        //
        // Component listener listenes to screen resize
        //

        addComponentListener((ComponentListener) new ResizeHandler());

    }

    //
    // Public Observation Methods
    //
    @
    Override
    public void updateScore()
    {

    }@
    Override
    public void updateRedHolePosition()
    {

        repaint();

    }

    @
    Override
    public void updateSoundStatus()
    {

    }

    @
    Override
    public void updateLivesRemaining()
    {

    }

    @
    Override
    public void updateLevel()
    {
        if (!model().isMuted())
            playSound(model().levelUpSound());
        getParent().setBackground(model().randomBackgroundColor());
        int newDelay = (int)(model().timerDelay() * 0.8);
        model().setTimerDelay(newDelay);
        repaint();

    }
    
    @Override
    public void updateHighScores()
    {
        
    }

    //
    // Public Overrides
    //

    //
    // Interacts with the model to set the position and sizes of the holes
    //

    @
    Override
    public void paintComponent(Graphics canvas)
    {
        model().timer().start();
        final Graphics2D canvas2D = ((Graphics2D) canvas);

        final double holeWidth =
            ((double) getWidth()) / model().numberOfColumns();
        final double holeHeight =
            ((double) getHeight()) / model().numberOfRows();


        for (int row = 0; row < model().numberOfRows(); ++row)
            for (int column = 0; column < model().numberOfColumns(); ++column)
            {


                canvas2D.setPaint(
                    row == model().redHoleRow() && column == model().redHoleColumn() ? Color.RED : Color.BLACK
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

        @
        Override
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

        @
        Override
        public void mouseClicked(MouseEvent event)
        {

            if (model().hole(
                    model().redHoleRow(),
                    model().redHoleColumn()
                ).contains(event.getPoint()) && !model().gameIsOver())
            {

                //
                // Update the score with the additional points
                //
                model().timer().restart();
                model().setScore(model().score() + model().scoreIncrement());

                if (!model().isMuted())
                {
                    playSound(model().correctSound());
                }
                //
                // Pseudo-randomly move the red hole
                //

                model().incrementCorrectClicks();
                if (model().correctClicks() % 10 == 0)
                {
                    model().incrementLevel();

                }

                model().randomizeRedHolePosition();

            }
            else if (!model().gameIsOver())
            {
                if (!model().isMuted())
                {
                    playSound(model().wrongSound());
                }
                model().decrementLivesRemaining();
            }

        }

    }


}