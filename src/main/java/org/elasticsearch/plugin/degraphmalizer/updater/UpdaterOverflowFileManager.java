package org.elasticsearch.plugin.degraphmalizer.updater;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.BlockingQueue;

import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;

public class UpdaterOverflowFileManager {

    private static final ESLogger LOG = Loggers.getLogger(UpdaterOverflowFileManager.class);

    private final String logPath;
    private final String filenamePrefix;
    private final int limit;

    public UpdaterOverflowFileManager(final String logPath, final String index, final int limit) {
        this.logPath = logPath;
        this.filenamePrefix = index + "-overflow-";
        this.limit = limit;
    }

    private final FilenameFilter filenameFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.matches(filenamePrefix + "\\d+");
        }
    };

    private final Comparator<File> fileComparator = new Comparator<File>() {
        @Override
        public int compare(File file1, File file2) {
            return Long.compare(file1.lastModified(), file2.lastModified());
        }
    };

    /**
     * Number of records in overflow files.
     */
    public int size() {
        return getOverflowFiles().length * limit; // TODO: niet elke file bevat per definitie 'limit' regels/items/changes. Regels tellen dus!
    }

    public boolean isEmpty() {
        return getOverflowFiles().length == 0;
    }

    public void clear() {
        for (File file : getOverflowFiles()) {
            if (!file.delete()) {
                try {
                    LOG.error("Error deleting file {}", file.getCanonicalPath());
                }
                catch (IOException e) {
                    LOG.error("Error deleting file {}", file.getName());
                }
            }
        }
    }

    /**
     * Saves the contents of the input queue to disk.
     */
    public void save(final BlockingQueue<DelayedImpl<Change>> queue) {
        File file;
        do {
            file = new File(logPath, filenamePrefix + System.currentTimeMillis());
        } while (file.exists());

        try {
            final PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            int count = 0;
            while (! queue.isEmpty() && count < limit) {
                try {
                    final Change change = queue.take().thing();
                    writer.println(Change.toValue(change));
                    count++;
                } catch (InterruptedException e) {
                    LOG.warn("Take from input queue interrupted: " + e.getMessage());
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LOG.error("Error saving overflow file {}: {}", file, e.getMessage());
        }
    }

    /**
     * Load the contents of the 'first in line' overflow file into the output queue.
     */
    public void load(final BlockingQueue<DelayedImpl<Change>> queue) {
        final File[] files = getOverflowFiles();

        if (files.length > 0) {
            final File file = files[0];
            try {
                final BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                do {
                    final Change change = Change.fromValue(line);
                    try {
                        queue.put(DelayedImpl.immediate(change));
                    } catch (InterruptedException e) {
                        LOG.warn("Put into queue was interrupted: {}", e.getMessage());
                    }
                    line = reader.readLine();
                } while (line != null);
                reader.close();
                if (!file.delete()) {
                    LOG.error("Can not remove file {}", file.getCanonicalPath());
                }
            } catch (IOException e) {
                LOG.error("Error loading overflow file {}: {}", file, e.getMessage());
            }
        }
    }

    /**
     * Array of overflow files sorted by last modified date.
     */
    private File[] getOverflowFiles() {
        final File[] files = new File(logPath).listFiles(filenameFilter);
        Arrays.sort(files, fileComparator);
        return files;
    }
}