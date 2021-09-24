package com.ssafy.api.service;


import com.ssafy.config.HdfsConfig;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Repository
public class HdfsApiService {

    private HdfsConfig conf;

    @Autowired
    public HdfsApiService(HdfsConfig conf){
        this.conf = conf;
    }


    public String read(String fileName) {
        System.out.println(1);

        String result = "";
        FileSystem hdfs = null;
        try {
            hdfs = FileSystem.get(conf.config());
            Path folderPath = new Path(hdfs.getHomeDirectory().toString());
            System.out.println(folderPath);

            Path filePath = new Path(folderPath + "/wordcount_test_out/" + fileName);
            if (hdfs.exists(filePath)) {

                System.out.println(filePath);
                //Init input stream
//                FSDataInputStream inputStream = hdfs.open(filePath);
//                System.out.println(inputStream);
////Classical input stream usage
//
//                String out= IOUtils.toString(inputStream, "UTF-8");
//                System.out.println(out);
//
//                inputStream.close();

                System.out.println(filePath);
//
                BufferedReader r = new BufferedReader(new InputStreamReader(hdfs.open(filePath), "UTF-8"));
                System.out.println(1);

                System.out.println(r.readLine());

                System.out.println(1);
                String line = null;

                while ((line= r.readLine())!=null) {
                    System.out.println("line: "+line);
                    result += line;
                }
                System.out.println(result);
            } else {
                result = "파일이 존재하지 않습니다!!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "오류가 발생했어요";
        } finally {
            try {
                if (hdfs != null)
                    hdfs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    public static void writeFileToHDFS() throws Exception {
        // Impersonates user "root" to avoid performance problems. You should replace it
        // with user that you are running your HDFS cluster with
        System.setProperty("HADOOP_USER_NAME", "root");

        // Path that we need to create in HDFS. Just like Unix/Linux file systems, HDFS file system starts with "/"
        final Path path = new Path("/user/j5a605/tutorials-links.txt");

        // Uses try with resources in order to avoid close calls on resources
        // Creates anonymous sub class of DistributedFileSystem to allow calling initialize as DFS will not be usable otherwise
        try(final DistributedFileSystem dFS = new DistributedFileSystem() {
            {
                initialize(new URI("hdfs://cluster2.p.ssafy.io:9000"), new Configuration());
            }
        };
            // Gets output stream for input path using DFS instance
            final FSDataOutputStream streamWriter = dFS.create(path);
            // Wraps output stream into PrintWriter to use high level and sophisticated methods
            final PrintWriter writer = new PrintWriter(streamWriter);) {
            // Writes tutorials information to file using print writer
            writer.println("Getting Started with Apache Spark => http://www.allprogrammingtutorials.com/tutorials/getting-started-with-apache-spark.php");
            writer.println("Developing Java Applications in Apache Spark => http://www.allprogrammingtutorials.com/tutorials/developing-java-applications-in-spark.php");
            writer.println("Getting Started with RDDs in Apache Spark => http://www.allprogrammingtutorials.com/tutorials/getting-started-with-rdds-in-spark.php");

            System.out.println("File Written to HDFS successfully!");
        }
    }

    public static void readFileFromHDFS() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://cluster2.p.ssafy.io:9000");
        FileSystem fileSystem = FileSystem.get(configuration);
        //Create a path
        String fileName = "a.txt";
        Path hdfsReadPath = new Path("/user/j5a605/" + fileName);
        //Init input stream
        FSDataInputStream inputStream = fileSystem.open(hdfsReadPath);
        //Classical input stream usage
        String out= IOUtils.toString(inputStream, "UTF-8");
        System.out.println(out);
        /*BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line = null;
        while ((line=bufferedReader.readLine())!=null){
            System.out.println(line);
        }*/
        inputStream.close();
        fileSystem.close();
    }

    public String write(String msg) {

        String result = "";
        String filename = "message1.txt";
        FileSystem hdfs = null;

        try {
            hdfs = FileSystem.get(conf.config());
            Path folderPath = new Path(hdfs.getHomeDirectory().toString());

            FileStatus[] inputFiles = hdfs.listStatus(folderPath);
            for (FileStatus f : inputFiles){
                System.out.println(f);
            }
            Path filePath = new Path(folderPath + "/" + filename);
            if (hdfs.exists(filePath)) {
                hdfs.delete(filePath, true);
            }

            FSDataOutputStream out = hdfs.create(filePath, false);
            System.out.println(msg);
            out.writeUTF(msg);

            out.close();

            result = filename + "으로 저장 완료되었어요.. 하둡가서 확인해 보세요..";
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
            result = "오류가 발생했어요";
        } finally {
            try {
                if (hdfs != null)
                    hdfs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }

}
