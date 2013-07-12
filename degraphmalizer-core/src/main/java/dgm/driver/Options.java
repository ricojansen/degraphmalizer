package dgm.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.beust.jcommander.Parameter;

public class Options {
    @Parameter(names = {"-t", "--transport"}, description = "Run against remote ES (host, port, cluster)",
        arity = 3)
    List<String> transport = new ArrayList<String>();

    @Parameter(names = {"-d", "--development"}, description = "Run in development mode")
    boolean development = false;

    @Parameter(names = {"-b", "--bind"}, description = "Host/IP to bind the listening ports to")
    String bindhost = null;

    @Parameter(names = {"-p", "--port"}, description = "Listening port")
    int port;

    @Parameter(names = {"-c", "--config"}, description = "Specify configuration directory")
    String config;

    @Parameter(names = {"-g", "--graphdb"}, description = "Specify graph DB storage directory")
    String graphdb;

    @Parameter(names = {"-j", "--jmx"}, description = "Enable JMX monitoring bean")
    boolean jmx;

    @Parameter(names = {"-r", "--reload"}, description = "Enable automatic configuration reloading")
    boolean reloading;

    @Parameter(names = {"-L", "--logback"}, description = "Specify logback configuration file")
    String logbackConf = "logback.xml";

    @Parameter(names = {"-f", "--fixtures"}, description = "Load fixtures on startup")
    String fixtures;

    @Parameter(names = {"-l", "--jslib"}, description = "Load Javascript library from this resource if its name ends with '.js'. If the resource is not on the class path, it will be interpreted as an URL. So to load a file it should start with file://.  If the name of the resource is  INDEX, it will interpret every line of the file as another library to load")
    List<String> libraries = new ArrayList<String>();

    @Parameter(names = {"-?", "--help"}, description = "Show command line options", help = true)
    boolean help;

    public Options() {
        this(System.getProperties());
    }

    /**
     * You can pass the system properties here, this will then be used as defaults that can be overridden using the CLI.
     */
    public Options(Properties properties) {
        port = Integer.parseInt(properties.getProperty("degraphmalizer.port", "9821"));
        bindhost = properties.getProperty("degraphmalizer.host");

        jmx = Boolean.parseBoolean(properties.getProperty("degraphmalizer.jmx.enabled"));
        reloading = Boolean.parseBoolean(properties.getProperty("degraphmalizer.autoreload"));
        fixtures = properties.getProperty("degraphmalizer.fixtures");

        // try to set the defaults for a cluster
        transport.add(properties.getProperty("elasticsearch.host", "localhost"));
        transport.add(properties.getProperty("elasticsearch.port", "9300"));
        transport.add(properties.getProperty("elasticsearch.cluster", "elasticsearch"));

        config = properties.getProperty("paths.config", "classpath:conf");
        graphdb = properties.getProperty("paths.graphdb", "data/graphdb");
    }

    /**
     * Get array of javascript library file names.
     */
    public String[] libraries() {
        return libraries.toArray(new String[libraries.size()]);
    }
}
