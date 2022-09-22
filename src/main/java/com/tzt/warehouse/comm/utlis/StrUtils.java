package com.tzt.warehouse.comm.utlis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * @author：帅气的汤
 * 字符工具类
 */
@Slf4j
public class StrUtils {
    public static boolean checkFileType(MultipartFile avatar) {
        List imgFileType = Arrays.asList("image/gif", "image/jpeg", "image/jpg", "image/x-png", "image/png","image/webp");
        String contentType = avatar.getContentType();
        if (!imgFileType.contains(contentType) || avatar.getSize() > 1024 * 10 * 1024) {
            log.error("文件类型错误，造反者：{}，文件名称：{},文件大小：{}字节", UserUtlis.get().getUser().getIdCard(), avatar.getOriginalFilename(),avatar.getSize() );
            return false;
        }
        return true;
    }
}
