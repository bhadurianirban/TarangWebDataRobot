/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangwebdatarobot.scrapdata;

import org.bhaduri.tarangwebdatarobot.config.TarangConfig;
import org.bhaduri.tarangwebdatarobot.config.ConfigValues;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author bhaduri
 */
public class WebDataCollect {

   
    
    
    
    private BufferedWriter scripDataFile;
    private ChromeDriver driver;
    private WebDriverWait wait;
    private String currentTimeStamp;
    private LocalTime currentTime;
    private List<String> scripValuesBuffer;

    public WebDataCollect() {
        
    }
    
    
    public void collectData() {

        initChromeDriver();
        initOutFile();
        sortByScripId();
        getCurrentTimeStamp();
        
        while (currentTime.isBefore(ConfigValues.endTime)) {
            resetScripValuesBuffer();
            getNiftyValue();
            getScripDataByPage(1);
            getScripDataByPage(2);
            getScripDataByPage(3);
            getScripDataByPage(4);
            writeScripValuesBufferToOutFile();
            sleepForSeconds(5);
            getCurrentTimeStamp();

        }
        
        driver.close();
        closeOutFile();
    }

    private void resetScripValuesBuffer() {
        scripValuesBuffer = new ArrayList<>();
    }
    

    
    private void initOutFile() {
        try {
            scripDataFile = new BufferedWriter(new FileWriter(ConfigValues.scripDataFileName, true));
        } catch (IOException ex) {
            Logger.getLogger(WebDataCollect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void closeOutFile() {
        try {
            scripDataFile.close();
        } catch (IOException ex) {
            Logger.getLogger(WebDataCollect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void writeScripValuesBufferToOutFile() {
        try {
            for (int i = 0; i < scripValuesBuffer.size(); i++) {
                //System.out.println("writing.."+scripValuesBuffer.get(i));
                scripDataFile.write(scripValuesBuffer.get(i));
                scripDataFile.newLine();
            }
            scripDataFile.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(WebDataCollect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getCurrentTimeStamp() {
        Date timeStamp = new java.util.Date();
        currentTime = LocalTime.now();
        currentTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(timeStamp);
    }

    private void initChromeDriver() {
        System.setProperty("webdriver.chrome.driver", ConfigValues.chromeDriverPath);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(ConfigValues.url);
    }

    /**
     * sort by column header to list the company names alphabetically This is
     * because by default the list is sorted by LTP and that may be the reason
     * for the scrips listed in pages to change.
     */
    private void sortByScripId() {
        WebElement sortColumnHeader = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='thead']/tr/th[1]/div/div")));
        sortColumnHeader.click();

        /*waiting this the next button on the page is clickable which means the full page is loaded*/
        try {
            WebElement activePaginationButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.MarketTable_pageList__82ska.MarketTable_active__2sHad")));
            if (activePaginationButton.getText().equals("1")) {
                System.out.println("Scrips sorted");
            }
        } catch (NoSuchElementException ex) {
            Logger.getLogger(WebDataCollect.class.getName()).log(Level.INFO, null, ex);
        }
    }

    private void getNiftyValue() {
        /*get the nifty 50 price.*/
        WebElement niftyValueElement = driver.findElement(By.xpath("//*[@id='overview']/div[2]"));
        //List<String> niftyValues = niftyValueElement.stream().map(x -> x.getText().replaceAll("\\n", " ").replaceAll("\\₹|\\,","" )).collect(Collectors.toList());
        String niftyValue = niftyValueElement.getText().replaceAll("\\n", " ").replaceAll("\\₹|\\,", "");
        scripValuesBuffer.add("NIFTY50 " + niftyValue + ",0," + currentTimeStamp);
    }

    /**
     * Get scrip ltp values for a pagecount in pagination
     *
     * @param pageCount page number in pagination
     */
    private void getScripDataByPage(int pageCount) {
        int paginationButtonCounter = pageCount + 1;
        /*click the active pagination button on the page*/
        try {
            WebElement paginationFirstButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='MarketTable_tablePagination__MhVJq']/ul/li[" + paginationButtonCounter + "]")));
            paginationFirstButton.click();
        } catch (NoSuchElementException ex) {
            Logger.getLogger(WebDataCollect.class.getName()).log(Level.INFO, null, ex);
        }

        /*after clicking the pageCount page wait for pageCount number button to become active */
        try {
            while (true) {
                WebElement activePaginationButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.MarketTable_pageList__82ska.MarketTable_active__2sHad")));
                if (activePaginationButton.getText().equals(Integer.toString(pageCount))) {
                    //System.out.println("Scrip List page " + activePaginationButton.getText() + " activated.");
                    break;
                }
            }
        } catch (NoSuchElementException ex) {
            Logger.getLogger(WebDataCollect.class.getName()).log(Level.INFO, null, ex);
        }

        /*now read the scrip values*/
        while (true) {
            List<WebElement> scripLtpValueElements = driver.findElements(By.xpath("//*[@id='table']/div[1]/table/tbody/tr"));
            List<WebElement> scripVolValueElements = driver.findElements(By.xpath("//*[@id='scrollableTable']/table/tbody/tr[*]/td[8]"));
            if (scripLtpValueElements.size() == scripVolValueElements.size()) {
                List<String> scripLtpValues = scripLtpValueElements.stream().map(x -> x.getText().replaceAll("[\\t\\n\\r]+", " ").replaceAll(",", "")).collect(Collectors.toList());
                List<String> scripVolValues = scripVolValueElements.stream().map(x -> x.getText().replaceAll(",", "")).collect(Collectors.toList());
                for (int i = 0; i < scripLtpValues.size(); i++) {
                    scripValuesBuffer.add(scripLtpValues.get(i) + "," + scripVolValues.get(i) + "," + currentTimeStamp);
                }
                break;
            }
        }

    }

    private void sleepForSeconds(int sleepSeconds) {
        try {
            Thread.sleep(Duration.ofSeconds(sleepSeconds));
        } catch (InterruptedException ex) {
            Logger.getLogger(WebDataCollect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
