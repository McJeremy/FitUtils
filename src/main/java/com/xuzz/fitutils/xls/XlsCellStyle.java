package com.xuzz.fitutils.xls;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuzz on 2017/3/28.
 */
public class XlsCellStyle {

    private final String STYLE_DEFAULT="STYLE_DEFAULT";
    private final String STYLE_DEFAULT_ALIGN_CENTER="STYLE_DEFAULT_ALIGNCENTER";
    private final String STYLE_DEFAULT_ALIGN_LEFT="STYLE_DEFAULT_ALIGNCLEFT";
    private final String STYLE_BOLD_ALIGN_CENTER="STYLE_BOLDALIGNCENTER";

    private final String FONT_DEFAULT="FONT_DEFAULT";
    private final String FONT_BOLD="FONT_BOLD";
    private final String FONT_BOLD_RED="FONT_BOLD_RED";

    private final Map<String,Object> exStyles=new HashMap<String,Object>();

    private HSSFWorkbook _workbook;
    public XlsCellStyle(HSSFWorkbook workbook)
    {
        _workbook=workbook;
    }
    private Font getDefaultFont()
    {
        if(!exStyles.containsKey(FONT_DEFAULT))
        {
           Font defaultFont = _workbook.createFont();
           defaultFont.setFontName("宋体");
           exStyles.put(FONT_DEFAULT,defaultFont);
        }
        return (Font)exStyles.get(FONT_DEFAULT);
    }

    private Font getBoldFont()
    {
        if(!exStyles.containsKey(FONT_BOLD))
        {
            Font font = _workbook.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short)15);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            exStyles.put(FONT_BOLD,font);
        }
        return (Font)exStyles.get(FONT_BOLD);
    }

    private Font getBoldRedFont()
    {
        if(!exStyles.containsKey(FONT_BOLD_RED))
        {
            Font  boldRedFont = _workbook.createFont();
            boldRedFont.setFontName("宋体");
            boldRedFont.setFontHeightInPoints((short)20);
            boldRedFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            boldRedFont.setColor(HSSFColor.RED.index);
            exStyles.put(FONT_BOLD_RED,boldRedFont);
        }
        return (Font)exStyles.get(FONT_BOLD_RED);
    }

    public HSSFCellStyle getBoldAlignCenterStyle(DataFormat dataFormat)
    {
        String key = STYLE_BOLD_ALIGN_CENTER+dataFormat.toString();
        if(!exStyles.containsKey(key))
        {
            HSSFCellStyle style = _workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFont(getBoldFont());

            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);

            setDataFormat(style,dataFormat);

            exStyles.put(key,style);
        }
        return (HSSFCellStyle) exStyles.get(key);
    }

    public HSSFCellStyle getDefaultStyle(DataFormat dataFormat)
    {
        String key = STYLE_DEFAULT+dataFormat.toString();
        if(!exStyles.containsKey(key))
        {
            HSSFCellStyle style = _workbook.createCellStyle();
            style.setFont(getDefaultFont());
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);

            style.setVerticalAlignment(VerticalAlignment.CENTER);

            setDataFormat(style,dataFormat);

            exStyles.put(key,style);
        }
        return (HSSFCellStyle) exStyles.get(key);
    }

    public HSSFCellStyle getDefaultStyleAlignCenter(DataFormat dataFormat)
    {
        String key = STYLE_DEFAULT_ALIGN_CENTER+dataFormat.toString();

        if(!exStyles.containsKey(key))
        {
            HSSFCellStyle style =getDefaultStyle(dataFormat);
            style.setAlignment(HorizontalAlignment.CENTER);

            exStyles.put(key,style);
        }
        return (HSSFCellStyle) exStyles.get(key);
    }

    public HSSFCellStyle getDefaultStyleAlignLeft(DataFormat dataFormat)
    {
        String key = STYLE_DEFAULT_ALIGN_LEFT+dataFormat.toString();

        if(!exStyles.containsKey(key))
        {
            HSSFCellStyle style =getDefaultStyle(dataFormat);
            style.setAlignment(HorizontalAlignment.LEFT);

            exStyles.put(key,style);
        }
        return (HSSFCellStyle) exStyles.get(key);
    }

    private void setDataFormat(HSSFCellStyle style,DataFormat dataFormat)
    {
        HSSFDataFormat format= _workbook.createDataFormat();

        if(dataFormat==DataFormat.DATE) {
            style.setDataFormat(format.getFormat("yyyy-m-dd"));
        }
        else if(dataFormat==DataFormat.TIME)
        {
            style.setDataFormat(format.getFormat("yyyy-m-dd HH:mm"));
        }
        else if(dataFormat==DataFormat.INTEGER)
        {
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        }
        else if(dataFormat==DataFormat.DECIMAL)
        {

            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        }
        else if(dataFormat==DataFormat.PERCENT)
        {
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
        }
        else if(dataFormat==DataFormat.CHINESE_UPPER)
        {
            style.setDataFormat(format.getFormat("[DbNum2][$-804]0"));
        }
        else if(dataFormat==DataFormat.CURRENCY)
        {
            style.setDataFormat(format.getFormat("¥#,##0"));
        }
    }
}
