package com.xuzz.fitutils;

import java.text.DecimalFormat;

public class MapUtil {

	private final static Double PI = 3.14159265358979323; // 圆周率
    private final static Double  R = 6371229.0; // 地球的半径

    /**
     * 返回A-B地图坐标点距离
     * @param longitudeA A点经度 
     * @param latitudeA A点纬度 
     * @param longitudeB B点经度 
     * @param latitudeB B点纬度 
     * @return	千米单位
     */
    public static Double getDistance(Double longitudeA, Double latitudeA, Double longitudeB,Double latitudeB) {
    	if (longitudeA == null || latitudeA == null || longitudeB == null || latitudeB == null) {
			return null;
		}
    	
    	Double x, y, distance;
        x = (longitudeB - longitudeA) * PI * R
                * Math.cos(((latitudeA + latitudeB) / 2) * PI / 180) / 180;
        y = (latitudeB - latitudeA) * PI * R / 180;
        distance = Math.hypot(x, y);
        
        //格式化距离，保留2为小数
        DecimalFormat df = new DecimalFormat("#.00");  
        distance = Double.parseDouble(df.format(distance/1000));
        
        return distance;
    }
    
    /**
     * 返回A-B地图坐标点距离
     * @param longitudeA A点经度 
     * @param latitudeA A点纬度 
     * @param longitudeB B点经度 
     * @param latitudeB B点纬度 
     * @return	米单位
     */
    public static Double getDistance_m(Double longitudeA, Double latitudeA, Double longitudeB,Double latitudeB) {
    	if (longitudeA == null || latitudeA == null || longitudeB == null || latitudeB == null) {
			return null;
		}
    	
    	Double x, y, distance;
        x = (longitudeB - longitudeA) * PI * R
                * Math.cos(((latitudeA + latitudeB) / 2) * PI / 180) / 180;
        y = (latitudeB - latitudeA) * PI * R / 180;
        distance = Math.hypot(x, y);
        
        //格式化距离，保留2为小数
        DecimalFormat df = new DecimalFormat("#.00");  
        distance = Double.parseDouble(df.format(distance));
        
        return distance;
    }
    
    public static void main(String[] args) {
    	System.out.println(getDistance(104.074565, 30.663528, 106.486654, 29.615467));
	}
}
