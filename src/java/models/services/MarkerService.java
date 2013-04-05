/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import models.ImageTransformer;
import models.entities.Marker;
import models.interfaces.IMarkerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MarkerService {
    
    public class GeoComparator implements Comparator<Marker> {
        
        private double lat, lng;

        public GeoComparator(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }        
        
        @Override
        public int compare(Marker a, Marker b) {
            double dista = Math.abs(lat - a.getLat()) + Math.abs(lng - a.getLng());
            double distb = Math.abs(lat - b.getLat()) + Math.abs(lng - b.getLng());
            return Double.compare(dista, distb);
        }
        
        
    }
    
    String imagePath = "../images/";
    final int imageWidth = 300;
    final int imageHeigth = 200;
    final long oldTime = 3 * 60 * 60 * 1000;

    private IMarkerDAO markerDAO;

    @Autowired
    public void setMarkerDAO(IMarkerDAO markerDAO) {
        this.markerDAO = markerDAO;
    }
    
    public void add(Marker marker) {
        markerDAO.add(marker);
    }
    
    public void update(Marker marker) {
        markerDAO.update(marker);
    }
    
    public void delete(Marker marker) {
        File f = new File(imagePath + marker.getUrl());
        f.delete();
        markerDAO.delete(marker);
    }
    
    public void deleteById(Long id) {
        
        Marker m = markerDAO.getById(id);
        delete(m);
        
    }
    
    public Marker getById(Long id) {
        return markerDAO.getById(id);
    }
    
    
    public List<Marker> getEnabledList() {
        return markerDAO.getEnabledList();
    }
    
    public List<Marker> getFullList() {
        return markerDAO.getFullList();
    }
    
    public List<Marker> getSortedList(double lat, double lng) {
        
        GeoComparator comp = new GeoComparator(lat, lng);
        List<Marker> list = markerDAO.getEnabledList();
        Collections.sort(list, comp);
        return list;
        
    }
    
    public int getPhoneRequestsNumberByTime(Long pid, Timestamp timestamp) {
        
        return markerDAO.getPhoneRequestsNumberByTime(pid, timestamp);
        
    }
    
    public List<Marker> getOldMarkers(Timestamp timestamp) {
        
        return markerDAO.getOldMarkers(timestamp);
        
    }
    
    public byte[] getImageById(long id) throws IOException {
        
        Marker m = markerDAO.getById(id);
        BufferedImage image = ImageIO.read(new File(imagePath + m.getUrl()));
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", bao);
        
        return bao.toByteArray();
        
    }
    
    public void writeImage(MultipartFile file, String filename) throws IOException {
        
        
        String path = imagePath + filename;

        File imageFile = new File(path);
        
        imageFile.getParentFile().createNewFile();
        BufferedImage image = ImageIO.read(file.getInputStream());
        image = ImageTransformer.scaleByHeigth(image, imageHeigth);
        imageFile.createNewFile();
        ImageIO.write(image, "jpg", imageFile);
        
    }
    
    @Scheduled(fixedRate=oldTime)
    public void deleteOld() {
        
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime() - oldTime);
        List<Marker> list = markerDAO.getOldMarkers(ts);
        for (int i = 0; i < list.size(); i++) {
            delete(list.get(i));
        }
        
        
    }
}
