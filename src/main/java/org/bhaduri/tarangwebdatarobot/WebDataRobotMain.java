/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.bhaduri.tarangwebdatarobot;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bhaduri
 */
public class WebDataRobotMain {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        //WebDataCollect wdc = new WebDataCollect();
        //wdc.collectData();
        String configFileName;
        if (args.length != 0) {
            configFileName = args[0];
        } else {
            configFileName = "/home/bhaduri/Documents/TarangDataCollectConfig.json";
        }
        new WebDataRobot(configFileName).readConfigFile().collectData();
    }
}
