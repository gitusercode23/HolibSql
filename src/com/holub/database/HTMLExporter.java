package com.holub.database;

import com.holub.tools.ArrayIterator;

import java.io.*;
import java.util.*;

public class HTMLExporter implements Table.Exporter {
    private PrintWriter out;
    private String[] columnNames;
    private String tableName;

    public HTMLExporter(PrintWriter outputWriter) {
        this.out = outputWriter;
    }

    @Override
    public void startTable() throws IOException {
        out.write("<html>\n<body>\n<table>\n");
    }

    @Override
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.tableName = tableName;
        this.columnNames = new String[width];
        int index = 0;
        while (columnNames.hasNext() && index < width) {
            this.columnNames[index++] = columnNames.next().toString();
        }
//        writeRow("<th>", "</th>\n", columnNames);
    }

    @Override
    public void storeRow(Iterator data) throws IOException {
        writeRow("<td>", "</td>\n", data);
    }

    @Override
    public void endTable() throws IOException {
        out.write("</table>\n</body>\n</html>");
    }

    private void writeRow(String startTag, String endTag, Iterator<String> iterator) throws IOException {
        StringBuilder row = new StringBuilder("<tr>\n");
        while (iterator.hasNext()) {
            String value = iterator.next();
            row.append(startTag).append(value).append(endTag);
        }
        row.append("</tr>\n");
        out.write(row.toString());
    }

    public static void main(String[] args) {
        try {
            String csvFilePath = "C:/dp2023/name.csv";

            PrintWriter outputWriter = new PrintWriter("output.html");

            FileReader fileReader = new FileReader(csvFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            HTMLExporter exporter = new HTMLExporter(outputWriter);

            String[] stringArray = { "first", "last", "addrId" };
            List<String> stringList = Arrays.asList(stringArray);
            Iterator<String> iterator = stringList.iterator();

            exporter.startTable();
            exporter.storeMetadata("Name Table", 1, -1, iterator);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] rowData = line.split(",");
                exporter.storeRow(new ArrayIterator(rowData));
            }

            exporter.endTable();

            outputWriter.close();
            bufferedReader.close();
            fileReader.close();

            System.out.println("CSV exported to HTML successfully！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}