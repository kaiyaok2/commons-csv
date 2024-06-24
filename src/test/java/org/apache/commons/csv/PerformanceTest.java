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

import static org.apache.commons.io.IOUtils.EOF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;


/**
 * Basic test harness.
 */
@SuppressWarnings("boxing")
public class PerformanceTest {

    @FunctionalInterface
    private interface CSVParserFactory {
        CSVParser createParser() throws IOException;
    }

    // Container for basic statistics
    private static final class Stats {
        final int count;
        final int fields;
        Stats(final int c, final int f) {
            count = c;
            fields = f;
        }
    }

    private static final String[] PROPERTY_NAMES = {
        "java.version",                  // Java Runtime Environment version
        "java.vendor",                   // Java Runtime Environment vendor
//        "java.vm.specification.version", // Java Virtual Machine specification version
//        "java.vm.specification.vendor",  // Java Virtual Machine specification vendor
//        "java.vm.specification.name",    // Java Virtual Machine specification name
        "java.vm.version",               // Java Virtual Machine implementation version
//        "java.vm.vendor",                // Java Virtual Machine implementation vendor
        "java.vm.name",                  // Java Virtual Machine implementation name
//        "java.specification.version",    // Java Runtime Environment specification version
//        "java.specification.vendor",     // Java Runtime Environment specification vendor
//        "java.specification.name",       // Java Runtime Environment specification name

        "os.name",                       // Operating system name
        "os.arch",                       // Operating system architecture
        "os.version",                    // Operating system version
    };
    private static int max = 11; // skip first test

    private static int num; // number of elapsed times recorded

    private static final long[] ELAPSED_TIMES = new long[max];
    private static final CSVFormat format = CSVFormat.EXCEL;

    private static final String TEST_RESRC = "org/apache/commons/csv/perf/worldcitiespop.txt.gz";

    private static final File BIG_FILE = new File(FileUtils.getTempDirectoryPath(), "worldcitiespop.txt");


    private static Lexer createTestCSVLexer(final String test, final ExtendedBufferedReader input)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, Exception {
        return test.startsWith("CSVLexer") ? getLexerCtor(test).newInstance(format, input) : new Lexer(format, input);
    }





    // calculate and show average

    // Display end stats; store elapsed for average









}