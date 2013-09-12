/*
 * Copyright (C) 2013 All rights reserved
 * VPRO The Netherlands
 */
package dgm.configuration.groovy;

import com.fasterxml.jackson.databind.JsonNode;
import dgm.configuration.PropertyConfig;
import dgm.configuration.WalkConfig;
import dgm.modules.elasticsearch.ResolvedPathElement;
import dgm.trees.Tree;


/**
 * User: rico
 * Date: 21/06/2013
 */
public class GroovyPropertyConfig implements PropertyConfig {
    @Override
    public String name() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JsonNode reduce(Tree<ResolvedPathElement> tree) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public WalkConfig walk() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
