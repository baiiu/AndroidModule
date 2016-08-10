package com.example.testing.myapplication.mClass.gsonConverter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * author: baiiu
 * date: on 16/8/10 19:55
 * description:
 */
class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            T t = adapter.read(jsonReader);


            return t;
        } finally {
            value.close();
        }
    }
}
