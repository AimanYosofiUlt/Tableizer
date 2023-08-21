package org.example.select_helper;

import org.example.select_helper.column.Column;

public class Table {
    public String name;

    public Table(String tableName) {
        this.name = tableName;
    }

    public Column all() {
        return new Column(this, name + ".*");
    }
}