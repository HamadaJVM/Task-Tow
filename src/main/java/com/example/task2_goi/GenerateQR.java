package com.example.task2_goi;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.scene.image.Image;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class GenerateQR {
    public static void generateQR() {
        String data = "https://github.com/HamadaJVM";
        String path = "C:/Users/Hmada_Z/IdeaProjects/Task2.goi/src/main/resources/QR_images.jpg";

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 100, 100);
            Path qrPath = FileSystems.getDefault().getPath(path);

            // Write the QR code image to the specified path
            MatrixToImageWriter.writeToPath(bitMatrix, "jpg", qrPath);
        } catch (Exception e) {
            System.out.println("Error generating QR code: " + e.getMessage());
        }
    }
    public static Image getQRCodeImage(){
        // Load the QR code image from the specified path
        Image image = new Image("C:\\Users\\Hmada_Z\\IdeaProjects\\Task2.goi\\src\\main\\resources\\QR_images.jpg");
        return image;
    }
}
