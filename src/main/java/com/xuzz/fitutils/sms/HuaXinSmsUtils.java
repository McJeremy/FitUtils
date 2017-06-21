package com.xuzz.fitutils.sms;

import com.xuzz.fitutils.ConfigUtil;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.Collection;

public class HuaXinSmsUtils {

//  private static String regCode = ConfigUtil.getServerConfig().getString("regCode"); // 华兴软通注册码，请在这里填写您从客服那得到的注册码
//  private static String regPasswod = ConfigUtil.getServerConfig().getString("regPasswod"); // 华兴软通注册码对应的密码，请在这里填写您从客服那得到的注册码

  /**
   * 
   * @param strUrl
   *            请求地址
   * @param param
   *            参数字符串
   * @return 返回字符串
   * @throws Exception
   */
  public static String requestGets(String strUrl, String param) {
      String returnStr = null; // 返回结果定义
      URL url = null;
      HttpsURLConnection httpsURLConnection = null;
      
      try {
          url = new URL(strUrl + "?" + param);
          httpsURLConnection = (HttpsURLConnection) url.openConnection();
          httpsURLConnection.setSSLSocketFactory(HuaXinSmsUtils.initSSLSocketFactory(2)); // 设置套接工厂
          httpsURLConnection.setRequestProperty("Accept-Charset", "utf-8");
          httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
          httpsURLConnection.setDoOutput(true);
          httpsURLConnection.setDoInput(true);
          httpsURLConnection.setRequestMethod("GET"); // get方式
          httpsURLConnection.setUseCaches(false); // 不用缓存
          httpsURLConnection.connect();

          BufferedReader reader = new BufferedReader(
                  new InputStreamReader(httpsURLConnection.getInputStream(), "utf-8"));
          StringBuffer buffer = new StringBuffer();
          String line = "";
          while ((line = reader.readLine()) != null) {
              buffer.append(line);
          }

          reader.close();
          returnStr = buffer.toString();
      } catch (Exception e) {
          e.printStackTrace();
          return null;
      } finally {
          if (httpsURLConnection != null) {
              httpsURLConnection.disconnect();
          }
      }
      return returnStr;
  }

  public static String requestPosts(String strUrl, String param) {
      String returnStr = null; // 返回结果定义
      URL url = null;
      HttpsURLConnection httpsURLConnection = null;
      
      try {
          url = new URL(strUrl);
          httpsURLConnection = (HttpsURLConnection) url.openConnection();
          httpsURLConnection.setSSLSocketFactory(HuaXinSmsUtils.initSSLSocketFactory(2)); // 设置套接工厂
          httpsURLConnection.setRequestProperty("Accept-Charset", "utf-8");
          httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
          httpsURLConnection.setDoOutput(true);
          httpsURLConnection.setDoInput(true);
          httpsURLConnection.setRequestMethod("POST"); // post方式
          httpsURLConnection.connect();

          //POST方法时使用
          byte[] byteParam = param.getBytes("UTF-8");     //得到utf-8编码的字节组
          DataOutputStream out = new DataOutputStream(httpsURLConnection.getOutputStream());
          out.write(byteParam);
          out.flush();
          out.close();
          BufferedReader reader = new BufferedReader(
                  new InputStreamReader(httpsURLConnection.getInputStream(), "utf-8"));
          StringBuffer buffer = new StringBuffer();
          String line = "";
          while ((line = reader.readLine()) != null) {
              buffer.append(line);
          }

          reader.close();
          returnStr = buffer.toString();
      } catch (Exception e) {
          e.printStackTrace();
          return null;
      } finally {
          if (httpsURLConnection != null) {
              httpsURLConnection.disconnect();
          }
      }
      return returnStr;
  }

