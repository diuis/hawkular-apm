/*
 * Copyright 2015-2017 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.apm.examples.vertx.opentracing.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.opentracing.propagation.TextMap;
import io.vertx.core.json.JsonObject;

/**
 * @author gbrown
 */
public final class VertxMessageExtractAdapter implements TextMap {
    private final Map<String, String> map;

    public VertxMessageExtractAdapter(final JsonObject obj) {
        // Convert to single valued map
        this.map = new HashMap<>();

        JsonObject header = obj.getJsonObject("_apmHeader");
        if (header != null) {
            header.forEach(entry -> map.put(entry.getKey(), entry.getValue().toString()));
            // Remove header
            obj.remove("_apmHeader");
        }
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return map.entrySet().iterator();
    }

    @Override
    public void put(String key, String value) {
        throw new UnsupportedOperationException(
                "VertxMessageExtractAdapter should only be used with Tracer.extract()");
    }
}
