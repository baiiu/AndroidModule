package com.example.testing.myapplication.mClass.stringConverter;

import com.example.testing.myapplication.util.LogUtil;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * author: baiiu
 * date: on 16/8/10 20:19
 * description:
 */
public class StringConverter extends Converter.Factory {

    public static Converter.Factory create() {
        return new StringConverter();
    }

    @Override public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
            Retrofit retrofit) {
        LogUtil.d("type: " + type);

        if (type == String.class) {
            return StringResponseBodyConverter.INSTANCE;
        }

        return null;
    }

    static class StringResponseBodyConverter implements Converter<ResponseBody, String> {
        private static final StringResponseBodyConverter INSTANCE = new StringResponseBodyConverter();

        @Override public String convert(ResponseBody value) throws IOException {
            return value.string();
        }
    }

}
