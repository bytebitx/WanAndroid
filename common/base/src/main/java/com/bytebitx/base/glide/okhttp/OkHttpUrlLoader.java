package com.bytebitx.base.glide.okhttp;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import androidx.annotation.NonNull;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    private final okhttp3.Call.Factory client;

    public OkHttpUrlLoader(@NonNull okhttp3.Call.Factory client) {
        this.client = client;
    }

    public boolean handles(@NonNull GlideUrl url) {
        return true;
    }

    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl model, int width, int height, @NonNull Options options) {
        return new LoadData(model, new OkHttpStreamFetcher(this.client, model));
    }

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private final okhttp3.Call.Factory client;

        public Factory(@NonNull okhttp3.Call.Factory client) {
            this.client = client;
        }

        @NonNull
        public ModelLoader<GlideUrl, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new OkHttpUrlLoader(this.client);
        }

        public void teardown() {
        }
    }
}

