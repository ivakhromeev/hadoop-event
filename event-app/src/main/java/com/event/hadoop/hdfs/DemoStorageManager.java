package com.event.hadoop.hdfs;

import com.event.hadoop.StorageManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DemoStorageManager implements StorageManager {

    private Configuration conf = null;

    public DemoStorageManager(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public void saveFile(String sourceFilePath, String destinationFile) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteFolder(String folderPath, boolean recursive) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> readFromFile(String filePath) throws IOException {

        FileSystem fs = FileSystem.get(conf);
        Path file = new Path(filePath);

        List<String> result = new ArrayList<>();

        /*try (BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fs.open(file))))) {
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
        }*/

        try (SequenceFile.Reader reader = new SequenceFile.Reader(fs, file, conf)) {
            NullWritable key = NullWritable.get();
            Text value = (Text) reader.getValueClass().newInstance();

            while (reader.next(key, value)) {
                result.add(value.toString());
            }

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void writeIntoFile(String filePath, List<String> textToWrite) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
