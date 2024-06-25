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

package org.apache.commons.csv.issues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

public class JiraCsv248Test {
    private static InputStream getTestInput() {
        return ClassLoader.getSystemClassLoader().getResourceAsStream("org/apache/commons/csv/CSV-248/csvRecord.bin");
    }

    /**
     * Test deserialisation of a CSVRecord created using version 1.6.
     *
     * <p>
     * This test asserts that serialization from 1.8 onwards is consistent with previous versions. Serialization was
     * broken in version 1.7.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException If the CSVRecord cannot be deserialized
     */
}
