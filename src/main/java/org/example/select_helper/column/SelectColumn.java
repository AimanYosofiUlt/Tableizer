package org.example.select_helper.column;

public class SelectColumn extends Column {
    public SelectColumn(Column column, String aliesName) {
        super(column.tableName, column.columnName + " AS " + aliesName);
    }
}
