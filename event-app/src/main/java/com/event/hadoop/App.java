package com.event.hadoop;

import com.event.hadoop.hdfs.HdfsStorageManager;
import com.event.hadoop.mr.BaseMapReduceRunner;
import com.event.hadoop.mr.WordCountMapper;
import com.event.hadoop.mr.WordCountReducer;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world App for Hadoop
 */
public class App {
    public static void main(String[] args) {

        Configuration configuration = getConfiguration();

        try {

            testIO(configuration);

            //testMR(configuration);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testIO(Configuration configuration) throws IOException {
        StorageManager hdfsStorage = new HdfsStorageManager(configuration);

        hdfsStorage.deleteFolder("folder", true);

        hdfsStorage.saveFile("/home/hadoop/test", "folder/plainTextFile");
        //hdfsStorage.writeIntoFile("folder/seqFile.seq", prepareTestText());
    }

    private static void testMR(Configuration configuration) throws Exception {
        MapReduceRunner runner = new BaseMapReduceRunner(configuration);
        runner.run(WordCountMapper.class, WordCountReducer.class, Collections.singletonList("folder/plainTextFile"));

        //TODO: read results
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration(true);

        configuration.set("fs.default.name", "hdfs://localhost:8020");
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        configuration.set("fs.file.impl", "org.apache.hadoop.fs.LocalFileSystem");
        configuration.set("mapred.local.dir", "/home/hadoop/hadoop_local/");
        configuration.set("mapred.job.tracker","localhost:8021");

        System.setProperty("HADOOP_USER_NAME", "hadoop");

        return configuration;
    }

    private static List<String> prepareTestText() {
        List<String> text = new ArrayList<>();

        text.add("1");
        text.add("2");
        text.add("3");

        return Collections.unmodifiableList(text);
    }
}
