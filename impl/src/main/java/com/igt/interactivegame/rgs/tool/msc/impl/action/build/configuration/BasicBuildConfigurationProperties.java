package com.igt.interactivegame.rgs.tool.msc.impl.action.build.configuration;

public class BasicBuildConfigurationProperties {
    private String baseDir;
    private String buildCommand;
    private String buildExe;

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getBuildCommand() {
        return buildCommand;
    }

    public void setBuildCommand(String buildCommand) {
        this.buildCommand = buildCommand;
    }

    public String getBuildExe() {
        return buildExe;
    }

    public void setBuildExe(String buildExe) {
        this.buildExe = buildExe;
    }
}
