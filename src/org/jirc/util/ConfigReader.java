/*
 * ConfigReader.java
 *
 * Created on 28. März 2005, 18:30
 */

package org.jirc.util;


import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Element;
import org.jdom.input.SAXHandler;

import java.util.List;
import java.util.Iterator;
 
/**
 *
 * @author Taggy
 */
public class ConfigReader {
    
    /** Creates a new instance of ConfigReader */
    
    private Document xmlFile;
    private Element root;
    private Element server;
    private List entries;
    private Iterator it;
        
    public ConfigReader(String fileName) {
        SAXBuilder builder = new SAXBuilder();
        try{
            xmlFile=builder.build(fileName);
            root = xmlFile.getRootElement();
            entries=root.getChildren();
            it=entries.iterator();
        }
        catch(JDOMException jde){
            jde.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean next(){
        
        if(it.hasNext()){
                server=(Element)it.next();
                return true;
        }
        else        
            return false;
    }
    
    public String getAddress(){
        
        return server.getChild("address").getText();
    }
    public int getPort(){
        
        return Integer.parseInt(server.getChild("port").getText());
    }
    public String getNick(){
        
        return server.getChild("nick").getText();
    }
    public String getHost(){
        
        return server.getChild("host").getText();
    }
    public String getRLName(){
        
        return server.getChild("rlname").getText();
    }
    public String getServer(){
        
        return server.getChild("server").getText();
    }
}
