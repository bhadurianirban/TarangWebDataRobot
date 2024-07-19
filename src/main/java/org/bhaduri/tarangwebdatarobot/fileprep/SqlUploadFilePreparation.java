/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangwebdatarobot.fileprep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bhaduri.tarangwebdatarobot.config.ConfigValues;

/**
 *
 * @author bhaduri
 */
public class SqlUploadFilePreparation {

    Map<String, String> scripMap;
    String line;
    MinuteDataDTO minuteDataDTO;

    public void prepareFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ConfigValues.scripDataFileName));
            while ((line = br.readLine()) != null) {

                prepareScripLine(line);
                System.out.println(minuteDataDTO.getScripId()+" "+minuteDataDTO.getDayLastPrice()+" "+minuteDataDTO.getPreviousClosePrice()+" "+minuteDataDTO.getTradedVolume()+" "+minuteDataDTO.getCurrentTimeStamp());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SqlUploadFilePreparation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SqlUploadFilePreparation.class.getName()).log(Level.SEVERE, null, ex);
        }
        //prepareScripLine("UltraTech Cem. 11659 -175.00,556047,2024-07-17 16:01:51.995");

    }

    private void prepareScripLine(String scripLine) {
        minuteDataDTO = new MinuteDataDTO();

        String tradedVolumeStr;
        String currentTimeStamp;

        String[] commaSplitString = scripLine.split(",");

        String scripNameAndLtp = commaSplitString[0];
        tradedVolumeStr = commaSplitString[1];
        currentTimeStamp = commaSplitString[2];

        String scripId;
        String scripLtpStr;
        String scripDayChangeStr;
        
        readScripCSV();

        if (line.startsWith("NIF")) {
            String scripNameAndLtpNifty = scripNameAndLtp.split("\\(")[0].trim();
            scripId = "NIFTY 50";
            scripLtpStr = scripNameAndLtpNifty.split(" ")[1].trim();
            scripDayChangeStr = scripNameAndLtpNifty.split(" ")[2].trim();
        } else {
            String[] scripNameLtp = scripNameAndLtp.split("[0-9]");
            String scripName = scripNameLtp[0].trim();
            scripId = scripMap.get(scripName);
            Pattern p = Pattern.compile("[-\\d]+\\.[\\d]+|[-\\d]+");
            //Pattern p = Pattern.compile("[0-9-].*[.0-9].*");
            Matcher m = p.matcher(scripNameAndLtp);
            m.find();
            scripLtpStr = m.group(0);
            m.find();
            scripDayChangeStr = m.group(0);
        }
        if (scripDayChangeStr.equals("-")) {
            scripDayChangeStr = "0";
        }
        DecimalFormat df = new DecimalFormat("#.##");
        Double scripLtp = Double.valueOf(scripLtpStr);
        Double scripDayChange = Double.valueOf(scripDayChangeStr);
        Double scripPreviousClosePrice = scripLtp - scripDayChange;
        
        if (!tradedVolumeStr.matches("[0-9]")) {
            tradedVolumeStr = "0";
        }

        minuteDataDTO.setPreviousClosePrice(df.format(scripPreviousClosePrice));
        minuteDataDTO.setDayLastPrice(df.format(scripLtp));
        minuteDataDTO.setTradedVolume(tradedVolumeStr);
        minuteDataDTO.setCurrentTimeStamp(currentTimeStamp);

        
        
        minuteDataDTO.setScripId(scripId);
        
    }

    private void readScripCSV() {
        scripMap = new HashMap<>();
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(ConfigValues.scripMapFileName));
            while ((line = br.readLine()) != null) {
                String[] scripMapStr = line.split(",");
                scripMap.put(scripMapStr[0], scripMapStr[1]);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SqlUploadFilePreparation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SqlUploadFilePreparation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
