package com.xuzz.fitutils.sms;

/**
 * 通用短信工具类
 */
public class CommonSmsUtils {

  private static int getRandom(){

//    return 2;
    return (int)(1+Math.random()*(2));
//    return 1;
//      return 2;
  }
  /**
   * 注册初始密码短信
   * @param phone
   * @param passwordPlain
   */
  public static String sendRegInitPasswd(String phone, String passwordPlain){

    String rescode = "";
    int random = getRandom();
    switch (random) {
      case 1:
        rescode = YunPianSmsUtils.sendRegInitPasswd(phone, passwordPlain);
        break;
      case 2:
        //rescode = HuaXinSmsUtils.sendRegInitPasswd(phone, passwordPlain);
        break;
    }
    return rescode;
  
  }
  /*
  
  *//**
   * 商户忘记密码
   * @param phone
   * @param code
   *//*
  public static String sendMchFindPasswordCode(String phone, String code) {
    String rescode = "";
    int random = getRandom();
    switch (random) {
      case 1:
        rescode = YunPianSmsUtils.sendMchFindPasswordCode(phone, code);
        break;
      case 2:
        rescode = HuaXinSmsUtils.sendMchFindPasswordCode(phone, code);
        break;
    }
    return rescode;
  }
  
  *//**
   * 用户忘记密码
   * @param phone
   * @param code
   *//*
  public static String sendUserFindPasswdCode(String phone, String code) {
    String rescode = "";
    int random = getRandom();
    switch (random) {
      case 1:
        rescode = YunPianSmsUtils.sendUserFindPasswdCode(phone, code);
        break;
      case 2:
        rescode = HuaXinSmsUtils.sendUserFindPasswdCode(phone, code);
        break;
    }
    return rescode;
  }
  

  *//**
   * 用户修改手机号
   * @param phone
   * @param code
   * @return
   *//*
  public static String sendUserModifyPhoneCode(String phone, String code) {
    String rescode = "";
    int random = getRandom();
    switch (random) {
      case 1:
        rescode = YunPianSmsUtils.sendUserModifyPhoneCode(phone, code);
        break;
      case 2:
        rescode = HuaXinSmsUtils.sendUserModifyPhoneCode(phone, code);
        break;
    }
    return rescode;
  }

  *//**
   * 发送用户添加银行卡短信
   * @param phone
   * @param code
   * @return
   *//*
  public static String sendUserAddBankcardCode(String phone, String code) {
    String rescode = "";
    int random = getRandom();
    switch (random) {
      case 1:
        rescode = YunPianSmsUtils.sendUserAddBankcardCode(phone, code);
        break;
      case 2:
        rescode = HuaXinSmsUtils.sendUserAddBankcardCode(phone, code);
        break;
    }
    return rescode;
  }

  *//**
   * 发送用户解绑银行卡短信
   * @param phone
   * @param code
   * @return
   *//*
  public static String sendUserCancelBankcardCode(String phone, String code) {
    String rescode = "";
    int random = getRandom();
    switch (random) {
      case 1:
        rescode = YunPianSmsUtils.sendUserCancelBankcardCode(phone, code);
        break;
      case 2:
        rescode = HuaXinSmsUtils.sendUserCancelBankcardCode(phone, code);
        break;
    }
    return rescode;
  
  }

  *//**
   * 用户注册
   * @param phone
   * @param code
   * @return
   *//*
  public static String sendRegisterCode(String phone, String code) {

    String rescode = "";
    int random = getRandom();
    switch (random) {
      case 1:
        rescode = YunPianSmsUtils.sendRegisterCode(phone, code);
        break;
      case 2:
        rescode = HuaXinSmsUtils.sendRegisterCode(phone, code);
        break;
    }
    return rescode;
  
  
  }

  *//**
   * 代理商找回密码
   * @param phone
   * @param code
   * @return
   *//*
  public static String sendProxyFindPasswordCode(String phone, String code) {
    String rescode = "";
    int random = getRandom();
    switch (random) {
      case 1:
        rescode = YunPianSmsUtils.sendProxyFindPasswordCode(phone, code);
        break;
      case 2:
        rescode = HuaXinSmsUtils.sendProxyFindPasswordCode(phone, code);
        break;
    }
    return rescode;
  
  }

  *//**
   * 代理修改手机号
   * @param phone
   * @param code
   * @return
   *//*
  public static String sendProxyModifyPhoneCode(String phone, String code) {
    String rescode = "";
    int random = getRandom();
    switch (random) {
      case 1:
        rescode = YunPianSmsUtils.sendProxyModifyPhoneCode(phone, code);
        break;
      case 2:
        rescode = HuaXinSmsUtils.sendProxyModifyPhoneCode(phone, code);
        break;
    }
    return rescode;
  
  }*/

}
