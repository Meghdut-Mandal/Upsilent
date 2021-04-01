/*
 * Copyright (c) 2019 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package com.meghdut.upsilent.models

class PickOptions(
        val readOnly: Boolean,
        val pickDirectory: Boolean,
        val mimeTypes: List<MimeType>,
        val localOnly: Boolean,
        val allowMultiple: Boolean
)
