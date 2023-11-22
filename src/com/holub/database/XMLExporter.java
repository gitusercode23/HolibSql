package com.holub.database;

import java.io.*;
import java.util.*;

public class XMLExporter implements Table.Exporter {
    private PrintWriter out;
    private String[] columnNames;
    private String tableName;

    public XMLExporter(PrintWriter outputWriter) {
        this.out = outputWriter;
    }

    @Override
    public void startTable() throws IOException {
        out.println("<table>");
    }

    @Override
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.tableName = tableName;
        this.columnNames = new String[width];
        int index = 0;
        while (columnNames.hasNext() && index < width) {
            this.columnNames[index++] = (String) columnNames.next();
        }
    }

    @Override
    public void storeRow(Iterator data) throws IOException {
        out.print("<row>");
        while (data.hasNext()) {
            out.print("<column>" + data.next() + "</column>");
        }
        out.println("</row>");
    }

    @Override
    public void endTable() throws IOException {
        out.println("</table>");
    }

    // Optional: Implement any additional methods from Table.Exporter interface if needed

    public static void main(String[] args) {
        try {
            String csvFilePath = "C:/dp2023/name.csv";

            PrintWriter outputWriter = new PrintWriter("output.xml");

            FileReader fileReader = new FileReader(csvFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            XMLExporter exporter = new XMLExporter(outputWriter);

            String[] columnNames = { "first", "last", "addrId" };
            List<String> columnList = Arrays.asList(columnNames);

            exporter.startTable();
            exporter.storeMetadata("NameTable", columnList.size(), -1, columnList.iterator());

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] rowData = line.split(",");
                exporter.storeRow(Arrays.asList(rowData).iterator());
            }

            exporter.endTable();

            outputWriter.close();
            bufferedReader.close();
            fileReader.close();

            System.out.println("CSV exported to XML successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}