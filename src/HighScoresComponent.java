
import java.util.Arrays;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

public class HighScoresComponent extends JPanel implements HolesModelObserver{

    // Class Fields
    
    private HolesModel myModel;
    private JTable myTable;
    private Object[][] myTableObject;
    
    // Private Accessors
    
    private HolesModel model()
    {
        return myModel;
    }
    
    private JTable table()
    {
        return myTable;
    }
    
    private Object[][] tableObject()
    {
        return myTableObject;
    }
    
    // Public Mutators
    
    private void setTable(JTable other)
    {
        myTable = other;
    }
    
    private void setModel(HolesModel other)
    {
        myModel = other;
    }
    
    private void setTableObject(Object[][] other)
    {
        myTableObject = other;
    }
    
    // Constructors
    
    public HighScoresComponent(HolesModel initialModel)
    {
        setModel(initialModel);
        model().attach(this);
        
//        String[][] exampleHighScores = {
//            {"David", "420"}, {"Swopnil", "500"}
//        };
//        
//        String[] columns = {"Name", "Score"};
//        
//        JTable testRun = new JTable(exampleHighScores, columns);
        this.add(new JLabel("All Time High Scores"));
//        this.add(testRun);
        updateHighScores();
        setTable(new JTable(tableObject(), new String[] {"Name", "Score"}));
        table().setModel(new DefaultTableModel(
                
                tableObject(),
                new String[] {"Name","Score"}
        ));
        
        this.add(table());
        
    }
    
    @Override
    public void updateScore() {
        
        
    }

    @Override
    public void updateRedHolePosition() {
        
    }

    @Override
    public void updateSoundStatus() {
        
    }

    @Override
    public void updateLivesRemaining() {
        
    }

    @Override
    public void updateLevel() {
        
    }
    
    @Override
    public void updateHighScores()
    {
        Object[][] outerObject = new Object[16][2];

        
        int currentOuterIndex = 0;
        for (Integer key: model().highScores().keySet())
        {
            
            outerObject[currentOuterIndex][1] = model().highScores().get(key);
            outerObject[currentOuterIndex][0] = key;
            
            
            ++currentOuterIndex;
            
        }
        
        setTableObject(outerObject);
        repaint();
        
        //this.add(table());
    }

    
}
