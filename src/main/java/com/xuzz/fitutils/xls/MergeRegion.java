package com.xuzz.fitutils.xls;

/**
 * Created by xuzz on 2017/3/28.
 */
public class MergeRegion {
    /**
     * 从哪一行开始合并
     */
    private int startRow;
    /**
     * 合并多少行
     */
    private int mergeRows;
    /**
     * 从那一列开始合并
     */
    private int firstColumn;
    /**
     * 合并到那一列
     */
    private int lastColumn;

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getMergeRows() {
        return mergeRows;
    }

    public void setMergeRows(int mergeRows) {
        this.mergeRows = mergeRows;
    }

    public int getFirstColumn() {
        return firstColumn;
    }

    public void setFirstColumn(int firstColumn) {
        this.firstColumn = firstColumn;
    }

    public int getLastColumn() {
        return lastColumn;
    }

    public void setLastColumn(int lastColumn) {
        this.lastColumn = lastColumn;
    }
}
