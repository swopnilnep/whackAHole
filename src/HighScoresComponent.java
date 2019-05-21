
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
        
        String[][] exampleHighScores = {
            {"Jon Snow","2094"},
            {"Denarys","2048"},
            {"David Bowie", "1998"}, 
            {"Bilbo Baggins","1953"},
            {"Freddie Mercury", "1945"}, 
            {"Tyrion Lannister", "1899"}, 
            {"Sansa Stark", "1856"}, 
            {"Arya Stark", "1820"}, 
            {"R2D2", "1742"}, 
            {"Harry Potter","1578"},
            {"Dr. Who","1467"},
            {"Sauron","1452"},
            {"Lakshmana","1345"},
            {"Kansha Bramhacharya","1311"},
        };
        
        String[] columns = {"Name", "Score"};
        
        JTable testRun = new JTable(exampleHighScores, columns);
        this.add(new JLabel("All Time High Scores"));
        this.add(testRun);
//        updateHighScores();
//        setTable(new JTable(tableObject(), new String[] {"Name", "Score"}));
//        table().setModel(new DefaultTableModel(
//                
//                tableObject(),
//                new String[] {"Name","Score"}
//        ));
//        
//        this.add(table());
        
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
