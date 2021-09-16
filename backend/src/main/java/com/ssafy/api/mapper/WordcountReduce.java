package com.ssafy.api.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordcountReduce extends Reducer<Text, IntWritable,Text,IntWritable> {
    int sum;
    IntWritable v = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //累计求和
        sum = 0;
        for (IntWritable count : values) {
            sum += count.get();
        }
        v.set(sum);
        context.write(key,v);
    }
}