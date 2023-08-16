package org.example.select_helper;

import org.example.select_helper.column.Column;
import org.example.select_helper.column.ConditionColumn;

import java.util.*;

public class DBSelectHelper {
    boolean isDistinct = false;
    Set<String> tableSet;
    ArrayList<String> selectColumns;
    ArrayList<String> whereConditions;
    ArrayList<String> groupByColumns;


    public static DBSelectHelper SELECT(Column... columns) {
        return new DBSelectHelper(columns);
    }

    public static DBSelectHelper SELECT_DISTINCT(Column... columns) {
        DBSelectHelper selectHelper = new DBSelectHelper(columns);
        selectHelper.isDistinct = true;
        return selectHelper;
    }

    public static DBSelectHelper SELECT_ALL() {
        return new DBSelectHelper();
    }

    public static DBSelectHelper SELECT_ALL_DISTINCT() {
        DBSelectHelper selectHelper = new DBSelectHelper();
        selectHelper.isDistinct = true;
        return selectHelper;
    }

    private DBSelectHelper() {
        init();
        selectColumns.add("*");
    }

    private DBSelectHelper(Column... columns) {
        init();
        for (Column column : columns) {
            tableSet.add(column.tableName);
            selectColumns.add(column.columnName);
        }
    }

    private void init() {
        tableSet = new HashSet<>();
        selectColumns = new ArrayList<>();
        whereConditions = new ArrayList<>();
        groupByColumns = new ArrayList<>();
    }

    public DBSelectHelper FROM(Table... tables) {
        for (Table table : tables) {
            tableSet.add(table.tableName);
        }
        return this;
    }

    public DBSelectHelper WHERE(ConditionColumn... conditionColumns) {
        for (ConditionColumn conditionColumn : conditionColumns) {
            whereConditions.add(conditionColumn.columnName);
        }
        return this;
    }

    public DBSelectHelper GROUP_BY(Column... columns) {
        for (Column column : columns) {
            groupByColumns.add(column.columnName);
        }
        return this;
    }

    @Override
    public String toString() {
        return ":LK";
    }
}
