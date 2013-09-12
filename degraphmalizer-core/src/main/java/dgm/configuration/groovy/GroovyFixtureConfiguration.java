/*
 * Copyright (C) 2013 All rights reserved
 * VPRO The Netherlands
 */
package dgm.configuration.groovy;

import dgm.configuration.FixtureConfiguration;
import dgm.configuration.FixtureIndexConfiguration;

import java.io.File;

/**
 * User: rico
 * Date: 21/06/2013
 */
public class GroovyFixtureConfiguration implements FixtureConfiguration {
    @Override
    public Iterable<String> getIndexNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FixtureIndexConfiguration getIndexConfig(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<String> getExpectedIndexNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FixtureIndexConfiguration getExpectedIndexConfig(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public File getResultsDirectory() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}