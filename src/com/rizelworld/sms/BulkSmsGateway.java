package com.rizelworld.sms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.rizelworld.email.EmailSending;

public class BulkSmsGateway {
	static final Logger LOGGER = Logger.getLogger(BulkSmsGateway.class);
    public static String sendSms(String recipient, String message, String senderId, String link) {
        Object sResult = null;
        try {
            String line;
            String data = "user=" + URLEncoder.encode("bhanu889", "UTF-8");
            data = String.valueOf(data) + "&password=" + URLEncoder.encode("NamoAgain!1", "UTF-8");
            //data = String.valueOf(data) + "&message=" + URLEncoder.encode(new StringBuilder(String.valueOf(message)).append("\n").append(link).toString(), "UTF-8");
            data = String.valueOf(data) + "&message=" + URLEncoder.encode(message, "UTF-8");
            data = String.valueOf(data) + "&message=" + URLEncoder.encode(link ,"UTF-8");
            data = String.valueOf(data) + "&sender=" + URLEncoder.encode(senderId, "UTF-8");
            data = String.valueOf(data) + "&mobile=" + URLEncoder.encode(recipient, "UTF-8");
            data = String.valueOf(data) + "&type=" + URLEncoder.encode("3", "UTF-8");
            URL url = new URL("http://login.bulksmsgateway.in/sendmessage.php?" + data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String sResult1 = "";
            while ((line = rd.readLine()) != null) {
                sResult1 = String.valueOf(sResult1) + line + " ";
            }
            wr.close();
            rd.close();
            System.out.println(sResult1);
            LOGGER.info("SMS Sent Successfully: "+sResult1);
            return sResult1;
        }
        catch (Exception e) {
            System.out.println("Error SMS " + e);
            LOGGER.error("Error While Sending SMS: "+e);
            return "Error " + e;
        }
    }
}