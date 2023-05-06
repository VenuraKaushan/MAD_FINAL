package com.example.hotelcrud

import android.provider.ContactsContract.CommonDataKinds.Email

data class GuideModel(
    var guideID: String? = null,
    var guideName: String? = null,
    var guideEmail: String? = null,
    var cantactNumber: String? = null,
)