package com.ssafy.api.service;


import com.ssafy.config.HdfsConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

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
            if (hdfs.exists(folderPath)) {

                Path filePath = new Path(folderPath + "/" + fileName);
                System.out.println(filePath);

                BufferedReader r = new BufferedReader(new InputStreamReader(hdfs.open(filePath)));
                System.out.println(1);

                for(Object line : r.lines().toArray()){
                    System.out.println(line);
                    result += line;
                }
//                String line = null;
//
//                while ((line= r.readLine())!=null) {
//                    System.out.println("line: "+line);
//                    result += line;
//                }
//                System.out.println(result);
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
    public String delete() {

        String result = "";
        String filename = "message.txt";
        try (FileSystem hdfs=FileSystem.get(conf.config())){
            Path folderPath = new Path(hdfs.getHomeDirectory().toString());
            Path filePath = new Path(folderPath + "/" + filename);

            hdfs.delete(filePath, true);
            result = filename + " 파일이 삭제되었어요.. 확인해 보세요..";
        } catch (Exception e) {
            e.printStackTrace();
            result = "오류가 발생했어요";
        }
        return result;
    }
}
