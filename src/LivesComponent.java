
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

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

    }

    @
    Override
    public void updateLivesRemaining()
    {
        setText("Lives: " + model().livesRemaining());
        repaint();
    }

    @
    Override
    public void updateLevel()
    {

    }
    
    @Override
    public void updateHighScores()
    {
        
    }

}
