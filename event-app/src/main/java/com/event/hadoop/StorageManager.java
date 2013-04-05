package com.event.hadoop;

import java.io.IOException;
import java.util.List;

/**
 * Few methods to work with remote fs for demo
 */
public interface StorageManager {

    /**
     * Saves files to remote fs
     * @param sourceFilePath the path to the source file which should be saved
     * @param destinationFile the path to the destination file on remote fs
     * @throws IOException
     */
    public void saveFile(String sourceFilePath, String destinationFile) throws IOException;

    /**
     * Deletes folder
     * @param folderPath the path to the folder which should be deleted
     * @param recursive the flag if folder should be deleted recursively
     * @throws IOException
     */
    public void deleteFolder(String folderPath, boolean recursive) throws IOException;

    /**
     * Reads the content of file from remote storage
     * @param filePath the path to the file to read from
     * @return all lines from file
     * @throws IOException
     */
    public List<String> readFromFile(String filePath) throws IOException;

    /**
     * Writes text into specified file
     * @param filePath the path to the file to write into
     * @param textToWrite the list of the strings which should be written into file
     */
    public void writeIntoFile(String filePath, List<String> textToWrite) throws IOException;

}
