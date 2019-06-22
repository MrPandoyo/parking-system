package com.parkingsystem.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

@Service
public class ImageService {

    private final List<String> FILE_EXTENSION = Arrays.asList("png", "jpg", "jpeg");

    public static final String ORIGINALPREFIX = "ori-";

    public static final String PROFILE = "profile";
    public static final String KTP = "ktp";
    public static final String QR_CODE = "qr_code";

    @Value("${app.folder.upload}")
    public String uploadDir;

    public File moveFile(MultipartFile multipartFile, String tpe) {
        try {
            String folder = uploadDir + File.separator + tpe + File.separator;
            File folderImages = new File(folder);
            if (!folderImages.exists()) Files.createDirectories(folderImages.toPath());

            String random = UUID.randomUUID().toString().substring(0, 7);

            int len = multipartFile.getOriginalFilename().length();
            String fileName = multipartFile.getOriginalFilename();
            if(len > 20)
                fileName = fileName.substring(len - 10);

            int occurance = StringUtils.countOccurrencesOf(fileName, ".");

            if(occurance > 1)
                for(int i = 0; i < occurance - 1; i++)
                    fileName = fileName.replaceFirst("\\.","-");

            fileName = fileName.replace(" ", "-");
            fileName = fileName.replace("_", "-");
            fileName = fileName.replaceAll("[^\\w\\-\\.]", "");
            String name = random + "-" + fileName;

            File originalFile = new File(folder + ORIGINALPREFIX + name);
            Files.copy(multipartFile.getInputStream(), originalFile.toPath());

            return originalFile;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Path load(String folder, String filename){
        return new File(uploadDir).toPath().resolve(folder+"/"+filename);
    }

    public Resource loadAsResource(String filename, String tpe) throws MalformedURLException {
        Path filePath = load(tpe, filename);
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() || resource.isReadable())
            return resource;
        else
            throw new MalformedURLException("Could not read file: " + filename);
    }

    public String uploadImage(MultipartFile image, String type){
        if (!image.getOriginalFilename().isEmpty()) {
            String extention = tokenizer(image.getOriginalFilename(), ".");
            if (FILE_EXTENSION.contains(extention.toLowerCase())) {
                File file = moveFile(image, type);
                return  file.getName();
            }
        }
        return "";
    }

    public String generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        String fileName= UUID.randomUUID().toString().replaceAll("-","").substring(0,15);
        String folder = uploadDir + File.separator + QR_CODE + File.separator ;
        File folderImages = new File(folder);
        if (!folderImages.exists()) Files.createDirectories(folderImages.toPath());

        folder = folder +fileName+".png";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(folder);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return fileName+".png";
    }

    public String tokenizer(String originalFilename, String token) {
        StringTokenizer tokenizer = new StringTokenizer(originalFilename, token);
        String result = "";
        while (tokenizer.hasMoreTokens()) {
            result = tokenizer.nextToken();
        }
        return result;
    }

}
