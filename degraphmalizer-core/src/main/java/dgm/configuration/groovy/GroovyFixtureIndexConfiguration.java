/*
 * Copyright (C) 2013 All rights reserved
 * VPRO The Netherlands
 */
package dgm.configuration.groovy;

import dgm.configuration.FixtureIndexConfiguration;
import dgm.configuration.FixtureTypeConfiguration;

/**
 * User: rico
 * Date: 21/06/2013
 */
public class GroovyFixtureIndexConfiguration implements FixtureIndexConfiguration {
    @Override
    public Iterable<String> getTypeNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FixtureTypeConfiguration getTypeConfig(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<FixtureTypeConfiguration> getTypeConfigurations() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
