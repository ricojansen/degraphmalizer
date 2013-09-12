/*
 * Copyright (C) 2013 All rights reserved
 * VPRO The Netherlands
 */
package dgm.configuration.groovy;

import com.fasterxml.jackson.databind.JsonNode;
import dgm.configuration.FixtureTypeConfiguration;


/**
 * User: rico
 * Date: 21/06/2013
 */
public class GroovyFixtureTypeConfiguration implements FixtureTypeConfiguration {
    @Override
    public JsonNode getMapping() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<JsonNode> getDocuments() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<String> getDocumentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JsonNode getDocumentById(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasDocuments() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
