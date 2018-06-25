package com.igt.interactivegame.rgs.tool.msc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipFile;

public class ZipUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);
    public static final ZipUtil INSTANCE = new ZipUtil();

    private ZipUtil() {
    }

    public void extractAll(Path zipFilePath, Path targetBasePath) throws IOException {
        ZipFile zipFile = new ZipFile(zipFilePath.toFile());
        zipFile.stream().forEach(entry -> {
            Path targetPath = targetBasePath.resolve(entry.getName());
            if (entry.isDirectory()) {
                logger.info("Creating Directory:" + targetBasePath.resolve(entry.getName()));
                try {
                    if (!Files.isDirectory(targetPath)) {
                        Files.createDirectories(targetPath);
                    }
                } catch (IOException e) {
                    logger.error("Fail to create directory.", e);
                }
            } else {
                try {
                    InputStream is = zipFile.getInputStream(entry);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    if (Files.exists(targetPath)) {
                        return;
                    }
                    Files.createFile(targetPath);
                    FileOutputStream fileOutput = new FileOutputStream(targetPath.toFile());
                    while (bis.available() > 0) {
                        fileOutput.write(bis.read());
                    }
                    fileOutput.close();
                    logger.info("Written :" + entry.getName());
                } catch (IOException e) {
                    logger.error("Fail to create file.", e);
                }
            }
        });
    }
}
