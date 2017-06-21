package com.xuzz.fitutils.xls;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by xuzz on 2017/3/28.
 */
public class SheetModel {
    /**
     * sheet名称
     */
    private String sheetName;
    /**
     * 表头高度
     */
    private short headerHeight=22;
    /**
     * 数据行高度
     */
    private short rowHeight=20;

    /**
     * 默认行高
     */
    private short defaultRowHeight=20;
    /**
     * 默认列宽
     */
    private short defaultColumnWidth=20;

    /**
     * 需要合并的区域
     */
    private List<MergeRegion> mergeRegions = new LinkedList<MergeRegion>();
    /**
     * 表头区域(可多行)
     */
    private Map<Integer,LinkedList<HeaderModel>> headers = new HashMap<Integer,LinkedList<HeaderModel>>();

    private LinkedList<LinkedList<DataModel>> datas = new LinkedList<LinkedList<DataModel>>();

    private int columnLength;

    public int getColumnLength() {
        return this.getHeaders().get(this.getHeaders().size()-1).size();
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public short getHeaderHeight() {
        return headerHeight;
    }

    public void setHeaderHeight(short headerHeight) {
        this.headerHeight = headerHeight;
    }

    public short getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(short rowHeight) {
        this.rowHeight = rowHeight;
    }

    public List<MergeRegion> getMergeRegions() {
        return mergeRegions;
    }

    public void setMergeRegions(List<MergeRegion> mergeRegions) {
        this.mergeRegions = mergeRegions;
    }

    public Map<Integer, LinkedList<HeaderModel>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<Integer, LinkedList<HeaderModel>> headers) {
        this.headers = headers;
    }

    public LinkedList<LinkedList<DataModel>> getDatas() {
        return datas;
    }

    public void setDatas(LinkedList<LinkedList<DataModel>> datas) {
        this.datas = datas;
    }

    public short getDefaultRowHeight() {
        return defaultRowHeight;
    }

    public void setDefaultRowHeight(short defaultRowHeight) {
        this.defaultRowHeight = defaultRowHeight;
    }

    public short getDefaultColumnWidth() {
        return defaultColumnWidth;
    }

    public void setDefaultColumnWidth(short defaultColumnWidth) {
        this.defaultColumnWidth = defaultColumnWidth;
    }

    public void setColumnLength(int columnLength) {
        this.columnLength = columnLength;
    }
}
