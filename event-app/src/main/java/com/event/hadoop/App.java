package com.event.hadoop;

import com.event.hadoop.hdfs.HdfsStorageManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;

/**
 * Hello world App for Hadoop
 */
public class App {
    public static void main(String[] args) {

        Configuration configuration = getConfiguration();

        try {

            StorageManager hdfsStorage = new HdfsStorageManager(configuration);

            hdfsStorage.saveFile("some_file_on_local_fs", "testFile");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration(true);

        configuration.set("fs.default.name", "hdfs://localhost:8020");
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        configuration.set("fs.file.impl", "org.apache.hadoop.fs.LocalFileSystem");
        configuration.set("mapred.local.dir", "${HOME}/hadoop_local/");
        configuration.set("hadoop.user.name", "hadoop");
        configuration.set("mapred.job.tracker","localhost:8021");

        System.setProperty("HADOOP_USER_NAME", "hadoop");

        return configuration;
    }
}
