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

import static org.apache.commons.csv.CSVFormat.RFC4180;
import static org.apache.commons.csv.Constants.CR;
import static org.apache.commons.csv.Constants.CRLF;
import static org.apache.commons.csv.Constants.LF;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.csv.CSVFormat.Builder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVFormat}.
 */
public class CSVFormatTest {

    public enum EmptyEnum {
        // empty enum.
    }

    public enum Header {
        Name, Email, Phone
    }

    private static void assertNotEquals(final Object right, final Object left) {
        Assertions.assertNotEquals(right, left);
        Assertions.assertNotEquals(left, right);
    }


    private void assertNotEquals(final String name, final String type, final Object left, final Object right) {
        if (left.equals(right) || right.equals(left)) {
            fail("Objects must not compare equal for " + name + "(" + type + ")");
        }
        if (left.hashCode() == right.hashCode()) {
            fail("Hash code should not be equal for " + name + "(" + type + ")");
        }
    }






















    @Test
    public void testEqualsHash() throws Exception {
        final Method[] methods = CSVFormat.class.getDeclaredMethods();
        for (final Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                final String name = method.getName();
                if (name.startsWith("with")) {
                    for (final Class<?> cls : method.getParameterTypes()) {
                        final String type = cls.getCanonicalName();
                        if ("boolean".equals(type)) {
                            final Object defTrue = method.invoke(CSVFormat.DEFAULT, Boolean.TRUE);
                            final Object defFalse = method.invoke(CSVFormat.DEFAULT, Boolean.FALSE);
                            assertNotEquals(name, type, defTrue, defFalse);
                        } else if ("char".equals(type)) {
                            final Object a = method.invoke(CSVFormat.DEFAULT, 'a');
                            final Object b = method.invoke(CSVFormat.DEFAULT, 'b');
                            assertNotEquals(name, type, a, b);
                        } else if ("java.lang.Character".equals(type)) {
                            final Object a = method.invoke(CSVFormat.DEFAULT, new Object[] { null });
                            final Object b = method.invoke(CSVFormat.DEFAULT, Character.valueOf('d'));
                            assertNotEquals(name, type, a, b);
                        } else if ("java.lang.String".equals(type)) {
                            final Object a = method.invoke(CSVFormat.DEFAULT, new Object[] { null });
                            final Object b = method.invoke(CSVFormat.DEFAULT, "e");
                            assertNotEquals(name, type, a, b);
                        } else if ("java.lang.String[]".equals(type)) {
                            final Object a = method.invoke(CSVFormat.DEFAULT, new Object[] { new String[] { null, null } });
                            final Object b = method.invoke(CSVFormat.DEFAULT, new Object[] { new String[] { "f", "g" } });
                            assertNotEquals(name, type, a, b);
                        } else if ("org.apache.commons.csv.QuoteMode".equals(type)) {
                            final Object a = method.invoke(CSVFormat.DEFAULT, QuoteMode.MINIMAL);
                            final Object b = method.invoke(CSVFormat.DEFAULT, QuoteMode.ALL);
                            assertNotEquals(name, type, a, b);
                        } else if ("org.apache.commons.csv.DuplicateHeaderMode".equals(type)) {
                            final Object a = method.invoke(CSVFormat.DEFAULT, DuplicateHeaderMode.ALLOW_ALL);
                            final Object b = method.invoke(CSVFormat.DEFAULT, DuplicateHeaderMode.DISALLOW);
                            assertNotEquals(name, type, a, b);
                        } else if ("java.lang.Object[]".equals(type)) {
                            final Object a = method.invoke(CSVFormat.DEFAULT, new Object[] { new Object[] { null, null } });
                            final Object b = method.invoke(CSVFormat.DEFAULT, new Object[] { new Object[] { new Object(), new Object() } });
                            assertNotEquals(name, type, a, b);
                        } else if ("withHeader".equals(name)) { // covered above by String[]
                            // ignored
                        } else {
                            fail("Unhandled method: " + name + "(" + type + ")");
                        }
                    }
                }
            }
        }
    }



















































































}
