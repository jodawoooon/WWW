package com.ssafy.api.service;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Repository
public class HadoopDAO {

    public String readwrite(String name) {
        String result = "";
        Configuration conf = new Configuration();
        conf.set("fs.defaultFs", "hdfs://cluster2.p.ssafy.io:9000");

        FileSystem hdfs = null;
        try  {
            hdfs = FileSystem.get(conf);
            Path filePath = new Path("/j5a605/"+name);
            if(hdfs.exists(filePath)) {
                BufferedReader r = new BufferedReader(new InputStreamReader(hdfs.open(filePath), "utf-8"));
                String line = null;
                while((line=r.readLine())!=null) {
                    System.out.println(line);
                    result += line;
                }
                r.close();
            } else {
                FSDataOutputStream out = hdfs.create(filePath, false);
                out.write("스프링에서 하둡에 보낸 데이터임!!".getBytes("utf-8"));
                out.flush();
                result = filePath.getName() + "으로 저장 완료되었어요. 하둡가서 확인해보세요.";
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
