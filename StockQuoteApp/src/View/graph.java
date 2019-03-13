package View;
import Controller.graphButton;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

//managing the design of the graph
public class graph extends ApplicationFrame {
    //dataset has been initialised which will show on the graph
    private DefaultCategoryDataset dataset;
    private JButton stopMonitor;
    private ChartPanel chartPanel;
    private int index;
    private java.util.List<graphButton> buttonObservers = new ArrayList<graphButton>();
    public void attach(graphButton bObserver){
        buttonObservers.add(bObserver);
    }
    private void notifyAllObservers(){
        for (graphButton button : buttonObservers) {
            //stop pulling data for the graph when the stop monitoring button has been clicked
            button.stopMonitoring(index);
        }
    }
    //creates the graph to be displayed
    public graph(String tabName, String ChartTitle, int y) {
        super(tabName);
        JFreeChart lineGraph = ChartFactory.createLineChart(ChartTitle,"New Price at current time", "Price ($) Dollars", createDataset(), PlotOrientation.VERTICAL, true, true, false);
        chartPanel = new ChartPanel(lineGraph);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 800));
        createBtns();
        createPanels();
        this.index = y;
    }
    //creates a button to stop monitoring of the graph
    private void createBtns() {
        stopMonitor = new JButton("Stop Monitoring");
        class stopMonitorListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyAllObservers();
            }

        }
        ActionListener stopTheMonitorProcess = new stopMonitorListener();
        stopMonitor.addActionListener(stopTheMonitorProcess);
    }
    //creates the panel layout for the graph
    private void createPanels() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        JPanel panel = new JPanel();
        contentPane.add(chartPanel,BorderLayout.CENTER);
        contentPane.add(panel,BorderLayout.NORTH);
        panel.add(stopMonitor);
        panel.setBackground(Color.white);
    }

    //creates the dataset for every new graph being made
    private DefaultCategoryDataset createDataset() {
        dataset = new DefaultCategoryDataset();
        return dataset;
    }
    //updates the line graph by adding the stock price value
    public void updateGraph(float price, String time){
        dataset.setValue(price,"Price in $ Dollars",time);


    }


}