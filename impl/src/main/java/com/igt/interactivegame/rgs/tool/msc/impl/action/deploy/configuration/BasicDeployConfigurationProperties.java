package com.igt.interactivegame.rgs.tool.msc.impl.action.deploy.configuration;

public class BasicDeployConfigurationProperties {
    private String targetRootDir;
    private String originalTomcatZipFilePath;
    private String originalApacheZipFilePath;
    private String targetTomcatRelativeDeployDir;
    private String targetApacheRelativeDeployDir;

    public String getOriginalTomcatZipFilePath() {
        return originalTomcatZipFilePath;
    }

    public void setOriginalTomcatZipFilePath(String originalTomcatZipFilePath) {
        this.originalTomcatZipFilePath = originalTomcatZipFilePath;
    }

    public String getOriginalApacheZipFilePath() {
        return originalApacheZipFilePath;
    }

    public void setOriginalApacheZipFilePath(String originalApacheZipFilePath) {
        this.originalApacheZipFilePath = originalApacheZipFilePath;
    }

    public String getTargetTomcatRelativeDeployDir() {
        return targetTomcatRelativeDeployDir;
    }

    public void setTargetTomcatRelativeDeployDir(String targetTomcatRelativeDeployDir) {
        this.targetTomcatRelativeDeployDir = targetTomcatRelativeDeployDir;
    }

    public String getTargetApacheRelativeDeployDir() {
        return targetApacheRelativeDeployDir;
    }

    public void setTargetApacheRelativeDeployDir(String targetApacheRelativeDeployDir) {
        this.targetApacheRelativeDeployDir = targetApacheRelativeDeployDir;
    }

    public String getTargetRootDir() {
        return targetRootDir;
    }

    public void setTargetRootDir(String targetRootDir) {
        this.targetRootDir = targetRootDir;
    }
}
