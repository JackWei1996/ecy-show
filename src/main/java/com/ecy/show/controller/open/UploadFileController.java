package com.ecy.show.controller.open;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/open")
@Api(tags = "上传文件公共接口")
public class UploadFileController {
    // 上传后的路径
    @Value("${web.upload-path}")
    String filePath;
    @Value("${web.server-path}")
    String serverPath;
    /**
     * 文件上传
     */
    @ApiOperation("文件上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("upload") MultipartFile file){
        if (file.isEmpty()) {
            return "文件为空";
        }

        // 文件名
        String fileName = file.getOriginalFilename();
        // 后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        // 新文件名
        fileName = UUID.randomUUID() + suffixName;

        File dest = new File(filePath + fileName);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
            return serverPath + fileName;
        } catch (IOException e) {
            return "上传失败";
        }
    }
}
