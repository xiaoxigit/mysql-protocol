package protocol.resultDomain.mode;

import protocol.dataPackage.ColumnPackage;
import protocol.dataPackage.ResultSetRowPacket;

import java.util.Arrays;

public class QueryResult extends CommandResult{
    int columnNum;
    ColumnPackage[] columns;
    ResultSetRowPacket[] rows;

    public QueryResult() {
        type = "query with result";
    }

    public QueryResult(int columnNum, ColumnPackage[] columns, ResultSetRowPacket[] rows) {
        this.columnNum = columnNum;
        this.columns = columns;
        this.rows = rows;
        type = "query with result";
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public ColumnPackage[] getColumns() {
        return columns;
    }

    public void setColumns(ColumnPackage[] columns) {
        this.columns = columns;
    }

    public ResultSetRowPacket[] getRows() {
        return rows;
    }

    public void setRows(ResultSetRowPacket[] rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "columnNum=" + columnNum +
                ", columns=" + Arrays.toString(columns) +
                ", rows=" + Arrays.toString(rows) +
                ", type='" + type + '\'' +
                '}';
    }
}
