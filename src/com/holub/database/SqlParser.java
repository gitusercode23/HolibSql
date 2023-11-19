package com.holub.database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;  
  
public class SqlParser {  
    public static void main(String[] args) {  
        String sql = "select street, city from address, name where address.addrid = name.addrid";  
  
        // 截取出 select 后的所有列名  
        Pattern columnPattern = Pattern.compile("select\\s+([^,]+)");  
        Matcher columnMatcher = columnPattern.matcher(sql);  
        if (columnMatcher.find()) {  
            String columns = columnMatcher.group(1); // 获取 select 后面的所有列名，以逗号分隔  
            String[] columnsArray = columns.split(","); // 将列名分割成数组  
            for (String column : columnsArray) {  
                System.out.println("Column: " + column.trim()); // 打印列名  
            }  
        }  
  
        // 截取出 from 后的所有以逗号分割的表名  
        Pattern tablePattern = Pattern.compile("from\\s+([^,]+)");  
        Matcher tableMatcher = tablePattern.matcher(sql);  
        if (tableMatcher.find()) {  
            String tables = tableMatcher.group(1); // 获取 from 后面的所有表名，以逗号分隔  
            String[] tablesArray = tables.split(","); // 将表名分割成数组  
            for (String table : tablesArray) {  
                System.out.println("Table: " + table.trim()); // 打印表名  
            }  
        }  
  
        // 截取出 where 后的所有条件  
        Pattern conditionPattern = Pattern.compile("where\\s+([^,]+)");  
        Matcher conditionMatcher = conditionPattern.matcher(sql);  
        if (conditionMatcher.find()) {  
            String condition = conditionMatcher.group(1); // 获取 where 后面的所有条件  
            System.out.println("Condition: " + condition); // 打印条件  
        }  
    }  
}