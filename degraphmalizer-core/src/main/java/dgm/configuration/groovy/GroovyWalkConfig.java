/*
 * Copyright (C) 2013 All rights reserved
 * VPRO The Netherlands
 */
package dgm.configuration.groovy;

import com.tinkerpop.blueprints.Direction;
import dgm.configuration.PropertyConfig;
import dgm.configuration.TypeConfig;
import dgm.configuration.WalkConfig;

import java.util.Map;

/**
 * User: rico
 * Date: 21/06/2013
 */
public class GroovyWalkConfig implements WalkConfig {
    @Override
    public Direction direction() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeConfig type() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, ? extends PropertyConfig> properties() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String name() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
