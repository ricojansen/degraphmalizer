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
import groovy.lang.MetaClass;
import groovy.lang.MetaMethod;
import groovy.lang.Script;

import java.util.List;
import java.util.Map;

/**
 * User: rico
 * Date: 21/06/2013
 */
public class GroovyTypeConfig implements TypeConfig {
    String name;
    IndexConfig config;
    Script script;

    final String sourceIndex;
    final String sourceType;

    public GroovyTypeConfig(String name, IndexConfig config, Script script) {
        this.name = name;
        this.config = config;
        this.script = script;
        MetaClass metaClass = script.getMetaClass();
        List<MetaMethod> methods = metaClass.getMethods();
        // TODO check if all methods required are present in this script.
        sourceIndex = (String) script.getProperty("sourceIndex");
        sourceType = (String) script.getProperty("sourceType");
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Subgraph extract(JsonNode document) {
        Subgraph subgraph = null;
        script.invokeMethod("extract", new Object[]{document, subgraph});
        return subgraph;
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
        return config;
    }

    @Override
    public String targetIndex() {
        return config.name();
    }

    @Override
    public String targetType() {
        return name();
    }

    @Override
    public String sourceIndex() {
        return sourceIndex;
    }

    @Override
    public String sourceType() {
        return sourceType;
    }

    @Override
    public Map<String, WalkConfig> walks() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer maximalWalkDepth() {
        int result = 0;
        for (WalkConfig walkConfig : walks().values()) {
            if (walkConfig.maxDistance() > result) {
                result = walkConfig.maxDistance();
            }
        }
        return result;
    }

}
