import Controller.manage;
import View.information;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//This is to create the layout for our window
class infoWindow {

    private JFrame frame;

    //Size
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 100;

    //Labels
    private JLabel stockCodeLabel;
    private JLabel websService;

    //Combobox for the Stock ID
    private JComboBox<String> stockCodeDropDown;
    private JComboBox<String> webServiceDropDown;
    private JComboBox<String> displayTypesDropDown;

    //Button
    private JButton monitorBtn;

    //Inputs the Stock to be monitored
    private information window = new information();

    //this is used to keep track of request activities
    private manage stkmanager = new manage(window);

    //This is to store them so we are able to remove and delete
    private String[] stocks = new String[] {};
    private String[] webservice = new String[] {"StockQuoteWS", "StockQuoteTimeLapseService"};
    private JLabel displayTypesInfo;
    private String[] displayTypes = new String[] {"Information", "Graph"};

    //Creating the application
    infoWindow() {
        initialize();
    }

    private void initialize() {
        setFrame(new JFrame());

        getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getFrame().setTitle("Stock Quote Monitor");
        getFrame().setSize(FRAME_WIDTH, FRAME_HEIGHT);
        getFrame().setResizable(false);

        createTextFields();
        createBtn();
        createPanels();
    }

    //Creates the field to display our information on the first screen that pops up
    private void createTextFields() {
        stockCodeLabel = new JLabel("ASX Stock Code: ");

        stockCodeDropDown = new JComboBox<>(stocks);
        stockCodeDropDown.setEditable(true);
        websService = new JLabel(" WebService");
        webServiceDropDown = new JComboBox<>(webservice);
        webServiceDropDown.setEditable(true);
        displayTypesInfo = new JLabel(" Display Type:");
        displayTypesDropDown = new JComboBox<>(displayTypes);
        displayTypesDropDown.setEditable(true);





    }

    //Adds the function of adding more stock to the information window
    private void createBtn() {
        monitorBtn = new JButton("Monitor");


        class AppListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                String symbol = String.valueOf(stockCodeDropDown.getSelectedItem());
                String webServiceType = String.valueOf(webServiceDropDown.getSelectedItem());
                String display = String.valueOf(displayTypesDropDown.getSelectedItem());

                stkmanager.monitorButtonClicked(symbol,webServiceType,display);

            }
        }


        ActionListener listen = new AppListener();
        monitorBtn.addActionListener(listen);



    }

    //layout and design of the initial screen
    private void createPanels() {
        JPanel panel = new JPanel();
        getFrame().getContentPane().add(panel, BorderLayout.CENTER);
        panel.add(stockCodeLabel,BorderLayout.PAGE_START);
        panel.add(stockCodeDropDown);
        panel.add(websService);
        panel.add(webServiceDropDown);
        panel.add(displayTypesInfo);
        panel.add(displayTypesDropDown);
        getFrame().getContentPane().add(monitorBtn, BorderLayout.SOUTH);
        panel.setBackground(Color.white);
        panel.setPreferredSize(new java.awt.Dimension(800, 800));
    }


    JFrame getFrame() {
        return frame;
    }

    private void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
