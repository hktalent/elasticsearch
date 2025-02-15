/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */
package org.elasticsearch.xpack.sql.proto;

import org.elasticsearch.xpack.sql.proto.xcontent.ConstructingObjectParser;
import org.elasticsearch.xpack.sql.proto.xcontent.ParseField;
import org.elasticsearch.xpack.sql.proto.xcontent.XContentParser;

import java.util.Objects;

import static org.elasticsearch.xpack.sql.proto.xcontent.ConstructingObjectParser.optionalConstructorArg;

/**
 * Response to the request to clean all SQL resources associated with the cursor for JDBC/CLI client
 */
public class SqlClearCursorResponse {

    public static final ParseField SUCCEEDED = new ParseField("succeeded");
    public static final ConstructingObjectParser<SqlClearCursorResponse, Void> PARSER = new ConstructingObjectParser<>(
        SqlClearCursorResponse.class.getName(),
        true,
        objects -> new SqlClearCursorResponse(objects[0] == null ? false : (boolean) objects[0])
    );

    static {
        PARSER.declareBoolean(optionalConstructorArg(), SUCCEEDED);
    }

    private final boolean succeeded;

    public SqlClearCursorResponse(boolean succeeded) {
        this.succeeded = succeeded;
    }

    /**
     * @return Whether the attempt to clear a cursor was successful.
     */
    public boolean isSucceeded() {
        return succeeded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlClearCursorResponse response = (SqlClearCursorResponse) o;
        return succeeded == response.succeeded;
    }

    @Override
    public int hashCode() {
        return Objects.hash(succeeded);
    }

    public static SqlClearCursorResponse fromXContent(XContentParser parser) {
        return PARSER.apply(parser, null);
    }

}
