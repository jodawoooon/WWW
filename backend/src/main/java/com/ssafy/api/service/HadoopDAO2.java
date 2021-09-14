package com.ssafy.api.service;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Repository
public class HadoopDAO2 {



    public String read(String name) {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFs", "hdfs://cluster2.p.ssafy.io:9000");
        String result = "";
        FileSystem hdfs = null;
        try {
            hdfs = FileSystem.get(conf);
            Path filePath = new Path(name);
            if (hdfs.exists(filePath)) {
                BufferedReader r = new BufferedReader(new InputStreamReader(hdfs.open(filePath), "utf-8"));
                String line = null;
                while ((line = r.readLine()) != null) {
                    System.out.println(line);
                    result += line;
                }
                r.close();
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
    public String write(String msg) {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFs", "hdfs://cluster2.p.ssafy.io:9000");
        String result = "";
        String filename = "/j5a605/message.txt";
        FileSystem hdfs = null;
        try {
            hdfs = FileSystem.get(conf);
            Path filePath = new Path(filename);
            if (hdfs.exists(filePath)) {
                hdfs.delete(filePath, true);
            }
            FSDataOutputStream out = hdfs.create(filePath, false);
            out.write(msg.getBytes("utf-8"));
            out.flush();
            result = filename + "으로 저장 완료되었어요.. 하둡가서 확인해 보세요..";
            out.close();
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
    public String delete() {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFs", "hdfs://cluster2.p.ssafy.io:9000");
        String result = "";
        String filename = "/j5a605/message.txt";
        try (FileSystem hdfs=FileSystem.get(conf)){
            Path filePath = new Path(filename);
            hdfs.delete(filePath, true);
            result = filename + " 파일이 삭제되었어요.. 확인해 보세요..";
        } catch (Exception e) {
            e.printStackTrace();
            result = "오류가 발생했어요";
        }
        return result;
    }
}
