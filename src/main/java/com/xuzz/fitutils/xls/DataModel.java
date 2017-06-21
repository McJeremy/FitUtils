package com.xuzz.fitutils.xls;

/**
 * Created by xuzz on 2017/3/28.
 */
public class DataModel {
    private Object data;
    private String remark;
    private DataFormat dataFormat;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }
}
