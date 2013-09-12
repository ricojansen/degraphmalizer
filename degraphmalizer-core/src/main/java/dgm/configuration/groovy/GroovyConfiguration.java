/*
 * Copyright (C) 2013 All rights reserved
 * VPRO The Netherlands
 */
package dgm.configuration.groovy;

import dgm.configuration.Configuration;
import dgm.configuration.Configurations;
import dgm.configuration.IndexConfig;
import dgm.exceptions.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: rico
 * Date: 21/06/2013
 */
public class GroovyConfiguration implements Configuration {
    private static final Logger LOG = LoggerFactory.getLogger(GroovyConfiguration.class);

    private final Map<String, GroovyIndexConfig> indices = new HashMap<String, GroovyIndexConfig>();

    public GroovyConfiguration(String directory, URL... libraries) throws IOException {
        LOG.info("Reading {} with libraries {}", directory, Arrays.asList(libraries));
        final List<String> directories = Configurations.listDirectories(directory);
        if (directories == null) {
            throw new ConfigurationException("Configuration directory " + directory + " does not exist");
        }

        for (String dir : directories) {
            // each subdirectory encodes an index
            String[] dirArray = dir.split("/");
            String dirname = dirArray[dirArray.length - 1];
            LOG.info("Reading {} ({})", dir, dirname);

            indices.put(dirname, new GroovyIndexConfig(dirname, dir, libraries));
        }
    }

    @Override
    public Map<String, ? extends IndexConfig> indices() {
        return indices;
    }
}
