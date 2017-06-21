package com.xuzz.fitutils.xls;

import com.xuzz.fitutils.BigMoney;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 生成xls文档
 *
 * @author XUZZ
 * @version 1.0
 */
public class XlsUtil {
    private HSSFWorkbook workBook;
    private XlsCellStyle cellStyle;
    private XlsData xlsData;

    public XlsData getXlsData() {
        return xlsData;
    }

    public void setXlsData(XlsData xlsData) {
        this.xlsData = xlsData;
    }

    public HSSFWorkbook getWorkBook() {
        return workBook;
    }

    public void setWorkBook(HSSFWorkbook workBook) {
        this.workBook = workBook;
    }

    public XlsCellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(XlsCellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    private HSSFSheet createSheet(SheetModel sheetModel) {
        HSSFSheet sheet = workBook.createSheet(sheetModel.getSheetName());

        sheet.setDefaultColumnWidth(256 * sheetModel.getDefaultColumnWidth());
        sheet.setDefaultRowHeight((short) (20 * sheetModel.getDefaultRowHeight()));

        buildHeader(sheet, sheetModel);
        buildDataRow(sheet, sheetModel);
        addMergeRegion(sheet, sheetModel);

        return sheet;
    }

    private void buildHeader(Sheet sheet, SheetModel sheetModel) {
        if (sheetModel.getHeaders().size() <= 0) {
            return;
        }
        int headerRowIndex = 0;

        for (Map.Entry<Integer, LinkedList<HeaderModel>> headerModels : sheetModel.getHeaders().entrySet()) {
            int headerColumnIndex = 0;
            Row headerRow = createRow(sheet, headerRowIndex++);
            headerRow.setHeight((short) (20 * sheetModel.getHeaderHeight()));

            for (HeaderModel headerModel : headerModels.getValue()) {

                Cell headerCell = headerRow.createCell(headerColumnIndex);
                headerCell.setCellValue(headerModel.getHeadName());
                headerCell.setCellStyle(cellStyle.getBoldAlignCenterStyle(DataFormat.STRING));

                if (headerModel.getHeadWidth() > 0) {
                    sheet.setColumnWidth(headerColumnIndex, 256 * headerModel.getHeadWidth());
                } else {
                    sheet.setColumnWidth(headerColumnIndex, 256 * sheetModel.getDefaultColumnWidth());
                }

                headerColumnIndex++;
            }
        }
    }

    private void buildDataRow(Sheet sheet, SheetModel sheetModel) {
        int startRowIndex = sheetModel.getHeaders().size();

        for (LinkedList<DataModel> datas : sheetModel.getDatas()) {
            Row dataRow = createRow(sheet, startRowIndex++);
            if (!(sheetModel.getRowHeight() <= 0)) {
                dataRow.setHeight((short) (20 * sheetModel.getRowHeight()));
            }

            int startCellIndex = 0;
            for (DataModel data : datas) {
                Cell dataCell = dataRow.createCell(startCellIndex++);
                if (data.getDataFormat() == DataFormat.STRING) {
                    dataCell.setCellValue(data.getData().toString());
                    dataCell.setCellStyle(cellStyle.getDefaultStyle(data.getDataFormat()));
                } else if (data.getDataFormat() == DataFormat.DATE || data.getDataFormat() == DataFormat.TIME) {
                    dataCell.setCellValue((Date) data.getData());
                    dataCell.setCellStyle(cellStyle.getDefaultStyleAlignCenter(data.getDataFormat()));
                } else if (data.getDataFormat() == DataFormat.DECIMAL || data.getDataFormat() == DataFormat.INTEGER || data.getDataFormat() == DataFormat.PERCENT || data.getDataFormat() == DataFormat.CURRENCY || data.getDataFormat() == DataFormat.CHINESE_UPPER) {
                    dataCell.setCellValue(BigMoney.parseDouble(data.getData()));
                    dataCell.setCellStyle(cellStyle.getDefaultStyle(data.getDataFormat()));
                }

            }
        }
    }

    private void addMergeRegion(Sheet sheet, SheetModel sheetModel) {
        for (MergeRegion mergeRegion : sheetModel.getMergeRegions()) {
            CellRangeAddress rangeAddress = new CellRangeAddress(mergeRegion.getStartRow(),
                    mergeRegion.getStartRow() + mergeRegion.getMergeRows(),
                    mergeRegion.getFirstColumn(),
                    mergeRegion.getLastColumn());

            sheet.addMergedRegion(rangeAddress);

        }
    }

    private Row createRow(Sheet sheet, int rowIndex) {
        return sheet.createRow(rowIndex);
    }

    private void buildWorkBook(XlsData xlsData) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        this.setWorkBook(workbook);
        this.setCellStyle(new XlsCellStyle(workbook));
        for (SheetModel sheet : xlsData.getSheets()) {
            createSheet(sheet);
        }
    }

    public void exportXls(XlsData xlsData, HttpServletRequest request, HttpServletResponse response) throws IOException {
        buildWorkBook(xlsData);

        if (null == workBook) {
            return;
        }

        setResponseHeader(response, xlsData.getXlsName());

        OutputStream outStream = response.getOutputStream();
        try {
            workBook.write(outStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        outStream.flush();
        outStream.close();
    }

    public void saveXls(XlsData xlsData) throws IOException {
        buildWorkBook(xlsData);

        if (null == workBook) {
            return;
        }

        OutputStream outStream = new FileOutputStream(new File(xlsData.getSavePath()));
        try {
            workBook.write(outStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        outStream.flush();
        outStream.close();
    }

    /**
     * 设置响应头
     *
     * @param response
     */
    public static void setResponseHeader(HttpServletResponse response, String excelName) {
        String charset = response.getCharacterEncoding();
        response.setContentType("application/octet-stream;charset=" + charset);
        try {
            excelName = new String(excelName.getBytes("gb2312"), charset);
            response.setHeader("Content-Disposition", "attachment;filename=" + excelName + URLEncoder.encode(".xls", charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给sheet创建一个Header，并可以设置宽度，以及header所处的行
     *
     * @param sheet
     * @param names
     * @param widths
     * @param num
     */
    public static void createHeaderModel(SheetModel sheet, String[] names, Integer[] widths, int num) {
        LinkedList<HeaderModel> headerModels = new LinkedList<HeaderModel>();
        for (int i = 0; i < names.length; i++) {
            HeaderModel header = new HeaderModel();
            header.setHeadName(names[i]);
            if (widths != null && widths[i] != null) {
                header.setHeadWidth(widths[i]);
            }
            headerModels.add(header);
        }
        sheet.getHeaders().put(num, headerModels);
    }

    /**
     * 给sheet赋予数据，并可以数据格式
     *
     * @param sheet
     * @param objList
     * @param dataFormats
     */
    public static void createDataModel(SheetModel sheet, List<List<Object>> objList, DataFormat[] dataFormats) {
        if (objList != null && objList.size() > 0) {
            for (int i = 0; i < objList.size(); i++) {
                LinkedList<DataModel> datas = new LinkedList<DataModel>();
                for (int j = 0; j < objList.get(i).size(); j++) {
                    DataModel data = new DataModel();
                    data.setData(objList.get(i).get(j));
                    if (objList.get(i).get(j) != null) {
                        if (dataFormats != null && dataFormats[j] != null) {
                            data.setDataFormat(dataFormats[j]);
                        } else {
                            data.setDataFormat(DataFormat.STRING);
                        }
                    }
                    datas.add(data);
                    if (j == objList.get(i).size() - 1) {
                        sheet.getDatas().add(i, datas);
                    }
                }
            }
        }
    }

}
