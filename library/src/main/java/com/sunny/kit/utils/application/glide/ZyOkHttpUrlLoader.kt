package com.sunny.kit.utils.application.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoader.LoadData
import com.sunny.zy.glide.ZyOkHttpStreamFetcher
import okhttp3.Call
import java.io.InputStream

/**
 * Desc
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2020/9/29 10:24
 */
class ZyOkHttpUrlLoader(private val client: Call.Factory) : ModelLoader<GlideUrl, InputStream> {

    override fun buildLoadData(model: GlideUrl, width: Int, height: Int, options: Options): LoadData<InputStream> {
        return LoadData(model, ZyOkHttpStreamFetcher(client, model))
    }

    override fun handles(model: GlideUrl): Boolean = true
}