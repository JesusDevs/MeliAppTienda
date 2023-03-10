package com.example.meliapp.utils

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Scale
import com.bumptech.glide.Glide
import com.example.meliapp.R
import com.squareup.picasso.Picasso

//para koltin svg coil
fun ImageView.loadSvg(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .placeholder(R.drawable.ic_launcher_background)
        .scale(Scale.FILL)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}
//para koltin svg coil
fun ImageView.loadSvgReco(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvgReco.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .size(350, 350)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}
//para koltin Picasso leer imagen jpg o png
fun ImageView.loadImg(url: String) {
    Picasso.get().load(url).resize(350, 350)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}
fun ImageView.loadImgShop(url: String) {
    Picasso.get().load(url)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}
fun ImageView.loadImgBank(url: String) {
    Picasso.get().load(url).resize(150, 65)
        .centerInside()
        .error(R.drawable.ic_launcher_background)
        .into(this)
}
fun ImageView.loadGif(url: String) {
    Glide.with(context!!).asGif().load(url).override(150,65).into(this)
}
//metodo para java
fun ImageSvgNews(context: Context ,url: String,imageView: ImageView ,placeHolder :Int ) {
    val imageLoader = ImageLoader.Builder(context)
        .componentRegistry { add(SvgDecoder(context)) }
        .build()

    val request = ImageRequest.Builder(context)
        .placeholder(placeHolder)
        .data(url)
        .target(imageView)
        .build()

    imageLoader.enqueue(request)
}