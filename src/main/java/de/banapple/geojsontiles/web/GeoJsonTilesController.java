/**
 * 
 */
package de.banapple.geojsontiles.web;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import javax.json.*;
import javax.json.stream.*;
import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import de.banapple.geojsontiles.data.*;

@Controller
public class GeoJsonTilesController
{
    private static final Logger log = Logger.getLogger("GeoJsonTilesController");
    
    @Autowired
    private GeoJsonTilesProvider tilesProvider;

    @RequestMapping(value = "/markers/{z}/{x}/{y}.json")
    public void tiles(
        @PathVariable int z,
        @PathVariable int x,
        @PathVariable int y,
        HttpServletResponse response) throws IOException
    {
        log.info("serving tile " + z + "/" + x + "/" + y);
        
        response.setContentType("application/json; charset=UTF-8");

        List<Marker> markers = tilesProvider.getMarkers(z, x, y);
        log.info("markers: " + markers.size());
        
        PrintWriter writer = response.getWriter();

        JsonGeneratorFactory factory = Json.createGeneratorFactory(null);
        JsonGenerator json = factory.createGenerator(writer);
        writeJsonForMarkers(json, markers);
    }

    private void writeJsonForMarkers(
        JsonGenerator json,
        List<Marker> markers)
    {
        json.writeStartObject()
            .write("type", "FeatureCollection")
            .writeStartArray("features");
        for ( Marker item : markers ) {
            json.writeStartObject();
            json.write("type", "Feature");
            json.writeStartObject("geometry");
            json.write("type", "Point");
            json.writeStartArray("coordinates");
            json.write(item.getLng());
            json.write(item.getLat());
            json.writeEnd(); /* end of coordinates */
            json.writeEnd(); /* end of geometry */
            json.writeStartObject("properties");
            json.write("id", item.getId());
            json.writeEnd(); /* end of properties */
            json.write("id", item.getId());
            json.writeEnd(); /* end of the feature */
        }
        json.writeEnd() /* end of features */
            .writeEnd() /* end of the object */
            .close();
    }
}
