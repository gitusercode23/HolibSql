package com.holub.database;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.*;


public class XMLImporterTest {

    @Test
    public void testXMLImporter() {
        try {
            String xmlFilePath = "E:/code/HolubSQL/output.xml";
            XMLImporter importer = new XMLImporter(xmlFilePath);

            // Test loadTableName()
            assertEquals("name", importer.loadTableName());

            // Test loadWidth()
            assertEquals(3, importer.loadWidth());

            // Test loadColumnNames()
            Iterator<String> columnNames = importer.loadColumnNames();
            assertTrue(columnNames.hasNext());
            assertEquals("first", columnNames.next());
            assertTrue(columnNames.hasNext());
            assertEquals("last", columnNames.next());
            assertTrue(columnNames.hasNext());
            assertEquals("addrId", columnNames.next());
            assertFalse(columnNames.hasNext());

            // Test loadRow()
            Iterator<String> rowData = importer.loadRow();
            assertNotNull(rowData);
            assertTrue(rowData.hasNext());
            assertEquals("Fred", rowData.next());
            assertTrue(rowData.hasNext());
            assertEquals("Flintstone", rowData.next());
            assertTrue(rowData.hasNext());
            assertEquals("1", rowData.next());
            assertFalse(rowData.hasNext());

        } catch (IOException | SAXException | ParserConfigurationException | InterruptedException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}