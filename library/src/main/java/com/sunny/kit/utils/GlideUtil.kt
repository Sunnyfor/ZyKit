package com.sunny.kit.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.sunny.kit.R
import com.sunny.kit.utils.glide.GlideApp
import com.sunny.kit.utils.glide.GlideRequest

@Suppress("MemberVisibilityCanBePrivate")
object GlideUtil {
    /**
     * 默认占位图
     */
    var DEFAULT_PLACEHOLDER = R.drawable.zy_ic_placeholder

    fun getLoadImageRequest(imgView: ImageView, imgPath: Any, placeholder: Int = DEFAULT_PLACEHOLDER): GlideRequest<Drawable> {
        return GlideApp.with(imgView.context).load(imgPath).placeholder(placeholder)
    }

    fun getLoadGifRequest(imgView: ImageView, imgPath: Any, placeholder: Int = DEFAULT_PLACEHOLDER): GlideRequest<GifDrawable> {
        return GlideApp.with(imgView.context).asGif().load(imgPath).placeholder(placeholder)
    }


    fun loadImage(imgView: ImageView, imgPath: Any, placeholder: Int = DEFAULT_PLACEHOLDER) {
        getLoadImageRequest(imgView, imgPath, placeholder).into(imgView)
    }


    fun loadImageNoCache(imgView: ImageView, imgPath: Any, placeholder: Int = DEFAULT_PLACEHOLDER) {
        getLoadImageRequest(imgView, imgPath, placeholder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgView)
    }


    fun loadGif(imgView: ImageView, imgPath: Any, placeholder: Int = DEFAULT_PLACEHOLDER) {
        getLoadGifRequest(imgView, imgPath, placeholder).into(imgView)
    }

    fun loadGifNoCache(imgView: ImageView, imgPath: Any, placeholder: Int = DEFAULT_PLACEHOLDER) {
        getLoadGifRequest(imgView, imgPath, placeholder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgView)
    }


    fun loadBitmap(imgView: ImageView, imgPath: Any, placeholder: Int = DEFAULT_PLACEHOLDER, callback: (bitmap: Bitmap) -> Unit) {
        GlideApp.with(imgView.context).asBitmap().placeholder(placeholder).load(imgPath).into(object : CustomViewTarget<ImageView, Bitmap>(imgView) {
            override fun onLoadFailed(errorDrawable: Drawable?) {}

            override fun onResourceCleared(placeholder: Drawable?) {}

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                callback.invoke(resource)
            }

        })
    }

    fun loadBitmapNoCache(imgView: ImageView, imgPath: Any, placeholder: Int = DEFAULT_PLACEHOLDER, callback: (bitmap: Bitmap) -> Unit) {
        GlideApp.with(imgView.context).asBitmap().placeholder(placeholder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
            .load(imgPath).into(object : CustomViewTarget<ImageView, Bitmap>(imgView) {
                override fun onLoadFailed(errorDrawable: Drawable?) {}

                override fun onResourceCleared(placeholder: Drawable?) {}

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback.invoke(resource)
                }
            })
    }


    fun loadDrawable(imgView: ImageView, imgPath: Any, placeholder: Int = DEFAULT_PLACEHOLDER, callback: (drawable: Drawable) -> Unit) {
        GlideApp.with(imgView.context).asDrawable().placeholder(placeholder).load(imgPath)
            .into(object : CustomViewTarget<ImageView, Drawable>(imgView) {
                override fun onLoadFailed(errorDrawable: Drawable?) {}

                override fun onResourceCleared(placeholder: Drawable?) {}

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    callback.invoke(resource)
                }

            })
    }

    fun loadDrawableNoCache(imgView: ImageView, imgPath: Any, placeholder: Int = DEFAULT_PLACEHOLDER, callback: (drawable: Drawable) -> Unit) {
        GlideApp.with(imgView.context).asDrawable().placeholder(placeholder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
            .load(imgPath).into(object : CustomViewTarget<ImageView, Drawable>(imgView) {
                override fun onLoadFailed(errorDrawable: Drawable?) {}

                override fun onResourceCleared(placeholder: Drawable?) {}

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    callback.invoke(resource)
                }
            })
    }
}