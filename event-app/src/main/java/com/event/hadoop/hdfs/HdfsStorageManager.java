package com.event.hadoop.hdfs;

import com.event.hadoop.StorageManager;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.compress.DefaultCodec;

import java.io.*;
import java.util.List;

public class HdfsStorageManager implements StorageManager {

    private Configuration conf = null;

    public HdfsStorageManager(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public void saveFile(String sourceFilePath, String destinationFile) throws IOException {
        FileSystem fs = FileSystem.get(conf);

        fs.copyFromLocalFile(new Path(sourceFilePath), new Path(destinationFile));
    }

    @Override
    public void deleteFolder(String folderPath, boolean recursive) throws IOException {
        FileSystem fs = FileSystem.get(conf);

        fs.delete(new Path(folderPath), recursive);
    }

    @Override
    public void writeIntoFile(String filePath, List<String> textToWrite) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path file = new Path(filePath);

        try (OutputStream outputStream = fs.append(file)) {
            for (String line : textToWrite) {
                outputStream.write(line.getBytes());
            }

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*try (SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, file,
                NullWritable.class, Text.class, SequenceFile.CompressionType.NONE, new DefaultCodec()) ) {

            for (String line : textToWrite) {
                writer.append(NullWritable.get(), new Text(line));
            }
        }*/
    }

    @Override
    public List<String> readFromFile(String filePath) throws IOException {
        return null; //TODO: implement me
    }
}
