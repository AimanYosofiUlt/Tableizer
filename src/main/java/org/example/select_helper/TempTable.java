package org.example.select_helper;

import org.example.select_helper.column.Column;

public class TempTable extends Table {
    DBSelectHelper selectHelper;

    public TempTable(String tableName, DBSelectHelper selectHelper) {
        super(tableName);
        this.selectHelper = selectHelper;
    }

    public DBSelectHelper getSelectHelper(int parentCount) {
        return selectHelper
                .setParentCount(parentCount + 1);
    }

    public Column getColumn(Column column) {
        return new Column(this, column.name);
    }
}
