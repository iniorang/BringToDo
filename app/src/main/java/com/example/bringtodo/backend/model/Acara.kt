package com.example.bringtodo.backend.model

import com.google.gson.annotations.SerializedName


class Acara {
    @SerializedName("id")
    var id : Int = 0
    @SerializedName("attributes")
    var attributes : AcaraAttributes = AcaraAttributes()
}

class AcaraAttributes{
    @SerializedName("name")
    var name : String = ""
    @SerializedName("desc")
    var desc : String = ""
    @SerializedName("date")
    var date : String = ""
    @SerializedName("time")
    var time : String = ""
}
