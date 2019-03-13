package Controller;
import Model.fetch;
import Model.monitor;
import Model.updateInfo;
import View.information;
import org.w3c.dom.NodeList;

/**
 * This is another controller class which updates the normal view for each request that the user made.
 */
public class update implements monitor {
    private fetch fetch;
    private updateInfo updateInfo;
    private Boolean First;
    private information window;
    private Boolean firstUpdate = true;
    private Integer rowNum;
    private NodeList qvals;
    private NodeList fvals;
    private Boolean firstRowDeleted;

    //Intialisation
    update(fetch fetch, updateInfo updateInfo, boolean first, information window, Integer rownum, boolean firstrowDeleted){
        this.fetch = fetch;
        this.fetch.attach(this);
        this.updateInfo = updateInfo;
        this.First = first;
        this.window = window;
        this.rowNum = rownum;
        this.firstRowDeleted = firstrowDeleted;


    }

    @Override
    public void update() {
        fvals = updateInfo.retrievefilednames();
        qvals = updateInfo.retrievenewquotevals();
        if (First) {
            window.UpdateTable(fvals,fvals.getLength());
        }
        else {

            if (firstUpdate && rowNum == 0){

                if (!firstRowDeleted) {
                    window.UpdateData(qvals, rowNum);
                    firstUpdate = false;
                }
                else {

                    window.AddData(qvals,qvals.getLength());
                    firstUpdate = false;

                }

            }
            else if (firstUpdate && rowNum > 0){
                window.AddData(qvals,qvals.getLength());
                firstUpdate = false;

            }
            else {

                window.UpdateData(qvals,rowNum);
            }



        }



    }

    //This function stops the view updating process by detaching the monitorStockUpdater
    @Override
    public void stopMonitor() {
        this.fetch.detach(this);
    }
}
