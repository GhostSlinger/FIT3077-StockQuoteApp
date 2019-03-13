package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.w3c.dom.NodeList;
import Controller.graphButton;


//This manages the stock quote on our information table
public class information extends JFrame {
    private List<graphButton> buttonObservers = new ArrayList<graphButton>();
    public void attach(graphButton bObserver){
        buttonObservers.add(bObserver);
    }
    //this lets observers know when a change has been done row removed or cleared
    private void notifyAllObservers(){
        for (graphButton button : buttonObservers) {
            button.update();
        }
    }
    //size
    private static final int FRAME_WIDTH = 650;
    private static final int FRAME_HEIGHT = 600;

    //design of table, buttons
    private JTable table;
    private JButton btnRemoveLastItem, btnRemoveAll;
    private DefaultTableModel model = new DefaultTableModel();
    private Integer rowDeleted = null;

    //window initialised
    public information() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        createBtns();
        createPanels();

    }


    //buttons created as a action when clicked
    private void createBtns() {
        btnRemoveLastItem = new JButton("Remove Last Item");
        class RemoveItemListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getRowCount() != 0) {
                    rowDeleted = model.getRowCount()-1;
                    model.removeRow(model.getRowCount() - 1);
                    notifyAllObservers();
                }
            }
        }

        ActionListener removeRow = new RemoveItemListener();
        btnRemoveLastItem.addActionListener(removeRow);

        //button to move the rows
        btnRemoveAll = new JButton("Remove All");
        class RemoveAllListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowCount = model.getRowCount();
                for (int items = rowCount - 1; items >= 0; items--) {
                    rowDeleted = items;
                    model.removeRow(items);
                    notifyAllObservers();
                }


            }
        }

        ActionListener removeAll = new RemoveAllListener();
        btnRemoveAll.addActionListener(removeAll);}

    //aligning of panels
    private void createPanels() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        panel.add(btnRemoveLastItem);
        panel.add(btnRemoveAll);


        JPanel tablePanel = new JPanel();
        contentPane.add(tablePanel, BorderLayout.CENTER);

        table = new JTable(model);
        tablePanel.add(table);
    }

    //Adds the field names as the column name for the table
    public void UpdateTable(NodeList getFields, int y) {
        getContentPane().setLayout(new FlowLayout());

        //adds column to array
        String[] coloumnNames = new String[y];
        for (int x = 0; x < getFields.getLength(); x++) {
            coloumnNames[x] = String.valueOf(getFields.item(x).getTextContent());

        }

        //adds the stock quote data in an object into the row
        Object[][] data = new Object[1][y];
        model = new DefaultTableModel(data, coloumnNames);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
    }

    //Adds the data into the table
    public void AddData(NodeList getQuote,int y) {
        Object[] data = new Object[y];
        for (int x = 0; x < getQuote.getLength(); x++) {

            data[x] = String.valueOf(getQuote.item(x).getTextContent());
        }
        model.addRow(data);
    }

    //this is used to fetch the index of the row which is deleted
    public Integer getRowsDeleted() {
        return rowDeleted;
    }



    // This is used to get the request value of a object at a selected row and change the old value with the new one
    public void UpdateData(NodeList quotvals, int rownum) {
        for (int u = 0; u < quotvals.getLength(); u++) {
            model.setValueAt(String.valueOf(quotvals.item(u).getTextContent()), rownum, u);
        }

    }

}
