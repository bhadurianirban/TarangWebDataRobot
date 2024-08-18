/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangwebdatarobot.exceptions;

import org.apache.logging.log4j.LogManager;

/**
 *
 * @author bhaduri
 */
public class TarangUncaughatException implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LogManager.getLogger(TarangUncaughatException.class.getName()).fatal("Uncaught Exception",e);
    }
    
}
