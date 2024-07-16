/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangwebdatarobot;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bhaduri
 */
public class WebDataRobot {

    private String configFileName;

    public WebDataRobot(String configFileName) {
        this.configFileName = configFileName;
    }

    public WebDataCollect readConfigFile() {
        TarangConfig tarangConfig = null;
        File configJson = new File(configFileName);
        try {
            tarangConfig = new ObjectMapper().readValue(configJson, TarangConfig.class);

        } catch (IOException ex) {
            Logger.getLogger(WebDataRobotMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        WebDataCollect wdc = new WebDataCollect(tarangConfig);
        return wdc;
    }

}
