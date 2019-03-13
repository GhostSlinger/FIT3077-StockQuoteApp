package Model;

import org.w3c.dom.NodeList;
import javax.xml.soap.*;
import java.util.ArrayList;
import java.util.List;

public abstract class fetch {
    private List<monitor> monitors = new ArrayList<monitor>();
    public abstract void setState(String symbol, boolean timeLapse);

    public abstract NodeList getState();
    public abstract NodeList getFieldData();

    public void attach(monitor monitor){
        monitors.add(monitor);
    }


    public void detach(monitor monitor){
      monitors.remove(monitor);
    }


    private static SOAPMessage createMessageTemplate() throws Exception{
        MessageFactory fieldNamesQueryFactory = MessageFactory.newInstance();
        SOAPMessage fieldNamesQuery = fieldNamesQueryFactory.createMessage();
        return fieldNamesQuery;
    }

    static SOAPMessage createSOAPGetFieldNamesRequest() throws Exception {
        SOAPMessage fieldNamesmessage = createMessageTemplate();


        MimeHeaders Header = fieldNamesmessage.getMimeHeaders();
        Header.addHeader("SOAPAction", "urn:getFieldNames");

        fieldNamesmessage.saveChanges();

        return fieldNamesmessage;
    }


    static SOAPMessage createSOAPgetQuoteRequest(String symbol, boolean timeLapse) throws Exception {

        SOAPMessage quotevaluequery = createMessageTemplate();
        SOAPPart quotevaluequerySOAPPart = quotevaluequery.getSOAPPart();

        String serverAddress = "http://StockQuoteService";


        SOAPEnvelope envelope = quotevaluequerySOAPPart.getEnvelope();
        envelope.addNamespaceDeclaration("stoc", serverAddress);

        SOAPBody body = envelope.getBody();
        SOAPElement bodyElem = body.addChildElement(symbol, "stoc");

        SOAPElement bodyElem1 = bodyElem.addChildElement("symbol", "stoc");



        bodyElem1.addTextNode(symbol);

        MimeHeaders headers = quotevaluequery.getMimeHeaders();
        if (!timeLapse) {
            headers.addHeader("SOAPAction", "urn:getQuote");
        }
        else {
            headers.addHeader("SOAPAction", "urn:getStockQuote");
        }
        quotevaluequery.saveChanges();
        return quotevaluequery;
    }

    NodeList processResponse(SOAPMessage Response) throws Exception {

        return Response.getSOAPPart().getEnvelope().getBody().getElementsByTagName("ns:return");
    }
    // This notifies the monitors when a request needs to be updated
    void notifyAllObservers(){
        for (monitor monitor : monitors) {
            monitor.update();
        }
    }






}
