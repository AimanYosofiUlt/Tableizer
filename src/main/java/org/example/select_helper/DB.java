package org.example.select_helper;

import org.example.select_helper.column.Column;

public class DB {
    public static DBFunctionColumn SUM(Column column) {
        return new DBFunctionColumn(column.table, column, DBKeywords.SUM);
    }

    public static DBFunctionColumn AVG(Column column) {
        return new DBFunctionColumn(column.table, column, DBKeywords.AVG);
    }

    public static DBFunctionColumn MAX(Column column) {
        return new DBFunctionColumn(column.table, column, DBKeywords.MAX);
    }

    public static DBFunctionColumn MIN(Column column) {
        return new DBFunctionColumn(column.table, column, DBKeywords.MIN);
    }

    public static DBFunctionColumn ABS(Column column) {
        return new DBFunctionColumn(column.table, column, DBKeywords.ABS);
    }

//    public static DBFunctionColumn ROUND(Column column, int decimalPlaces) {
//        return new DBFunctionColumn(column.table, column, DBKeywords.ROUND, decimalPlaces);
//    }
//
//    public static DBFunctionColumn CONCAT(Column column1, Column column2) {
//        return new DBFunctionColumn(column1.table, column1, DBKeywords.CONCAT, column2);
//    }
//
//    public static DBFunctionColumn LEFT(Column column, int n) {
//        return new DBFunctionColumn(column.table, column, DBKeywords.LEFT, n);
//    }
//
//    public static DBFunctionColumn RIGHT(Column column, int n) {
//        return new DBFunctionColumn(column.table, column, DBKeywords.RIGHT, n);
//    }
//
//    public static DBFunctionColumn SUBSTR(Column column, int start, int end) {
//        return new DBFunctionColumn(column.table, column, DBKeywords.SUBSTR, start, end);
//    }

    public static DBFunctionColumn UPPER(Column column) {
        return new DBFunctionColumn(column.table, column, DBKeywords.UPPER);
    }

    public static DBFunctionColumn LOWER(Column column) {
        return new DBFunctionColumn(column.table, column, DBKeywords.LOWER);
    }
}

