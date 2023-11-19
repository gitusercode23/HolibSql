package com.holub.database;

import java.io.*;
import java.util.Iterator;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLImporter implements Table.Importer {

    private DocumentBuilder builder;
    private NodeList rows;
    private int currentRow;

    public XMLImporter(String xmlFilePath) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(xmlFilePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        rows = document.getElementsByTagName("row");
        currentRow = 0;
    }

    @Override
    public void startTable() throws IOException {
        // Not implemented for CSV import
    }

    @Override
    public String loadTableName() throws IOException {
        Node row = rows.item(0);
        NodeList columns = row.getChildNodes();
        Node column = columns.item(0);
        return column.getTextContent();
    }

    @Override
    public int loadWidth() throws IOException {
        Node row = rows.item(1);
        NodeList columns = row.getChildNodes();
        return columns.getLength();
    }

    @Override
    public Iterator<String> loadColumnNames() throws IOException {
        Node row = rows.item(1);
        NodeList columns = row.getChildNodes();
        Iterator<String> columnNames = new Iterator<String>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < columns.getLength();
            }

            @Override
            public String next() {
                Node column = columns.item(currentIndex++);
                return column.getTextContent().trim();
            }
        };
        return columnNames;
    }

    @Override
    public Iterator<String> loadRow() throws IOException, InterruptedException {
        currentRow++;
        if (currentRow >= rows.getLength() - 1) {  // Ignore the first row with column names
            return null;
        }

        Node row = rows.item(currentRow+1);
        NodeList columns = row.getChildNodes();

        Iterator<String> rowData = new Iterator<String>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < columns.getLength();
            }

            @Override
            public String next() {
                Node column = columns.item(currentIndex++);
                return column.getTextContent().trim();
            }
        };
        return rowData;
    }

    @Override
    public void endTable() throws IOException {
        // Not implemented for CSV import
    }

    public static void main(String[] args) {
        try {
            XMLImporter importer = new XMLImporter("E:/code/HolubSQL/output.xml");
            File csvFile = new File("C:/dp2023/name1.csv");
            FileWriter csvWriter = new FileWriter(csvFile);

            importer.startTable();
            String tableName = importer.loadTableName();
            csvWriter.append(tableName + "\n");

            Iterator<String> columnNames = importer.loadColumnNames();
            while (columnNames.hasNext()) {
                String columnName=columnNames.next();
                System.out.println(columnName);
                csvWriter.append(columnName);
                if (columnNames.hasNext()) {
                    csvWriter.append(",");
                }
            }
            csvWriter.append("\n");

            Iterator<String> rowData;
            while ((rowData = importer.loadRow()) != null) {
                while (rowData.hasNext()) {
                    csvWriter.append(rowData.next());
                    if (rowData.hasNext()) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");
            }

            importer.endTable();
            csvWriter.flush();
            csvWriter.close();
            System.out.println("CSV file successfully imported.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}