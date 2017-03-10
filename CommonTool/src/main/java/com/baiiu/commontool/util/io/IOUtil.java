package com.baiiu.commontool.util.io;


import com.baiiu.commontool.app.UIUtil;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * author: baiiu
 * date: on 16/3/22 17:28
 * description:
 */
public class IOUtil {
    static final Charset UTF_8 = Charset.forName("UTF-8");

    public static String readAssets(String name) {
        BufferedReader br = null;
        try {
            InputStream is = UIUtil.getContext().getAssets().open(name);
            return inputStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeSilently(br);
        }

        return null;
    }

    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
        }
    }

    public static String inputStreamToString(InputStream in) throws IOException {
        return readFully(new InputStreamReader(in, UTF_8));
    }


    public static String readFully(Reader reader) throws IOException {
        try {
            StringWriter writer = new StringWriter();
            char[] buffer = new char[1024];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, count);
            }
            return writer.toString();
        } finally {
            reader.close();
        }
    }
}
