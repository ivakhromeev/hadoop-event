package com.event.hadoop.mr;

import com.event.hadoop.MapReduceRunner;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.List;

public class BaseMapReduceRunner implements MapReduceRunner {
    public static final String RESULT_FOLDER = "result";

    private Configuration conf = null;

    public BaseMapReduceRunner(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public boolean run(Class<? extends Mapper> mapper, Class<? extends Reducer> reducer, List<String> inputPaths) throws Exception {
        String jobName = "test MR " + System.currentTimeMillis();

        Job job = createJob(jobName, mapper, reducer);
        addInputPathToJob(job, inputPaths);

        job.waitForCompletion(false);

        return job.isSuccessful();
    }

    /**
     * Adds files to job as input for MR
     * @param job the job to add files in
     * @param inputPaths the list of files which should be added to input for MR
     */
    private void addInputPathToJob(Job job, List<String> inputPaths) {
        for (String path : inputPaths) {
            MultipleInputs.addInputPath(job, new Path(path), TextInputFormat.class);
        }
    }

    /**
     * Creates MR job to run in Hadoop
     * @param jobName the name of the job
     * @param mapper the mapper which should be used in job
     * @param reducer the reducer which should be used in job
     * @return new job
     * @throws IOException
     */
    private Job createJob(String jobName, Class<? extends Mapper> mapper, Class<? extends Reducer> reducer) throws IOException {
        Job job = new Job(conf);
        job.setJobName(jobName);

        job.setJarByClass(mapper);

        job.setMapperClass(mapper);
        job.setReducerClass(reducer);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        Path resultFolder = getResultFolder(jobName);
        FileOutputFormat.setOutputPath(job, resultFolder);

        return job;
    }

    private Path getResultFolder(String jobName) {
        StringBuilder sb = new StringBuilder(RESULT_FOLDER);
        sb.append(Path.SEPARATOR).append(jobName);

        return new Path(sb.toString());
    }
}
