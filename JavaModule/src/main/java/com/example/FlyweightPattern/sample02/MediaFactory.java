package com.example.FlyweightPattern.sample02;

import java.util.HashMap;

/**
 * author: baiiu
 * date: on 16/7/22 14:25
 * description:
 */
public enum MediaFactory {

    INSTANCE;

    private HashMap<MediaType, HashMap<String, Media>> hashMap;

    MediaFactory() {
        hashMap = new HashMap<>();
    }

    public Media getMedia(MediaType mediaType, String name) {
        HashMap<String, Media> stringMediaHashMap = hashMap.get(mediaType);
        if (stringMediaHashMap == null) {
            stringMediaHashMap = new HashMap<>();
            hashMap.put(mediaType, stringMediaHashMap);
        }
        Media media = stringMediaHashMap.get(name);

        if (media == null) {
            switch (mediaType) {
                case Image:
                    media = new MediaImage(name);
                    break;
                case Video:
                    media = new MediaVideo(name);
                    break;
                case Animation:
                    media = new MediaAnimation(name);
                    break;
            }

            stringMediaHashMap.put(name, media);
        }

        return media;
    }
}
