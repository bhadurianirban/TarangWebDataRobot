/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangwebdatarobot.scrapdata;

import org.bhaduri.tarangwebdatarobot.config.TarangConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bhaduri.tarangwebdatarobot.config.ConfigValues;

/**
 *
 * @author bhaduri
 */
public class WebDataRobotConfig {

    private final String configFileName;

    public WebDataRobotConfig(String configFileName) {
        this.configFileName = configFileName;
    }

    public void readConfigFile() {
        TarangConfig tarangConfig = null;
        File configJson = new File(configFileName);
        try {
            tarangConfig = new ObjectMapper().readValue(configJson, TarangConfig.class);

        } catch (IOException ex) {
            Logger.getLogger(WebDataRobotMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConfigValues.url = tarangConfig.getUrl();
        ConfigValues.chromeDriverPath = tarangConfig.getChromeDriverPath();
        ConfigValues.endTime = LocalTime.of(tarangConfig.getEndTime().getHour(), tarangConfig.getEndTime().getMinute(), tarangConfig.getEndTime().getSecond());
        ConfigValues.scripDataFileName = tarangConfig.getScripDataFileName();
        ConfigValues.scripMapFileName = tarangConfig.getScripMapFileName();
        ConfigValues.sqlLoadDataFileName = tarangConfig.getSqlLoadDataFileName();
        
    }

}
