/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangwebdatarobot.fileprep;

/**
 *
 * @author bhaduri
 */
public class MinuteDataDTO {
    private String scripId;
    private String currentTimeStamp;
    private String dayLastPrice;
    private String dayHighPrice;
    private String dayLowPrice;
    private String previousClosePrice;
    private String tradedVolume;

    public MinuteDataDTO() {
        
    }

    public String getScripId() {
        return scripId;
    }

    public void setScripId(String scripId) {
        this.scripId = scripId;
    }

    public String getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(String currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public String getDayLastPrice() {
        return dayLastPrice;
    }

    public void setDayLastPrice(String dayLastPrice) {
        this.dayLastPrice = dayLastPrice;
    }

    public String getDayHighPrice() {
        return dayHighPrice;
    }

    public void setDayHighPrice(String dayHighPrice) {
        this.dayHighPrice = dayHighPrice;
    }

    public String getDayLowPrice() {
        return dayLowPrice;
    }

    public void setDayLowPrice(String dayLowPrice) {
        this.dayLowPrice = dayLowPrice;
    }

    public String getPreviousClosePrice() {
        return previousClosePrice;
    }

    public void setPreviousClosePrice(String previousClosePrice) {
        this.previousClosePrice = previousClosePrice;
    }

    public String getTradedVolume() {
        return tradedVolume;
    }

    public void setTradedVolume(String tradedVolume) {
        this.tradedVolume = tradedVolume;
    }

        
}
