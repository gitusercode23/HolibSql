package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.junit.Assert.fail;

public class HTMLExporterTest {

    @Test
    public void testExportToHTML() {
        try {
            String csvFilePath = "C:/dp2023/name1.csv";

            PrintWriter outputWriter = new PrintWriter("output.html");

            FileReader fileReader = new FileReader(csvFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            HTMLExporter exporter = new HTMLExporter(outputWriter);

            String[] columnNames = {"first", "last", "addrId"};
            Iterator<String> columnNamesIterator = Arrays.asList(columnNames).iterator();

            exporter.startTable();
            exporter.storeMetadata("Name Table", columnNames.length, -1, columnNamesIterator);

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
            fail("IOException occurred during testExportToHTML");
        }
    }
}