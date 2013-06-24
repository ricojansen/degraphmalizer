/*
 * Copyright (C) 2013 All rights reserved
 * VPRO The Netherlands
 */
package dgm.configuration.groovy;

import dgm.configuration.Configuration;
import dgm.configuration.FixtureConfiguration;
import dgm.configuration.IndexConfig;

import java.util.Map;

/**
 * User: rico
 * Date: 21/06/2013
 */
public class GroovyConfiguration implements Configuration {
    @Override
    public Map<String, ? extends IndexConfig> indices() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FixtureConfiguration getFixtureConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
