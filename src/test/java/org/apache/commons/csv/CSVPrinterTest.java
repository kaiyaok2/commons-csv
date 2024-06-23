/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.csv;

import static org.apache.commons.csv.Constants.BACKSLASH;
import static org.apache.commons.csv.Constants.CR;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVPrinter}.
 */
public class CSVPrinterTest {

    private static final char DQUOTE_CHAR = '"';
    private static final char EURO_CH = '\u20AC';
    private static final int ITERATIONS_FOR_RANDOM_TEST = 50000;
    private static final char QUOTE_CH = '\'';

    private static String printable(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            final char ch = s.charAt(i);
            if (ch <= ' ' || ch >= 128) {
                sb.append("(").append((int) ch).append(")");
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private String longText2;

    private final String recordSeparator = CSVFormat.DEFAULT.getRecordSeparator();

    private File createTempFile() throws IOException {
        return createTempPath().toFile();
    }

    private Path createTempPath() throws IOException {
        return Files.createTempFile(getClass().getName(), ".csv");
    }



    /**
     * Converts an input CSV array into expected output values WRT NULLs. NULL strings are converted to null values because the parser will convert these
     * strings to null.
     */
    private <T> T[] expectNulls(final T[] original, final CSVFormat csvFormat) {
        final T[] fixed = original.clone();
        for (int i = 0; i < fixed.length; i++) {
            if (Objects.equals(csvFormat.getNullString(), fixed[i])) {
                fixed[i] = null;
            }
        }
        return fixed;
    }

    private String[][] generateLines(final int nLines, final int nCol) {
        final String[][] lines = new String[nLines][];
        for (int i = 0; i < nLines; i++) {
            final String[] line = new String[nCol];
            lines[i] = line;
            for (int j = 0; j < nCol; j++) {
                line[j] = randStr();
            }
        }
        return lines;
    }

    private Connection getH2Connection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:mem:my_test;", "sa", "");
    }


    private String randStr() {
        final Random r = new Random();

        final int sz = r.nextInt(20);
        // sz = r.nextInt(3);
        final char[] buf = new char[sz];
        for (int i = 0; i < sz; i++) {
            // stick in special chars with greater frequency
            final char ch;
            final int what = r.nextInt(20);
            switch (what) {
            case 0:
                ch = '\r';
                break;
            case 1:
                ch = '\n';
                break;
            case 2:
                ch = '\t';
                break;
            case 3:
                ch = '\f';
                break;
            case 4:
                ch = ' ';
                break;
            case 5:
                ch = ',';
                break;
            case 6:
                ch = DQUOTE_CHAR;
                break;
            case 7:
                ch = '\'';
                break;
            case 8:
                ch = BACKSLASH;
                break;
            default:
                ch = (char) r.nextInt(300);
                break;
            // default: ch = 'a'; break;
            }
            buf[i] = ch;
        }
        return new String(buf);
    }
















































    @Test
    public void testInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> CSVFormat.DEFAULT.withDelimiter(CR));
    }

    @Test


















    @Test
    public void testMySqlNullStringDefault() {
        assertEquals("\\N", CSVFormat.MYSQL.getNullString());
    }

    @Test
    public void testNewCsvPrinterAppendableNullFormat() {
        assertThrows(NullPointerException.class, () -> new CSVPrinter(new StringWriter(), null));
    }

    @Test
    public void testNewCsvPrinterNullAppendableFormat() {
        assertThrows(NullPointerException.class, () -> new CSVPrinter(null, CSVFormat.DEFAULT));
    }

    @Test







    @Test
    public void testPostgreSqlNullStringDefaultCsv() {
        assertEquals("", CSVFormat.POSTGRESQL_CSV.getNullString());
    }

    @Test
    public void testPostgreSqlNullStringDefaultText() {
        assertEquals("\\N", CSVFormat.POSTGRESQL_TEXT.getNullString());
    }

    @Test














    /**
     * Test to target the use of {@link IOUtils#copy(java.io.Reader, Appendable)} which directly buffers the value from the Reader to the Appendable.
     *
     * <p>
     * Requires the format to have no quote or escape character, value to be a {@link Reader Reader} and the output <i>MUST NOT</i> be a
     * {@link Writer Writer} but some other Appendable.
     * </p>
     *
     * @throws IOException Not expected to happen
     */

    /**
     * Test to target the use of {@link IOUtils#copyLarge(java.io.Reader, Writer)} which directly buffers the value from the Reader to the Writer.
     *
     * <p>
     * Requires the format to have no quote or escape character, value to be a {@link Reader Reader} and the output <i>MUST</i> be a
     * {@link Writer Writer}.
     * </p>
     *
     * @throws IOException Not expected to happen
     */





























    private String[] toFirstRecordValues(final String expected, final CSVFormat format) throws IOException {
        try (final CSVParser parser = CSVParser.parse(expected, format)) {
            return parser.getRecords().get(0).values();
        }
    }


}
