
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

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
    @
    Override
    public void updateScore()
    {
        setText("Score: " + model().score());

        repaint();
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
