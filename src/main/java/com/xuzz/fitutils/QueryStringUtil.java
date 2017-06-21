package com.xuzz.fitutils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by win10 on 2017/6/21.
 */
public class QueryStringUtil {
    /**
     * Map转换为查询字符串
     *
     * @param params
     * @return
     */
    public static String mapToQueryStringAndTrim(Map<String, Object> params)
    {
        StringBuffer sb = new StringBuffer();
        Iterator it = params.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString().trim();
            if (entry.getValue() == null)
            {
//				sb.append(key+"=&");
                continue;
            }
            String value = entry.getValue().toString().trim();
            if (!"".equals(value) && value != null)
            {
                sb.append(key + "=" + value + "&");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 将查询字符串转换为map
     *
     * @param queryString
     * @return
     */
    public static Map<String, String> queryStringToMap(String queryString)
    {
        Map<String, String> queryMap = new HashMap<String, String>();
        if (StringUtil.isEmpty(queryString))
        {
            return queryMap;
        }
        try
        {
            String[] pairs = queryString.split("&");
            for (String pair : pairs)
            {
                int idx = pair.indexOf("=");
                queryMap.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e)
        {
            System.out.println("queryStringToMap encoding exception:" + e.getMessage());
        }

        return queryMap;
    }
}
