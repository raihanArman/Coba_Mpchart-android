package com.simoku.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class DataModel(
    @SerializedName("fixCO")
    @Expose
    var fixCO: String,

    @SerializedName("fixNO2")
    @Expose
    var fixNO2: String,

    @SerializedName("fixSO2")
    @Expose
    var fixSO2: String,

    @SerializedName("fixO3")
    @Expose
    var fixO3: String,

    @SerializedName("fixPM10")
    @Expose
    var fixPM10: String,

    @SerializedName("date")
    @Expose
    var date: Date,

)