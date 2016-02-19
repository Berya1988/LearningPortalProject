package ua.berest.lab3.controller;

import org.xml.sax.InputSource;
import org.w3c.dom.*;

import org.xml.sax.SAXException;
import ua.berest.lab3.controller.processors.Processor;
import ua.berest.lab3.controller.processors.ProcessorShowAllStudents;
import ua.berest.lab3.exception.DataAccessException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 18.02.2016.
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        String jspFileName = null;
        List<Processor> listOfAllProcessors = new ArrayList<Processor>();
        List<String> namesOfAllProcessors = new ArrayList<String>();

        // extract list of working processors from the file
        namesOfAllProcessors = extractProcessorNamesFromXML();
        for (String name:namesOfAllProcessors) {
            request.getSession().setAttribute("name", name);
            try {
                try {
                    listOfAllProcessors.add((Processor) Class.forName(name).newInstance());
                } catch (InstantiationException e) {
                    System.err.println (e.getMessage());
                } catch (IllegalAccessException e) {
                    System.err.println (e.getMessage());
                }
            } catch (ClassNotFoundException e) {
                System.err.println (e.getMessage());
            }
        }

        // test application
        // listOfAllProcessors.add(new ProcessorShowAllStudents());
        for (Processor processor : listOfAllProcessors) {
            if(processor.canProcess(action)){
                try {
                    jspFileName = processor.process(request);
                } catch (DataAccessException e) {
                    System.err.println (e.getMessage());
                }
                break;
            }
        }

        /*RequestDispatcher rd = request.getRequestDispatcher(jspFileName + ".jsp");*/
        RequestDispatcher rd = request.getRequestDispatcher("template.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            System.err.println(e.getMessage());
        }
    }

    private List<String> extractProcessorNamesFromXML() {
        File xmlFile = new File("resources/processors.xml");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.println(e.getMessage());
        }

        Document document = null;
        try {
            document = docBuilder.parse(xmlFile);
        } catch (SAXException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        document.getDocumentElement().normalize();

        List<String> namesOfAllProcessors = new ArrayList<String>();
        String className;

        NodeList list = document.getElementsByTagName("processor");

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if(node instanceof Element) {
                Element element = (Element)list.item(i);
                className = element.getElementsByTagName("processorName").item(0).getTextContent();
                namesOfAllProcessors.add(className);
            }
        }
        return namesOfAllProcessors;
    }
}
