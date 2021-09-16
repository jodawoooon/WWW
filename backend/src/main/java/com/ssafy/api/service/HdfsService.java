package com.ssafy.api.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HdfsService {


    @Value("${hdfs.configuration.server}") String path;
    @Value("${hdfs.configuration.user}") String username;


    private static String hdfsPath;
    private static String hdfsName;
    private static final int bufferSize = 1024 * 1024 * 64;

    @PostConstruct
    public void getPath(){
        hdfsPath = this.path;
    }
    @PostConstruct
    public void getName(){
        hdfsName = this.username;
    }
    public static String  getHdfsPath(){
        return hdfsPath;
    }
    public String getUsername(){
        return username;
    }

    /**
     * 获取HDFS的配置信息
     * @return
     */
    private static Configuration getConfiguration(){
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS",hdfsPath);
        return configuration;
    }
    public FileSystem getFileSystem() throws Exception{
        FileSystem fileSystem = FileSystem.get(new URI(hdfsPath), getConfiguration(), hdfsName);
        return fileSystem;
    }

    /**
     * 在hdfs上创建文件夹
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public boolean mkdir(String path) throws Exception{
        //如果文件路径为空
        if (StringUtils.isEmpty(path)){
            return false;
        }
        //判断文件夹是否已经存在
        if (existFile(path)){
            return true;
        }
        FileSystem fs = getFileSystem();
        //目标路径
        Path srcPath = new Path(path);
        boolean isOk = fs.mkdirs(srcPath);
        fs.close();
        return isOk;
    }

    /**
     * 判断HDFS文件是否已经存在
     * @param path
     * @return
     * @throws Exception
     */
    public boolean existFile(String path) throws Exception{
        //判断路径是否为空
        if (StringUtils.isEmpty(path)){
            return false;
        }
        FileSystem fs = getFileSystem();
        Path srcPath = new Path(path);
        boolean isExists = fs.exists(srcPath);
        return isExists;
    }

    /**
     * 读取HDFS的目录结构
     * @param path
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> readPathInfo(String path) throws Exception{
        if (StringUtils.isEmpty(path)){
            return null;
        }
        //如果文件路径不存在
        if (!existFile(path)){
            return null;
        }
        FileSystem fs = getFileSystem();
        //目标路径
        Path newPath = new Path(path);
        FileStatus[] statusList = fs.listStatus(newPath);
        List<Map<String,Object>> list = new ArrayList<>();
        if (statusList != null && statusList.length > 0){
            for (FileStatus fileStatus : statusList) {
                Map<String,Object> map = new HashMap<>();
                map.put("filePath",fileStatus.getPath());
                map.put("fileStatus",fileStatus.toString());
                list.add(map);
            }
            return list;
        }else {
            return null;
        }
    }

    /**
     * HDFS创建文件
     * @param path 上传路径
     * @param file 要上传的文件
     * @throws Exception
     */
    public void createFile(String path, MultipartFile file) throws Exception{
        if (StringUtils.isEmpty(path) || file.isEmpty()){
            return;
        }
        String filename = file.getOriginalFilename();
        FileSystem fs = getFileSystem();
        //上传时默认当前目录，后面自动拼接文件的目录
        Path newPath = new Path(path + File.separator + filename);
        //打开一个输入流
        FSDataOutputStream outputStream = fs.create(newPath);
        outputStream.write(file.getBytes());
        outputStream.close();
        fs.close();
    }

    /**
     * 读取文件内容
     * @param path
     * @return
     * @throws Exception
     */
    public String readFile(String path) throws Exception{
        if (StringUtils.isEmpty(path)){
            return null;
        }
        if (!existFile(path)){
            return null;
        }
        FileSystem fs = getFileSystem();
        //目标路径
        Path srcPath = new Path(path);
        FSDataInputStream inputStream = null;
        try {
            inputStream = fs.open(srcPath);
            //防止中文乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = "";
            StringBuffer sb = new StringBuffer();
            while ((lineTxt = reader.readLine()) != null){
                sb.append(lineTxt);
            }
            return sb.toString();
        }finally {
            if (inputStream != null){
                inputStream.close();
            }
            fs.close();
        }
    }

    /**
     * 获取文件列表
     * @param path 文件夹路径
     * @return
     * @throws Exception
     */
    public List<Map<String,String>> listFile(String path) throws Exception{
        if (StringUtils.isEmpty(path)){
            return null;
        }
        if (!existFile(path)){
            return null;
        }
        FileSystem fs = getFileSystem();
        //目标路径
        Path srcPath = new Path(path);
        //递归找到所有的文件
        RemoteIterator<LocatedFileStatus> fileList = fs.listFiles(srcPath, true);
        List<Map<String,String>> returnList = new ArrayList<>();
        while (fileList.hasNext()){
            LocatedFileStatus next = fileList.next();
            String fileName = next.getPath().getName();
            Path filePath = next.getPath();
            Map<String,String> map = new HashMap<>();
            map.put("fileName",fileName);
            map.put("filePath",filePath.toString());
            returnList.add(map);
        }
        fs.close();
        return returnList;
    }

    public boolean renameFile(String oldName,String newName) throws Exception{
        if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)){
            return false;
        }
        FileSystem fs = getFileSystem();
        //源文件路径
        Path oldPath = new Path(oldName);
        //重名名目标路径
        Path newPath = new Path(newName);
        boolean isOk = fs.rename(oldPath, newPath);
        fs.close();
        return isOk;
    }

    /**
     * 删除文件
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public boolean deleteFile(String path) throws Exception{
        if (StringUtils.isEmpty(path)){
            return false;
        }
        if (!existFile(path)){
            return false;
        }
        FileSystem fs = getFileSystem();
        Path srcPath = new Path(path);
        boolean isOk = fs.deleteOnExit(srcPath);
        fs.close();
        return isOk;
    }

    /**
     * 上传HDFS文件
     * @param path 上传路径
     * @param uploadPath 目标路径
     * @throws Exception
     */
    public void uploadFile(String path,String uploadPath) throws Exception{
        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(uploadPath)){
            return;
        }
        FileSystem fs = getFileSystem();
        //上传路径
        Path clientPath = new Path(path);
        //目标路径
        Path serverPath = new Path(uploadPath);
        //调用文件系统的文件复制方法，第一个参数是否删除源文件true为删除，默认为false
        fs.copyFromLocalFile(false,clientPath,serverPath);
        fs.close();
    }

    /**
     *
     * @param path 文件路径
     * @param downloadPath 下载路径
     * @throws IOException
     */
    public void downloadFile(String path,String downloadPath) throws Exception{
        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(downloadPath)){
            return;
        }
        FileSystem fs = getFileSystem();
        //文件路径
        Path clientPath = new Path(path);
        //目标路径
        Path serverPath = new Path(downloadPath);
        //调用文件系统的文件复制方法，第一个参数是否删除源文件true为删除，默认为false
        fs.copyToLocalFile(false,clientPath,serverPath);
        fs.close();
    }

    /**
     * HDFS复制文件
     * @param sourcePath 来源路径
     * @param targetPath 目标路径
     * @throws Exception
     */
    public void copyFile(String sourcePath,String targetPath) throws Exception{
        if (StringUtils.isEmpty(sourcePath) || StringUtils.isEmpty(targetPath)){
            return;
        }
        FileSystem fs = getFileSystem();
        //原始文件路径
        Path oldPath = new Path(sourcePath);
        //目标路径
        Path newPath = new Path(targetPath);

        FSDataInputStream inputStream = null;
        FSDataOutputStream outputStream = null;
        try {
            inputStream = fs.open(oldPath);
            outputStream = fs.create(newPath);
            IOUtils.copyBytes(inputStream,outputStream,bufferSize);
        }finally {
            inputStream.close();
            outputStream.close();
            fs.close();
        }
    }

    /**
     * 打开HDFS上的文件并返回java对象
     * @param path path
     * @return
     * @throws Exception
     */
    public String openFileToObject(String path) throws Exception{
        if (StringUtils.isEmpty(path)){
            return null;
        }
        if (!existFile(path)){
            return null;
        }
        String jsonStr = readFile(path);
        return jsonStr;
    }

    /**
     * 获取某个文件在HDFS的集群位置
     * @param path
     * @return
     * @throws Exception
     */
    public BlockLocation[] getFileBlockLocations(String path) throws Exception{
        if (StringUtils.isEmpty(path)){
            return null;
        }
        if (!existFile(path)){
            return null;
        }
        FileSystem fs = getFileSystem();
        Path srcPath = new Path(path);
        FileStatus fileStatus = fs.getFileStatus(srcPath);
        return fs.getFileBlockLocations(fileStatus,0,fileStatus.getLen());
    }
}