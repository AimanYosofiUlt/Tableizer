package org.example.select_helper;

import org.example.select_helper.column.Column;

public class DBFunctionColumn extends Column {
    Column mainColumn;
    String functionName;

    public DBFunctionColumn(Table tableName, Column mainColumn, String functionName) {
        super(tableName, mainColumn.name);
        this.mainColumn = mainColumn;
        this.functionName = functionName;
    }
}
