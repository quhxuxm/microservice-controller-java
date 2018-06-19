package com.igt.interactivegame.rgs.tool.msc.impl.action.deploy;

import com.igt.interactivegame.rgs.tool.msc.api.ComponentTaskResult;
import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.deploy.configuration.BasicDeployConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipFile;

public class BasicDeployAction implements IComponentAction {
    private static final Logger logger = LoggerFactory.getLogger(BasicDeployAction.class);
    private IComponent component;
    private BasicDeployConfigurationProperties configurationProperties;

    public BasicDeployAction(IComponent component, BasicDeployConfigurationProperties configurationProperties) {
        this.component = component;
        this.configurationProperties = configurationProperties;
    }

    @Override
    public ActionName getName() {
        return ActionName.DEPLOY;
    }

    @Override
    public IComponent getOwner() {
        return this.component;
    }

    @Override
    public ComponentTaskResult exec() {
        this.prepareBasicDirs();
        try {
            logger.info("Begin to extract tomcat to target directory.");
            this.extractZip(Paths.get(this.configurationProperties.getOriginalTomcatZipFilePath()),
                    BasicDeployAction.this.getTomcatDeployPath());
            logger.info("Success to extract tomcat to target directory.");
        } catch (IOException e) {
            logger.error("Fail to extract tomcat to target directory.", e);
        }
        try {
            logger.info("Begin to extract apache to target directory.");
            this.extractZip(Paths.get(this.configurationProperties.getOriginalApacheZipFilePath()),
                    BasicDeployAction.this.getApacheDeployPath());
            logger.info("Success to extract apache to target directory.");
        } catch (IOException e) {
            logger.error("Fail to extract apache to target directory.", e);
        }
        ComponentTaskResult successResult = new ComponentTaskResult(ComponentTaskResult.Status.SUCCESS, this.component,
                0, "Success");
        return successResult;
    }

    private void prepareBasicDirs() {
        if (!Files.isDirectory(Paths.get(this.configurationProperties.getTargetRootDir()))) {
            try {
                logger.info("Begin to create root target directory.");
                Files.createDirectories(Paths.get(this.configurationProperties.getTargetRootDir()));
                logger.info("Success to create root target directory.");
            } catch (IOException e) {
                logger.error("Fail to create root target directory.", e);
            }
        }
        Path targetTomcatDirPath = this.getTomcatDeployPath();
        if (!Files.isDirectory(targetTomcatDirPath)) {
            try {
                logger.info("Begin to create tomcat target directory.");
                Files.createDirectories(targetTomcatDirPath);
                logger.info("Success to create tomcat target directory.");
            } catch (IOException e) {
                logger.error("Fail to create tomcat target directory.", e);
            }
        }
        Path targetApacheDirPath = this.getApacheDeployPath();
        if (!Files.isDirectory(targetApacheDirPath)) {
            try {
                logger.info("Begin to create apache target directory.");
                Files.createDirectories(targetApacheDirPath);
                logger.info("Success to create apache target directory.");
            } catch (IOException e) {
                logger.error("Fail to create apache target directory.", e);
            }
        }
    }

    private Path getTomcatDeployPath() {
        return Paths.get(this.configurationProperties.getTargetRootDir())
                .resolve(this.configurationProperties.getTargetTomcatRelativeDeployDir());
    }

    private Path getApacheDeployPath() {
        return Paths.get(this.configurationProperties.getTargetRootDir())
                .resolve(this.configurationProperties.getTargetApacheRelativeDeployDir());
    }

    private void extractZip(Path originalZipFilePath, Path targetBasePath) throws IOException {
        ZipFile zipFile = new ZipFile(originalZipFilePath.toFile());
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
