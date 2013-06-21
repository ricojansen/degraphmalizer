/*
 * Copyright (C) 2013 All rights reserved
 * VPRO The Netherlands
 */
package dgm.configuration.groovy;

import com.fasterxml.jackson.databind.JsonNode;
import dgm.Subgraph;
import dgm.configuration.IndexConfig;
import dgm.configuration.TypeConfig;
import dgm.configuration.WalkConfig;

import java.util.Map;

/**
 * User: rico
 * Date: 21/06/2013
 */
public class GroovyTypeConfig implements TypeConfig {
    @Override
    public String name() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Subgraph extract(JsonNode document) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JsonNode transform(JsonNode document) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean filter(JsonNode document) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IndexConfig index() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String targetIndex() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String targetType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String sourceIndex() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String sourceType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, WalkConfig> walks() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
