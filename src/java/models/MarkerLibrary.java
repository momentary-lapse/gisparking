/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;

public class MarkerLibrary {

    ArrayList<Marker> markers;

    public void addMarker(Marker m) {

        markers.add(m);

    }

    public void fillArray() {
        markers.clear();
        addMarker(new Marker(55.1667, 61.4000, "Hi!"));
        addMarker(new Marker(55.1504, 61.4000, "Yay!"));
        addMarker(new Marker(55.1777, 61.4300, ":)"));
        for (int i = 0; i < markers.size(); i++) {
            markers.get(i).setId(i + 1);
        }
    }

    public ArrayList<Marker> getMarkers() {
        return markers;
    }

    public void deleteById(long id) {

        if (markers.isEmpty()) {
            return;
        }

        int k = 0;
        for (int i = 0; i < markers.size(); i++) {
            if (markers.get(i).getId() == id) {
                k = i;
                break;
            }
        }
        if (markers.get(k).getId() == id) {
            markers.remove(k);
        }

    }

    public MarkerLibrary() {

        markers = new ArrayList<Marker>();
        fillArray();

    }
}
