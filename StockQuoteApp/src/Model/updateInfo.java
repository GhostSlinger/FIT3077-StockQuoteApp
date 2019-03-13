package Model;


import org.w3c.dom.NodeList;


//This objects a request object and allows for a change when it is required
public class updateInfo implements monitor {

    private NodeList feildnames;
    private NodeList quotevals;

    // This is used to provide a certain request as the fetch is monitored
    private fetch fetch;
    public updateInfo(fetch fetch){

        this.fetch = fetch;
        this.fetch.attach(this);

    }

    //Stops the monitoring of a request when a user no longer wants to monitor a stock quote
    @Override
    public void stopMonitor(){
        this.fetch.detach(this);
    }

    //This is used to update the stock quote values
    @Override
    public void update() {
        quotevals= fetch.getState();
        feildnames = fetch.getFieldData();
    }

    // This allows for displaying of the field names and the quote values and also to retrieve the information
    public NodeList retrievenewquotevals(){
        return quotevals;
    }
    public NodeList retrievefilednames(){
        return feildnames;
    }
}
