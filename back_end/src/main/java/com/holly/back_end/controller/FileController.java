package com.holly.back_end.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import com.holly.back_end.common.AuthAccess;
import com.holly.back_end.common.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;
    @Value("${server.port}")
    String port;

    // C:\Users\Administrator\Desktop\HiringSystem\files
    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @AuthAccess
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename(); // 文件的原始名称
        // aaa.png
        String mainName = FileUtil.mainName(originalFileName);  // aaa
        String extName = FileUtil.extName(originalFileName); // png

        if (!FileUtil.exist(ROOT_PATH)) { //父级目录不存在，创建
            FileUtil.mkdir(ROOT_PATH);
        }
        if (!FileUtil.exist(ROOT_PATH + File.separator + originalFileName)) { // 上传文件存在，重命名
            originalFileName = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFileName);
        file.transferTo(saveFile);  // 存储文件
        // 返回路径
        String url = "http://" + ip + ":" + port + "/api/file/download/" + originalFileName;
        return Result.success(url); //返回文件的链接，就是文件下载的地址，由后台提供
    }

    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        String filePath = ROOT_PATH + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes); // 数组是一个字节数组，也就是字节流数组
        outputStream.flush();
        outputStream.close();
    }

    /**
     * wang-editor编辑器文件上传接口
     */
    @PostMapping("/wang/upload")
    public Map<String, Object> wangEditorUpload(MultipartFile file) {
        String flag = System.currentTimeMillis() + "";
        String filename = file.getOriginalFilename();
        try {
            //文件存储形式：时间戳-文件名
            FileUtil.writeBytes(file.getBytes(), ROOT_PATH + flag + "-" + filename);
            System.out.println(filename + "--上传成功");
            Thread.sleep(1L);
        } catch (Exception e) {
            System.err.println(filename + "--文件上传失败");
        }
        String http = "http://" + ip + ":" + port + "/api/file/";
        Map<String, Object> resMap = new HashMap<>();
        //wangEditor上传图片成功后，需返回的参数
        resMap.put("errno", 0);
        resMap.put("data", CollUtil.newArrayList(Dict.create().set("url", http + flag + "-" + filename)));
        return resMap;
    }
}
