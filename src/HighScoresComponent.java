
import javax.swing.JPanel;
import javax.swing.JTable;
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
    
    private Object [][] tableObject()
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
        setTable(new JTable());
        table().setModel(new DefaultTableModel(
                
                tableObject(),
                new String[] {"Name","Score"}
        ));
    }
    
    @Override
    public void updateScore() {
        
        Object[] outerObject = new Object[16];
        Object[] innerObject = new Object[2];
        
        int currentOuterIndex = 0;
        for (Integer key: model().highScores().keySet())
        {
            innerObject[0] = model().highScores().get(key);
            innerObject[1] = key;
            
            outerObject[currentOuterIndex] = innerObject;
             
        }
        
        setTableObject((Object[][]) outerObject);
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
        
    }

    
}
