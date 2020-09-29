package com.ivs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesFile {


    static String projectPath = System.getProperty("user.dir");
    static File file = new File(projectPath+"/src/main/resources/config.properties");
    static Properties properties;


    public PropertiesFile() throws IOException {
        properties = new Properties();
        InputStream input = new FileInputStream(file);
        properties.load(input);
    }

    public String getapplicationPIN(){return properties.getProperty("applicationPIN");}
    public String getLocalServerAddress(){return properties.getProperty("localServerAddress");}
    public String getRemoteServerAddress(){return properties.getProperty("remoteServerAddress");}
    public String getEnvironment(){return properties.getProperty("environment");}


}



