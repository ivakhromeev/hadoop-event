package com.event.hadoop.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
    public static final String WORDS_SPLIT_REGEXP = "[\\W]";

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = value.toString().split(WORDS_SPLIT_REGEXP);

        for (String word : words) {
            context.write(new Text(word), new LongWritable(1));
        }
    }
}
