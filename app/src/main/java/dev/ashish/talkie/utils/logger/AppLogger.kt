package dev.ashish.talkie.utils.logger

import android.util.Log

class AppLogger: Logger {
    override fun logger(tag: String, msg: String) {
        Log.d(tag,msg)
    }
}