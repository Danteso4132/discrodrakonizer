package com.danteso.discrodrakonizer.db;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageUtils {

    public static void saveImageFromByteArray(byte[] imageData, String outputPath, String imageFormat) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            FileOutputStream fos = new FileOutputStream(outputPath);

            // Write the image data to the file
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            fos.close();
            bis.close();

            // Read the saved image file from the byte array
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));

            // Save the BufferedImage to a file
            ImageIO.write(image, imageFormat, new File(outputPath));

            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}