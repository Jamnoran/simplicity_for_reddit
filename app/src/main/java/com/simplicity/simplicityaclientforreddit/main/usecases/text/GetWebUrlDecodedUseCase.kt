package com.simplicity.simplicityaclientforreddit.main.usecases.text

import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class GetWebUrlDecodedUseCase(val url: String) {
    fun invoke(): String {
        var urlDecoded = URLDecoder.decode(url, StandardCharsets.UTF_8.toString())
        if (urlDecoded.startsWith("{")) {
            urlDecoded = urlDecoded.drop(1)
        }
        if (urlDecoded.endsWith("}")) {
            urlDecoded = urlDecoded.dropLast(1)
        }
        return urlDecoded
    }
}