    /**
     * HTTP的Post请求方式(推荐)
     * @param strUrl 访问地址
     * @param param 参数字符串
     * */
    public static String requestPost(String strUrl, String param) {
        System.out.println("HTTP的POST请求:" + strUrl + ";数据:" + param);
        String returnStr = null; // 返回结果定义
        URL url = null;
        HttpURLConnection httpURLConnection = null;

        try {
            url = new URL(strUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST"); // post方式
            httpURLConnection.connect();
            //System.out.println("ResponseCode:" + httpURLConnection.getResponseCode());
            //POST方法时使用
            byte[] byteParam = param.getBytes("UTF-8");
            DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
            out.write(byteParam);
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            reader.close();
            returnStr = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return returnStr;
    }

    /**
     * HTTP的Get请求方式
     * @param strUrl 访问地址
     * @param param 参数字符串
     * */
    public static String requestGet(String strUrl, String param) {
        System.out.println("HTTP的GET请求:" + strUrl + "?" + param);
        String returnStr = null; // 返回结果定义
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        try {
            url = new URL(strUrl + "?" + param);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET"); // get方式
            httpURLConnection.setUseCaches(false); // 不用缓存
            httpURLConnection.connect();
            //System.out.println("ResponseCode:" + httpURLConnection.getResponseCode());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            reader.close();
            returnStr = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return returnStr;
    }
  /**
   * 
   * @param method 两种方法生成,第一种方法载入信任库文件(推荐),第二种方法载入信任证书pem文件
   * @return
   */
  public static SSLSocketFactory initSSLSocketFactory(int method) throws Exception {
      SSLSocketFactory returnSSLSocketFactory = null;
      if(method == 1){
          SSLContext sslcontext = SSLContext.getInstance("TLS");
          KeyStore keyStore = KeyStore.getInstance("jks"); 
          String fileTruseStore = ConfigUtil.getServerConfig().getString("fileTruseStore");      //信任库文件，发布项目时打包到resource路径,可以用相对路径
          String pwTruseStore = ConfigUtil.getServerConfig().getString("pwTruseStore");      //默认密码，和库文件绑定的，不需要改，如果一定要改请和客服联系
          FileInputStream f_trustStore=new FileInputStream(fileTruseStore);
          keyStore.load(f_trustStore, pwTruseStore.toCharArray()); 
          String alg = TrustManagerFactory.getDefaultAlgorithm(); 
          TrustManagerFactory tmFact = TrustManagerFactory.getInstance(alg); 
          tmFact.init(keyStore);
          TrustManager[] tms = tmFact.getTrustManagers(); 
          sslcontext.init(null, tms, new java.security.SecureRandom());
          returnSSLSocketFactory = sslcontext.getSocketFactory();
      }else{
          SSLContext sslcontext = SSLContext.getInstance("TLS");
          KeyStore keyStore = KeyStore.getInstance("jks");
          keyStore.load(null, null);      //设置一个空密匙库
          String trustCertPath = ConfigUtil.getServerConfig().getString("trustCertPath");     //证书文件路径，发布项目时打包到resource路径
          FileInputStream trustCertStream = new FileInputStream(trustCertPath);
          CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
          Collection<? extends Certificate> certs = cerFactory.generateCertificates(trustCertStream);     //读取多份证书(如果文件流中存在的话)
          for (Certificate certificate : certs) {
              keyStore.setCertificateEntry("" + ((X509Certificate)certificate).getSubjectDN(), certificate);      //遍历集合把证书添加到keystore里，每个证书都必须用不同的唯一别名，否则会被覆盖
          }

          TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
          tmf.init(keyStore);
          TrustManager tms[] = tmf.getTrustManagers();
          sslcontext.init(null, tms, new java.security.SecureRandom());
          returnSSLSocketFactory = sslcontext.getSocketFactory();
      }
      
      return returnSSLSocketFactory;
  }

  /**
   * 发送短信的方法
   *            
   */
  public static String sendSms(String phone,String content) {
      String sendFlag = ConfigUtil.getServerConfig().getString("sendFlag");
      /*if(!"1".equals(sendFlag)){
        return "000000";
      }
      String url = "https://www.stongnet.com/sdkhttp/sendsms.aspx";
      String sourceAdd = null;        //子通道号（最长10位，可为空
      try {
          content = URLEncoder.encode(content,"UTF-8");
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
      
      String param = "reg=" + regCode + "&pwd=" + regPasswod + "&sourceadd=" + sourceAdd + "&phone=" + phone + "&content=" + content;
      
      String returnStr = null;
      returnStr = requestPost(url, param);
      System.out.println(returnStr);*/
      return "000000";
  }
  /**
   * 发送商户忘记密码短信
   * @param phone
   * @param code
   */
  public static String sendMchFindPasswordCode(String phone,String code){
    String content = "尊敬的您好，您此次的验证码是："+code+"，仅限找回密码使用";
//      String content = MessageFormat.format(HuaXinTemplate.MERCHANT_FIND_PASSWORD,code);
    return sendSms(phone, content);
  }

}
