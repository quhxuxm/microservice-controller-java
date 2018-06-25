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

public class BasicDeployTomcatAction extends AbstractDeployBasicComponentAction {
    private static final Logger logger = LoggerFactory.getLogger(BasicDeployTomcatAction.class);
    private IComponent component;
    private BasicDeployConfigurationProperties configurationProperties;

    public BasicDeployTomcatAction(IComponent component, BasicDeployConfigurationProperties configurationProperties) {
        super(component, configurationProperties);
    }

    @Override
    public ActionName getName() {
        return ActionName.DEPLOY_APACHE;
    }

    protected void prepareBasicDirs() throws IOException {
        super.prepareBasicDirs();
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
    }

    @Override
    protected void prepareBasicComponent() throws IOException {
        logger.info("Begin to extract tomcat to target directory.");
        ZipUtil.INSTANCE.extractAll(Paths.get(this.configurationProperties.getOriginalTomcatZipFilePath()),
                BasicDeployTomcatAction.this.getTomcatDeployPath());
        logger.info("Success to extract tomcat to target directory.");
    }

    private Path getTomcatDeployPath() {
        return Paths.get(this.configurationProperties.getTargetRootDir())
                .resolve(this.configurationProperties.getTargetTomcatRelativeDeployDir());
    }
}
