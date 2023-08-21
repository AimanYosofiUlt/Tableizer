package org.example.select_helper;

import org.example.select_helper.column.Column;

public class StringTable extends Table {
    String selectStatement;

    public static StringTable getInstance(String tableName, String selectStatement) {
        return new StringTable(tableName, selectStatement);
    }

    private StringTable(String tableName, String selectStatement) {
        super(tableName);
        this.selectStatement = selectStatement;
    }

    public Column getColumn(String colName) {
        return new Column(this, colName);
    }
}
