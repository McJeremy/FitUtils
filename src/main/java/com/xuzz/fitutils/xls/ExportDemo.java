package com.xuzz.fitutils.xls;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by xuzz on 2017/3/29.
 */
public class ExportDemo {

    public void doExport(HttpServletRequest request, HttpServletResponse response)
    {
        XlsData xlsData=new XlsData();
        xlsData.setXlsName("测试导出");

        SheetModel sheet1=new SheetModel();
        sheet1.setSheetName("取悦1");
        LinkedList<HeaderModel> header1=new LinkedList<HeaderModel>();
        HeaderModel header=new HeaderModel();
        header.setHeadName("整数");
        header1.add(header);
        header=new HeaderModel();
        header.setHeadName("小数");
        header1.add(header);
        header=new HeaderModel();
        header.setHeadName("货币");
        header1.add(header);
        header=new HeaderModel();
        header.setHeadName("百分比");
        header1.add(header);
        header=new HeaderModel();
        header.setHeadName("日期");
        header.setHeadWidth(50);
        header1.add(header);
        header=new HeaderModel();
        header.setHeadName("时间");
        header.setHeadWidth(50);
        header1.add(header);

        sheet1.getHeaders().put(0,header1);

        LinkedList<DataModel> datas=new LinkedList<DataModel>();
        DataModel data=new DataModel();
        data.setData(123);
        data.setDataFormat(DataFormat.INTEGER);
        datas.add(data);
        data=new DataModel();
        data.setData(45.78);
        data.setDataFormat(DataFormat.DECIMAL);
        datas.add(data);
        data=new DataModel();
        data.setData(1234000);
        data.setDataFormat(DataFormat.CURRENCY);
        datas.add(data);
        data=new DataModel();
        data.setData(0.17);
        data.setDataFormat(DataFormat.PERCENT);
        datas.add(data);
        data=new DataModel();
        data.setData(new Date());
        data.setDataFormat(DataFormat.DATE);
        datas.add(data);
        data=new DataModel();
        data.setData(new Date());
        data.setDataFormat(DataFormat.TIME);
        datas.add(data);
        sheet1.getDatas().add(datas);

        xlsData.getSheets().add(sheet1);

        SheetModel sheet2=new SheetModel();
        sheet2.setSheetName("取悦2");
        sheet2.getDatas().add(datas);
        sheet2.getHeaders().put(0,header1);
        sheet2.getHeaders().put(1,header1);

        MergeRegion mr=new MergeRegion();
        mr.setStartRow(0);  //起始行
        mr.setMergeRows(0);   //合并多少行，0开始
        mr.setFirstColumn(0);   //从那一列开始合并
        mr.setLastColumn(header1.size()-1); //合并到哪一列
        sheet2.getMergeRegions().add(mr);

        xlsData.getSheets().add(sheet2);

        XlsUtil util = new XlsUtil();
        try {

            util.exportXls(xlsData,request,response);
        }
        catch (IOException e)
        {}
    }
}
