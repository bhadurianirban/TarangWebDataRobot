/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.bhaduri.tarangwebdatarobot.scrapdata;

import org.apache.logging.log4j.LogManager;
import org.bhaduri.tarangwebdatarobot.config.ConfigValues;
import org.bhaduri.tarangwebdatarobot.exceptions.TarangUncaughatException;
import org.bhaduri.tarangwebdatarobot.fileprep.SqlUploadFilePreparation;

/**
 *
 * @author bhaduri
 */
public class WebDataRobotMain {
    public static int RUN_MODE=0;
    public static final int WEB_SCRAP = 1;
    public static final int SQL_FILE_PREP = 2;
    
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new TarangUncaughatException());
        
        //System.out.println("Hello");
        //LogManager.getLogger(WebDataRobotMain.class.getName()).info("Something is right");
        decideFunctionMode(args);
        if (RUN_MODE == WEB_SCRAP) {
            new WebDataRobotConfig(ConfigValues.configFileName).readConfigFile();
            new WebDataCollect().collectData();
        }
        if (RUN_MODE == SQL_FILE_PREP) {
            new WebDataRobotConfig(ConfigValues.configFileName).readConfigFile();
            new SqlUploadFilePreparation().prepareFile();
        }
    }

    private static void decideFunctionMode(String[] args) {
        switch (args.length) {
            case 0 -> {
                System.out.println("Need to provide config file name and run mode");
                System.out.println("Syntax: --runmode[WS|SQLUP] configfilename");
            }
            case 1 -> {
                System.out.println("Need to provide config file name and run mode");
                System.out.println("Syntax: --runmode[WS|SQLUP] configfilename");
            }
            case 2 -> {
                String runMode = args[0];
                if (!runMode.startsWith("--")) {
                    System.out.println("Run mode should be --WS or --SQLUP");
                    System.out.println("Syntax: --runmode[WS|SQLUP] configfilename");
                } else if (runMode.equals("--WS")) {
                    RUN_MODE = WEB_SCRAP;
                    ConfigValues.configFileName = args[1];
                } else if (runMode.equals("--SQLUP")) {
                    RUN_MODE = SQL_FILE_PREP;
                    ConfigValues.configFileName = args[1];
                } else {
                    System.out.println("Syntax: --runmode[WS|SQLUP] configfilename");
                }   
            }
            default -> {
                System.out.println("Need to provide config file name and run mode");
                System.out.println("Syntax: --runmode[WS|SQLUP] configfilename");
            }
        }

    }
}
