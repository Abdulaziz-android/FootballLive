package com.abdulaziz.footballlive.models.fixtures

import com.google.gson.annotations.SerializedName

 class AwayScorer {
     @SerializedName("in")
     var _in: String? = null

     @SerializedName("out")
     var _out: String? = null

     constructor(_in: String?, _out: String?) {
         this._in = _in
         this._out = _out
     }
 }