/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.bhaduri.tarangwebdatarobot.scrapdata;

import org.bhaduri.tarangwebdatarobot.fileprep.SqlUploadFilePreparation;

/**
 *
 * @author bhaduri
 */
public class WebDataRobotMain {

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        //WebDataCollect wdc = new WebDataCollect();
        //wdc.collectData();
        String configFileName;
        if (args.length != 0) {
            configFileName = args[0];
        } else {
            configFileName = "/home/bhaduri/Documents/TarangDataCollectConfig.json";
        }
        new WebDataRobotConfig(configFileName).readConfigFile();
        //new WebDataCollect().collectData();
        new SqlUploadFilePreparation().prepareFile();
    }
}
