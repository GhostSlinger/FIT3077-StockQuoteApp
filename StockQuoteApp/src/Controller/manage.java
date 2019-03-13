package Controller;

import Model.updateInfo;
import Model.request;
import View.graph;
import View.information;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * This is the controller class to respond to the user input and choices of display and web service.
 */
public class manage implements graphButton {


    private List<request> getstock= new ArrayList();
    private List<updateInfo> updateInfos = new ArrayList();
    private List<String> lsrt = new ArrayList<>();
    private List<update> Updaters = new ArrayList<>();
    private Boolean timeLapse;
    private Boolean first = true;
    private Integer row_num = 0;
    private Boolean firstRowDeleted = false;
    private int y = 0;
    private List<updateInfo> graphUpdateInfos = new ArrayList();
    private List<realTimeGraph> graphicalUpdaters = new ArrayList<>();
    private List<request> graphRequests = new ArrayList<>();
    private List<graph> getDisplays = new ArrayList<>();
    private List<String> getDisplayingStocks = new ArrayList<>();







    private information window;

    public manage(information Window){
        this.window= Window;
        this.window.attach(this);

    }

    //Remove function that respond to the removeButtonClicked
    @Override
    public void update() {


        removeButtonClicked(window.getRowsDeleted());



    }

    @Override

    /**
     * This function manages the stopMonitoring button, which will stop and close
    the graph that the user wants to stop monitoring.*/
    
    public void stopMonitoring(int index) {
        request stopMonitorRequest = graphRequests.get(index);

        stopMonitorRequest.stopUpdating();
        graphRequests.set(index,null);
        graphUpdateInfos.set(index,null);
        realTimeGraph stopMonitorUpdater = graphicalUpdaters.get(index);

        stopMonitorUpdater.stopMonitor();
        graphicalUpdaters.set(index,null);
        getDisplays.get(index).setVisible(false);
        getDisplays.set(index, null);
        getDisplayingStocks.set(index,null);




    }


    public void monitorButtonClicked(String symbol, String webServiceType, String display){

        timeLapse = !Objects.equals(webServiceType, "StockQuoteWS");

        if (!timeLapse || ((symbol.equals("QAN")) || (symbol.equals("RIO")) || (symbol.equals("CBA")) || (symbol.equals("ANZ")) || (symbol.equals("BHP")) || (symbol.equals("NAB")))) {

            if (first) {
                getFieldNames();
                first = false;
            }

            if (!((lsrt.contains(symbol)) && (getDisplayingStocks.contains(symbol)))) {

                request newRequest = new request();
                updateInfo newUpdateInfo = new updateInfo(newRequest);
                if (Objects.equals(display, "Information")) {

                    if (!(lsrt.contains(symbol))) {
                        update Updater = new update(newRequest, newUpdateInfo, first, window, row_num, firstRowDeleted);
                        updateInfos.add(newUpdateInfo);
                        Updaters.add(Updater);
                        window.setVisible(true);
                        getstock.add(newRequest);
                        lsrt.add(symbol);
                        row_num++;
                    }

                } else if (Objects.equals(display, "Graph")) {

                    if (!(getDisplayingStocks.contains(symbol))) {

                        graph chart = new graph(
                                symbol + "  Graph",
                                symbol + " Real Time Price", y);

                        chart.pack();
                        RefineryUtilities.centerFrameOnScreen(chart);
                        chart.setVisible(true);
                        chart.attach(this);
                        realTimeGraph Updater = new realTimeGraph(newRequest, newUpdateInfo, chart);
                        getDisplays.add(chart);
                        graphUpdateInfos.add(newUpdateInfo);
                        graphicalUpdaters.add(Updater);
                        graphRequests.add(newRequest);
                        getDisplayingStocks.add(symbol);
                        y++;
                    }


                }


                newRequest.setState(symbol, timeLapse);


            }
        }



    }

    private void getFieldNames() {
        request fieldNamesRequest = new request();
        updateInfo fieldNamesUpdateInfo = new updateInfo(fieldNamesRequest);
        update dummyUpdater = new update(fieldNamesRequest, fieldNamesUpdateInfo,first,window,0,firstRowDeleted);
        fieldNamesRequest.setState("RIO", true);
        dummyUpdater.stopMonitor();
        fieldNamesRequest.stopUpdating();
    }

    private void removeButtonClicked(int rowDeleted){
        request deletingRequest = getstock.get(rowDeleted);
        deletingRequest.stopUpdating();
        updateInfo deletingUpdateInfo = updateInfos.get(rowDeleted);
        deletingUpdateInfo.stopMonitor();
        update deletingUpdater = Updaters.get(rowDeleted);
        deletingUpdater.stopMonitor();
        lsrt.remove(rowDeleted);
        getstock.remove(rowDeleted);
        updateInfos.remove(rowDeleted);
        Updaters.remove(rowDeleted);

        if (row_num != 0){
            row_num --;
        }

        if (row_num == 0){
            firstRowDeleted = true;
        }



    }



}
