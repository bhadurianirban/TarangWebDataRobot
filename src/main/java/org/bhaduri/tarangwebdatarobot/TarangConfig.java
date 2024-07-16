/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangwebdatarobot;

/**
 *
 * @author bhaduri
 */
public class TarangConfig {
    private String url;
    private String chromeDriverPath;
    private String scripDataFileName;
    private EndTime endTime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getChromeDriverPath() {
        return chromeDriverPath;
    }

    public void setChromeDriverPath(String chromeDriverPath) {
        this.chromeDriverPath = chromeDriverPath;
    }

    public String getScripDataFileName() {
        return scripDataFileName;
    }

    public void setScripDataFileName(String scripDataFileName) {
        this.scripDataFileName = scripDataFileName;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public void setEndTime(EndTime endTime) {
        this.endTime = endTime;
    }
    
}
