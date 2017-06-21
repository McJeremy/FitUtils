package com.xuzz.fitutils.sms;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 云片网短信接口
 */
public class YunPianSmsUtils {

  /**
   * 服务http地址
   */
  private static String BASE_URI = "http://yunpian.com";
  /**
   * 服务版本号
   */
  private static String VERSION = "v1";

  /**
   * 模板发送接口的http地址
   */
  private static String URI_TPL_SEND_SMS = BASE_URI + "/" + VERSION + "/sms/tpl_send.json";
  /**
   * apikey.
   */
  private static final String APIKEY = "";

  /**
   * 通过模板发送短信
   * 
   * @param tpl_id 模板id
   * @param tpl_value 模板变量值
   * @param mobile 接受的手机号
   * @throws IOException
   */
  public static String tplSendSms(String tpl_id, String tpl_value, String mobile) {
    String resStr = "";

      HttpPost httppost = new HttpPost(URI_TPL_SEND_SMS);
      HttpClient httpclient = new DefaultHttpClient();
      // 创建参数队列
      List<NameValuePair> formparams = new ArrayList<>();
      UrlEncodedFormEntity uefEntity;
      try {
        formparams.add(new BasicNameValuePair("apikey", APIKEY));
        formparams.add(new BasicNameValuePair("tpl_id", tpl_id));
        formparams.add(new BasicNameValuePair("tpl_value", tpl_value));
        formparams.add(new BasicNameValuePair("mobile", mobile));
  
        uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
        httppost.setEntity(uefEntity);
        HttpResponse response;
        response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          resStr = EntityUtils.toString(entity, "UTF-8");
        }
      } catch (ClientProtocolException e) {
        e.printStackTrace();
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        httpclient.getConnectionManager().shutdown();
      }
    return resStr;
  }
  
  /**
   * 注册初始密码短信
   * @param phone
   * @param passwordPlain
   */
  public static String  sendRegInitPasswd(String phone, String passwordPlain){
    String resCode = "";
    StringBuffer buf = new StringBuffer();
    buf.append("#password#=" + passwordPlain);
//    String res = tplSendSms(YunPianTemplate.REGISTER_INITIAL_PASSWORD, buf.toString(), phone);
    String res="";
    JSONObject json = JSONObject.parseObject(res);
    if(json.getIntValue("code")==0){
      resCode = "000000";
    }
    return resCode;
  }
}
