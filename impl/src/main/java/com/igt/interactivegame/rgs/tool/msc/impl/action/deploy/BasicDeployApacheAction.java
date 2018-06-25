package com.igt.interactivegame.rgs.tool.msc.impl.action.deploy;

import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.impl.action.deploy.configuration.BasicDeployConfigurationProperties;
import com.igt.interactivegame.rgs.tool.msc.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BasicDeployApacheAction extends AbstractDeployBasicComponentAction {
    private static final Logger logger = LoggerFactory.getLogger(BasicDeployApacheAction.class);
    private IComponent component;
    private BasicDeployConfigurationProperties configurationProperties;

    public BasicDeployApacheAction(IComponent component, BasicDeployConfigurationProperties configurationProperties) {
        super(component, configurationProperties);
    }

    @Override
    public ActionName getName() {
        return ActionName.DEPLOY_APACHE;
    }

    protected void prepareBasicDirs() throws IOException {
        super.prepareBasicDirs();
        Path targetApacheDirPath = this.getApacheDeployPath();
        if (!Files.isDirectory(targetApacheDirPath)) {
            logger.info("Begin to create apache target directory.");
            Files.createDirectories(targetApacheDirPath);
            logger.info("Success to create apache target directory.");
        }
    }

    @Override
    protected void prepareBasicComponent() throws IOException {
        logger.info("Begin to extract apache to target directory.");
        ZipUtil.INSTANCE.extractAll(Paths.get(this.configurationProperties.getOriginalApacheZipFilePath()),
                BasicDeployApacheAction.this.getApacheDeployPath());
        logger.info("Success to extract apache to target directory.");
    }

    private Path getApacheDeployPath() {
        return Paths.get(this.configurationProperties.getTargetRootDir())
                .resolve(this.configurationProperties.getTargetApacheRelativeDeployDir());
    }
}
