package com.igt.interactivegame.rgs.tool.msc.impl.action.deploy;

import com.igt.interactivegame.rgs.tool.msc.api.ComponentTaskResult;
import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.deploy.configuration.BasicDeployConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractDeployBasicComponentAction implements IComponentAction {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDeployBasicComponentAction.class);
    private IComponent component;
    private BasicDeployConfigurationProperties configurationProperties;

    public AbstractDeployBasicComponentAction(IComponent component,
            BasicDeployConfigurationProperties configurationProperties) {
        this.component = component;
        this.configurationProperties = configurationProperties;
    }

    @Override
    public IComponent getOwner() {
        return this.component;
    }

    @Override
    public final ComponentTaskResult exec() {
        long start = System.nanoTime();
        try {
            logger.info("Begin to prepare basic directories.");
            this.prepareBasicDirs();
            logger.info("Success to prepare basic directories.");
        } catch (IOException e) {
            logger.error("Fail to prepare basic directories.", e);
        }
        try {
            logger.info("Begin to prepare basic components.");
            this.prepareBasicComponent();
            logger.info("Success to prepare basic components.");
        } catch (IOException e) {
            logger.error("Fail to prepare basic components.", e);
        }
        long end = System.nanoTime();
        return new ComponentTaskResult(ComponentTaskResult.Status.SUCCESS, this.component, end - start, "Success");
    }

    protected void prepareBasicDirs() throws IOException {
        if (!Files.isDirectory(Paths.get(this.configurationProperties.getTargetRootDir()))) {
            try {
                logger.info("Begin to create root target directory.");
                Files.createDirectories(Paths.get(this.configurationProperties.getTargetRootDir()));
                logger.info("Success to create root target directory.");
            } catch (IOException e) {
                logger.error("Fail to create root target directory.", e);
            }
        }
    }

    protected abstract void prepareBasicComponent() throws IOException;
}
