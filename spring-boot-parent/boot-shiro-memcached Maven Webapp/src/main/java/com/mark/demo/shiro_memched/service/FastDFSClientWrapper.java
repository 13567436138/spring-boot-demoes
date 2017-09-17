package com.mark.demo.shiro_memched.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.MateData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.mark.demo.shiro_memched.utils.StringUtils;

/*
*hxp(hxpwangyi@126.com)
*2017年9月12日
*客户端主要包括以下接口： 
TrackerClient - TrackerServer接口 
GenerateStorageClient - 一般文件存储接口 (StorageServer接口) 
FastFileStorageClient - 为方便项目开发集成的简单接口(StorageServer接口) 
AppendFileStorageClient - 支持文件续传操作的接口 (StorageServer接口)
*
*/
@Component
public class FastDFSClientWrapper {
	private final Logger logger = LoggerFactory.getLogger(FastDFSClientWrapper.class);

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return storePath.getFullPath();
    }
    
    public byte[] downloadFile(String path){
    	StorePath storePath = StorePath.praseFromUrl(path);
    	DownloadByteArray downloadByteArray = new DownloadByteArray();
    	byte[]data= storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), downloadByteArray);
    	return data;
    }
    
   /* private String getGroupName(String path){
    	int groupIndex=path.indexOf("group");
    	int groupIndexEnd=path.indexOf("/", groupIndex);
    	return path.substring(groupIndex,groupIndexEnd);
    }
    
    private String getFilePath(String path,String groupName){
    	return path.substring(path.indexOf(groupName)+groupName.length()+1);
    }*/

    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件内容
     * @param fileExtension
     * @return
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream,buff.length, fileExtension,null);
        return storePath.getFullPath();
    }
    
    public String uploadFile(InputStream inputStream, long fileSize, String fileExtName, Set<MateData> metaDataSet){
    	StorePath storePath =storageClient.uploadFile(inputStream, fileSize, fileExtName, metaDataSet);
    	return storePath.getFullPath();
    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }
    
    public Set<MateData> getMetadata(String groupName, String path){
    	return storageClient.getMetadata(groupName, path);
    }
    
 
    
    // 上传图片并同时生成一个缩略图
    public String uploadImageAndCrtThumbImage(InputStream inputStream, long fileSize, String fileExtName,
                Set<MateData> metaDataSet){
    	 StorePath storePath =storageClient.uploadImageAndCrtThumbImage(inputStream, fileSize, fileExtName, metaDataSet);
    	 return storePath.getFullPath();
    }
}
