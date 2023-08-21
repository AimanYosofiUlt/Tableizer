package org.example.select_helper.column;

import org.example.select_helper.DBKeywords;
import org.example.select_helper.DBSelectHelper;
import org.example.select_helper.Table;

import java.util.*;

import static org.example.select_helper.DBOperationKeywords.*;

public final class Condition {
    public Column mainColumn;
    public List<Column> conditionColumns;
    public List<String> values;
    String operation;

    boolean isValueCondition;

    static Condition getOperationInstance(Column mainColumn, String operation, String... values) {
        ArrayList<String> passingValues = new ArrayList<>();
        for (String value : values) passingValues.add("'" + value + "'");
        return new Condition(mainColumn, operation, passingValues);
    }

    static Condition getOperationInstance(Column mainColumn, String operation, double... values) {
        ArrayList<String> passingValues = new ArrayList<>();
        for (double value : values) passingValues.add(String.valueOf(value));
        return new Condition(mainColumn, operation, passingValues);
    }

    static Condition getOperationInstance(Column mainColumn, String operation, int... values) {
        ArrayList<String> passingValues = new ArrayList<>();
        for (int value : values) passingValues.add(String.valueOf(value));
        return new Condition(mainColumn, operation, passingValues);
    }

    static Condition getOperationInstance(Column mainColumn, String operation, Column... value) {
        return new Condition(mainColumn, operation, value);
    }

    static Condition getOperationInstance(Column mainColumn, String operation, DBSelectHelper selectHelper) {
        return new Condition(mainColumn, operation, ArrayListOf.of(selectHelper.toString()));
    }

    static Condition getSimpleConditionColumn(Column column, String operation) {
        return new Condition(column, operation, new ArrayList<>());
    }


    private Condition(Column mainColumn, String operation, List<String> values) {
        this.mainColumn = mainColumn;
        this.conditionColumns = Collections.emptyList();
        this.values = values;
        this.operation = operation;
        this.isValueCondition = true;
    }

    private Condition(Column mainColumn, String operation, Column... columns) {
        this.mainColumn = mainColumn;
        this.conditionColumns = List.of(columns);
        this.values = Collections.emptyList();
        this.operation = operation;
        this.isValueCondition = false;
    }

    public Condition not() {
        switch (operation) {
            case EQUAL:
                operation = NOT_EQUAL;
                break;
            case NOT_EQUAL:
                operation = EQUAL;
                break;

            case LIKE:
                operation = NOT_LIKE;
                break;
            case NOT_LIKE:
                operation = LIKE;
                break;

            case BETWEEN:
                operation = NOT_BETWEEN;
                break;
            case NOT_BETWEEN:
                operation = BETWEEN;
                break;

            case IN:
                operation = NOT_IN;
                break;
            case NOT_IN:
                operation = IN;
                break;

            case IS_NULL:
                operation = IS_NOT_NULL;
                break;
            case IS_NOT_NULL:
                operation = IS_NULL;
                break;
        }

        return this;
    }

    public Condition or(Condition conditionBuilder){
        return this;
    }

    public String getCondition(List<String> duplicatedColumns) {
        String mainColumnName = duplicatedColumns.contains(mainColumn.name) ?
                mainColumn.getFullName() : mainColumn.getName();

        switch (operation) {
            case EQUAL:
            case NOT_EQUAL:
            case GREATER_THAN:
            case LESS_THAN:
            case GREATER_THAN_OR_EQUAL:
            case LESS_THAN_OR_EQUAL:
            case LIKE:
            case NOT_LIKE: {
                if (isValueCondition)
                    return mainColumnName + " " + operation + " " + values.get(0);

                String conditionColumn = duplicatedColumns.contains(conditionColumns.get(0).name) ?
                        conditionColumns.get(0).getFullName() : conditionColumns.get(0).getName();
                return mainColumnName + " " + operation + " " + conditionColumn;
            }


            case BETWEEN:
            case NOT_BETWEEN: {
                if (isValueCondition)
                    return mainColumnName + " " + operation + " " + values.get(0) + DBKeywords.AND + values.get(1);

                String firstColumnName = duplicatedColumns.contains(conditionColumns.get(0).name) ?
                        conditionColumns.get(0).getFullName() : conditionColumns.get(0).getName();

                String secondColumnName = duplicatedColumns.contains(conditionColumns.get(1).name) ?
                        conditionColumns.get(1).getFullName() : conditionColumns.get(1).getName();

                return mainColumnName + " " + operation + " " + firstColumnName + DBKeywords.AND + secondColumnName;
            }

            case IN:
            case NOT_IN:
                StringBuilder valuesStr = new StringBuilder();
                if (isValueCondition) {
                    for (int i = 0; i < values.size(); i++) {
                        valuesStr.append(values.get(i)).append(",");
                        if (i < values.size() - 1)
                            valuesStr.append(", ");
                    }
                } else {
                    for (int i = 0; i < conditionColumns.size(); i++) {
                        if (duplicatedColumns.contains(conditionColumns.get(i).name))
                            valuesStr.append(conditionColumns.get(i).getFullName());
                        else
                            valuesStr.append(conditionColumns.get(i).getName());

                        if (i < conditionColumns.size() - 1)
                            valuesStr.append(", ");
                    }
                }
                return mainColumnName + " " + operation + " (" + valuesStr + ") ";


            case IS_NULL:
            case IS_NOT_NULL:
                return mainColumnName + " " + operation;
        }
        return "";
    }

    public Set<Table> getTables() {
        Set<Table> tables = new HashSet<>();
        tables.add(mainColumn.table);
        for (Column conditionColumn : conditionColumns) {
            tables.add(conditionColumn.table);
        }
        return tables;
    }
}
