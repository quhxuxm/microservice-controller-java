package com.igt.interactivegame.rgs.tool.msc.impl.action.build;

import com.igt.interactivegame.rgs.tool.msc.api.ComponentTaskResult;
import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.build.configuration.BasicBuildConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicBuildAction implements IComponentAction {
    private static final Logger logger = LoggerFactory.getLogger(BasicBuildAction.class);
    private IComponent owner;
    private BasicBuildConfigurationProperties configurationProperties;

    public BasicBuildAction(IComponent owner, BasicBuildConfigurationProperties configurationProperties) {
        this.owner = owner;
        this.configurationProperties = configurationProperties;
    }

    @Override
    public ActionName getName() {
        return ActionName.BUILD;
    }

    @Override
    public IComponent getOwner() {
        return this.owner;
    }

    @Override
    public ComponentTaskResult exec() {
        long startTime = System.nanoTime();
        try {
            List<String> command = new ArrayList<>();
            command.add(this.configurationProperties.getBuildExe());
            command.addAll(Arrays.asList(this.configurationProperties.getBuildCommand().split(" ")));
            String[] commandStrings = new String[command.size()];
            commandStrings = command.toArray(commandStrings);
            ProcessBuilder processBuilder = new ProcessBuilder(commandStrings);
            processBuilder.directory(new File(this.configurationProperties.getBaseDir()));
            Process process = processBuilder.start();
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String commandOutputLine = null;
            while ((commandOutputLine = stdout.readLine()) != null) {
                logger.info(commandOutputLine);
            }
            int returnCode = process.waitFor();
            if (returnCode != 0) {
                logger.info("Fail to execute command: [{}]", String.join(" ", commandStrings));
                long endTime = System.nanoTime();
                return new ComponentTaskResult(ComponentTaskResult.Status.FAIL, this.owner, endTime - startTime,
                        String.format("Fail to execute command: [%s]", String.join(" ", commandStrings)));
            }
        } catch (Exception e) {
            logger.error("Can not build component [{}] because of exception.", this.getOwner().getName(), e);
            long endTime = System.nanoTime();
            return new ComponentTaskResult(ComponentTaskResult.Status.FAIL, this.owner, endTime - startTime,
                    e.getMessage());
        }
        long endTime = System.nanoTime();
        logger.info("Success to build component [{}].", this.owner.getName());
        return new ComponentTaskResult(ComponentTaskResult.Status.SUCCESS, this.owner, endTime - startTime,
                String.format("Success to build component [%s]", this.owner.getName()));
    }
}
