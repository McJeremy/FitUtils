package com.rongqiangu.weather.util;

import com.rongqiangu.weather.common.util.Md5Util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * API接口安全验证
 * @author  xuzz
 */
public class APISignUtil {

    private static final String _salt="34903k*7";

    /**
     * 验证加密字符串
     * @param request
     * @return 是否验证通过
     * @throws UnsupportedEncodingException
     */
    public static boolean validateSign(HttpServletRequest request) throws UnsupportedEncodingException
    {

        /*
        正确的查询参数，必须包含sign、secret、timestamp
        其中，sign为客户端（调用端）发送的加密后的结果，附加在查询参数中
        secret 为平台分配的调用标识码
        timestamp为系统时间戳
         */
        String sign = request.getParameter("sign");
        String secret = request.getParameter("secret");
        String timestamp = request.getParameter("timestamp");
        if (StringUtil.isBlank(sign)) {
            //抛出无sign参数异常
            return false;
        }
        if (StringUtil.isBlank(timestamp)) {
            //抛出无timestamp参数异常
            //后续可以加时间有效期验证，比如只能30秒有效。
            //注意，这个时间有效期和微信等accesstoken有效期不是一个概念。
            return false;
        }
        if (StringUtil.isBlank(secret)) {
            //抛出无secret参数异常
            return false;
        }
        if (secret.equals(_salt)) {
            //抛出secret参数不对异常
            //后续如果需要，可以扩展为每个app、每个用户有单独的secret
            return false;
        }
        boolean result = false;
        Enumeration<?> pNames = request.getParameterNames();
        Map<String, Object> params = new HashMap<String, Object>();
        while (pNames.hasMoreElements()) {
            String pName = (String) pNames.nextElement();
            if ("sign".equals(pName)) continue;
            Object pValue = request.getParameter(pName);
            params.put(pName, pValue);
        }

        System.out.print("sign："+sign);
        System.out.print("机密参数集："+createSignature(params, true));
        if (sign.equals(createSignature(params, true))) {
            result = true;
        }
        return result;
    }

    /**
     * 创建加密参数结果
     * @param params 参数
     * @param encode 编码
     * @return  加密后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String createSignature(Map<String, Object> params, boolean encode) throws UnsupportedEncodingException {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer stringBuffer = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                stringBuffer.append("&");
            }
            stringBuffer.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
            }
            if (encode) {
                stringBuffer.append(URLEncoder.encode(valueString, "UTF-8"));
            } else {
                stringBuffer.append(valueString);
            }
        }

        return Md5Util.string2Md5(stringBuffer.toString());
    }
}
