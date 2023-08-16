package org.example.select_helper.column;

public final class ConditionColumn extends Column {
    static final String EQUAL = " = ";
    static final String NOT_EQUAL = " <> ";
    static final String GREATER_THAN = " > ";
    static final String LESS_THAN = " < ";
    static final String GREATER_THAN_OR_EQUAL = " >= ";
    static final String LESS_THAN_OR_EQUAL = " <= ";
    static final String LIKE = " LIKE ";
    static final String NOT_LIKE = " NOT LIKE ";
    static final String BETWEEN = " BETWEEN ";
    static final String NOT_BETWEEN = " NOT BETWEEN ";
    static final String IN = " IN ";
    static final String NOT_IN = " NOT IN ";
    static final String IS_NULL = " IS NULL ";
    static final String IS_NOT_NULL = " IS NOT NULL ";

    Column mainColumn;
    String value;
    String operation;

    static ConditionColumn getOperationInstance(Column column, String operation, String value) {
        return new ConditionColumn(column, operation, value);
    }

    static ConditionColumn getSimpleConditionColumn(Column column, String operation) {
        return new ConditionColumn(column, operation);
    }


    private ConditionColumn(Column column, String operation, String value) {
        super(column.tableName, column.columnName + operation + value);
        this.mainColumn = column;
        this.value = value;
        this.operation = operation;
    }

    private ConditionColumn(Column column, String operation) {
        super(column.tableName, column.columnName + operation);
        this.mainColumn = column;
        this.operation = operation;
    }

    public ConditionColumn not() {
        if (operation.equals(EQUAL))
            return ConditionColumn.getOperationInstance(mainColumn, NOT_EQUAL, value);

        if (operation.equals(NOT_EQUAL))
            return ConditionColumn.getOperationInstance(mainColumn, EQUAL, value);

        if (operation.equals(LIKE))
            return ConditionColumn.getOperationInstance(mainColumn, NOT_LIKE, value);

        if (operation.equals(NOT_LIKE))
            return ConditionColumn.getOperationInstance(mainColumn, LIKE, value);

        if (operation.equals(BETWEEN))
            return ConditionColumn.getOperationInstance(mainColumn, NOT_BETWEEN, value);

        if (operation.equals(NOT_BETWEEN))
            return ConditionColumn.getOperationInstance(mainColumn, BETWEEN, value);

        if (operation.equals(IN))
            return ConditionColumn.getOperationInstance(mainColumn, NOT_IN, value);

        if (operation.equals(NOT_IN))
            return ConditionColumn.getOperationInstance(mainColumn, IN, value);

        if (operation.equals(IS_NULL))
            return ConditionColumn.getSimpleConditionColumn(mainColumn, IS_NOT_NULL);

        if (operation.equals(IS_NOT_NULL))
            return ConditionColumn.getSimpleConditionColumn(mainColumn, IS_NULL);

        return this;
    }

}
