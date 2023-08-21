package org.example.select_helper;

import org.example.select_helper.column.Column;
import org.example.select_helper.column.Condition;

import java.lang.reflect.Field;
import java.util.*;

public class DBSelectHelper {
    boolean isDistinct = false;
    Set<Table> tableSet;
    ArrayList<Column> selectColumns;
    ArrayList<Condition> whereConditions;
    ArrayList<Column> groupByColumns;
    ArrayList<Column> orderByColumns;
    List<String> duplicatedColumns;

    int limitCount = -1;

    int parentCount = 0;

    public static DBSelectHelper SELECT(Column column, Column... columns) {
        return new DBSelectHelper(false, column, columns);
    }

    public static DBSelectHelper SELECT_DISTINCT(Column column, Column... columns) {
        return new DBSelectHelper(true, column, columns);
    }

    public static FromHelper SELECT_ALL() {
        return new FromHelper(false);
    }

    public static FromHelper SELECT_DISTINCT_ALL() {
        return new FromHelper(true);
    }

    private DBSelectHelper(boolean isDistinct) {
        init(isDistinct);
    }


    private DBSelectHelper(boolean isDistinct, Column column, Column... columns) {
        init(isDistinct);
        selectColumns.add(column);
        selectColumns.addAll(Arrays.asList(columns));
        addColumnsTables(column);
        addColumnsTables(columns);
    }

    private void init(boolean isDistinct) {
        this.isDistinct = isDistinct;
        tableSet = new HashSet<>();
        selectColumns = new ArrayList<>();
        whereConditions = new ArrayList<>();
        groupByColumns = new ArrayList<>();
        orderByColumns = new ArrayList<>();
        duplicatedColumns = new ArrayList<>();
    }

    private void addColumnsTables(Column... columns) {
        for (Column column : columns) {
            tableSet.add(column.table);
        }
    }


    public DBSelectHelper FROM(Table table, Table... tables) {
        tableSet.add(table);
        tableSet.addAll(Arrays.asList(tables));
        return this;
    }

    public DBSelectHelper WHERE(Condition... conditionColumns) {
        whereConditions.addAll(Arrays.asList(conditionColumns));
        for (Condition conditionColumn : conditionColumns) {
            tableSet.addAll(conditionColumn.getTables());
        }
        return this;
    }

    public DBSelectHelper GROUP_BY(Column... columns) {
        groupByColumns.addAll(Arrays.asList(columns));
        addColumnsTables(columns);
        return this;
    }

    public DBSelectHelper ORDER_BY(Column... columns) {
        orderByColumns.addAll(Arrays.asList(columns));
        addColumnsTables(columns);
        return this;
    }

    public TempTable toTable(String tableName) {
        return new TempTable(tableName, this);
    }

    @Override
    public String toString() {
        duplicatedColumns = getDuplicatedColumns();
        String selectPreSpace = "";
        String preSpace = "";
        for (int i = 0; i < parentCount; i++) {
            preSpace += "\t\t";
            if (i == parentCount - 1) {
                selectPreSpace += "\t";
            } else {
                selectPreSpace += "\t\t";
            }
        }
        String preSelect = isDistinct ? DBKeywords.SELECT_DISTINCT : DBKeywords.SELECT;
        String selectStatement = selectColumns.size() == 0 ?
                selectPreSpace + preSelect + "\t* " :
                getColumnStatementOf(preSpace, preSelect, selectColumns);


        return selectStatement +
                getTableStatementOf(preSpace, DBKeywords.FROM, tableSet) +
                getConditionStatementOf(preSpace, DBKeywords.WHERE, whereConditions, DBKeywords.AND) +
                getColumnStatementOf(preSpace, DBKeywords.GROUP_BY, groupByColumns) +
                getColumnStatementOf(preSpace, DBKeywords.ORDER_BY, orderByColumns) +
                ((limitCount != -1) ? "\n" + preSpace + DBKeywords.LIMIT + limitCount : "");
    }


