/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangwebdatarobot.exceptions;

import org.apache.logging.log4j.LogManager;
import org.bhaduri.tarangwebdatarobot.config.ConfigValues;
import org.bhaduri.tarangwebdatarobot.scrapdata.WebDataCollectTask;

/**
 *
 * @author bhaduri
 */
public class TarangUncaughatException implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LogManager.getLogger(TarangUncaughatException.class.getName()).fatal("Uncaught Exception", e);
        LogManager.getLogger(TarangUncaughatException.class.getName()).info("WebDataCollectTask Thread status: %s\n", t.getState());
        LogManager.getLogger(TarangUncaughatException.class.getName()).info("Closing Chromedriver");
        ConfigValues.driver.close();
        ConfigValues.driver.quit();
        LogManager.getLogger(TarangUncaughatException.class.getName()).info("Restarting WebDataCollectTask Thread");
        new Thread(new WebDataCollectTask()).start();

    }

}
