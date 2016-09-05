/*
 * Copyright 2015-2016 Red Hat, Inc. and/or its affiliates
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

package org.hawkular.apm.server.api.utils.zipkin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hawkular.apm.api.model.Property;
import org.hawkular.apm.api.model.events.CommunicationDetails;
import org.hawkular.apm.api.model.events.CompletionTime;
import org.hawkular.apm.api.model.events.NodeDetails;
import org.hawkular.apm.api.model.events.SourceInfo;
import org.hawkular.apm.api.model.trace.Component;
import org.hawkular.apm.api.model.trace.Consumer;
import org.hawkular.apm.api.model.trace.Node;
import org.hawkular.apm.api.model.trace.Producer;
import org.hawkular.apm.api.model.trace.Trace;

/**
 * @author Pavol Loffay
 */
public class MappingResult implements Serializable {

    /**
     * Mapping for component type {@link Component#componentType}
     */
    private final String componentType;
    /**
     * Mapping for endpoint type is {@link Producer#endpointType} and @see {@link Consumer#endpointType}.
     */
    private final String endpointType;
    /**
     * Mapping for details map {@link Node#details} and {@link NodeDetails#details}.
     */
    private final Map<String, String> nodeDetails;
    /**
     * Mapping for property {@link Trace#properties}, and its derived objects e.g.
     * {@link CommunicationDetails#properties},
     * {@link CompletionTime#properties},
     * {@link NodeDetails#properties},
     * {@link SourceInfo#properties}.
     */
    private final List<Property> properties;

    public MappingResult(String componentType, String endpointType,
                         Map<String, String> nodeDetails,
                         List<Property> properties) {
        this.componentType = componentType;
        this.endpointType = endpointType;
        this.nodeDetails = Collections.unmodifiableMap(nodeDetails);
        this.properties = Collections.unmodifiableList(properties);
    }

    public MappingResult() {
        this.componentType = null;
        this.endpointType = null;
        this.nodeDetails = Collections.emptyMap();
        this.properties = Collections.emptyList();
    }

    public String getComponentType() {
        return componentType;
    }

    public String getEndpointType() {
        return endpointType;
    }

    public Map<String, String> getNodeDetails() {
        return nodeDetails;
    }

    public List<Property> getProperties() {
        return properties;
    }

    static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String componentType;
        private String endpointType;
        private Map<String, String> nodeDetails = new HashMap<>();
        private List<Property> properties = new ArrayList<>();

        private Builder() {}

        public Builder withComponentType(String componentType) {
            this.componentType = componentType;
            return this;
        }

        public Builder withEndpointType(String endpointType) {
            this.endpointType = endpointType;
            return this;
        }

        public Builder withNodeDetails(Map<String, String> nodeDetails) {
            this.nodeDetails = nodeDetails;
            return this;
        }

        public Builder withProperties(List<Property> properties) {
            this.properties = properties;
            return this;
        }

        public Builder addNodeDetail(String key, String value) {
            nodeDetails.put(key, value);
            return this;
        }

        public Builder addProperty(Property property) {
            properties.add(property);
            return this;
        }

        public MappingResult build() {
            return new MappingResult(componentType, endpointType, nodeDetails, properties);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MappingResult)) return false;

        MappingResult that = (MappingResult) o;

        if (componentType != null ? !componentType.equals(that.componentType) : that.componentType != null)
            return false;
        if (endpointType != null ? !endpointType.equals(that.endpointType) : that.endpointType != null) return false;
        if (nodeDetails != null ? !nodeDetails.equals(that.nodeDetails) : that.nodeDetails != null) return false;
        return properties != null ? properties.equals(that.properties) : that.properties == null;

    }

    @Override
    public int hashCode() {
        int result = componentType != null ? componentType.hashCode() : 0;
        result = 31 * result + (endpointType != null ? endpointType.hashCode() : 0);
        result = 31 * result + (nodeDetails != null ? nodeDetails.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }
}
