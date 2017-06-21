package com.xuzz.fitutils.xls;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xuzz on 2017/3/28.
 */
public class XlsData {
    private String xlsName;
    private String savePath;

    private List<SheetModel> sheets = new LinkedList<SheetModel>();


    public String getXlsName() {
        return xlsName;
    }

    public void setXlsName(String xlsName) {
        this.xlsName = xlsName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public List<SheetModel> getSheets() {
        return sheets;
    }

    public void setSheets(List<SheetModel> sheets) {
        this.sheets = sheets;
    }
}
