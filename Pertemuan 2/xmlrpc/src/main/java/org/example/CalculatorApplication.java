package org.example;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import org.example.service.CalculatorService;

public class CalculatorApplication {
    public static void main(String[] args) {
        try {
            System.out.println("Starting XML-RPC Calculator Service...");

            // Buat server XML-RPC pada port 8080
            WebServer webServer = new WebServer(8080);
            XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

            // Konfigurasi handler mapping
            PropertyHandlerMapping phm = new PropertyHandlerMapping();
            phm.addHandler("calculator", CalculatorService.class);
            xmlRpcServer.setHandlerMapping(phm);

            // Konfigurasi server
            XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
            serverConfig.setEnabledForExtensions(true);
            serverConfig.setContentLengthOptional(false);

            // Mulai server
            webServer.start();
            System.out.println("XML-RPC Calculator Service is running.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
