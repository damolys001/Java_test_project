/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cardex.util;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Administrator
 */
public class Truster {
    
 public static void ignoreCertificates() throws Exception {
            TrustManager localTrustManager = new TrustManager();

            TrustManager[] arrayOfTrustManager = {localTrustManager};

            HostnameVerifier local1 = new HostnameVerifier() {
                public boolean verify(String paramString, SSLSession paramSSLSession) {
                    return true;
                }
            };
            SSLContext localSSLContext = SSLContext.getInstance("SSL");
            localSSLContext.init(null, arrayOfTrustManager, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(localSSLContext
                    .getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(local1);
        }

        static class TrustManager implements X509TrustManager {


            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                    throws CertificateException {


            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                    throws CertificateException {


            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                // TODO Auto-generated method stub
                return null;
            }
        }
    }