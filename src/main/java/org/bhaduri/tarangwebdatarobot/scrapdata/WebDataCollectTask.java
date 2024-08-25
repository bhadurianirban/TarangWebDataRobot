/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangwebdatarobot.scrapdata;

/**
 *
 * @author bhaduri
 */
public class WebDataCollectTask implements Runnable{

    @Override
    public void run() {
        new WebDataCollect().collectData();
    }
    
}
