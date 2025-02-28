/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangwebdatarobot.scrapdata;

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
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
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
    //private ChromeDriver driver;
    private WebDriverWait wait;
    private String currentTimeStamp;
    private LocalTime currentTime;
    private List<String> scripValuesBuffer;

    public void collectData() {

        initChromeDriver();
        initOutFile();
        
        getCurrentTimeStamp();

        while (currentTime.isBefore(ConfigValues.endTime)) {
            sortByScripId();
            resetScripValuesBuffer();
            getNiftyValue();
            getScripDataByPage(1);
//            getScripDataByPage(2);
//            getScripDataByPage(3);
//            getScripDataByPage(4);
            writeScripValuesBufferToOutFile();
//            sleepForSeconds(10);
            getCurrentTimeStamp();

        }

        ConfigValues.driver.close();
        ConfigValues.driver.quit();
        closeOutFile();
    }

    private void initChromeDriver() {
        LogManager.getLogger(WebDataCollect.class.getName()).info("Chrome driver path " + ConfigValues.chromeDriverPath);
        System.setProperty("webdriver.chrome.driver", ConfigValues.chromeDriverPath);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        ConfigValues.driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(ConfigValues.driver, Duration.ofSeconds(10));
        ConfigValues.driver.get(ConfigValues.url);
    }

    private void initOutFile() {
        try {
            scripDataFile = new BufferedWriter(new FileWriter(ConfigValues.scripDataFileName, true));
            LogManager.getLogger(WebDataCollect.class.getName()).info("Output file "+ConfigValues.scripDataFileName+" initiated.");
        } catch (IOException ex) {
            LogManager.getLogger(WebDataCollect.class.getName()).fatal("Error writing temporary scrap file", ex);
        }
    }

    /**
     * sort by column header to list the company names alphabetically This is
     * because by default the list is sorted by LTP and that may be the reason
     * for the scrips listed in pages to change.
     */
    private void sortByScripId() {
        //LogManager.getLogger(WebDataCollect.class.getName()).info("Sorting Scrips ");
//        try {
//            List<WebElement> visibleTable = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='table']/div[1]/table/tbody/tr")));
//        } catch (NoSuchElementException ex) {
//            LogManager.getLogger(WebDataCollect.class.getName()).fatal("Sort button not found in page", ex);
//        }
        WebElement sortColumnHeader = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='thead']/tr/th[1]/div/div")));
        sortColumnHeader.click();
        //LogManager.getLogger(WebDataCollect.class.getName()).info("Sort scrips Clicked");
        sleepForSeconds(5);
//      -----removing this block due a new change in wesite on 2024/12/13        
        /*waiting this the next button on the page is clickable which means the full page is loaded*/
//        try {
//            WebElement activePaginationButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#slider-otherindices > div > div > div.slick-dots > div > div.custom-slick-dots.undefined > ul > li.slick-active > button")));
//            if (!activePaginationButton.getText().equals("1")) {
//                LogManager.getLogger(WebDataCollect.class.getName()).info("Scrips Sorted but page not active");
//            }
//        } catch (NoSuchElementException ex) {
//            LogManager.getLogger(WebDataCollect.class.getName()).fatal("Sort button not found in page", ex);
//        }
    }

    private void getCurrentTimeStamp() {
        Date timeStamp = new java.util.Date();
        currentTime = LocalTime.now();
        currentTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(timeStamp);
    }

    private void resetScripValuesBuffer() {
        scripValuesBuffer = new ArrayList<>();
    }

    private void getNiftyValue() {
        /*get the nifty 50 price.*/
        WebElement niftyValueElement = ConfigValues.driver.findElement(By.xpath("//*[@id='overview']/div[2]"));
        //List<String> niftyValues = niftyValueElement.stream().map(x -> x.getText().replaceAll("\\n", " ").replaceAll("\\₹|\\,","" )).collect(Collectors.toList());   
        String niftyValue = niftyValueElement.getText().replaceAll("\\n", " ").replaceAll("\\₹|\\,", "");
        String niftyValueTxt = "NIFTY50 " + niftyValue + ",0," + currentTimeStamp;
        //LogManager.getLogger(WebDataCollect.class.getName()).info(niftyValueTxt);
        scripValuesBuffer.add(niftyValueTxt);
    }

    /**
     * Get scrip ltp values for a pagecount in pagination
     *
     * @param pageCount page number in pagination
     */
    private void getScripDataByPage(int pageCount) {
//        int paginationButtonCounter = pageCount + 1;
        /*click the active pagination button on the page*/
//        try {
//            WebElement paginationFirstButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='MarketTable_tablePagination__MhVJq']/ul/li[" + paginationButtonCounter + "]")));
//            paginationFirstButton.click();
//        } catch (NoSuchElementException ex) {
//            LogManager.getLogger(WebDataCollect.class.getName()).fatal("Active pagination button not found in page", ex);
//        }

        /*after clicking the pageCount page wait for pageCount number button to become active */
//        try {
//            while (true) {
//                WebElement activePaginationButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.MarketTable_pageList__82ska.MarketTable_active__2sHad")));
//                if (activePaginationButton.getText().equals(Integer.toString(pageCount))) {
//                    //System.out.println("Scrip List page " + activePaginationButton.getText() + " activated.");
//                    break;
//                }
//            }
//        } catch (NoSuchElementException ex) {
//            LogManager.getLogger(WebDataCollect.class.getName()).fatal("Pagination button is not active", ex);
//        }

        /*now read the scrip values*/
        sleepForSeconds(15);
        List<WebElement> scripLtpValueElements = ConfigValues.driver.findElements(By.xpath("//*[@id='table']/div[1]/table/tbody/tr"));
        List<WebElement> scripVolValueElements = ConfigValues.driver.findElements(By.xpath("//*[@id='scrollableTable']/table/tbody/tr[*]/td[12]"));
        if (scripLtpValueElements.size() == scripVolValueElements.size()) {
            List<String> scripLtpValues = scripLtpValueElements.stream().map(x -> x.getText().replaceAll("[\\t\\n\\r]+", " ").replaceAll(",", "")).collect(Collectors.toList());
            List<String> scripVolValues = scripVolValueElements.stream().map(x -> x.getText().replaceAll(",", "")).collect(Collectors.toList());
            for (int i = 0; i < scripLtpValues.size(); i++) {
                scripValuesBuffer.add(scripLtpValues.get(i) + "," + scripVolValues.get(i) + "," + currentTimeStamp);
            }
        }
    }

    private void writeScripValuesBufferToOutFile() {
        try {
            for (int i = 0; i < scripValuesBuffer.size(); i++) {
//                System.out.println("writing.."+scripValuesBuffer.get(i));
                scripDataFile.write(scripValuesBuffer.get(i));
                scripDataFile.newLine();

            }
            scripDataFile.flush();

        } catch (IOException ex) {
            LogManager.getLogger(WebDataCollect.class.getName()).fatal("Error writing temporary scrap file", ex);
        }
        LogManager.getLogger(WebDataCollect.class.getName()).info("Scrapped for time " + currentTimeStamp);
    }

    private void sleepForSeconds(int sleepSeconds) {
        try {
            Thread.sleep(Duration.ofSeconds(sleepSeconds));
        } catch (InterruptedException ex) {
            LogManager.getLogger(WebDataCollect.class.getName()).fatal("Cannot sleep - Thread Sleep", ex);
        }
    }

    private void closeOutFile() {
        try {
            scripDataFile.close();
        } catch (IOException ex) {
            LogManager.getLogger(WebDataCollect.class.getName()).fatal("Error closing temporary scrap file", ex);
        }
    }

}
