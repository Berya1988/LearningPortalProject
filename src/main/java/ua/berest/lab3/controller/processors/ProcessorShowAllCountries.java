package ua.berest.lab3.controller.processors;

import ua.berest.lab3.controller.OracleDataAccess;
import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.Location;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Oleg on 01.03.2016.
 */
public class ProcessorShowAllCountries extends Processor {
    public ProcessorShowAllCountries() {
        actionToPerform = "showAllCountries";
    }
    public String process(HttpServletRequest request) throws DataAccessException {
        List<Location> listOfAllCountries = OracleDataAccess.getInstance().getAllCountries();
        request.getSession().setAttribute("listOfAllCountries", listOfAllCountries);
        return "showAllCountries";
    }
}
