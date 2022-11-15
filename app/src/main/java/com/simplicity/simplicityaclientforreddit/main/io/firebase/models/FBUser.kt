package com.simplicity.simplicityaclientforreddit.main.io.firebase.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FBUser(val username: String? = null, val ignoredSubReddits: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
