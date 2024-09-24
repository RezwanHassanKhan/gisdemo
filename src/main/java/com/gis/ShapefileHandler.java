package com.gis;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShapefileHandler {

    public static void main(String[] args) {
        try {
            // Path to your shapefile
            String shapefilePath = "/Users/raiseonemore/Downloads/ne_10m_admin_0_boundary_lines_land/ne_10m_admin_0_boundary_lines_land.shp";

            // Create a map to store the shapefile parameters
            Map<String, Object> map = new HashMap<>();
            map.put("url", new File(shapefilePath).toURI().toURL());

            // Load the shapefile using GeoTools DataStore
            DataStore dataStore = DataStoreFinder.getDataStore(map);
            if (dataStore == null) {
                System.out.println("Could not load shapefile.");
                return;
            }

            // Retrieve the feature source from the shapefile
            String typeName = dataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);

            // Create a map content and add the shapefile layer to it
            MapContent mapContent = new MapContent();
            mapContent.setTitle("Shapefile Visualization");

            // Create a simple style to display the features
            Style style = SLD.createSimpleStyle(featureSource.getSchema());

            // Create a feature layer using the feature source and style
            FeatureLayer layer = new FeatureLayer(featureSource, style);
            mapContent.addLayer(layer);

            // Create a map frame and display it
            JMapFrame mapFrame = new JMapFrame(mapContent);
            // mapFrame.setPreferredSize(new Dimension(800, 600)); // Comment out if Dimension is not available
            mapFrame.enableStatusBar(true); // Shows the status bar
            mapFrame.enableToolBar(true);   // Enables toolbars (zoom, pan, etc.)
            // mapFrame.pack();                // Comment out if Dimension is not available
            mapFrame.setVisible(true);      // Make the frame visible

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}