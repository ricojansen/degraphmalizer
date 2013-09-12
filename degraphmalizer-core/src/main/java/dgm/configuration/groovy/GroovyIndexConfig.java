/*
 * Copyright (C) 2013 All rights reserved
 * VPRO The Netherlands
 */
package dgm.configuration.groovy;

import com.google.common.base.Predicate;
import dgm.configuration.Configurations;
import dgm.configuration.IndexConfig;
import dgm.configuration.TypeConfig;
import dgm.exceptions.ConfigurationException;
import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
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
public class GroovyIndexConfig implements IndexConfig {

    private static final Logger LOG = LoggerFactory.getLogger(GroovyIndexConfig.class);

    final String index;
    final Map<String, GroovyTypeConfig> types = new HashMap<String, GroovyTypeConfig>();

    GroovyScriptEngine engine;

    public GroovyIndexConfig(String index, String directory, URL... libraries) throws IOException {
        this.index = index;
        LOG.info("Index: {}, directory: {}, libraries {}", new Object[]{index, directory, Arrays.asList(libraries)});

        engine = new GroovyScriptEngine(libraries);

        // non recursively load all configuration files
        final Predicate<URL> filenameFilter = new Predicate<URL>() {
            @Override
            public boolean apply(URL url) {
                return url.getFile().endsWith(".conf.js");

            }
        };

        final List<URL> configFiles = Configurations.list(directory, filenameFilter);
        if (configFiles == null) {
            throw new ConfigurationException("Configuration directory " + directory + " can not be read");
        }
        LOG.info("{}: Found config files for index [{}]", directory, configFiles);
        for (URL file : configFiles) {
            final String type = file.getFile().replaceFirst(".conf.groovy", "").replaceFirst(".*/", "");
            Script script;
            Binding binding = new Binding();
            // Add global variables to the binding here if needed
            try {
                script = engine.createScript(file.getFile(), binding);
                types.put(type, new GroovyTypeConfig(type, this,script));
            } catch (ResourceException e) {
                LOG.error("Can't load script: "+file);
            } catch (ScriptException e) {
                LOG.error("Can't parse script: "+file+", "+e.getMessage());
            }
        }
    }

    @Override
    public String name() {
        return name();
    }

    @Override
    public Map<String, ? extends TypeConfig> types() {
        return types;
    }
}