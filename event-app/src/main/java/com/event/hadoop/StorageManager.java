package com.event.hadoop;

import java.io.IOException;
import java.util.List;

/**
 * Few methods to work with remote fs for demo
 */
public interface StorageManager {

    /**
     * Saves files to remote fs
     * @param sourceFilePath the path to source file which should be saved
     * @param destinationFile the path to destination file on remote fs
     * @throws IOException
     */
    public void saveFile(String sourceFilePath, String destinationFile) throws IOException;

    /**
     * Deletes folder
     * @param folderPath the path to folder which should be deleted
     * @param recursive the flag if folder should be deleted recursively
     * @throws IOException
     */
    public void deleteFolder(String folderPath, boolean recursive) throws IOException;

    /**
     * Reads the content of file from remote storage
     * @param filePath the path to file to read from
     * @return all lines from file
     * @throws IOException
     */
    public List<String> readFile(String filePath) throws IOException;

}
