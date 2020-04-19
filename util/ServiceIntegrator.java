

package com.cardex.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.stereotype.Service;

@Service("caller")
public class ServiceIntegrator {
    
   public  String callService(String endPointUrl, String requestXml) throws Exception {
        
       Truster.ignoreCertificates();

        System.setProperty("javax.net.debug", "true");

        HttpURLConnection httpUrlConn = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;

        String sOutputXML = null;


        try {
            URL url = new URL(endPointUrl);

            httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setAllowUserInteraction(true);
            httpUrlConn.setRequestMethod("POST");
            httpUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //httpUrlConn.setRequestProperty("Content-Type", "text/xml");
            //httpUrlConn.setReadTimeout(timeOutInMs);

            System.out.println("set some request properties and timeout");

            outputStream = httpUrlConn.getOutputStream();
            outputStream.write(requestXml.getBytes());

            int returnCode = httpUrlConn.getResponseCode();

            InputStream connectionIn = null;
            if (returnCode == 200)
                connectionIn = httpUrlConn.getInputStream();
            else {
                connectionIn = httpUrlConn.getErrorStream();
            }

            bufferedReader = new BufferedReader(new InputStreamReader(connectionIn));

            StringBuffer stringBuffer = new StringBuffer();
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuffer = stringBuffer.append(inputLine);
            }

            sOutputXML = stringBuffer.toString();

        } catch (Exception ep) {
            //System.out.println("Error occurred in processing request -- "+ ep.toString());

            sOutputXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                    + "<Exception>\n"
                    + "<Info>Exception while executing integration call : " + ep.toString() + "</Info>"
                    + "</Exception>";
        } finally {
            try {
                if (httpUrlConn != null) {
                    httpUrlConn.disconnect();

                }
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();

                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                    System.out.println("bufferedReader  outputstream");
                }
            } catch (IOException localIOException) {
                localIOException.printStackTrace();
            }
        }

        return sOutputXML;

    } 
}
