package com.xuzz.fitutils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigUtil {
    private static final Logger logger = LoggerFactory
            .getLogger(ConfigUtil.class);
    /**
     * This is the parameter name of JVM. You can use it by specify the JVM
     * startup parameter like this,
     * -Dapp_conf=/data/server/apache-tomcat-7.55/bin/app_conf
     */
    private static final String APP_CONF_NAME = "app_conf";
    private static final String serverConfigFile = "serverConfig.properties";
    
    private static  PropertiesParser serverConfigParser=initPropParserer(serverConfigFile);
    
    private static  PropertiesParser initPropParserer(String fileName){
        return ConfigUtil.getProrpetiesParser(fileName);
    }
    public static void reload(){
      InputStream inputStream = ConfigUtil.class.getClassLoader()
          .getResourceAsStream(serverConfigFile);
      Properties properties = new Properties();
      try {
          properties.load(inputStream);
      } catch (IOException e) {
          logger.error(e.getMessage(), e);
      }
      serverConfigParser = new PropertiesParser(properties);
    }
    /**
     * 
     * @author yliu 
     * @date Oct 17, 2014 3:32:03 PM
     */
    public static PropertiesParser getServerConfig(){
        return serverConfigParser;
    }
    /**
     * Get the config file as Properties object
     * 
     * @author yliu
     * @date Sep 22, 2014 1:21:34 PM
     * @param fileName
     * @return
     */
    public static Properties getConfigs(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            logger.error("filename is invalid. Please fix it.");
            return null;
        }
        String confPath = getAppConfPath();
        InputStream inputStream = null;
        if (confPath == null) {
           /* logger.warn(
                    "Not {} parameter be specified,try to load resource from classpath",
                    APP_CONF_NAME);*/
            inputStream = ConfigUtil.class.getClassLoader()
                    .getResourceAsStream(fileName);
        } else {
            String filePath = confPath + fileName;
            try {
                inputStream = new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                logger.error("file not found:" + filePath);
                return null;
            }
        }

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }

        return properties;
    }

    /**
     * 通过文件名，或绝对路径 获取properties
     * @param fileName
     * @return
     */
    public static Properties getPropertiesByFile(String fileName) {
        InputStream inputStream = ConfigUtil.class.getClassLoader()
                .getResourceAsStream(fileName);

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.getMessage();
        }
        return properties;
    }

    public static PropertiesParser getProrpetiesParser(String fileName) {
        Properties prop = getConfigs(fileName);
        if (prop == null) {
            return null;
        }
        return new PropertiesParser(prop);
    }

    /**
     * get the root conf path
     * 
     * @author yliu
     * @date Sep 22, 2014 1:22:03 PM
     * @return the path
     */
    public static String getAppConfPath() {
        String confPath = System.getProperty(APP_CONF_NAME);
        if (confPath == null) {
            return null;
        }
        if (confPath.endsWith("/")) {
            return confPath;
        }
        return confPath + "/";
    }
    

}
