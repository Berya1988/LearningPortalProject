package ua.berest.lab3.controller;

import org.w3c.dom.*;

import org.xml.sax.SAXException;
import ua.berest.lab3.controller.processors.Processor;
import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.ProcessorResult;

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
        try {
            namesOfAllProcessors = extractProcessorNamesFromXMLFile("/WEB-INF/resources/processors.xml");
        } catch (IOException e) {
            throw new ServletException(e);
        } catch (ParserConfigurationException e) {
            throw new ServletException(e);
        } catch (SAXException e) {
            throw new ServletException(e);
        }

        for (String name : namesOfAllProcessors) {
            try {
                listOfAllProcessors.add((Processor) Class.forName(name).newInstance());
            } catch (InstantiationException e) {
                throw new ServletException(e);
            } catch (IllegalAccessException e) {
                throw new ServletException(e);
            } catch (ClassNotFoundException e) {
                throw new ServletException(e);
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

        for (Processor processor : listOfAllProcessors) {
            try {
                if(processor.canProcess(request.getParameter("action"))){
                    ProcessorResult result = processor.process(request);
                    if(result.getIncludedPage() != null) {
                        request.getSession().setAttribute("includedJSPName", result.getIncludedPage());
                    }
                    if (result.isForward()) {
                        try {
                            RequestDispatcher rd = request.getRequestDispatcher(result.getUrl());
                            rd.forward(request, response);
                        } catch (ServletException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        response.sendRedirect("DispatcherServlet?action=" + result.getUrl());
                    }
                    break;
                }
            } catch (DataAccessException e) {
                System.err.println (e.getMessage());
            }
        }
    }

    private List<String> extractProcessorNamesFromXMLFile(String path) throws IOException, ParserConfigurationException, SAXException {

        String fullPath = getServletContext().getResource(path).getPath();
        List<String> localNamesOfAllProcessors = new ArrayList<String>();

        File xmlFile = new File(fullPath);
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document document = docBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();

        String className;

        NodeList list = document.getElementsByTagName("processor");
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if(node instanceof Element) {
                Element element = (Element)list.item(i);
                className = element.getElementsByTagName("processorName").item(0).getTextContent();
                localNamesOfAllProcessors.add(className);
            }
        }
        return localNamesOfAllProcessors;
    }
}
