package com.ivs.testrail;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

public class ProxyUtils {
    public static Proxy of(String ip, int port, String username, String password) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        Authenticator authenticator = new Authenticator() {

            public PasswordAuthentication getPasswordAuthentication() {
                return (new PasswordAuthentication(username, password.toCharArray()));
            }
        };
        Authenticator.setDefault(authenticator);

        // Need to disable ssl handshake when using proxy as there is an error when fetching pages with https
        SSLUtilities.trustAllHostnames();
        SSLUtilities.trustAllHttpsCertificates();

        return proxy;
    }
}
