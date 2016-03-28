package ua.berest.lab3.controller.processors;

import ua.berest.lab3.controller.OracleDataAccess;
import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Oleg on 02.03.2016.
 */
public class ProcessorAddLocation extends Processor {
    public ProcessorAddLocation() {
        actionToPerform = "addLocation";
    }
    public ProcessorResult process(HttpServletRequest request) throws DataAccessException {
        String name = request.getParameter("locationName");
        Integer parent_id = Integer.valueOf(request.getParameter("parent"));
        String is_last_level = request.getParameter("course");
        String description = request.getParameter("descriptionName");
        Location location = new LocationImpl(1, name, parent_id, is_last_level, description);
        System.out.println("Location id: " + location.getLocationId());
        System.out.println("Name: " + location.getName());
        System.out.println("Parent id: " + location.getParentId());
        System.out.println("Is course: " + location.getCourse());
        System.out.println("Description: " + location.getDescription());
        OracleDataAccess.getInstance().addLocation(location);
        return new ProcessorResult(("?action=showAllLocations&parentId=" + location.getParentId()), "showAllLocations.jsp", false);
    }
}
