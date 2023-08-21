package org.example.select_helper;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBLog {

    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_BLUE = "\u001B[34m";
    final String ANSI_PURPLE = "\u001B[35m";
    final String ANSI_CYAN = "\u001B[36m";

    String statement;

    public static DBLog getInstance(String statement) {
        return new DBLog(statement);
    }

    private DBLog(String statement) {
        this.statement = statement;
    }

    public void logColorizedSelectStatement(String tag, Set<Table> tableSet) {
        colorizeMainKeyword(DBKeywords.SELECT);
        colorizeMainKeyword(DBKeywords.FROM);
        colorizeMainKeyword(DBKeywords.WHERE);
        colorizeMainKeyword(DBKeywords.GROUP_BY);
        colorizeMainKeyword(DBKeywords.HAVING);
        colorizeMainKeyword(DBKeywords.ORDER_BY);
        colorizeMainKeyword(DBKeywords.DISTINCT);
        colorizeMainKeyword(DBKeywords.LIMIT);
        colorizeMainKeyword(DBKeywords.OFFSET);

        colorizeOperationKeyword(DBKeywords.AND);
        colorizeOperationKeyword(DBKeywords.OR);
        colorizeOperationKeyword(DBKeywords.AS);
        colorizeOperationKeyword(DBKeywords.ASC);
        colorizeOperationKeyword(DBKeywords.DESC);

        colorizeOperationKeyword(DBOperationKeywords.EQUAL);
        colorizeOperationKeyword(DBOperationKeywords.NOT_EQUAL);
        colorizeOperationKeyword(DBOperationKeywords.GREATER_THAN);
        colorizeOperationKeyword(DBOperationKeywords.LESS_THAN);
        colorizeOperationKeyword(DBOperationKeywords.GREATER_THAN_OR_EQUAL);
        colorizeOperationKeyword(DBOperationKeywords.LESS_THAN_OR_EQUAL);
        colorizeOperationKeyword(DBOperationKeywords.LIKE);
        colorizeOperationKeyword(DBOperationKeywords.NOT_LIKE);
        colorizeOperationKeyword(DBOperationKeywords.BETWEEN);
        colorizeOperationKeyword(DBOperationKeywords.NOT_BETWEEN);
        colorizeOperationKeyword(DBOperationKeywords.IN);
        colorizeOperationKeyword(DBOperationKeywords.NOT_IN);
        colorizeOperationKeyword(DBOperationKeywords.IS_NULL);
        colorizeOperationKeyword(DBOperationKeywords.IS_NOT_NULL);


        for (Table table : tableSet) {
            colorizeTable(table.name);
        }

        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(statement);
        while (matcher.find()) {
            String matchedText = matcher.group();
            colorizeString(matchedText);
        }

        System.out.println(
                tag + ":\n" + statement + "\n\n"
        );
    }

    private void colorizeMainKeyword(String keyword) {
        colorizesWith(keyword, ANSI_BLUE);
    }

    private void colorizeOperationKeyword(String keyword) {
        colorizesWith(keyword, ANSI_YELLOW);
    }

    private void colorizeTable(String value) {
        colorizesWith(value, ANSI_PURPLE);
    }

    private void colorizeString(String value) {
        colorizesWith(value, ANSI_CYAN);
    }

    private void colorizesWith(String keyword, String color) {
        final String ANSI_RESET = "\u001B[0m";
        statement = statement.replace(keyword, color + keyword + ANSI_RESET);
    }
}
