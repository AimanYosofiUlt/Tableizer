package org.example.select_helper.column;

import org.example.select_helper.DBSelectHelper;

public class Column {
    public String tableName;
    public String columnName;


    public Column(String tableName, String name) {
        this.tableName = tableName;
        this.columnName = name;
    }

    public String getColumnName() {
        return columnName;
    }

    public SelectColumn as(String aliesName) {
        return new SelectColumn(this, aliesName);
    }

    public Column castAs(String type) {
        return new Column(tableName, "CAST(" + columnName + " AS " + type + ")");
    }


    //CONDITION BUILD====================================
    private ConditionColumn getOperationConditionColumn(String operation, String value) {
        return ConditionColumn.getOperationInstance(this, operation, "'" + value + "' ");
    }

    private ConditionColumn getOperationConditionColumn(String operation, double value) {
        return ConditionColumn.getOperationInstance(this, operation, String.valueOf(value));
    }

    private ConditionColumn getOperationConditionColumn(String operation, Column column) {
        return ConditionColumn.getOperationInstance(this, operation, column.columnName);
    }

    private ConditionColumn getOperationConditionColumn(String operation, DBSelectHelper selectHelper) {
        return ConditionColumn.getOperationInstance(this, operation, "(" + selectHelper.toString() + ")");
    }


    //--EQUAL====================================
    public ConditionColumn equal(String value) {
        return getOperationConditionColumn(ConditionColumn.EQUAL, value);
    }

    public ConditionColumn equal(double value) {
        return getOperationConditionColumn(ConditionColumn.EQUAL, value);
    }

    public ConditionColumn equal(Column column) {
        return getOperationConditionColumn(ConditionColumn.EQUAL, column);
    }

    public ConditionColumn equal(DBSelectHelper selectHelper) {
        return getOperationConditionColumn(ConditionColumn.EQUAL, selectHelper);
    }


    //--GREATER_THAN====================================
    public ConditionColumn graterThan(double value) {
        return getOperationConditionColumn(ConditionColumn.GREATER_THAN, value);
    }

    public ConditionColumn graterThan(Column column) {
        return getOperationConditionColumn(ConditionColumn.GREATER_THAN, column);
    }

    public ConditionColumn graterThan(DBSelectHelper selectHelper) {
        return getOperationConditionColumn(ConditionColumn.GREATER_THAN, selectHelper);
    }


    //--LESS_THAN====================================
    public ConditionColumn lessThan(double value) {
        return getOperationConditionColumn(ConditionColumn.LESS_THAN, value);
    }

    public ConditionColumn lessThan(Column column) {
        return getOperationConditionColumn(ConditionColumn.LESS_THAN, column);
    }

    public ConditionColumn lessThan(DBSelectHelper selectHelper) {
        return getOperationConditionColumn(ConditionColumn.LESS_THAN, selectHelper);
    }


    //--GREATER_THAN_OR_EQUAL====================================
    public ConditionColumn greaterThanOrEqual(double value) {
        return getOperationConditionColumn(ConditionColumn.GREATER_THAN_OR_EQUAL, value);
    }

    public ConditionColumn greaterThanOrEqual(Column column) {
        return getOperationConditionColumn(ConditionColumn.GREATER_THAN_OR_EQUAL, column);
    }

    public ConditionColumn greaterThanOrEqual(DBSelectHelper selectHelper) {
        return getOperationConditionColumn(ConditionColumn.GREATER_THAN_OR_EQUAL, selectHelper);
    }


    //--LESS_THAN_OR_EQUAL====================================
    public ConditionColumn lessThanOrEqual(double value) {
        return getOperationConditionColumn(ConditionColumn.LESS_THAN_OR_EQUAL, value);
    }

    public ConditionColumn lessThanOrEqual(Column column) {
        return getOperationConditionColumn(ConditionColumn.LESS_THAN_OR_EQUAL, column);
    }

    public ConditionColumn lessThanOrEqual(DBSelectHelper selectHelper) {
        return getOperationConditionColumn(ConditionColumn.LESS_THAN_OR_EQUAL, selectHelper);
    }


    //LIKE====================================
    public ConditionColumn isLike(String value) {
        return getOperationConditionColumn(ConditionColumn.LIKE, value);
    }

    public ConditionColumn isLike(Column column) {
        return getOperationConditionColumn(ConditionColumn.LIKE, column);
    }

    public ConditionColumn isLike(DBSelectHelper selectHelper) {
        return getOperationConditionColumn(ConditionColumn.LIKE, selectHelper);
    }


    //--IN=========================================
    public ConditionColumn in(String... values) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String value : values) {
            stringBuilder.append("'").append(value).append("'").append(",");
        }
        stringBuilder.delete(stringBuilder.lastIndexOf(","), stringBuilder.length());

        return ConditionColumn.getOperationInstance(this, ConditionColumn.IN, "(" + stringBuilder + ")");
    }

    public ConditionColumn in(double... values) {
        StringBuilder stringBuilder = new StringBuilder();
        for (double value : values) {
            stringBuilder.append(value).append(",");
        }
        stringBuilder.delete(stringBuilder.lastIndexOf(","), stringBuilder.length());

        return ConditionColumn.getOperationInstance(this, ConditionColumn.IN, "(" + stringBuilder + ")");
    }

    public ConditionColumn in(Column... columns) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Column column : columns) {
            stringBuilder.append(column.columnName).append(",");
        }
        stringBuilder.delete(stringBuilder.lastIndexOf(","), stringBuilder.length());

        return ConditionColumn.getOperationInstance(this, ConditionColumn.IN, "(" + stringBuilder + ")");
    }

    public ConditionColumn in(DBSelectHelper selectHelper) {
        return getOperationConditionColumn(ConditionColumn.IN, selectHelper);
    }


    //--BETWEEN====================================
    public ConditionColumn between(String value1, String value2) {
        return ConditionColumn.getOperationInstance(this, ConditionColumn.BETWEEN, "'" + value1 + "' AND '" + value2 + "'");
    }

    public ConditionColumn between(double value1, double value2) {
        return ConditionColumn.getOperationInstance(this, ConditionColumn.BETWEEN, value1 + " AND " + value2);
    }

    public ConditionColumn between(Column column1, Column column2) {
        return ConditionColumn.getOperationInstance(this, ConditionColumn.BETWEEN, column1.columnName + " AND " + column2.columnName);
    }


    //--IS_NULL====================================
    public ConditionColumn isNull() {
        return ConditionColumn.getSimpleConditionColumn(this, ConditionColumn.IS_NULL);
    }


}
