package ua.berest.lab3.controller;

import org.w3c.dom.*;

import org.xml.sax.SAXException;
import ua.berest.lab3.controller.processors.Processor;
import ua.berest.lab3.exception.DataAccessException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 18.02.2016.
 */
public class DispatcherServlet extends HttpServlet {

    private  List<Processor> listOfAllProcessors = new ArrayList<Processor>();
    private List<String> namesOfAllProcessors;

    @Override
    public void init() throws ServletException {
        super.init();
        List<String> namesOfAllProcessors = extractProcessorNamesFromXML();
        System.out.println("Size of processor name list: " + namesOfAllProcessors.size());
        for (String name : namesOfAllProcessors) {
            System.out.println("Name of processor: " + name);
            try {
                try {
                    listOfAllProcessors.add((Processor) Class.forName("ua.berest.lab3.controller.processors." + name).newInstance());
                } catch (InstantiationException e) {
                    System.err.println (e.getMessage());
                } catch (IllegalAccessException e) {
                    System.err.println (e.getMessage());
                }
            } catch (ClassNotFoundException e) {
                System.err.println (e.getMessage());
            }
        }
    }

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

        request.getSession().setAttribute("namesOfAllProcessors", namesOfAllProcessors);

        // test application
        System.out.println("Size of processor list: " + listOfAllProcessors.size());
        for (Processor processor : listOfAllProcessors) {
            if(processor.canProcess(action)){
                try {
                    jspFileName = processor.process(request) + ".jsp";
                } catch (DataAccessException e) {
                    System.err.println (e.getMessage());
                }
                break;
            }
        }

        request.getSession().setAttribute("includedJSPName", jspFileName);
        RequestDispatcher rd = null;
        if (action.equals("logOut")){
            rd = request.getRequestDispatcher("/");
        } else if(action.equals("addNewStudent")) {
            response.sendRedirect("DispatcherServlet?action=showAllStudents");
        }
        else {
            rd = request.getRequestDispatcher("pages/template.jsp");
        }

        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            System.err.println(e.getMessage());
        }
    }

    private List<String> extractProcessorNamesFromXML() {
        String fullPath = null;
        ServletContext servletContext = getServletContext();

        try {
            fullPath = servletContext.getResource("/WEB-INF/resources/processors.xml").getPath();
            System.out.println(fullPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        File xmlFile = new File(fullPath);
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
