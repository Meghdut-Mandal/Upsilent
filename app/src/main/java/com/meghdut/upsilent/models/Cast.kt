package com.meghdut.upsilent.models

import java.io.Serializable

/**
 * Created by Meghdut Mandal on 10/02/17.
 */
data class Cast(var cast_id: Int = 0, var credit_id: String? = null, var name: String? = null, var profile_path: String? = null, var character: String? = null) : Serializable