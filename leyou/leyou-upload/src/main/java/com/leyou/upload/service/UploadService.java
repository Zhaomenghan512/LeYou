package com.leyou.upload.service;


import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    private static final List<String> CONTENT_TYPE = Arrays.asList("image/gif","image/jpeg","image/jpg","image/png");

    @Autowired
    private FastFileStorageClient storageClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);
    public String uploadImage(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        //校验文件类型
        String contentType = file.getContentType();
        if (!CONTENT_TYPE.contains(contentType)) {
            LOGGER.info("文件类型不合法：{}",originalFilename);
            return null;
        }
        //校验文件内容
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                LOGGER.info("文件内容不合法：{}",originalFilename);
                return null;
            }
            String ext = StringUtils.substringAfterLast(originalFilename, ".");
            //保存到服务器
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
//            file.transferTo(new File("E:\\IDeaWorkSpace\\leyou-manage-web\\static\\images\\"+originalFilename));
            //返回URL，进行回写
//            return "http://image.leyou.com/"+originalFilename;
            return "http:///image/leyou.com/"+storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("服务器内部异常：{}",originalFilename);
            e.printStackTrace();
        }
        return null;

    }
}
