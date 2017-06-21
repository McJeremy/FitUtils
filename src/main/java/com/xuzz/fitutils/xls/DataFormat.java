package com.xuzz.fitutils.xls;

/**
 * Created by xuzz on 2017/3/28.
 */
public enum DataFormat {
    STRING(0,"字符串"),
    INTEGER(1,"整数"),
    DECIMAL(2,"小数"),
    DATE(3,"日期"),
    TIME(4,"时间"),
    CURRENCY(5,"货币"),
    PERCENT(6,"百分比"),
    CHINESE_UPPER(7,"中文大写");

    private int type;
    private String name;

    DataFormat(int type,String name)
    {
        this.type=type;
        this.name=name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
