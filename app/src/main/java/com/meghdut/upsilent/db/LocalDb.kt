package com.meghdut.upsilent.db

import com.chibatching.kotpref.KotprefModel

object LocalDb : KotprefModel() {
    var name by stringPref("None")
    var email by stringPref("None")
    var address by stringPref("Not Specified")
    var phoneNumber by stringPref("Not Specified")
    var isLoggedIn by booleanPref(false)
}