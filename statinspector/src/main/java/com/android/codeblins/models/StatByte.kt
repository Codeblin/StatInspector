package com.android.codeblins.models

import java.text.DecimalFormat

/**
 * Created by Codeblin S. on 3/10/2019.
 */

data class StatByte(val value: Long){
    val text: String
        get(){
            if (value <= 0) return "0"
            val units = arrayOf("B", "kB", "MB", "GB", "TB")
            val digitGroups = (Math.log10(value.toDouble()) / Math.log10(1024.0)).toInt()
            return DecimalFormat("#,##0.#").format(value / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
        }
}