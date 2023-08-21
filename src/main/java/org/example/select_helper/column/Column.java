package org.example.select_helper.column;

import org.example.select_helper.DBKeywords;
import org.example.select_helper.DBOperationKeywords;
import org.example.select_helper.DBSelectHelper;
import org.example.select_helper.Table;

import java.util.Arrays;

public class Column {
    public Table table;
    public String name;
    public String aliesName;
    public String orderType;

    public Column(Table tableName, String name) {
        this.table = tableName;
        this.name = name;
        aliesName = "";
        orderType = "";
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return table.name + "." + name;
    }

    public Column as(String aliesName) {
        this.aliesName = aliesName;
        return this;
    }

    public Column asc() {
        orderType = DBKeywords.ASC;
        return this;
    }

    public Column desc() {
        orderType = DBKeywords.ASC;
        return this;
    }

    public Column castAs(String type) {
        return new Column(table, "CAST(" + name + " AS " + type + ")");
    }

    //--EQUAL====================================
    public Condition equal(String value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.EQUAL, value);
    }

    public Condition equal(double value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.EQUAL, value);
    }

    public Condition equal(int value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.EQUAL, value);
    }

    public Condition equal(Column column) {
        return Condition.getOperationInstance(this, DBOperationKeywords.EQUAL, column);
    }

    public Condition equal(DBSelectHelper selectHelper) {
        return Condition.getOperationInstance(this, DBOperationKeywords.EQUAL, selectHelper);
    }


    //--GREATER_THAN====================================
    public Condition graterThan(double value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.GREATER_THAN, value);
    }

    public Condition graterThan(int value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.GREATER_THAN, value);
    }

    public Condition graterThan(Column column) {
        return Condition.getOperationInstance(this, DBOperationKeywords.GREATER_THAN, column);
    }

    public Condition graterThan(DBSelectHelper selectHelper) {
        return Condition.getOperationInstance(this, DBOperationKeywords.GREATER_THAN, selectHelper);
    }


    //--LESS_THAN====================================
    public Condition lessThan(double value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.LESS_THAN, value);
    }

    public Condition lessThan(int value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.LESS_THAN, value);
    }

    public Condition lessThan(Column column) {
        return Condition.getOperationInstance(this, DBOperationKeywords.LESS_THAN, column);
    }

    public Condition lessThan(DBSelectHelper selectHelper) {
        return Condition.getOperationInstance(this, DBOperationKeywords.LESS_THAN, selectHelper);
    }


    //--GREATER_THAN_OR_EQUAL====================================
    public Condition greaterThanOrEqual(double value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.GREATER_THAN_OR_EQUAL, value);
    }

    public Condition greaterThanOrEqual(int value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.GREATER_THAN_OR_EQUAL, value);
    }

    public Condition greaterThanOrEqual(Column column) {
        return Condition.getOperationInstance(this, DBOperationKeywords.GREATER_THAN_OR_EQUAL, column);
    }

    public Condition greaterThanOrEqual(DBSelectHelper selectHelper) {
        return Condition.getOperationInstance(this, DBOperationKeywords.GREATER_THAN_OR_EQUAL, selectHelper);
    }


    //--LESS_THAN_OR_EQUAL====================================
    public Condition lessThanOrEqual(double value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.LESS_THAN_OR_EQUAL, value);
    }

    public Condition lessThanOrEqual(int value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.LESS_THAN_OR_EQUAL, value);
    }

    public Condition lessThanOrEqual(Column column) {
        return Condition.getOperationInstance(this, DBOperationKeywords.LESS_THAN_OR_EQUAL, column);
    }

    public Condition lessThanOrEqual(DBSelectHelper selectHelper) {
        return Condition.getOperationInstance(this, DBOperationKeywords.LESS_THAN_OR_EQUAL, selectHelper);
    }


    //LIKE====================================
    public Condition isLike(String value) {
        return Condition.getOperationInstance(this, DBOperationKeywords.LIKE, value);
    }

    public Condition isLike(Column column) {
        return Condition.getOperationInstance(this, DBOperationKeywords.LIKE, column);
    }

    //--IN=========================================
    public Condition in(String... values) {
        return Condition.getOperationInstance(this, DBOperationKeywords.IN, values);
    }

    public Condition in(double... values) {
        return Condition.getOperationInstance(this, DBOperationKeywords.IN, Arrays.toString(values));
    }

    public Condition in(int... values) {
        return Condition.getOperationInstance(this, DBOperationKeywords.IN, Arrays.toString(values));
    }

    public Condition in(Column... columns) {
        return Condition.getOperationInstance(this, DBOperationKeywords.IN, columns);
    }

    public Condition in(DBSelectHelper selectHelper) {
        return Condition.getOperationInstance(this, DBOperationKeywords.IN, selectHelper);
    }


    //--BETWEEN====================================
    public Condition between(String value1, String value2) {
        return Condition.getOperationInstance(this, DBOperationKeywords.BETWEEN, value1, value2);
    }

    public Condition between(double value1, double value2) {
        return Condition.getOperationInstance(this, DBOperationKeywords.BETWEEN, value1, value2);
    }

    public Condition between(int value1, int value2) {
        return Condition.getOperationInstance(this, DBOperationKeywords.BETWEEN, value1, value2);
    }

    public Condition between(Column column1, Column column2) {
        return Condition.getOperationInstance(this, DBOperationKeywords.BETWEEN, column1, column2);
    }


    //--IS_NULL====================================
    public Condition isNull() {
        return Condition.getSimpleConditionColumn(this, DBOperationKeywords.IS_NULL);
    }


}