    private List<String> getDuplicatedColumns() {
        ArrayList<String> buffer = new ArrayList<>();
        ArrayList<String> similarItems = new ArrayList<>();
        for (Table table : tableSet) {
            ArrayList<String> names = getFieldsName(table.getClass());
            for (String name : names) {
                if (buffer.contains(name)) {
                    similarItems.add(name);
                }
                buffer.add(name);
            }
        }
        return similarItems;
    }

    private ArrayList<String> getFieldsName(Class<?> aClass) {
        ArrayList<String> list = new ArrayList<>();
        for (Field field : aClass.getDeclaredFields()) {
            list.add(field.getName());
        }
        return list;
    }


    private String getTableStatementOf(String preSpace, String keyword, Set<Table> tableSet) {
        StringBuilder stringBuilder = new StringBuilder("\n" + preSpace + keyword + "\t");
        int dataShowCount = 1;
        String characterBetween = ",  ";
        for (Table table : tableSet) {
            if (table instanceof TempTable) {
                TempTable tempTable = (TempTable) table;

                stringBuilder
                        .append("\n\t(")
                        .append(tempTable.getSelectHelper(parentCount).toString())
                        .append(")").append(" ").append(tempTable.name);
            } else
                stringBuilder.append(table.name);
            stringBuilder.append(characterBetween);
            if (dataShowCount++ % 3 == 0)
                stringBuilder.append("\n\t\t");
        }
        removeLast(stringBuilder, characterBetween);
        return stringBuilder.toString();
    }

    private String getColumnStatementOf(String preSpace, String keyword, List<Column> list) {
        String characterBetween = ",  ";
        return getColumnStatementOf(preSpace, keyword, list, characterBetween);
    }

    private String getColumnStatementOf(String preSpace, String keyword, List<Column> columns, String characterBetween) {
        if (columns.isEmpty())
            return "";
        StringBuilder stringBuilder = new StringBuilder("\n" + preSpace + keyword + "\t");
        int dataShowCount = 1;
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            if (duplicatedColumns.contains(column.name))
                stringBuilder.append(column.table.name).append(".").append(column.name);
            else
                stringBuilder.append(column.name);

            if (keyword.equals(DBKeywords.SELECT) && !column.aliesName.isEmpty()) {
                stringBuilder.append(DBKeywords.AS).append(column.aliesName);
            } else if (keyword.equals(DBKeywords.ORDER_BY)) {
                stringBuilder.append(" ").append(column.orderType);
            }

            if (i != columns.size() - 1) {
                stringBuilder.append(characterBetween);

                if (dataShowCount++ % 3 == 0)
                    stringBuilder.append("\n\t\t");
            }
        }
        return stringBuilder.toString();
    }


    private String getConditionStatementOf(String preSpace, String keyword, ArrayList<Condition> columns, String characterBetween) {
        if (columns.isEmpty())
            return "";
        StringBuilder stringBuilder = new StringBuilder("\n" + preSpace + keyword + "\t");
        int dataShowCount = 1;
        for (int i = 0; i < columns.size(); i++) {
            stringBuilder.append(columns.get(i).getCondition(duplicatedColumns));

            if (i != columns.size() - 1) {
                stringBuilder.append(characterBetween);

                if (dataShowCount++ % 3 == 0)
                    stringBuilder.append("\n\t\t");
            }
        }
        return stringBuilder.toString();
    }

    private void removeLast(StringBuilder stringBuilder, String s) {
        stringBuilder.delete(stringBuilder.lastIndexOf(","), stringBuilder.length());
    }

    public DBSelectHelper LIMIT(int count) {
        this.limitCount = count;
        return this;
    }

    public DBSelectHelper setParentCount(int parentCount) {
        this.parentCount = parentCount;
        return this;
    }

    public DBSelectHelper log(String tag) {
        DBLog.getInstance(this.toString())
                .logColorizedSelectStatement("DBSelectHelper:: " + tag,
                        tableSet
                );
        return this;
    }

    public static class FromHelper {
        DBSelectHelper selectHelper;
        boolean isDistinct = false;

        public FromHelper(boolean isDistinct) {
            this.isDistinct = isDistinct;
            selectHelper = new DBSelectHelper(isDistinct);
        }

        public DBSelectHelper FROM(Table table, Table... tables) {
            return selectHelper.FROM(table, tables);
        }
    }
}
