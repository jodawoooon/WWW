package com.ssafy.api.service;


import com.ssafy.api.mapper.WordcountMapper;
import com.ssafy.api.mapper.WordcountReduce;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.soap.Text;
import java.io.IOException;

@Component
public class ReduceJobsUtils {

    @Value("${hdfs.configuration.server}") String path;

    private static String hdfsPath;

    /**
     * 获取HDFS的配置信息
     * @return
     */
    private static Configuration getConfiguration(){
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS",hdfsPath);
        configuration.set("mapred.job.tracker",hdfsPath);
//        //运行在yarn的集群模式
//        configuration.set("mapreduce.framework.name","yarn");
//        //这个配置是让main方法寻找该机器的mr环境
//        configuration.set("yarn.resourcemanager.hostname","node1");
        return configuration;
    }

    @PostConstruct
    public void getPath(){
        hdfsPath = this.path;
    }
    public static String getHdfsPath(){
        return hdfsPath;
    }

    /**
     * 获取单词一年最高气温计算配置
     * @param jobName
     * @return
     */
    public void getWrodCountJobsConf(String jobName,String outputPath,String inputPath)
            throws IOException,ClassNotFoundException,InterruptedException {
        Configuration configuration = getConfiguration();
        Job job = Job.getInstance(configuration, jobName);
        job.setMapperClass(WordcountMapper.class);
        job.setReducerClass(WordcountReduce.class);
        //map输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //设置最终输出kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //设置输入输出路径
        FileInputFormat.setInputPaths(job,new Path(inputPath));
        FileOutputFormat.setOutputPath(job,new Path(outputPath));
        job.waitForCompletion(true);
        //CombineFileInputFormat切片机制
        //job.setInputFormatClass(CombineFileInputFormat.class);
        //CombineFileInputFormat.setMaxInputSplitSize(job,4194304);
    }

//    public void kvTextJob(String jobName,String inputPath,String output)
//            throws IOException,ClassNotFoundException,InterruptedException{
//        Configuration configuration = getConfiguration();
//        Job job = Job.getInstance(configuration, jobName);
//        //关联mapper和reducer
//        job.setMapperClass(KVTextMapper.class);
//        job.setReducerClass(KVTextReducer.class);
//        //设置map输出kv的类型
//        job.setMapOutputKeyClass(Text.class);
//        job.setMapOutputValueClass(LongWritable.class);
//        //设置最终输出kv的类型
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(LongWritable.class);
//        //设置输入路径
//        FileInputFormat.setInputPaths(job,new Path(inputPath));
//        //设置输入格式
//        job.setInputFormatClass(KeyValueTextInputFormat.class);
//        //设置输出路径
//        FileOutputFormat.setOutputPath(job,new Path(output));
//        //提交job
//        job.waitForCompletion(true);
//    }
//
//    public void NLineJob(String jobName,String inputPath,String outputPath)
//            throws IOException,ClassNotFoundException,InterruptedException{
//        Configuration configuration = getConfiguration();
//        Job job = Job.getInstance(configuration, jobName);
//        //关联mapper和reducer
//        job.setMapperClass(NLineMapper.class);
//        job.setReducerClass(NLineReducer.class);
//        //设置map的输出kv类型
//        job.setMapOutputValueClass(Text.class);
//        job.setMapOutputKeyClass(LongWritable.class);
//        //设置最终输出kv类型
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(LongWritable.class);
//        //设置输入输出数据路径
//        FileInputFormat.setInputPaths(job,new Path(inputPath));
//        FileOutputFormat.setOutputPath(job,new Path(outputPath));
//        //设置每一个切片InputSplit中划分三条记录
//        NLineInputFormat.setNumLinesPerSplit(job,3);
//        //使用NLineInputFormat处理记录数
//        job.setInputFormatClass(NLineInputFormat.class);
//        //提交job
//        job.waitForCompletion(true);
//    }
}
