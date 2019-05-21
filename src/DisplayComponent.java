
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class DisplayComponent extends JPanel implements HolesModelObserver
{
    private HolesModel myModel;
    private JTextField myUserRows = new JTextField(4);
    private JTextField myUserColumns = new JTextField(4);
    private JTextField myUserLives = new JTextField(4);
    private JTextField myUserTimerDelay = new JTextField(4);
    private JTextField myUserName = new JTextField(10);


    private ArrayList < ArrayList < Ellipse2D.Double > > holesArray;

    // Private Accessors

    private HolesModel model()
    {
        return myModel;
    }

    private JTextField userRows()
    {
        return myUserRows;
    }

    private JTextField userColumns()
    {
        return myUserColumns;
    }

    private JTextField userLives()
    {
        return myUserLives;
    }

    private JTextField userTimerDelay()
    {
        return myUserTimerDelay;
    }

    private JTextField userName()
    {
        return myUserName;
    }

    // Private Mutators
    
    private void setModel(HolesModel otherModel)
    {
        myModel = otherModel;
    }


    // Public Constructors

    public DisplayComponent(HolesModel initialModel)
    {
        setModel(initialModel);
        
        // Enter Name of the user
        
        this.add(new JLabel("Enter your name:"));
        this.add(userName());
        
        // Enter Integers
        this.add(new JLabel("Enter Rows:"));
        this.add(userRows());
        this.add(new JLabel("Enter Columns:"));
        this.add(userColumns());
        this.add(new JLabel("Enter Lives:"));
        this.add(userLives());
        this.add(new JLabel("Enter Timer Delay (seconds):"));
        this.add(userTimerDelay());
        this.add(new SubmitButton());
    }

    // Private Classes

    class SubmitButton extends JButton
    {

        private ArrayList < ArrayList < Ellipse2D.Double > > myHoles;

        public SubmitButton()
        {

            this.setText("Submit");
            this.addActionListener(new ActionListener()
            {

                @
                Override
                public void actionPerformed(ActionEvent e)
                {

                    try
                    {
                        
                        String userName = userName().getText();
                        model().setUserName( userName );
                        
                        int userDelay = Integer.parseInt(userTimerDelay().getText()) * 1000;
                        int livesRemaining = Integer.parseInt(userLives().getText());

                        if (userDelay > 0 && userDelay < 5)
                            model().setTimerDelay(userDelay);

                        if (livesRemaining < 10 && livesRemaining > 0)
                            model().setLivesRemaining(livesRemaining);
                        else
                            model().setLivesRemaining(3);

                        int initialRows = Integer.parseInt(userRows().getText());
                        int initialColumns = Integer.parseInt(userColumns().getText());

                        myHoles = new ArrayList < ArrayList < Ellipse2D.Double > > ();

                        for (int row = 0; row < initialRows; ++row)
                        {

                            myHoles.add(new ArrayList < Ellipse2D.Double > ());

                            for (int column = 0; column < initialColumns; ++column)
                                myHoles.get(row).add(new Ellipse2D.Double(0, 0, 0, 0));

                        }
                        
                        getParent().setVisible(false);


                    }
                    catch (NumberFormatException ex)
                    {
                        System.out.println("Exception : " + ex);

                    }

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
