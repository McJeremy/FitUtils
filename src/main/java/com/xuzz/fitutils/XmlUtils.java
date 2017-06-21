package com.xuzz.fitutils;

import com.alibaba.fastjson.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Baih
 * @version V1.0
 * @Title: HxtxXmlUtils
 * @Package com.hxtx.core.utils
 * @Description: 慧享天下代码库中操作Xml的工具类
 * @date 2016/12/13 16:20
 */
public class XmlUtils
{
    private static DocumentBuilderFactory documentFactory;
    private static DocumentBuilder builder;
    static {
        try
        {
            documentFactory = DocumentBuilderFactory.newInstance();
            builder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 将String转换为Map
     * @param resultStrDoXml  xml字符串
     * @return OrderSearchResult
     */
    public static Map<String, String> strDoXmlToMap(String resultStrDoXml) throws IOException, SAXException
    {
        Map<String, String> dataMap = new TreeMap<String, String>();
        //通过解析器就可以得到代表整个内存中XML的Document对象
        InputStream inpu = new ByteArrayInputStream(resultStrDoXml.getBytes());
        Document document = builder.parse(inpu);
        NodeList nodeList = document.getChildNodes();
        Node node = nodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
            nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                String value = nodeList.item(i).getTextContent();
                String key = nodeList.item(i).getNodeName();
                dataMap.put(key, value);
            }
        }
        return dataMap;
    }

    /**
     * 将String转换为JSON
     * @param resultStrDoXml  xml字符串
     * @return OrderSearchResult
     */
    public static JSONObject strDoXmlToJSON(String resultStrDoXml) throws IOException, SAXException
    {
        if(resultStrDoXml == null || "".equals(resultStrDoXml)) return null;
        JSONObject jsonObject = new JSONObject();
        //通过解析器就可以得到代表整个内存中XML的Document对象
        InputStream inpu = new ByteArrayInputStream(resultStrDoXml.getBytes());
        Document document = builder.parse(inpu);
        NodeList nodeList = document.getChildNodes();
        Node node = nodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
            nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                String value = nodeList.item(i).getTextContent();
                String key = nodeList.item(i).getNodeName();
                jsonObject.put(key, value);
            }
        }
        return jsonObject;
    }
    /**
     * 将String转换为JSON
     * @param resultStrDoXml  xml字符串
     * @return OrderSearchResult
     */
    public static String strDoXmlToJSONString(String resultStrDoXml) throws IOException, SAXException
    {
        if(resultStrDoXml == null || "".equals(resultStrDoXml)) return null;
        JSONObject jsonObject = new JSONObject();
        //通过解析器就可以得到代表整个内存中XML的Document对象
        InputStream inpu = new ByteArrayInputStream(resultStrDoXml.getBytes());
        Document document = builder.parse(inpu);
        NodeList nodeList = document.getChildNodes();
        Node node = nodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
            nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                String value = nodeList.item(i).getTextContent();
                String key = nodeList.item(i).getNodeName();
                jsonObject.put(key, value);
            }
        }
        return jsonObject.toJSONString();
    }
}