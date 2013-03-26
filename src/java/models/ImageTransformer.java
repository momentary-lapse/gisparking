/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageTransformer {

    public static BufferedImage scaleByWidth(BufferedImage original, int newWidth) {
        
        int w = original.getWidth();
        int h = original.getHeight();
        int newHeight = (int) (((double) newWidth / w) * h);

        BufferedImage image = new BufferedImage(newWidth, newHeight, original.getType());
        
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(original, 0, 0, newWidth, newHeight, null);
        g2.dispose();
        
        return image;

    }
}
