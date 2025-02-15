/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */

package org.elasticsearch.xpack.sql.proto;

import org.elasticsearch.test.ESTestCase;
import org.elasticsearch.xpack.sql.proto.xcontent.DeprecationHandler;
import org.elasticsearch.xpack.sql.proto.xcontent.NamedXContentRegistry;
import org.elasticsearch.xpack.sql.proto.xcontent.XContentParser;
import org.elasticsearch.xpack.sql.proto.xcontent.json.JsonXContent;

import java.io.IOException;

public class ProtoUtilsTests extends ESTestCase {

    public void testGenericValueParsing() throws IOException {

        String json = ProtoUtils.toString((builder, params) -> {
            builder.field("int", 42);
            builder.field("double", 42.5);
            builder.field("string", "foobar");
            builder.nullField("null");
            return builder;
        });

        XContentParser parser = JsonXContent.jsonXContent.createParser(
            NamedXContentRegistry.EMPTY,
            DeprecationHandler.THROW_UNSUPPORTED_OPERATION,
            json
        );

        assertEquals(XContentParser.Token.START_OBJECT, parser.nextToken());
        while (parser.nextToken() != XContentParser.Token.END_OBJECT) {
            assertEquals(XContentParser.Token.FIELD_NAME, parser.currentToken());
            String fieldName = parser.currentName();
            parser.nextToken();
            Object val = ProtoUtils.parseFieldsValue(parser);
            switch (fieldName) {
                case "int":
                    assertEquals(42, val);
                    break;
                case "double":
                    assertEquals(42.5, val);
                    break;
                case "string":
                    assertEquals("foobar", val);
                    break;
                case "null":
                    assertNull(val);
                    break;
                default:
                    fail("Unexpected value " + fieldName);
            }
        }
        assertNull(parser.nextToken());

    }

}
