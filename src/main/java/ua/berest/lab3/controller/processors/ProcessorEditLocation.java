package ua.berest.lab3.controller.processors;

import ua.berest.lab3.controller.OracleDataAccess;
import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Oleg on 02.03.2016.
 */
public class ProcessorEditLocation extends Processor {
    public ProcessorEditLocation() {
        actionToPerform = "editLocation";
    }
    public ProcessorResult process(HttpServletRequest request) throws DataAccessException {
        Integer id = Integer.valueOf(request.getParameter("idName"));
        String name = request.getParameter("locationName");
        Integer parent_id = Integer.valueOf(request.getParameter("parent"));
        String is_last_level = request.getParameter("course");
        String description = request.getParameter("descriptionName");
        Location location = new LocationImpl(id, name, parent_id, is_last_level, description);
        OracleDataAccess.getInstance().updateLocation(location);
        return new ProcessorResult(("?action=showAllLocations&parentId=" + id), "showAllLocations.jsp", false);
    }
}
