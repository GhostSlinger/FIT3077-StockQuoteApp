package Model;

import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.*;

//this communicates between the webservice and the java class to update the stock quotes and let the monitors know that it has been updated
public class request extends fetch {
  
    private NodeList getFieldsNames;
    private NodeList getQuoteVals;
    private String symbol;
    private Boolean timeLapse;
    private Boolean continueUpdating = true;
    //Updates the stock with the stock that is being monitored
    //normal updating
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    if (continueUpdating) {

                            long delay = 60 * 1000L;  //every 1 minutes
                            Thread.sleep(delay);
                            getStockData(symbol,timeLapse);
                            notifyAllObservers();
                    }
                    else {
                        Thread.currentThread().interrupt();
                        return;
                    }

                }



            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }



    };
    //Timelapse
    Runnable tlapse = new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    if (continueUpdating) {


                            long delay = 5 * 1000L; //Every 5 seconds
                            Thread.sleep(delay);
                            getStockData(symbol,timeLapse);
                            notifyAllObservers();

                    }
                    else {
                        Thread.currentThread().interrupt();
                        return;
                    }

                }



            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }



    };



    private void getStockData(String symbol, boolean timeLapse) {
        try {


            SOAPConnectionFactory webServiceConnectionFactory = SOAPConnectionFactory.newInstance();

            SOAPConnection webServiceConnection = webServiceConnectionFactory.createConnection();


            String url;
            if (!timeLapse) {
                url = "http://viper.infotech.monash.edu.au:8180/ASXStockQuote/services/StockQuoteWS?wsdl";
            }
            else {
                url = "http://viper.infotech.monash.edu.au:8180/axis2/services/StockQuoteTimeLapseService?wsdl";
            }
            SOAPMessage fieldnames = webServiceConnection.call(createSOAPGetFieldNamesRequest(), url);
            getFieldsNames = processResponse(fieldnames);


            SOAPMessage stockquotevalues = webServiceConnection.call(createSOAPgetQuoteRequest(symbol,timeLapse), url);

            getQuoteVals = processResponse(stockquotevalues);
            getPriceValues(timeLapse);


            webServiceConnection.close();


        } catch (Exception e) {
            System.out.print("The soap message could not be sent to the web service. Please check internet connection.");
        }
    }


    @Override
    public NodeList getState() {
        return getQuoteVals;
    }
    public NodeList getFieldData(){
        return getFieldsNames;
    }

    //this retrieves the last used quote values and lets the observing monitors know of the changed state
    @Override
    public void setState(String s,boolean tl) {
        getStockData(s,tl);
        symbol = s;
        timeLapse = tl;
        notifyAllObservers();
        Thread update = new Thread(runnable);
        Thread timeLapse = new Thread(tlapse);
        if (!tl) {
            update.start();
        }
        else {
            timeLapse.start();
        }

    }
    //This is used to decide whether the time lapse is being used or not
    private void getPriceValues(boolean timeLapse){
        List <Integer> indexOfPriceVals = new ArrayList<>();
        indexOfPriceVals.add(1);
        indexOfPriceVals.add(4);
        indexOfPriceVals.add(5);
        indexOfPriceVals.add(6);
        indexOfPriceVals.add(7);
        if (timeLapse){
            for (int item : indexOfPriceVals) {

                String u = String.valueOf(getQuoteVals.item(item).getTextContent());
                u = convertFromCentsToDollars(u);
                getQuoteVals.item(item).setTextContent(u);
            }

        }

    }
    //this is used to prevent it from updating
    public void stopUpdating(){
       continueUpdating = false;
    }
    //converting cents to dollar values
    private String convertFromCentsToDollars(String u){
        float m = Float.parseFloat(u);
        m = m /100;
        return String.valueOf(m);

    }


}
