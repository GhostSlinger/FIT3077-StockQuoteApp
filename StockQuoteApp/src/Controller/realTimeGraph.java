package Controller;
import Model.fetch;
import Model.updateInfo;
import Model.monitor;
import View.graph;

/**
 * This controller class is used to update the graph view class in response to request state changes.
 */
public class realTimeGraph implements monitor {
    private fetch fetch;
    private updateInfo updateInfo;
    private graph chart;
    private float price;
    private String time;

    realTimeGraph(fetch fetch, updateInfo updateInfo, graph timeLapseWindow){
        this.fetch = fetch;
        this.fetch.attach(this);
        this.updateInfo = updateInfo;
        this.chart = timeLapseWindow;
    }

    //This method is responsible for updating the graphical view associated with realTimeGraph.
    @Override
    public void update() {

        price = Float.parseFloat(String.valueOf(updateInfo.retrievenewquotevals().item(1).getTextContent()));

        time = String.valueOf(updateInfo.retrievenewquotevals().item(3).getTextContent());

        chart.updateGraph(price,time);

    }

    //This method is responsible for removing the realTimeGraph monitor when the user requests.
    @Override
    public void stopMonitor() {
        this.fetch.detach(this);
    }
}
