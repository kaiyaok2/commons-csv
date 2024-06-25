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

import static org.apache.commons.csv.Constants.CR;
import static org.apache.commons.csv.Constants.CRLF;
import static org.apache.commons.csv.Constants.LF;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * CSVParserTest
 *
 * The test are organized in three different sections: The 'setter/getter' section, the lexer section and finally the
 * parser section. In case a test fails, you should follow a top-down approach for fixing a potential bug (its likely
 * that the parser itself fails if the lexer has problems...).
 */
public class CSVParserTest {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private static final String UTF_8_NAME = UTF_8.name();

    private static final String CSV_INPUT = "a,b,c,d\n" + " a , b , 1 2 \n" + "\"foo baar\", b,\n"
    // + " \"foo\n,,\n\"\",,\n\\\"\",d,e\n";
        + "   \"foo\n,,\n\"\",,\n\"\"\",d,e\n"; // changed to use standard CSV escaping

    private static final String CSV_INPUT_1 = "a,b,c,d";

    private static final String CSV_INPUT_2 = "a,b,1 2";

    private static final String[][] RESULT = {{"a", "b", "c", "d"}, {"a", "b", "1 2"}, {"foo baar", "b", ""}, {"foo\n,,\n\",,\n\"", "d", "e"}};

    // CSV with no header comments
    static private final String CSV_INPUT_NO_COMMENT = "A,B"+CRLF+"1,2"+CRLF;

    // CSV with a header comment
    static private final String CSV_INPUT_HEADER_COMMENT = "# header comment" + CRLF + "A,B" + CRLF + "1,2" + CRLF;

    // CSV with a single line header and trailer comment
    static private final String CSV_INPUT_HEADER_TRAILER_COMMENT = "# header comment" + CRLF + "A,B" + CRLF + "1,2" + CRLF + "# comment";

    // CSV with a multi-line header and trailer comment
    static private final String CSV_INPUT_MULTILINE_HEADER_TRAILER_COMMENT = "# multi-line" + CRLF + "# header comment" + CRLF + "A,B" + CRLF + "1,2" + CRLF + "# multi-line" + CRLF + "# comment";

    // Format with auto-detected header
    static private final CSVFormat FORMAT_AUTO_HEADER = CSVFormat.Builder.create(CSVFormat.DEFAULT).setCommentMarker('#').setHeader().build();

    // Format with explicit header
    static private final CSVFormat FORMAT_EXPLICIT_HEADER = CSVFormat.Builder.create(CSVFormat.DEFAULT)
            .setSkipHeaderRecord(true)
            .setCommentMarker('#')
            .setHeader("A", "B")
            .build();

    // Format with explicit header that does not skip the header line
    CSVFormat FORMAT_EXPLICIT_HEADER_NOSKIP = CSVFormat.Builder.create(CSVFormat.DEFAULT)
            .setCommentMarker('#')
            .setHeader("A", "B")
            .build();



    private void parseFully(final CSVParser parser) {
        parser.forEach(Assertions::assertNotNull);
    }


































    /**
     * Tests an exported Excel worksheet with a header row and rows that have more columns than the headers
     */




















    /**
     * Tests reusing a parser to process new string records one at a time as they are being discovered. See [CSV-110].
     *
     * @throws IOException when an I/O error occurs.
     */


























    @Test
    public void testInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> CSVFormat.DEFAULT.withDelimiter(CR));
    }




















    
    
    
    
    
    
















}
