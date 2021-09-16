package com.ssafy.api.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


public class WordcountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    Text k = new Text();
    IntWritable v = new IntWritable(1);

    /**
     * 读取文件
     * @param key 默认情况下，是mapreduce所读取的一行文版的起始偏移量
     * @param value 默认情况下，是mapreduce所读取的一行文本的内容，
     * @param context 是用户自定义逻辑处理完成后输出的key,在此处是单词
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取一行
        String line = value.toString();
        //切割
        String[] words = line.split(" ");
        //输出
        for (String word : words) {
            k.set(word);
            context.write(k,v);
        }
    }
}