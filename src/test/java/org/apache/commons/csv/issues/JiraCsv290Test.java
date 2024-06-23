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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

// psql (14.5 (Homebrew))
//
// create table COMMONS_CSV_PSQL_TEST (ID INTEGER, COL1 VARCHAR, COL2 VARCHAR, COL3 VARCHAR, COL4 VARCHAR);
// insert into COMMONS_CSV_PSQL_TEST select 1, 'abc', 'test line 1' || chr(10) || 'test line 2', null, '';
// insert into COMMONS_CSV_PSQL_TEST select 2, 'xyz', '\b:' || chr(8) || ' \t:' || chr(9) || ' \n:' || chr(10) || ' \r:' || chr(13), 'a', 'b';
// insert into COMMONS_CSV_PSQL_TEST values (3, 'a', 'b,c,d', '"quoted"', 'e');
// copy COMMONS_CSV_PSQL_TEST TO '/tmp/psql.csv' WITH (FORMAT CSV);
// copy COMMONS_CSV_PSQL_TEST TO '/tmp/psql.tsv';
//
// cat /tmp/psql.csv
// 1,abc,"test line 1
// test line 2",,""
// 2,xyz,"\b:^H \t:         \n:
//  \r:^M",a,b
// 3,a,"b,c,d","""quoted""",e
//
// cat /tmp/psql.tsv
// 1    abc    test line 1\ntest line 2                  \N
// 2    xyz    \\b:\b \\t:\t \\n:\n \\r:\r    a          b
// 3    a      b,c,d                         "quoted"    e
//
public class JiraCsv290Test {

}