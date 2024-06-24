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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Specifies the format of a CSV file for parsing and writing.
 *
 * <h2>Using predefined formats</h2>
 *
 * <p>
 * You can use one of the predefined formats:
 * </p>
 *
 * <ul>
 * <li>{@link #DEFAULT}</li>
 * <li>{@link #EXCEL}</li>
 * <li>{@link #INFORMIX_UNLOAD}</li>
 * <li>{@link #INFORMIX_UNLOAD_CSV}</li>
 * <li>{@link #MONGODB_CSV}</li>
 * <li>{@link #MONGODB_TSV}</li>
 * <li>{@link #MYSQL}</li>
 * <li>{@link #ORACLE}</li>
 * <li>{@link #POSTGRESQL_CSV}</li>
 * <li>{@link #POSTGRESQL_TEXT}</li>
 * <li>{@link #RFC4180}</li>
 * <li>{@link #TDF}</li>
 * </ul>
 *
 * <p>
 * For example:
 * </p>
 *
 * <pre>
 * CSVParser parser = CSVFormat.EXCEL.parse(reader);
 * </pre>
 *
 * <p>
 * The {@link CSVParser} provides static methods to parse other input types, for example:
 * </p>
 *
 * <pre>
 * CSVParser parser = CSVParser.parse(file, StandardCharsets.US_ASCII, CSVFormat.EXCEL);
 * </pre>
 *
 * <h2>Defining formats</h2>
 *
 * <p>
 * You can extend a format by calling the {@code set} methods. For example:
 * </p>
 *
 * <pre>
 * CSVFormat.EXCEL.withNullString(&quot;N/A&quot;).withIgnoreSurroundingSpaces(true);
 * </pre>
 *
 * <h2>Defining column names</h2>
 *
 * <p>
 * To define the column names you want to use to access records, write:
 * </p>
 *
 * <pre>
 * CSVFormat.EXCEL.withHeader(&quot;Col1&quot;, &quot;Col2&quot;, &quot;Col3&quot;);
 * </pre>
 *
 * <p>
 * Calling {@link Builder#setHeader(String...)} lets you use the given names to address values in a {@link CSVRecord}, and assumes that your CSV source does not
 * contain a first record that also defines column names.
 *
 * If it does, then you are overriding this metadata with your names and you should skip the first record by calling
 * {@link Builder#setSkipHeaderRecord(boolean)} with {@code true}.
 * </p>
 *
 * <h2>Parsing</h2>
 *
 * <p>
 * You can use a format directly to parse a reader. For example, to parse an Excel file with columns header, write:
 * </p>
 *
 * <pre>
 * Reader in = ...;
 * CSVFormat.EXCEL.withHeader(&quot;Col1&quot;, &quot;Col2&quot;, &quot;Col3&quot;).parse(in);
 * </pre>
 *
 * <p>
 * For other input types, like resources, files, and URLs, use the static methods on {@link CSVParser}.
 * </p>
 *
 * <h2>Referencing columns safely</h2>
 *
 * <p>
 * If your source contains a header record, you can simplify your code and safely reference columns, by using {@link Builder#setHeader(String...)} with no
 * arguments:
 * </p>
 *
 * <pre>
 * CSVFormat.EXCEL.withHeader();
 * </pre>
 *
 * <p>
 * This causes the parser to read the first record and use its values as column names.
 *
 * Then, call one of the {@link CSVRecord} get method that takes a String column name argument:
 * </p>
 *
 * <pre>
 * String value = record.get(&quot;Col1&quot;);
 * </pre>
 *
 * <p>
 * This makes your code impervious to changes in column order in the CSV file.
 * </p>
 *
 * <h2>Serialization</h2>
 * <p>
 *   This class implements the {@link Serializable} interface with the following caveats:
 * </p>
 * <ul>
 *   <li>This class will no longer implement Serializable in 2.0.</li>
 *   <li>Serialization is not supported from one version to the next.</li>
 * </ul>
 * <p>
 *   The {@code serialVersionUID} values are:
 * </p>
 * <ul>
 *   <li>Version 1.10.0: {@code 2L}</li>
 *   <li>Version 1.9.0 through 1.0: {@code 1L}</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <p>
 * This class is immutable.
 * </p>
 * <p>
 * Not all settings are used for both parsing and writing.
 * </p>
 */
public final class CSVFormat implements Serializable {

    /**
     * Builds CSVFormat instances.
     *
     * @since 1.9.0
     */
    public static class Builder {

        /**
         * Creates a new default builder.
         *
         * @return a copy of the builder
         */

        /**
         * Creates a new builder for the given format.
         *
         * @param csvFormat the source format.
         * @return a copy of the builder
         */

        private boolean allowMissingColumnNames;

        private boolean autoFlush;

        private Character commentMarker;

        private String delimiter;

        private DuplicateHeaderMode duplicateHeaderMode;

        private Character escapeCharacter;

        private String[] headerComments;

        private String[] headers;

        private boolean ignoreEmptyLines;

        private boolean ignoreHeaderCase;

        private boolean ignoreSurroundingSpaces;

        private String nullString;

        private Character quoteCharacter;

        private String quotedNullString;

        private QuoteMode quoteMode;

        private String recordSeparator;

        private boolean skipHeaderRecord;

        private boolean lenientEof;

        private boolean trailingData;

        private boolean trailingDelimiter;

        private boolean trim;


        /**
         * Builds a new CSVFormat instance.
         *
         * @return a new CSVFormat instance.
         */

        /**
         * Sets the duplicate header names behavior, true to allow, false to disallow.
         *
         * @param allowDuplicateHeaderNames the duplicate header names behavior, true to allow, false to disallow.
         * @return This instance.
         * @deprecated Use {@link #setDuplicateHeaderMode(DuplicateHeaderMode)}.
         */
        @Deprecated

        /**
         * Sets the parser missing column names behavior, {@code true} to allow missing column names in the header line, {@code false} to cause an
         * {@link IllegalArgumentException} to be thrown.
         *
         * @param allowMissingColumnNames the missing column names behavior, {@code true} to allow missing column names in the header line, {@code false} to
         *                                cause an {@link IllegalArgumentException} to be thrown.
         * @return This instance.
         */

        /**
         * Sets whether to flush on close.
         *
         * @param autoFlush whether to flush on close.
         * @return This instance.
         */

        /**
         * Sets the comment marker character, use {@code null} to disable comments.
         * <p>
         * The comment start character is only recognized at the start of a line.
         * </p>
         * <p>
         * Comments are printed first, before headers.
         * </p>
         * <p>
         * Use {@link #setCommentMarker(char)} or {@link #setCommentMarker(Character)} to set the comment marker written at the start of
         * each comment line.
         * </p>
         * <p>
         * If the comment marker is not set, then the header comments are ignored.
         * </p>
         * <p>
         * For example:
         * </p>
         * <pre>
         * builder.setCommentMarker('#')
         *        .setHeaderComments("Generated by Apache Commons CSV", Instant.ofEpochMilli(0));
         * </pre>
         * <p>
         * writes:
         * </p>
         * <pre>
         * # Generated by Apache Commons CSV.
         * # 1970-01-01T00:00:00Z
         * </pre>
         *
         * @param commentMarker the comment start marker, use {@code null} to disable.
         * @return This instance.
         * @throws IllegalArgumentException thrown if the specified character is a line break
         */

        /**
         * Sets the comment marker character, use {@code null} to disable comments.
         * <p>
         * The comment start character is only recognized at the start of a line.
         * </p>
         * <p>
         * Comments are printed first, before headers.
         * </p>
         * <p>
         * Use {@link #setCommentMarker(char)} or {@link #setCommentMarker(Character)} to set the comment marker written at the start of
         * each comment line.
         * </p>
         * <p>
         * If the comment marker is not set, then the header comments are ignored.
         * </p>
         * <p>
         * For example:
         * </p>
         * <pre>
         * builder.setCommentMarker('#')
         *        .setHeaderComments("Generated by Apache Commons CSV", Instant.ofEpochMilli(0));
         * </pre>
         * <p>
         * writes:
         * </p>
         * <pre>
         * # Generated by Apache Commons CSV.
         * # 1970-01-01T00:00:00Z
         * </pre>
         *
         * @param commentMarker the comment start marker, use {@code null} to disable.
         * @return This instance.
         * @throws IllegalArgumentException thrown if the specified character is a line break
         */

        /**
         * Sets the delimiter character.
         *
         * @param delimiter the delimiter character.
         * @return This instance.
         */

        /**
         * Sets the delimiter character.
         *
         * @param delimiter the delimiter character.
         * @return This instance.
         */

        /**
         * Sets the duplicate header names behavior.
         *
         * @param duplicateHeaderMode the duplicate header names behavior
         * @return This instance.
         * @since 1.10.0
         */

        /**
         * Sets the escape character.
         *
         * @param escapeCharacter the escape character.
         * @return This instance.
         * @throws IllegalArgumentException thrown if the specified character is a line break
         */

        /**
         * Sets the escape character.
         *
         * @param escapeCharacter the escape character.
         * @return This instance.
         * @throws IllegalArgumentException thrown if the specified character is a line break
         */

        /**
         * Sets the header defined by the given {@link Enum} class.
         *
         * <p>
         * Example:
         * </p>
         *
         * <pre>
         * public enum HeaderEnum {
         *     Name, Email, Phone
         * }
         *
         * Builder builder = builder.setHeader(HeaderEnum.class);
         * </pre>
         * <p>
         * The header is also used by the {@link CSVPrinter}.
         * </p>
         *
         * @param headerEnum the enum defining the header, {@code null} if disabled, empty if parsed automatically, user-specified otherwise.
         * @return This instance.
         */

        /**
         * Sets the header from the result set metadata. The header can be parsed automatically from the input file with:
         *
         * <pre>
         * builder.setHeader();
         * </pre>
         *
         * or specified manually with:
         *
         * <pre>
         * builder.setHeader(resultSet);
         * </pre>
         * <p>
         * The header is also used by the {@link CSVPrinter}.
         * </p>
         *
         * @param resultSet the resultSet for the header, {@code null} if disabled, empty if parsed automatically, user-specified otherwise.
         * @return This instance.
         * @throws SQLException SQLException if a database access error occurs or this method is called on a closed result set.
         */

        /**
         * Sets the header from the result set metadata. The header can be parsed automatically from the input file with:
         *
         * <pre>
         * builder.setHeader();
         * </pre>
         *
         * or specified manually with:
         *
         * <pre>
         * builder.setHeader(resultSetMetaData);
         * </pre>
         * <p>
         * The header is also used by the {@link CSVPrinter}.
         * </p>
         *
         * @param resultSetMetaData the metaData for the header, {@code null} if disabled, empty if parsed automatically, user-specified otherwise.
         * @return This instance.
         * @throws SQLException SQLException if a database access error occurs or this method is called on a closed result set.
         */

        /**
         * Sets the header to the given values. The header can be parsed automatically from the input file with:
         *
         * <pre>
         * builder.setHeader();
         * </pre>
         *
         * or specified manually with:
         *
         * <pre>
         * builder.setHeader(&quot;name&quot;, &quot;email&quot;, &quot;phone&quot;);
         * </pre>
         * <p>
         * The header is also used by the {@link CSVPrinter}.
         * </p>
         *
         * @param header the header, {@code null} if disabled, empty if parsed automatically, user-specified otherwise.
         * @return This instance.
         */

        /**
         * Sets the header comments to write before the CSV data.
         * <p>
         * This setting is ignored by the parser.
         * </p>
         * <p>
         * Comments are printed first, before headers.
         * </p>
         * <p>
         * Use {@link #setCommentMarker(char)} or {@link #setCommentMarker(Character)} to set the comment marker written at the start of
         * each comment line.
         * </p>
         * <p>
         * If the comment marker is not set, then the header comments are ignored.
         * </p>
         * <p>
         * For example:
         * </p>
         * <pre>
         * builder.setCommentMarker('#')
         *        .setHeaderComments("Generated by Apache Commons CSV", Instant.ofEpochMilli(0));
         * </pre>
         * <p>
         * writes:
         * </p>
         * <pre>
         * # Generated by Apache Commons CSV.
         * # 1970-01-01T00:00:00Z
         * </pre>
         *
         * @param headerComments the headerComments which will be printed by the Printer before the CSV data.
         * @return This instance.
         */

        /**
         * Sets the header comments to write before the CSV data.
         * <p>
         * This setting is ignored by the parser.
         * </p>
         * <p>
         * Comments are printed first, before headers.
         * </p>
         * <p>
         * Use {@link #setCommentMarker(char)} or {@link #setCommentMarker(Character)} to set the comment marker written at the start of
         * each comment line.
         * </p>
         * <p>
         * If the comment marker is not set, then the header comments are ignored.
         * </p>
         * <p>
         * For example:
         * </p>
         * <pre>
         * builder.setCommentMarker('#')
         *        .setHeaderComments("Generated by Apache Commons CSV", Instant.ofEpochMilli(0).toString());
         * </pre>
         * <p>
         * writes:
         * </p>
         * <pre>
         * # Generated by Apache Commons CSV.
         * # 1970-01-01T00:00:00Z
         * </pre>
         *
         * @param headerComments the headerComments which will be printed by the Printer before the CSV data.
         * @return This instance.
         */

        /**
         * Sets the empty line skipping behavior, {@code true} to ignore the empty lines between the records, {@code false} to translate empty lines to empty
         * records.
         *
         * @param ignoreEmptyLines the empty line skipping behavior, {@code true} to ignore the empty lines between the records, {@code false} to translate
         *                         empty lines to empty records.
         * @return This instance.
         */

        /**
         * Sets the parser case mapping behavior, {@code true} to access name/values, {@code false} to leave the mapping as is.
         *
         * @param ignoreHeaderCase the case mapping behavior, {@code true} to access name/values, {@code false} to leave the mapping as is.
         * @return This instance.
         */

        /**
         * Sets the parser trimming behavior, {@code true} to remove the surrounding spaces, {@code false} to leave the spaces as is.
         *
         * @param ignoreSurroundingSpaces the parser trimming behavior, {@code true} to remove the surrounding spaces, {@code false} to leave the spaces as is.
         * @return This instance.
         */

        /**
         * Sets whether reading end-of-file is allowed even when input is malformed, helps Excel compatibility.
         *
         * @param lenientEof whether reading end-of-file is allowed even when input is malformed, helps Excel compatibility.
         * @return This instance.
         * @since 1.11.0
         */

        /**
         * Sets the String to convert to and from {@code null}. No substitution occurs if {@code null}.
         *
         * <ul>
         * <li><strong>Reading:</strong> Converts strings equal to the given {@code nullString} to {@code null} when reading records.</li>
         * <li><strong>Writing:</strong> Writes {@code null} as the given {@code nullString} when writing records.</li>
         * </ul>
         *
         * @param nullString the String to convert to and from {@code null}. No substitution occurs if {@code null}.
         * @return This instance.
         */

        /**
         * Sets the quote character.
         *
         * @param quoteCharacter the quote character.
         * @return This instance.
         */

        /**
         * Sets the quote character, use {@code null} to disable.
         *
         * @param quoteCharacter the quote character, use {@code null} to disable.
         * @return This instance.
         */

        /**
         * Sets the quote policy to use for output.
         *
         * @param quoteMode the quote policy to use for output.
         * @return This instance.
         */

        /**
         * Sets the record separator to use for output.
         *
         * <p>
         * <strong>Note:</strong> This setting is only used during printing and does not affect parsing. Parsing currently only works for inputs with '\n', '\r'
         * and "\r\n"
         * </p>
         *
         * @param recordSeparator the record separator to use for output.
         * @return This instance.
         */

        /**
         * Sets the record separator to use for output.
         *
         * <p>
         * <strong>Note:</strong> This setting is only used during printing and does not affect parsing. Parsing currently only works for inputs with '\n', '\r'
         * and "\r\n"
         * </p>
         *
         * @param recordSeparator the record separator to use for output.
         * @return This instance.
         */

        /**
         * Sets whether to skip the header record.
         *
         * @param skipHeaderRecord whether to skip the header record.
         * @return This instance.
         */

        /**
         * Sets whether reading trailing data is allowed in records, helps Excel compatibility.
         *
         * @param trailingData whether reading trailing data is allowed in records, helps Excel compatibility.
         * @return This instance.
         * @since 1.11.0
         */

        /**
         * Sets whether to add a trailing delimiter.
         *
         * @param trailingDelimiter whether to add a trailing delimiter.
         * @return This instance.
         */

        /**
         * Sets whether to trim leading and trailing blanks.
         *
         * @param trim whether to trim leading and trailing blanks.
         * @return This instance.
         */
    }

    /**
     * Predefines formats.
     *
     * @since 1.2
     */
    public enum Predefined {

        /**
         * @see CSVFormat#DEFAULT
         */
        Default(DEFAULT),

        /**
         * @see CSVFormat#EXCEL
         */
        Excel(EXCEL),

        /**
         * @see CSVFormat#INFORMIX_UNLOAD
         * @since 1.3
         */
        InformixUnload(INFORMIX_UNLOAD),

        /**
         * @see CSVFormat#INFORMIX_UNLOAD_CSV
         * @since 1.3
         */
        InformixUnloadCsv(INFORMIX_UNLOAD_CSV),

        /**
         * @see CSVFormat#MONGODB_CSV
         * @since 1.7
         */
        MongoDBCsv(MONGODB_CSV),

        /**
         * @see CSVFormat#MONGODB_TSV
         * @since 1.7
         */
        MongoDBTsv(MONGODB_TSV),

        /**
         * @see CSVFormat#MYSQL
         */
        MySQL(MYSQL),

        /**
         * @see CSVFormat#ORACLE
         */
        Oracle(ORACLE),

        /**
         * @see CSVFormat#POSTGRESQL_CSV
         * @since 1.5
         */
        PostgreSQLCsv(POSTGRESQL_CSV),

        /**
         * @see CSVFormat#POSTGRESQL_CSV
         */
        PostgreSQLText(POSTGRESQL_TEXT),

        /**
         * @see CSVFormat#RFC4180
         */
        RFC4180(CSVFormat.RFC4180),

        /**
         * @see CSVFormat#TDF
         */
        TDF(CSVFormat.TDF);

        private final CSVFormat format;

        Predefined(final CSVFormat format) {
            this.format = format;
        }

        /**
         * Gets the format.
         *
         * @return the format.
         */
    }

    /**
     * Standard Comma Separated Value format, as for {@link #RFC4180} but allowing
     * empty lines.
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter(',')}</li>
     * <li>{@code setQuote('"')}</li>
     * <li>{@code setRecordSeparator("\r\n")}</li>
     * <li>{@code setIgnoreEmptyLines(true)}</li>
     * <li>{@code setDuplicateHeaderMode(DuplicateHeaderMode.ALLOW_ALL)}</li>
     * </ul>
     *
     * @see Predefined#Default
     */
    public static final CSVFormat DEFAULT = new CSVFormat(Constants.COMMA, Constants.DOUBLE_QUOTE_CHAR, null, null, null, false, true, Constants.CRLF, null,
            null, null, false, false, false, false, false, false, DuplicateHeaderMode.ALLOW_ALL, false, false);

    /**
     * Excel file format (using a comma as the value delimiter). Note that the actual value delimiter used by Excel is locale-dependent, it might be necessary
     * to customize this format to accommodate your regional settings.
     *
     * <p>
     * For example for parsing or generating a CSV file on a French system the following format will be used:
     * </p>
     *
     * <pre>
     * CSVFormat fmt = CSVFormat.EXCEL.withDelimiter(';');
     * </pre>
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter(',')}</li>
     * <li>{@code setQuote('"')}</li>
     * <li>{@code setRecordSeparator("\r\n")}</li>
     * <li>{@code setDuplicateHeaderMode(DuplicateHeaderMode.ALLOW_ALL)}</li>
     * <li>{@code setIgnoreEmptyLines(false)}</li>
     * <li>{@code setAllowMissingColumnNames(true)}</li>
     * <li>{@code setTrailingData(true)}</li>
     * <li>{@code setLenientEof(true)}</li>
     * </ul>
     * <p>
     * Note: This is currently like {@link #RFC4180} plus {@link Builder#setAllowMissingColumnNames(boolean) Builder#setAllowMissingColumnNames(true)} and
     * {@link Builder#setIgnoreEmptyLines(boolean) Builder#setIgnoreEmptyLines(false)}.
     * </p>
     *
     * @see Predefined#Excel
     */
    // @formatter:off
    public static final CSVFormat EXCEL = DEFAULT.builder()
            .setIgnoreEmptyLines(false)
            .setAllowMissingColumnNames(true)
            .setTrailingData(true)
            .setLenientEof(true)
            .build();
    // @formatter:on

    /**
     * Default Informix CSV UNLOAD format used by the {@code UNLOAD TO file_name} operation.
     *
     * <p>
     * This is a comma-delimited format with an LF character as the line separator. Values are not quoted and special characters are escaped with {@code '\'}.
     * The default NULL string is {@code "\\N"}.
     * </p>
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter(',')}</li>
     * <li>{@code setEscape('\\')}</li>
     * <li>{@code setQuote("\"")}</li>
     * <li>{@code setRecordSeparator('\n')}</li>
     * </ul>
     *
     * @see Predefined#MySQL
     * @see <a href= "http://www.ibm.com/support/knowledgecenter/SSBJG3_2.5.0/com.ibm.gen_busug.doc/c_fgl_InOutSql_UNLOAD.htm">
     *      http://www.ibm.com/support/knowledgecenter/SSBJG3_2.5.0/com.ibm.gen_busug.doc/c_fgl_InOutSql_UNLOAD.htm</a>
     * @since 1.3
     */
    // @formatter:off
    public static final CSVFormat INFORMIX_UNLOAD = DEFAULT.builder()
            .setDelimiter(Constants.PIPE)
            .setEscape(Constants.BACKSLASH)
            .setQuote(Constants.DOUBLE_QUOTE_CHAR)
            .setRecordSeparator(Constants.LF)
            .build();
    // @formatter:on

    /**
     * Default Informix CSV UNLOAD format used by the {@code UNLOAD TO file_name} operation (escaping is disabled.)
     *
     * <p>
     * This is a comma-delimited format with an LF character as the line separator. Values are not quoted and special characters are escaped with {@code '\'}.
     * The default NULL string is {@code "\\N"}.
     * </p>
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter(',')}</li>
     * <li>{@code setQuote("\"")}</li>
     * <li>{@code setRecordSeparator('\n')}</li>
     * </ul>
     *
     * @see Predefined#MySQL
     * @see <a href= "http://www.ibm.com/support/knowledgecenter/SSBJG3_2.5.0/com.ibm.gen_busug.doc/c_fgl_InOutSql_UNLOAD.htm">
     *      http://www.ibm.com/support/knowledgecenter/SSBJG3_2.5.0/com.ibm.gen_busug.doc/c_fgl_InOutSql_UNLOAD.htm</a>
     * @since 1.3
     */
    // @formatter:off
    public static final CSVFormat INFORMIX_UNLOAD_CSV = DEFAULT.builder()
            .setDelimiter(Constants.COMMA)
            .setQuote(Constants.DOUBLE_QUOTE_CHAR)
            .setRecordSeparator(Constants.LF)
            .build();
    // @formatter:on

    /**
     * Default MongoDB CSV format used by the {@code mongoexport} operation.
     * <p>
     * <b>Parsing is not supported yet.</b>
     * </p>
     *
     * <p>
     * This is a comma-delimited format. Values are double quoted only if needed and special characters are escaped with {@code '"'}. A header line with field
     * names is expected.
     * </p>
     * <p>
     * As of 2024-04-05, the MongoDB documentation for {@code mongoimport} states:
     * </p>
     * <blockquote>The csv parser accepts that data that complies with RFC <a href="https://tools.ietf.org/html/4180">RFC-4180</a>.
     * As a result, backslashes are not a valid escape character. If you use double-quotes to enclose fields in the CSV data, you must escape
     * internal double-quote marks by prepending another double-quote.
     * </blockquote>
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter(',')}</li>
     * <li>{@code setEscape('"')}</li>
     * <li>{@code setQuote('"')}</li>
     * <li>{@code setQuoteMode(QuoteMode.ALL_NON_NULL)}</li>
     * <li>{@code setSkipHeaderRecord(false)}</li>
     * </ul>
     *
     * @see Predefined#MongoDBCsv
     * @see <a href="https://docs.mongodb.com/manual/reference/program/mongoexport/">MongoDB mongoexport command documentation</a>
     * @since 1.7
     */
    // @formatter:off
    public static final CSVFormat MONGODB_CSV = DEFAULT.builder()
            .setDelimiter(Constants.COMMA)
            .setEscape(Constants.DOUBLE_QUOTE_CHAR)
            .setQuote(Constants.DOUBLE_QUOTE_CHAR)
            .setQuoteMode(QuoteMode.MINIMAL)
            .setSkipHeaderRecord(false)
            .build();
    // @formatter:off

    /**
     * Default MongoDB TSV format used by the {@code mongoexport} operation.
     * <p>
     * <b>Parsing is not supported yet.</b>
     * </p>
     *
     * <p>
     * This is a tab-delimited format. Values are double quoted only if needed and special
     * characters are escaped with {@code '"'}. A header line with field names is expected.
     * </p>
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter('\t')}</li>
     * <li>{@code setEscape('"')}</li>
     * <li>{@code setQuote('"')}</li>
     * <li>{@code setQuoteMode(QuoteMode.ALL_NON_NULL)}</li>
     * <li>{@code setSkipHeaderRecord(false)}</li>
     * </ul>
     *
     * @see Predefined#MongoDBCsv
     * @see <a href="https://docs.mongodb.com/manual/reference/program/mongoexport/">MongoDB mongoexport command
     *          documentation</a>
     * @since 1.7
     */
    // @formatter:off
    public static final CSVFormat MONGODB_TSV = DEFAULT.builder()
            .setDelimiter(Constants.TAB)
            .setEscape(Constants.DOUBLE_QUOTE_CHAR)
            .setQuote(Constants.DOUBLE_QUOTE_CHAR)
            .setQuoteMode(QuoteMode.MINIMAL)
            .setSkipHeaderRecord(false)
            .build();
    // @formatter:off

    /**
     * Default MySQL format used by the {@code SELECT INTO OUTFILE} and {@code LOAD DATA INFILE} operations.
     *
     * <p>
     * This is a tab-delimited format with an LF character as the line separator. Values are not quoted and special
     * characters are escaped with {@code '\'}. The default NULL string is {@code "\\N"}.
     * </p>
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter('\t')}</li>
     * <li>{@code setEscape('\\')}</li>
     * <li>{@code setIgnoreEmptyLines(false)}</li>
     * <li>{@code setQuote(null)}</li>
     * <li>{@code setRecordSeparator('\n')}</li>
     * <li>{@code setNullString("\\N")}</li>
     * <li>{@code setQuoteMode(QuoteMode.ALL_NON_NULL)}</li>
     * </ul>
     *
     * @see Predefined#MySQL
     * @see <a href="https://dev.mysql.com/doc/refman/5.1/en/load-data.html"> https://dev.mysql.com/doc/refman/5.1/en/load
     *      -data.html</a>
     */
    // @formatter:off
    public static final CSVFormat MYSQL = DEFAULT.builder()
            .setDelimiter(Constants.TAB)
            .setEscape(Constants.BACKSLASH)
            .setIgnoreEmptyLines(false)
            .setQuote(null)
            .setRecordSeparator(Constants.LF)
            .setNullString(Constants.SQL_NULL_STRING)
            .setQuoteMode(QuoteMode.ALL_NON_NULL)
            .build();
    // @formatter:off

    /**
     * Default Oracle format used by the SQL*Loader utility.
     *
     * <p>
     * This is a comma-delimited format with the system line separator character as the record separator. Values are
     * double quoted when needed and special characters are escaped with {@code '"'}. The default NULL string is
     * {@code ""}. Values are trimmed.
     * </p>
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter(',') // default is {@code FIELDS TERMINATED BY ','}}</li>
     * <li>{@code setEscape('\\')}</li>
     * <li>{@code setIgnoreEmptyLines(false)}</li>
     * <li>{@code setQuote('"')  // default is {@code OPTIONALLY ENCLOSED BY '"'}}</li>
     * <li>{@code setNullString("\\N")}</li>
     * <li>{@code setTrim()}</li>
     * <li>{@code setSystemRecordSeparator()}</li>
     * <li>{@code setQuoteMode(QuoteMode.MINIMAL)}</li>
     * </ul>
     *
     * @see Predefined#Oracle
     * @see <a href="https://s.apache.org/CGXG">Oracle CSV Format Specification</a>
     * @since 1.6
     */
    // @formatter:off
    public static final CSVFormat ORACLE = DEFAULT.builder()
            .setDelimiter(Constants.COMMA)
            .setEscape(Constants.BACKSLASH)
            .setIgnoreEmptyLines(false)
            .setQuote(Constants.DOUBLE_QUOTE_CHAR)
            .setNullString(Constants.SQL_NULL_STRING)
            .setTrim(true)
            .setRecordSeparator(System.lineSeparator())
            .setQuoteMode(QuoteMode.MINIMAL)
            .build();
    // @formatter:off

    /**
     * Default PostgreSQL CSV format used by the {@code COPY} operation.
     *
     * <p>
     * This is a comma-delimited format with an LF character as the line separator. Values are double quoted and special
     * characters are not escaped. The default NULL string is {@code ""}.
     * </p>
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter(',')}</li>
     * <li>{@code setEscape(null)}</li>
     * <li>{@code setIgnoreEmptyLines(false)}</li>
     * <li>{@code setQuote('"')}</li>
     * <li>{@code setRecordSeparator('\n')}</li>
     * <li>{@code setNullString("")}</li>
     * <li>{@code setQuoteMode(QuoteMode.ALL_NON_NULL)}</li>
     * </ul>
     *
     * @see Predefined#MySQL
     * @see <a href="https://www.postgresql.org/docs/current/static/sql-copy.html">PostgreSQL COPY command
     *          documentation</a>
     * @since 1.5
     */
    // @formatter:off
    public static final CSVFormat POSTGRESQL_CSV = DEFAULT.builder()
            .setDelimiter(Constants.COMMA)
            .setEscape(null)
            .setIgnoreEmptyLines(false)
            .setQuote(Constants.DOUBLE_QUOTE_CHAR)
            .setRecordSeparator(Constants.LF)
            .setNullString(Constants.EMPTY)
            .setQuoteMode(QuoteMode.ALL_NON_NULL)
            .build();
    // @formatter:off

    /**
     * Default PostgreSQL text format used by the {@code COPY} operation.
     *
     * <p>
     * This is a tab-delimited format with an LF character as the line separator. Values are not quoted and special
     * characters are escaped with {@code '\\'}. The default NULL string is {@code "\\N"}.
     * </p>
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter('\t')}</li>
     * <li>{@code setEscape('\\')}</li>
     * <li>{@code setIgnoreEmptyLines(false)}</li>
     * <li>{@code setQuote(null)}</li>
     * <li>{@code setRecordSeparator('\n')}</li>
     * <li>{@code setNullString("\\N")}</li>
     * <li>{@code setQuoteMode(QuoteMode.ALL_NON_NULL)}</li>
     * </ul>
     *
     * @see Predefined#MySQL
     * @see <a href="https://www.postgresql.org/docs/current/static/sql-copy.html">PostgreSQL COPY command
     *          documentation</a>
     * @since 1.5
     */
    // @formatter:off
    public static final CSVFormat POSTGRESQL_TEXT = DEFAULT.builder()
            .setDelimiter(Constants.TAB)
            .setEscape(Constants.BACKSLASH)
            .setIgnoreEmptyLines(false)
            .setQuote(null)
            .setRecordSeparator(Constants.LF)
            .setNullString(Constants.SQL_NULL_STRING)
            .setQuoteMode(QuoteMode.ALL_NON_NULL)
            .build();
    // @formatter:off

    /**
     * Comma separated format as defined by <a href="https://tools.ietf.org/html/rfc4180">RFC 4180</a>.
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter(',')}</li>
     * <li>{@code setQuote('"')}</li>
     * <li>{@code setRecordSeparator("\r\n")}</li>
     * <li>{@code setIgnoreEmptyLines(false)}</li>
     * </ul>
     *
     * @see Predefined#RFC4180
     */
    public static final CSVFormat RFC4180 = DEFAULT.builder().setIgnoreEmptyLines(false).build();

    private static final long serialVersionUID = 2L;

    /**
     * Tab-delimited format.
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter('\t')}</li>
     * <li>{@code setQuote('"')}</li>
     * <li>{@code setRecordSeparator("\r\n")}</li>
     * <li>{@code setIgnoreSurroundingSpaces(true)}</li>
     * </ul>
     *
     * @see Predefined#TDF
     */
    // @formatter:off
    public static final CSVFormat TDF = DEFAULT.builder()
            .setDelimiter(Constants.TAB)
            .setIgnoreSurroundingSpaces(true)
            .build();
    // @formatter:on

    /**
     * Null-safe clone of an array.
     *
     * @param <T>    The array element type.
     * @param values the source array
     * @return the cloned array.
     */
    @SafeVarargs

    /**
     * Returns true if the given string contains the search char.
     *
     * @param source the string to check.
     * @param searchCh the character to search.
     *
     * @return true if {@code c} contains a line break character
     */

    /**
     * Returns true if the given string contains a line break character.
     *
     * @param source the string to check.
     *
     * @return true if {@code c} contains a line break character.
     */


    /**
     * Returns true if the given character is a line break character.
     *
     * @param c the character to check.
     *
     * @return true if {@code c} is a line break character.
     */

    /**
     * Returns true if the given character is a line break character.
     *
     * @param c the character to check, may be null.
     *
     * @return true if {@code c} is a line break character (and not null).
     */

    /** Same test as in as {@link String#trim()}. */

    /** Same test as in as {@link String#trim()}. */

    /**
     * Creates a new CSV format with the specified delimiter.
     *
     * <p>
     * Use this method if you want to create a CSVFormat from scratch. All fields but the delimiter will be initialized with null/false.
     * </p>
     *
     * @param delimiter the char used for value separation, must not be a line break character
     * @return a new CSV format.
     * @throws IllegalArgumentException if the delimiter is a line break character
     *
     * @see #DEFAULT
     * @see #RFC4180
     * @see #MYSQL
     * @see #EXCEL
     * @see #TDF
     */



    /**
     * Gets one of the predefined formats from {@link CSVFormat.Predefined}.
     *
     * @param format name
     * @return one of the predefined formats
     * @since 1.2
     */

    private final DuplicateHeaderMode duplicateHeaderMode;

    private final boolean allowMissingColumnNames;

    private final boolean autoFlush;

    /** Set to null if commenting is disabled. */
    private final Character commentMarker;

    private final String delimiter;

    /** Set to null if escaping is disabled. */
    private final Character escapeCharacter;

    /** Array of header column names. */
    private final String[] headers;

    /** Array of header comment lines. */
    private final String[] headerComments;

    private final boolean ignoreEmptyLines;

    /** Should ignore header names case. */
    private final boolean ignoreHeaderCase;

    /** TODO Should leading/trailing spaces be ignored around values?. */
    private final boolean ignoreSurroundingSpaces;

    /** The string to be used for null values. */
    private final String nullString;

    /** Set to null if quoting is disabled. */
    private final Character quoteCharacter;

    private final String quotedNullString;

    private final QuoteMode quoteMode;

    /** For output. */
    private final String recordSeparator;

    private final boolean skipHeaderRecord;

    private final boolean lenientEof;

    private final boolean trailingData;

    private final boolean trailingDelimiter;

    private final boolean trim;


    /**
     * Creates a customized CSV format.
     *
     * @param delimiter               the char used for value separation, must not be a line break character.
     * @param quoteChar               the Character used as value encapsulation marker, may be {@code null} to disable.
     * @param quoteMode               the quote mode.
     * @param commentStart            the Character used for comment identification, may be {@code null} to disable.
     * @param escape                  the Character used to escape special characters in values, may be {@code null} to disable.
     * @param ignoreSurroundingSpaces {@code true} when whitespaces enclosing values should be ignored.
     * @param ignoreEmptyLines        {@code true} when the parser should skip empty lines.
     * @param recordSeparator         the line separator to use for output.
     * @param nullString              the line separator to use for output.
     * @param headerComments          the comments to be printed by the Printer before the actual CSV data..
     * @param header                  the header.
     * @param skipHeaderRecord        if {@code true} the header row will be skipped.
     * @param allowMissingColumnNames if {@code true} the missing column names are allowed when parsing the header line.
     * @param ignoreHeaderCase        if {@code true} header names will be accessed ignoring case when parsing input.
     * @param trim                    if {@code true} next record value will be trimmed.
     * @param trailingDelimiter       if {@code true} the trailing delimiter wil be added before record separator (if set)..
     * @param autoFlush               if {@code true} the underlying stream will be flushed before closing.
     * @param duplicateHeaderMode     the behavior when handling duplicate headers.
     * @param trailingData            whether reading trailing data is allowed in records, helps Excel compatibility.
     * @param lenientEof              whether reading end-of-file is allowed even when input is malformed, helps Excel compatibility.
     * @throws IllegalArgumentException if the delimiter is a line break character.
     */
    private CSVFormat(final String delimiter, final Character quoteChar, final QuoteMode quoteMode, final Character commentStart, final Character escape,
            final boolean ignoreSurroundingSpaces, final boolean ignoreEmptyLines, final String recordSeparator, final String nullString,
            final Object[] headerComments, final String[] header, final boolean skipHeaderRecord, final boolean allowMissingColumnNames,
            final boolean ignoreHeaderCase, final boolean trim, final boolean trailingDelimiter, final boolean autoFlush,
            final DuplicateHeaderMode duplicateHeaderMode, final boolean trailingData, final boolean lenientEof) {
        this.delimiter = delimiter;
        this.quoteCharacter = quoteChar;
        this.quoteMode = quoteMode;
        this.commentMarker = commentStart;
        this.escapeCharacter = escape;
        this.ignoreSurroundingSpaces = ignoreSurroundingSpaces;
        this.allowMissingColumnNames = allowMissingColumnNames;
        this.ignoreEmptyLines = ignoreEmptyLines;
        this.recordSeparator = recordSeparator;
        this.nullString = nullString;
        this.headerComments = toStringArray(headerComments);
        this.headers = clone(header);
        this.skipHeaderRecord = skipHeaderRecord;
        this.ignoreHeaderCase = ignoreHeaderCase;
        this.lenientEof = lenientEof;
        this.trailingData = trailingData;
        this.trailingDelimiter = trailingDelimiter;
        this.trim = trim;
        this.autoFlush = autoFlush;
        this.quotedNullString = quoteCharacter + nullString + quoteCharacter;
        this.duplicateHeaderMode = duplicateHeaderMode;
        validate();
    }



    /**
     * Creates a new Builder for this instance.
     *
     * @return a new Builder.
     */

    /**
     * Creates a copy of this instance.
     *
     * @return a copy of this instance.
     */

    @Override


    /**
     * Formats the specified values.
     *
     * @param values the values to format
     * @return the formatted values
     */


    /**
     * Gets whether duplicate names are allowed in the headers.
     *
     * @return whether duplicate header names are allowed
     * @since 1.7
     * @deprecated Use {@link #getDuplicateHeaderMode()}.
     */
    @Deprecated

    /**
     * Gets whether missing column names are allowed when parsing the header line.
     *
     * @return {@code true} if missing column names are allowed when parsing the header line, {@code false} to throw an {@link IllegalArgumentException}.
     */

    /**
     * Gets whether to flush on close.
     *
     * @return whether to flush on close.
     * @since 1.6
     */

    /**
     * Gets the comment marker character, {@code null} disables comments.
     * <p>
     * The comment start character is only recognized at the start of a line.
     * </p>
     * <p>
     * Comments are printed first, before headers.
     * </p>
     * <p>
     * Use {@link Builder#setCommentMarker(char)} or {@link Builder#setCommentMarker(Character)} to set the comment
     * marker written at the start of each comment line.
     * </p>
     * <p>
     * If the comment marker is not set, then the header comments are ignored.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>
     * builder.setCommentMarker('#')
     *        .setHeaderComments("Generated by Apache Commons CSV", Instant.ofEpochMilli(0));
     * </pre>
     * <p>
     * writes:
     * </p>
     * <pre>
     * # Generated by Apache Commons CSV.
     * # 1970-01-01T00:00:00Z
     * </pre>
     *
     * @return the comment start marker, may be {@code null}
     */

    /**
     * Gets the first character delimiting the values (typically ';', ',' or '\t').
     *
     * @return the first delimiter character.
     * @deprecated Use {@link #getDelimiterString()}.
     */
    @Deprecated

    /**
     * Gets the character delimiting the values (typically ";", "," or "\t").
     *
     * @return the delimiter.
     */

    /**
     * Gets the character delimiting the values (typically ";", "," or "\t").
     *
     * @return the delimiter.
     * @since 1.9.0
     */

    /**
     * Gets how duplicate headers are handled.
     *
     * @return if duplicate header values are allowed, allowed conditionally, or disallowed.
     * @since 1.10.0
     */

    /**
     * Gets the escape character.
     *
     * @return the escape character, may be {@code 0}
     */

    /**
     * Gets the escape character.
     *
     * @return the escape character, may be {@code null}
     */

    /**
     * Gets a copy of the header array.
     *
     * @return a copy of the header array; {@code null} if disabled, the empty array if to be read from the file
     */

    /**
     * Gets a copy of the header comment array to write before the CSV data.
     * <p>
     * This setting is ignored by the parser.
     * </p>
     * <p>
     * Comments are printed first, before headers.
     * </p>
     * <p>
     * Use {@link Builder#setCommentMarker(char)} or {@link Builder#setCommentMarker(Character)} to set the comment
     * marker written at the start of each comment line.
     * </p>
     * <p>
     * If the comment marker is not set, then the header comments are ignored.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>
     * builder.setCommentMarker('#')
     *        .setHeaderComments("Generated by Apache Commons CSV", Instant.ofEpochMilli(0));
     * </pre>
     * <p>
     * writes:
     * </p>
     * <pre>
     * # Generated by Apache Commons CSV.
     * # 1970-01-01T00:00:00Z
     * </pre>
     *
     * @return a copy of the header comment array; {@code null} if disabled.
     */

    /**
     * Gets whether empty lines between records are ignored when parsing input.
     *
     * @return {@code true} if empty lines between records are ignored, {@code false} if they are turned into empty records.
     */

    /**
     * Gets whether header names will be accessed ignoring case when parsing input.
     *
     * @return {@code true} if header names cases are ignored, {@code false} if they are case-sensitive.
     * @since 1.3
     */

    /**
     * Gets whether spaces around values are ignored when parsing input.
     *
     * @return {@code true} if spaces around values are ignored, {@code false} if they are treated as part of the value.
     */

    /**
     * Gets whether reading end-of-file is allowed even when input is malformed, helps Excel compatibility.
     *
     * @return whether reading end-of-file is allowed even when input is malformed, helps Excel compatibility.
     * @since 1.11.0
     */

    /**
     * Gets the String to convert to and from {@code null}.
     * <ul>
     * <li><strong>Reading:</strong> Converts strings equal to the given {@code nullString} to {@code null} when reading records.</li>
     * <li><strong>Writing:</strong> Writes {@code null} as the given {@code nullString} when writing records.</li>
     * </ul>
     *
     * @return the String to convert to and from {@code null}. No substitution occurs if {@code null}
     */

    /**
     * Gets the character used to encapsulate values containing special characters.
     *
     * @return the quoteChar character, may be {@code null}
     */

    /**
     * Gets the quote policy output fields.
     *
     * @return the quote policy
     */

    /**
     * Gets the record separator delimiting output records.
     *
     * @return the record separator
     */

    /**
     * Gets whether to skip the header record.
     *
     * @return whether to skip the header record.
     */

    /**
     * Gets whether reading trailing data is allowed in records, helps Excel compatibility.
     *
     * @return whether reading trailing data is allowed in records, helps Excel compatibility.
     * @since 1.11.0
     */

    /**
     * Gets whether to add a trailing delimiter.
     *
     * @return whether to add a trailing delimiter.
     * @since 1.3
     */

    /**
     * Gets whether to trim leading and trailing blanks. This is used by {@link #print(Object, Appendable, boolean)} Also by
     * {CSVParser#addRecordValue(boolean)}
     *
     * @return whether to trim leading and trailing blanks.
     */

    @Override

    /**
     * Tests whether comments are supported by this format.
     *
     * Note that the comment introducer character is only recognized at the start of a line.
     *
     * @return {@code true} is comments are supported, {@code false} otherwise
     */

    /**
     * Tests whether the next characters constitute a delimiter
     *
     * @param ch0
     *            the first char (index 0).
     * @param charSeq
     *            the match char sequence
     * @param startIndex
     *            where start to match
     * @param delimiter
     *            the delimiter
     * @param delimiterLength
     *            the delimiter length
     * @return true if the match is successful
     */

    /**
     * Tests whether escapes are being processed.
     *
     * @return {@code true} if escapes are processed
     */

    /**
     * Tests whether a null string has been defined.
     *
     * @return {@code true} if a nullString is defined
     */

    /**
     * Tests whether a quoteChar has been defined.
     *
     * @return {@code true} if a quoteChar is defined
     */

    /**
     * Parses the specified content.
     *
     * <p>
     * See also the various static parse methods on {@link CSVParser}.
     * </p>
     *
     * @param reader the input stream
     * @return a parser over a stream of {@link CSVRecord}s.
     * @throws IOException If an I/O error occurs
     */

    /**
     * Prints to the specified output.
     *
     * <p>
     * See also {@link CSVPrinter}.
     * </p>
     *
     * @param out the output.
     * @return a printer to an output.
     * @throws IOException thrown if the optional header cannot be printed.
     */

    /**
     * Prints to the specified {@code File} with given {@code Charset}.
     *
     * <p>
     * See also {@link CSVPrinter}.
     * </p>
     *
     * @param out     the output.
     * @param charset A charset.
     * @return a printer to an output.
     * @throws IOException thrown if the optional header cannot be printed.
     * @since 1.5
     */


    /**
     * Prints the {@code value} as the next value on the line to {@code out}. The value will be escaped or encapsulated as needed. Useful when one wants to
     * avoid creating CSVPrinters. Trims the value if {@link #getTrim()} is true.
     *
     * @param value     value to output.
     * @param out       where to print the value.
     * @param newRecord if this a new record.
     * @throws IOException If an I/O error occurs.
     * @since 1.4
     */


    /**
     * Prints to the specified {@code Path} with given {@code Charset},
     * returns a {@code CSVPrinter} which the caller MUST close.
     *
     * <p>
     * See also {@link CSVPrinter}.
     * </p>
     *
     * @param out     the output.
     * @param charset A charset.
     * @return a printer to an output.
     * @throws IOException thrown if the optional header cannot be printed.
     * @since 1.5
     */


    /**
     * Prints to the {@link System#out}.
     *
     * <p>
     * See also {@link CSVPrinter}.
     * </p>
     *
     * @return a printer to {@link System#out}.
     * @throws IOException thrown if the optional header cannot be printed.
     * @since 1.5
     */

    /**
     * Outputs the trailing delimiter (if set) followed by the record separator (if set).
     *
     * @param appendable where to write
     * @throws IOException If an I/O error occurs.
     * @since 1.4
     */

    /**
     * Prints the given {@code values} to {@code out} as a single record of delimiter-separated values followed by the record separator.
     *
     * <p>
     * The values will be quoted if needed. Quotes and new-line characters will be escaped. This method adds the record separator to the output after printing
     * the record, so there is no need to call {@link #println(Appendable)}.
     * </p>
     *
     * @param appendable    where to write.
     * @param values values to output.
     * @throws IOException If an I/O error occurs.
     * @since 1.4
     */

    /*
     * Note: Must only be called if escaping is enabled, otherwise can throw exceptions.
     */

    /*
     * Note: Must only be called if escaping is enabled, otherwise can throw exceptions.
     */

    /*
     * Note: must only be called if quoting is enabled, otherwise will generate NPE
     */
    // the original object is needed so can check for Number

    /**
     * Always use quotes unless QuoteMode is NONE, so we do not have to look ahead.
     *
     * @param reader What to print
     * @param appendable Where to print it
     * @throws IOException If an I/O error occurs
     */

    @Override


    /**
     * Verifies the validity and consistency of the attributes, and throws an {@link IllegalArgumentException} if necessary.
     * <p>
     * Because an instance can be used for both writing and parsing, not all conditions can be tested here. For example, allowMissingColumnNames is only used
     * for parsing, so it cannot be used here.
     * </p>
     *
     * @throws IllegalArgumentException Throw when any attribute is invalid or inconsistent with other attributes.
     */

    /**
     * Builds a new {@code CSVFormat} that allows duplicate header names.
     *
     * @return a new {@code CSVFormat} that allows duplicate header names
     * @since 1.7
     * @deprecated Use {@link Builder#setAllowDuplicateHeaderNames(boolean) Builder#setAllowDuplicateHeaderNames(true)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with duplicate header names behavior set to the given value.
     *
     * @param allowDuplicateHeaderNames the duplicate header names behavior, true to allow, false to disallow.
     * @return a new {@code CSVFormat} with duplicate header names behavior set to the given value.
     * @since 1.7
     * @deprecated Use {@link Builder#setAllowDuplicateHeaderNames(boolean)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the missing column names behavior of the format set to {@code true}.
     *
     * @return A new CSVFormat that is equal to this but with the specified missing column names behavior.
     * @see Builder#setAllowMissingColumnNames(boolean)
     * @since 1.1
     * @deprecated Use {@link Builder#setAllowMissingColumnNames(boolean) Builder#setAllowMissingColumnNames(true)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the missing column names behavior of the format set to the given value.
     *
     * @param allowMissingColumnNames the missing column names behavior, {@code true} to allow missing column names in the header line, {@code false} to cause
     *                                an {@link IllegalArgumentException} to be thrown.
     * @return A new CSVFormat that is equal to this but with the specified missing column names behavior.
     * @deprecated Use {@link Builder#setAllowMissingColumnNames(boolean)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with whether to flush on close.
     *
     * @param autoFlush whether to flush on close.
     *
     * @return A new CSVFormat that is equal to this but with the specified autoFlush setting.
     * @since 1.6
     * @deprecated Use {@link Builder#setAutoFlush(boolean)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the comment start marker of the format set to the specified character.
     *
     * Note that the comment start character is only recognized at the start of a line.
     *
     * @param commentMarker the comment start marker
     * @return A new CSVFormat that is equal to this one but with the specified character as the comment start marker
     * @throws IllegalArgumentException thrown if the specified character is a line break
     * @deprecated Use {@link Builder#setCommentMarker(char)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the comment start marker of the format set to the specified character.
     *
     * Note that the comment start character is only recognized at the start of a line.
     *
     * @param commentMarker the comment start marker, use {@code null} to disable
     * @return A new CSVFormat that is equal to this one but with the specified character as the comment start marker
     * @throws IllegalArgumentException thrown if the specified character is a line break
     * @deprecated Use {@link Builder#setCommentMarker(Character)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the delimiter of the format set to the specified character.
     *
     * @param delimiter the delimiter character
     * @return A new CSVFormat that is equal to this with the specified character as a delimiter
     * @throws IllegalArgumentException thrown if the specified character is a line break
     * @deprecated Use {@link Builder#setDelimiter(char)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the escape character of the format set to the specified character.
     *
     * @param escape the escape character
     * @return A new CSVFormat that is equal to this but with the specified character as the escape character
     * @throws IllegalArgumentException thrown if the specified character is a line break
     * @deprecated Use {@link Builder#setEscape(char)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the escape character of the format set to the specified character.
     *
     * @param escape the escape character, use {@code null} to disable
     * @return A new CSVFormat that is equal to this but with the specified character as the escape character
     * @throws IllegalArgumentException thrown if the specified character is a line break
     * @deprecated Use {@link Builder#setEscape(Character)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} using the first record as header.
     *
     * <p>
     * Calling this method is equivalent to calling:
     * </p>
     *
     * <pre>
     * CSVFormat format = aFormat.withHeader().withSkipHeaderRecord();
     * </pre>
     *
     * @return A new CSVFormat that is equal to this but using the first record as header.
     * @see Builder#setSkipHeaderRecord(boolean)
     * @see Builder#setHeader(String...)
     * @since 1.3
     * @deprecated Use {@link Builder#setHeader(String...) Builder#setHeader()}.{@link Builder#setSkipHeaderRecord(boolean) setSkipHeaderRecord(true)}.
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the header of the format defined by the enum class.
     *
     * <p>
     * Example:
     * </p>
     *
     * <pre>
     * public enum Header {
     *     Name, Email, Phone
     * }
     *
     * CSVFormat format = aformat.withHeader(Header.class);
     * </pre>
     * <p>
     * The header is also used by the {@link CSVPrinter}.
     * </p>
     *
     * @param headerEnum the enum defining the header, {@code null} if disabled, empty if parsed automatically, user specified otherwise.
     * @return A new CSVFormat that is equal to this but with the specified header
     * @see Builder#setHeader(String...)
     * @see Builder#setSkipHeaderRecord(boolean)
     * @since 1.3
     * @deprecated Use {@link Builder#setHeader(Class)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the header of the format set from the result set metadata. The header can either be parsed automatically from the
     * input file with:
     *
     * <pre>
     * CSVFormat format = aformat.withHeader();
     * </pre>
     *
     * or specified manually with:
     *
     * <pre>
     * CSVFormat format = aformat.withHeader(resultSet);
     * </pre>
     * <p>
     * The header is also used by the {@link CSVPrinter}.
     * </p>
     *
     * @param resultSet the resultSet for the header, {@code null} if disabled, empty if parsed automatically, user-specified otherwise.
     * @return A new CSVFormat that is equal to this but with the specified header
     * @throws SQLException SQLException if a database access error occurs or this method is called on a closed result set.
     * @since 1.1
     * @deprecated Use {@link Builder#setHeader(ResultSet)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the header of the format set from the result set metadata. The header can either be parsed automatically from the
     * input file with:
     *
     * <pre>
     * CSVFormat format = aformat.withHeader();
     * </pre>
     *
     * or specified manually with:
     *
     * <pre>
     * CSVFormat format = aformat.withHeader(metaData);
     * </pre>
     * <p>
     * The header is also used by the {@link CSVPrinter}.
     * </p>
     *
     * @param resultSetMetaData the metaData for the header, {@code null} if disabled, empty if parsed automatically, user specified otherwise.
     * @return A new CSVFormat that is equal to this but with the specified header
     * @throws SQLException SQLException if a database access error occurs or this method is called on a closed result set.
     * @since 1.1
     * @deprecated Use {@link Builder#setHeader(ResultSetMetaData)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the header of the format set to the given values. The header can either be parsed automatically from the input file
     * with:
     *
     * <pre>
     * CSVFormat format = aformat.withHeader();
     * </pre>
     *
     * or specified manually with:
     *
     * <pre>
     * CSVFormat format = aformat.withHeader(&quot;name&quot;, &quot;email&quot;, &quot;phone&quot;);
     * </pre>
     * <p>
     * The header is also used by the {@link CSVPrinter}.
     * </p>
     *
     * @param header the header, {@code null} if disabled, empty if parsed automatically, user-specified otherwise.
     * @return A new CSVFormat that is equal to this but with the specified header
     * @see Builder#setSkipHeaderRecord(boolean)
     * @deprecated Use {@link Builder#setHeader(String...)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the header comments of the format set to the given values. The comments will be printed first, before the headers.
     * This setting is ignored by the parser.
     *
     * <pre>
     * CSVFormat format = aformat.withHeaderComments(&quot;Generated by Apache Commons CSV.&quot;, Instant.now());
     * </pre>
     *
     * @param headerComments the headerComments which will be printed by the Printer before the actual CSV data.
     * @return A new CSVFormat that is equal to this but with the specified header
     * @see Builder#setSkipHeaderRecord(boolean)
     * @since 1.1
     * @deprecated Use {@link Builder#setHeaderComments(Object...)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the empty line skipping behavior of the format set to {@code true}.
     *
     * @return A new CSVFormat that is equal to this but with the specified empty line skipping behavior.
     * @see Builder#setIgnoreEmptyLines(boolean)
     * @since 1.1
     * @deprecated Use {@link Builder#setIgnoreEmptyLines(boolean) Builder#setIgnoreEmptyLines(true)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the empty line skipping behavior of the format set to the given value.
     *
     * @param ignoreEmptyLines the empty line skipping behavior, {@code true} to ignore the empty lines between the records, {@code false} to translate empty
     *                         lines to empty records.
     * @return A new CSVFormat that is equal to this but with the specified empty line skipping behavior.
     * @deprecated Use {@link Builder#setIgnoreEmptyLines(boolean)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the header ignore case behavior set to {@code true}.
     *
     * @return A new CSVFormat that will ignore the new case header name behavior.
     * @see Builder#setIgnoreHeaderCase(boolean)
     * @since 1.3
     * @deprecated Use {@link Builder#setIgnoreHeaderCase(boolean) Builder#setIgnoreHeaderCase(true)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with whether header names should be accessed ignoring case.
     *
     * @param ignoreHeaderCase the case mapping behavior, {@code true} to access name/values, {@code false} to leave the mapping as is.
     * @return A new CSVFormat that will ignore case header name if specified as {@code true}
     * @since 1.3
     * @deprecated Use {@link Builder#setIgnoreHeaderCase(boolean)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the parser trimming behavior of the format set to {@code true}.
     *
     * @return A new CSVFormat that is equal to this but with the specified parser trimming behavior.
     * @see Builder#setIgnoreSurroundingSpaces(boolean)
     * @since 1.1
     * @deprecated Use {@link Builder#setIgnoreSurroundingSpaces(boolean) Builder#setIgnoreSurroundingSpaces(true)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the parser trimming behavior of the format set to the given value.
     *
     * @param ignoreSurroundingSpaces the parser trimming behavior, {@code true} to remove the surrounding spaces, {@code false} to leave the spaces as is.
     * @return A new CSVFormat that is equal to this but with the specified trimming behavior.
     * @deprecated Use {@link Builder#setIgnoreSurroundingSpaces(boolean)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with conversions to and from null for strings on input and output.
     * <ul>
     * <li><strong>Reading:</strong> Converts strings equal to the given {@code nullString} to {@code null} when reading records.</li>
     * <li><strong>Writing:</strong> Writes {@code null} as the given {@code nullString} when writing records.</li>
     * </ul>
     *
     * @param nullString the String to convert to and from {@code null}. No substitution occurs if {@code null}
     * @return A new CSVFormat that is equal to this but with the specified null conversion string.
     * @deprecated Use {@link Builder#setNullString(String)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the quoteChar of the format set to the specified character.
     *
     * @param quoteChar the quote character
     * @return A new CSVFormat that is equal to this but with the specified character as quoteChar
     * @throws IllegalArgumentException thrown if the specified character is a line break
     * @deprecated Use {@link Builder#setQuote(char)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the quoteChar of the format set to the specified character.
     *
     * @param quoteChar the quote character, use {@code null} to disable.
     * @return A new CSVFormat that is equal to this but with the specified character as quoteChar
     * @throws IllegalArgumentException thrown if the specified character is a line break
     * @deprecated Use {@link Builder#setQuote(Character)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the output quote policy of the format set to the specified value.
     *
     * @param quoteMode the quote policy to use for output.
     *
     * @return A new CSVFormat that is equal to this but with the specified quote policy
     * @deprecated Use {@link Builder#setQuoteMode(QuoteMode)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the record separator of the format set to the specified character.
     *
     * <p>
     * <strong>Note:</strong> This setting is only used during printing and does not affect parsing. Parsing currently only works for inputs with '\n', '\r' and
     * "\r\n"
     * </p>
     *
     * @param recordSeparator the record separator to use for output.
     * @return A new CSVFormat that is equal to this but with the specified output record separator
     * @deprecated Use {@link Builder#setRecordSeparator(char)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the record separator of the format set to the specified String.
     *
     * <p>
     * <strong>Note:</strong> This setting is only used during printing and does not affect parsing. Parsing currently only works for inputs with '\n', '\r' and
     * "\r\n"
     * </p>
     *
     * @param recordSeparator the record separator to use for output.
     * @return A new CSVFormat that is equal to this but with the specified output record separator
     * @throws IllegalArgumentException if recordSeparator is none of CR, LF or CRLF
     * @deprecated Use {@link Builder#setRecordSeparator(String)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with skipping the header record set to {@code true}.
     *
     * @return A new CSVFormat that is equal to this but with the specified skipHeaderRecord setting.
     * @see Builder#setSkipHeaderRecord(boolean)
     * @see Builder#setHeader(String...)
     * @since 1.1
     * @deprecated Use {@link Builder#setSkipHeaderRecord(boolean) Builder#setSkipHeaderRecord(true)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with whether to skip the header record.
     *
     * @param skipHeaderRecord whether to skip the header record.
     * @return A new CSVFormat that is equal to this but with the specified skipHeaderRecord setting.
     * @see Builder#setHeader(String...)
     * @deprecated Use {@link Builder#setSkipHeaderRecord(boolean)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with the record separator of the format set to the operating system's line separator string, typically CR+LF on Windows
     * and LF on Linux.
     *
     * <p>
     * <strong>Note:</strong> This setting is only used during printing and does not affect parsing. Parsing currently only works for inputs with '\n', '\r' and
     * "\r\n"
     * </p>
     *
     * @return A new CSVFormat that is equal to this but with the operating system's line separator string.
     * @since 1.6
     * @deprecated Use {@link Builder#setRecordSeparator(String) setRecordSeparator(System.lineSeparator())}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} to add a trailing delimiter.
     *
     * @return A new CSVFormat that is equal to this but with the trailing delimiter setting.
     * @since 1.3
     * @deprecated Use {@link Builder#setTrailingDelimiter(boolean) Builder#setTrailingDelimiter(true)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with whether to add a trailing delimiter.
     *
     * @param trailingDelimiter whether to add a trailing delimiter.
     * @return A new CSVFormat that is equal to this but with the specified trailing delimiter setting.
     * @since 1.3
     * @deprecated Use {@link Builder#setTrailingDelimiter(boolean)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} to trim leading and trailing blanks. See {@link #getTrim()} for details of where this is used.
     *
     * @return A new CSVFormat that is equal to this but with the trim setting on.
     * @since 1.3
     * @deprecated Use {@link Builder#setTrim(boolean) Builder#setTrim(true)}
     */
    @Deprecated

    /**
     * Builds a new {@code CSVFormat} with whether to trim leading and trailing blanks. See {@link #getTrim()} for details of where this is used.
     *
     * @param trim whether to trim leading and trailing blanks.
     * @return A new CSVFormat that is equal to this but with the specified trim setting.
     * @since 1.3
     * @deprecated Use {@link Builder#setTrim(boolean)}
     */
    @Deprecated
}
