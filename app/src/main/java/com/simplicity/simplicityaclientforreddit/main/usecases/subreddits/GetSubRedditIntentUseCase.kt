package com.simplicity.simplicityaclientforreddit.main.usecases.subreddits

import android.app.Activity
import android.content.Intent
import com.simplicity.simplicityaclientforreddit.main.MainActivity

class GetSubRedditIntentUseCase(val subreddit: String, val activity: Activity) {
    fun execute(): Intent {
        AddSubRedditVisitedUseCase(subreddit).execute()
        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra(MainActivity.KEY_SUBREDDIT, subreddit)
        return intent
    }
}
