package com.event.hadoop;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.util.List;

public interface MapReduceRunner {

    /**
     * Runs new MR for specified files
     * @param mapper the mapper which should be used
     * @param reducer the reducer which should be used
     * @param inputPaths the list of files which should be processed with MR
     * @return the state of MR
     * @throws Exception
     */
    public boolean run(Class<? extends Mapper> mapper, Class<? extends Reducer> reducer, List<String> inputPaths) throws Exception;
}
