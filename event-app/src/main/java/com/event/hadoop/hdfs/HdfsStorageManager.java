package com.event.hadoop.hdfs;

import com.event.hadoop.StorageManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
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
    public List<String> readFile(String filePath) throws IOException {
        return null; //TODO: implement me
    }
}
