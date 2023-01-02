package com.simplicity.simplicityaclientforreddit.main.screen.posts.detail

import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseListCompose
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.serializers.CommentSerializer
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PostDetailLogic : BaseLogic() {
    private val _stateFlow = MutableStateFlow<UiState<RedditPost>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<RedditPost>> = _stateFlow

    private val singlePostUrl = redditPost()
    fun getPostMarkDownTest(): String = "/user/Jamnoran/comments/z3mz2a/markdown_test/"
    fun getPostUrlImage(): String = "/r/sweden/comments/xxqy0a/det_är_fredag_mina_bekanta_bör_vi_skicka_våra/"
    fun getPostUrlYoutube(): String = "/r/videos/comments/xxls7u/im_a_voice_actor_i_edited_the_super_mario_bros/"
    fun getPostUrlGif(): String = "/r/gifs/comments/xvtkcc/spiderman/"
    fun getPostUrlGif2(): String = "/r/memes/comments/xpchdz/not_based_on_reality_just_a_joke/"
    fun getPostUrlGallery(): String = "/r/valheim/comments/xyjvkw/first_build_that_i_put_a_few_hours_into/"
    fun getPostUrlText(): String = "/r/homeassistant/comments/xxo8z5/thread_support_working/"
    fun getPostUrlLink(): String = "/r/science/comments/xyfwjf/phone_snubbing_your_partner_can_lead_to_a_vicious/"
    fun getPostUrlVideoWithSound(): String = "/r/aww/comments/xym0km/i_literally_felt_my_heart_melt_as_she_nodded_off/"
    fun getPostUrlVideo(): String = "/r/valheim/comments/y80st0/what_just_absolutely_smoked_me/"
    fun getPostUrlVideo2(): String = "/r/HumansBeingBros/comments/y8lyuk/strangers_stop_car_and_saves_an_unconscious/"
    fun getPostImgur(): String =
        "/r/gifs/comments/ya0wfp/have_you_ever_felt_a_supermarket_completely/?utm_source=share&utm_medium=android_app&utm_name=androidcss&utm_term=1&utm_content=share_button"

    fun getPostWithRemovedComments(): String = "/r/television/comments/yvbzwv/christina_applegate_makes_emotional_first_public/"
    fun getPostGalleryWithDescriptions(): String = "/r/valheim/comments/yv6gdi/it_started_as_a_farm_for_my_boars_did_i_go/"
    fun postTextWithEncapsulated(): String = "/r/gonewildstories/comments/zx84a1/my_girlfriend_rode_me_on_the_beach_with_her_micro/"
    fun redditPost(): String = "/r/MadeMeSmile/comments/zym7fl/principal_shaves_his_head_for_bullied_student/"
    fun twitchPost(): String = "/r/LivestreamFail/comments/zyi9hu/andrew_tate_arrested_on_suspected_organised_crime/"

    // Non working posts
    fun redditLink(): String =
        "/r/worldnews/comments/zxz776/explosions_rock_ukrainian_cities_as_russia/?utm_source=share&utm_medium=android_app&utm_name=androidcss&utm_term=1&utm_content=share_button"

    fun removedPost(): String =
        "/r/yesyesyesno/comments/zxxe45/damn_some_lucky_guy/?utm_source=share&utm_medium=android_app&utm_name=androidcss&utm_term=1&utm_content=share_button"

    fun start() {
        Log.i(TAG, "Fetch post called with a state of ${_stateFlow.value}")
        background {
            fetchPostWithComments(singlePostUrl)
        }
    }

    private fun fetchPostWithComments(singlePostUrl: String) {
        val logic = this
        API(CommentResponse::class.java, CommentSerializer()).getPost(
            singlePostUrl
        ).enqueue(object : CustomResponseListCompose<CommentResponse>(logic) {
            override fun success(responseBody: ArrayList<CommentResponse>) {
                responseBody.let { commentsResponse ->
                    commentsResponse.first().redditPost?.let { redditPost ->
//                        DownloadIfRequiredLinkedRedditPostUseCase(redditPost).execute()
                        foreground {
                            Log.i(TAG, "We are emitting success to _stateflow")
                            _stateFlow.emit(UiState.Success(redditPost))
                        }
                    }
                }
            }
        })
    }

    fun upVote(it: RedditPost) {
        Log.i("PostListLogic", "UpVote ${it.data.author}")
    }

    fun downVote(it: RedditPost) {
        Log.i("PostListLogic", "downVote ${it.data.author}")
    }

    companion object {
        private val TAG = "PostListLogic"
    }
}
