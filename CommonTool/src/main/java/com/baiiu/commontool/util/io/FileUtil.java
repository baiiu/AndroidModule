package com.baiiu.commontool.util.io;

import java.io.File;
import java.io.IOException;

/**
 * author: baiiu
 * date: on 16/3/23 15:10
 * description: 对文件删除操作
 */
public class FileUtil {

    /**
     * 递归删除文件目录
     */
    public static void deleteFile(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IOException("not a readable directory: " + dir);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteFile(file);
            }
            if (!file.delete()) {
                throw new IOException("failed to delete file: " + file);
            }
        }
    }


}
