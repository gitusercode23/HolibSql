package com.holub.database;

import org.junit.Test;
import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class XMLExporterTest {

    @Test
    public void testXMLExport() throws IOException {
        String[] columnNames = { "first", "last", "addrId" };
        List<String> columnList = Arrays.asList(columnNames);
        List<List<String>> rowDataList = new ArrayList<>();
        rowDataList.add(Arrays.asList("John", "Doe", "1"));
        rowDataList.add(Arrays.asList("Jane", "Smith", "2"));

        PrintWriter outputWriter = new PrintWriter("test_output.xml");

        XMLExporter exporter = new XMLExporter(outputWriter);

        exporter.startTable();
        exporter.storeMetadata("TestTable", columnList.size(), -1, columnList.iterator());

        for (List<String> rowData : rowDataList) {
            exporter.storeRow(rowData.iterator());
        }

        exporter.endTable();

        outputWriter.close();

        BufferedReader reader = new BufferedReader(new FileReader("test_output.xml"));
        String line;
        StringBuilder xmlContent = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            xmlContent.append(line.trim());
        }
        reader.close();

        String expectedXML = "<table><row><column>John</column><column>Doe</column><column>1</column></row>"
                + "<row><column>Jane</column><column>Smith</column><column>2</column></row></table>";
        String actualXML = xmlContent.toString();

        assertEquals(expectedXML, actualXML);
    }
}