package de.banapple.geojsontiles.data;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import javax.annotation.*;

import org.apache.commons.io.*;
import org.springframework.stereotype.*;

@Component
public class GeoJsonTilesProvider
{
    private static final Logger log = Logger.getLogger("GeoJsonTilesProvider");

    private static final String resource = "/german_cities.csv";

    private List<Marker> markers;
    private Map<Tile, List<Marker>> tileMarkers;
    
    @PostConstruct
    public void initializeMarkers() throws IOException
    {
        log.info("initialize markers");
        
        /* load data from csv */
        List<Marker> markers = new LinkedList<Marker>();
        InputStream is = this.getClass().getResourceAsStream(resource);
        LineIterator it = IOUtils.lineIterator(is, "UTF-8");
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                String[] tokens = line.split(",");
                if ( tokens.length != 3 )
                    continue;

                int id = Integer.parseInt(tokens[0]);
                float lat = Float.parseFloat(tokens[1]);
                float lng = Float.parseFloat(tokens[2]);
                markers.add(new Marker(id, lat, lng));
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        log.info("loaded " + markers.size() + " markers");
        this.markers = markers;
        
        /* determine the tiles for each marker */
        tileMarkers = new HashMap<Tile, List<Marker>>();
        for (Marker marker : markers) {
            
            for ( int zoom = 5; zoom <= 18; zoom++ ) {
                Tile tile = getTileNumber(marker.getLat(), marker.getLng(), zoom);
                List<Marker> currentMarkers = tileMarkers.get(tile);
                if (currentMarkers==null) {
                    currentMarkers = new LinkedList<Marker>();
                    tileMarkers.put(tile, currentMarkers);
                }
                log.finest("adding marker " + marker + " to tile " + tile);
                currentMarkers.add(marker);
            }
        }
        log.info("different tiles: " + tileMarkers.size());
    }

    /**
     * Taken from http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Java
     * 
     * @param lat
     * @param lon
     * @param zoom
     * 
     * @return the x and y value of the tile
     */
    private Tile getTileNumber(
        final double lat,
        final double lon,
        final int zoom)
    {

        int xtile = (int) Math.floor( ( lon + 180 ) / 360 * ( 1 << zoom ));
        int ytile =
            (int) Math.floor( ( 1 - Math.log(Math.tan(Math.toRadians(lat)) + 1
                / Math.cos(Math.toRadians(lat)))
                / Math.PI )
                / 2 * ( 1 << zoom ));
        return new Tile(zoom, xtile, ytile);
    }

    public List<Marker> getMarkers(int z, int x, int y)
    {
        List<Marker> result = tileMarkers.get(new Tile(z,x,y));
        if (result==null) {
            result = new LinkedList<Marker>();
        }
        return result;
    }
}
