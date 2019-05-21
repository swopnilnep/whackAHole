
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

// 
// Authors: David Will and Swopnil N. Shrestha
// Purpose: Develop a Simple whack a mole game with Java Awt and Swing Libraries
// Instructor: Dr. Alan K Zaring
// Class: CS252 Object Oriented Programming with Java (Spring 2019)
// Date: 2019/18/04
// Phase: 03
// 


public class Holes
{
    public static void main(String[] commandLineArguments)
    {

        // 
        // Run the program frame that displays a score and a userInputRows by userInputColumns
        // grid of holes (Ellipses), of which one hole is pseudo-randomly chosen to be colored
        // in red while all the other holes are colored in black. Clicking on the red hole adds
        // 5 points to the user
        // 

        EventQueue.invokeLater(
            new Runnable()
            {@
                Override
                public void run()
                {

                    ProgramFrame frame = new ProgramFrame();
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
    private HolesModel myModel;

    // 
    // Private Accessors
    // 

    private JPanel mainPanel()
    {
        return myMainPanel;
    }

    private HolesModel model()
    {
        return myModel;
    }

    // 
    // Private Mutators
    // 

    private void setMainPanel(JPanel other)
    {
        myMainPanel = other;
    }

    public void setModel(HolesModel otherModel)
    {
        myModel = otherModel;
    }

    // 
    // Public Constructors
    // 

    public ProgramFrame()
    {
        this(5);
    }

    public ProgramFrame(int sideLength)
    {
        this(sideLength, sideLength);
    }

    public ProgramFrame(int initialNumberOfRows, int initialNumberOfColumns)
    {

        setSize(
            initialNumberOfColumns * OUR_INITIAL_HOLE_DIAMETER,
            getInsets().top + initialNumberOfRows * OUR_INITIAL_HOLE_DIAMETER
        );
        setTitle(OUR_TITLE);


        setModel(new HolesModel(initialNumberOfRows, initialNumberOfColumns));

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
        JPanel displayComponent = new DisplayComponent(model());
        JPanel highScoresComponent = new HighScoresComponent(model());

        displayComponent.setLayout(new BoxLayout(displayComponent, BoxLayout.Y_AXIS));
        highScoresComponent.setLayout(new BoxLayout(highScoresComponent, BoxLayout.Y_AXIS));
        
        // Setup the Holes Panel
        holesPanel.setLayout(new BoxLayout(holesPanel, BoxLayout.Y_AXIS));
        holesPanel.add(new HolesComponent( model()) );
        holesPanel.setBackground(model().randomBackgroundColor());

        // Setup the Sidebar Panel Layout and Components
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        
        sidebarPanel.add(new ScoreComponent(model()));
        sidebarPanel.add(new LevelsComponent(model()));
        sidebarPanel.add(new LivesComponent(model()));
        sidebarPanel.add(new OptionsComponent(model()));

        sidebarPanel.add(displayComponent);
        sidebarPanel.add(highScoresComponent);

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
