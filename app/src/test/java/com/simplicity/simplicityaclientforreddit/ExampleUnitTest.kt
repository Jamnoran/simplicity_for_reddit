package com.simplicity.simplicityaclientforreddit

import com.google.gson.Gson
import com.simplicity.simplicityaclientforreddit.ui.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.ui.main.usecases.post.GetPostYoutubeIDUseCase
import com.simplicity.simplicityaclientforreddit.ui.main.usecases.post.IsPostYoutubeUseCase
import com.simplicity.simplicityaclientforreddit.main.media.VideoHelper
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testRegExp() {
        val url = "https://v.redd.it/rv9jnqqoryp81/DASH_1080.mp4?source=fallback"
        assertEquals("https://v.redd.it/rv9jnqqoryp81/DASH_audio.mp4?source=fallback", VideoHelper.getAudioUrl(url))
    }

    @Test
    fun testPostYoutube() {
        val postData = getPost().data
        val isYoutube = IsPostYoutubeUseCase().execute(postData)
        assert(isYoutube)
        val youtubeId = GetPostYoutubeIDUseCase().execute(postData)
        assertEquals(youtubeId, "LPpW_8c5jE4")
    }

    fun getPost(): RedditPost {
        val postJson = "{\n" +
            "            \"kind\":\"t3\",\n" +
            "            \"data\":{\n" +
            "               \"approved_at_utc\":null,\n" +
            "               \"subreddit\":\"videos\",\n" +
            "               \"selftext\":\"\",\n" +
            "               \"author_fullname\":\"t2_fvqnq\",\n" +
            "               \"saved\":false,\n" +
            "               \"mod_reason_title\":null,\n" +
            "               \"gilded\":1,\n" +
            "               \"clicked\":false,\n" +
            "               \"title\":\"Random Japanese guy V.S. my wife at an Arcade.\",\n" +
            "               \"link_flair_richtext\":[\n" +
            "                  \n" +
            "               ],\n" +
            "               \"subreddit_name_prefixed\":\"r/videos\",\n" +
            "               \"hidden\":false,\n" +
            "               \"pwls\":6,\n" +
            "               \"link_flair_css_class\":null,\n" +
            "               \"downs\":0,\n" +
            "               \"thumbnail_height\":105,\n" +
            "               \"top_awarded_type\":null,\n" +
            "               \"hide_score\":false,\n" +
            "               \"name\":\"t3_wfmzno\",\n" +
            "               \"quarantine\":false,\n" +
            "               \"link_flair_text_color\":\"dark\",\n" +
            "               \"upvote_ratio\":0.93,\n" +
            "               \"author_flair_background_color\":null,\n" +
            "               \"subreddit_type\":\"public\",\n" +
            "               \"ups\":3677,\n" +
            "               \"total_awards_received\":2,\n" +
            "               \"media_embed\":{\n" +
            "                  \"content\":\"&lt;iframe width=\\\"356\\\" height=\\\"200\\\" src=\\\"https://www.youtube.com/embed/LPpW_8c5jE4?feature=oembed&amp;enablejsapi=1\\\" frameborder=\\\"0\\\" allow=\\\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\\\" allowfullscreen title=\\\"Random Japanese guy VS my wife\\\"&gt;&lt;/iframe&gt;\",\n" +
            "                  \"width\":356,\n" +
            "                  \"scrolling\":false,\n" +
            "                  \"height\":200\n" +
            "               },\n" +
            "               \"thumbnail_width\":140,\n" +
            "               \"author_flair_template_id\":null,\n" +
            "               \"is_original_content\":false,\n" +
            "               \"user_reports\":[\n" +
            "                  \n" +
            "               ],\n" +
            "               \"secure_media\":{\n" +
            "                  \"type\":\"youtube.com\",\n" +
            "                  \"oembed\":{\n" +
            "                     \"provider_url\":\"https://www.youtube.com/\",\n" +
            "                     \"version\":\"1.0\",\n" +
            "                     \"title\":\"Random Japanese guy VS my wife\",\n" +
            "                     \"type\":\"video\",\n" +
            "                     \"thumbnail_width\":480,\n" +
            "                     \"height\":200,\n" +
            "                     \"width\":356,\n" +
            "                     \"html\":\"&lt;iframe width=\\\"356\\\" height=\\\"200\\\" src=\\\"https://www.youtube.com/embed/LPpW_8c5jE4?feature=oembed&amp;enablejsapi=1\\\" frameborder=\\\"0\\\" allow=\\\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\\\" allowfullscreen title=\\\"Random Japanese guy VS my wife\\\"&gt;&lt;/iframe&gt;\",\n" +
            "                     \"author_name\":\"Mat Hanson\",\n" +
            "                     \"provider_name\":\"YouTube\",\n" +
            "                     \"thumbnail_url\":\"https://i.ytimg.com/vi/LPpW_8c5jE4/hqdefault.jpg\",\n" +
            "                     \"thumbnail_height\":360,\n" +
            "                     \"author_url\":\"https://www.youtube.com/user/matrh\"\n" +
            "                  }\n" +
            "               },\n" +
            "               \"is_reddit_media_domain\":false,\n" +
            "               \"is_meta\":false,\n" +
            "               \"category\":null,\n" +
            "               \"secure_media_embed\":{\n" +
            "                  \"content\":\"&lt;iframe width=\\\"356\\\" height=\\\"200\\\" src=\\\"https://www.youtube.com/embed/LPpW_8c5jE4?feature=oembed&amp;enablejsapi=1\\\" frameborder=\\\"0\\\" allow=\\\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\\\" allowfullscreen title=\\\"Random Japanese guy VS my wife\\\"&gt;&lt;/iframe&gt;\",\n" +
            "                  \"width\":356,\n" +
            "                  \"scrolling\":false,\n" +
            "                  \"media_domain_url\":\"https://www.redditmedia.com/mediaembed/wfmzno\",\n" +
            "                  \"height\":200\n" +
            "               },\n" +
            "               \"link_flair_text\":null,\n" +
            "               \"can_mod_post\":false,\n" +
            "               \"score\":3677,\n" +
            "               \"approved_by\":null,\n" +
            "               \"is_created_from_ads_ui\":false,\n" +
            "               \"author_premium\":true,\n" +
            "               \"thumbnail\":\"https://b.thumbs.redditmedia.com/QFQ2oHIjbN_25dqRmiiNYI22elcb9kSlZZuy9p8vWFs.jpg\",\n" +
            "               \"edited\":false,\n" +
            "               \"author_flair_css_class\":null,\n" +
            "               \"author_flair_richtext\":[\n" +
            "                  \n" +
            "               ],\n" +
            "               \"gildings\":{\n" +
            "                  \"gid_2\":1\n" +
            "               },\n" +
            "               \"post_hint\":\"rich:video\",\n" +
            "               \"content_categories\":null,\n" +
            "               \"is_self\":false,\n" +
            "               \"mod_note\":null,\n" +
            "               \"created\":1659573423.0,\n" +
            "               \"link_flair_type\":\"text\",\n" +
            "               \"wls\":6,\n" +
            "               \"removed_by_category\":null,\n" +
            "               \"banned_by\":null,\n" +
            "               \"author_flair_type\":\"text\",\n" +
            "               \"domain\":\"youtube.com\",\n" +
            "               \"allow_live_comments\":true,\n" +
            "               \"selftext_html\":null,\n" +
            "               \"likes\":null,\n" +
            "               \"suggested_sort\":null,\n" +
            "               \"banned_at_utc\":null,\n" +
            "               \"url_overridden_by_dest\":\"https://www.youtube.com/watch?v=LPpW_8c5jE4\",\n" +
            "               \"view_count\":null,\n" +
            "               \"archived\":false,\n" +
            "               \"no_follow\":false,\n" +
            "               \"is_crosspostable\":true,\n" +
            "               \"pinned\":false,\n" +
            "               \"over_18\":false,\n" +
            "               \"preview\":{\n" +
            "                  \"images\":[\n" +
            "                     {\n" +
            "                        \"source\":{\n" +
            "                           \"url\":\"https://external-preview.redd.it/Q-prA8pU_4b5uR7Sst7691Ahrcc9C6RLMNXJ-yL7S3A.jpg?auto=webp&amp;s=5fd06783834f5bebd458368f4321b21956bc1663\",\n" +
            "                           \"width\":480,\n" +
            "                           \"height\":360\n" +
            "                        },\n" +
            "                        \"resolutions\":[\n" +
            "                           {\n" +
            "                              \"url\":\"https://external-preview.redd.it/Q-prA8pU_4b5uR7Sst7691Ahrcc9C6RLMNXJ-yL7S3A.jpg?width=108&amp;crop=smart&amp;auto=webp&amp;s=c9df1e041a5a0d84425a5c383edcee140ec1937b\",\n" +
            "                              \"width\":108,\n" +
            "                              \"height\":81\n" +
            "                           },\n" +
            "                           {\n" +
            "                              \"url\":\"https://external-preview.redd.it/Q-prA8pU_4b5uR7Sst7691Ahrcc9C6RLMNXJ-yL7S3A.jpg?width=216&amp;crop=smart&amp;auto=webp&amp;s=212f8f01b211c24326394f078a9a8e65a0dffd87\",\n" +
            "                              \"width\":216,\n" +
            "                              \"height\":162\n" +
            "                           },\n" +
            "                           {\n" +
            "                              \"url\":\"https://external-preview.redd.it/Q-prA8pU_4b5uR7Sst7691Ahrcc9C6RLMNXJ-yL7S3A.jpg?width=320&amp;crop=smart&amp;auto=webp&amp;s=bd5d269477c4db8b98a22ab6522ecb5460e2e9cd\",\n" +
            "                              \"width\":320,\n" +
            "                              \"height\":240\n" +
            "                           }\n" +
            "                        ],\n" +
            "                        \"variants\":{\n" +
            "                           \n" +
            "                        },\n" +
            "                        \"id\":\"C0-I-KYWel-qESNY-HJAV1CO6DK-_1n6d6OSR0qNjSs\"\n" +
            "                     }\n" +
            "                  ],\n" +
            "                  \"enabled\":false\n" +
            "               },\n" +
            "               \"all_awardings\":[\n" +
            "                  {\n" +
            "                     \"giver_coin_reward\":null,\n" +
            "                     \"subreddit_id\":null,\n" +
            "                     \"is_new\":false,\n" +
            "                     \"days_of_drip_extension\":null,\n" +
            "                     \"coin_price\":500,\n" +
            "                     \"id\":\"gid_2\",\n" +
            "                     \"penny_donate\":null,\n" +
            "                     \"award_sub_type\":\"GLOBAL\",\n" +
            "                     \"coin_reward\":100,\n" +
            "                     \"icon_url\":\"https://www.redditstatic.com/gold/awards/icon/gold_512.png\",\n" +
            "                     \"days_of_premium\":7,\n" +
            "                     \"tiers_by_required_awardings\":null,\n" +
            "                     \"resized_icons\":[\n" +
            "                        {\n" +
            "                           \"url\":\"https://www.redditstatic.com/gold/awards/icon/gold_16.png\",\n" +
            "                           \"width\":16,\n" +
            "                           \"height\":16\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://www.redditstatic.com/gold/awards/icon/gold_32.png\",\n" +
            "                           \"width\":32,\n" +
            "                           \"height\":32\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://www.redditstatic.com/gold/awards/icon/gold_48.png\",\n" +
            "                           \"width\":48,\n" +
            "                           \"height\":48\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://www.redditstatic.com/gold/awards/icon/gold_64.png\",\n" +
            "                           \"width\":64,\n" +
            "                           \"height\":64\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://www.redditstatic.com/gold/awards/icon/gold_128.png\",\n" +
            "                           \"width\":128,\n" +
            "                           \"height\":128\n" +
            "                        }\n" +
            "                     ],\n" +
            "                     \"icon_width\":512,\n" +
            "                     \"static_icon_width\":512,\n" +
            "                     \"start_date\":null,\n" +
            "                     \"is_enabled\":true,\n" +
            "                     \"awardings_required_to_grant_benefits\":null,\n" +
            "                     \"description\":\"Gives 100 Reddit Coins and a week of r/lounge access and ad-free browsing.\",\n" +
            "                     \"end_date\":null,\n" +
            "                     \"sticky_duration_seconds\":null,\n" +
            "                     \"subreddit_coin_reward\":0,\n" +
            "                     \"count\":1,\n" +
            "                     \"static_icon_height\":512,\n" +
            "                     \"name\":\"Gold\",\n" +
            "                     \"resized_static_icons\":[\n" +
            "                        {\n" +
            "                           \"url\":\"https://www.redditstatic.com/gold/awards/icon/gold_16.png\",\n" +
            "                           \"width\":16,\n" +
            "                           \"height\":16\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://www.redditstatic.com/gold/awards/icon/gold_32.png\",\n" +
            "                           \"width\":32,\n" +
            "                           \"height\":32\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://www.redditstatic.com/gold/awards/icon/gold_48.png\",\n" +
            "                           \"width\":48,\n" +
            "                           \"height\":48\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://www.redditstatic.com/gold/awards/icon/gold_64.png\",\n" +
            "                           \"width\":64,\n" +
            "                           \"height\":64\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://www.redditstatic.com/gold/awards/icon/gold_128.png\",\n" +
            "                           \"width\":128,\n" +
            "                           \"height\":128\n" +
            "                        }\n" +
            "                     ],\n" +
            "                     \"icon_format\":null,\n" +
            "                     \"icon_height\":512,\n" +
            "                     \"penny_price\":null,\n" +
            "                     \"award_type\":\"global\",\n" +
            "                     \"static_icon_url\":\"https://www.redditstatic.com/gold/awards/icon/gold_512.png\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"giver_coin_reward\":null,\n" +
            "                     \"subreddit_id\":null,\n" +
            "                     \"is_new\":false,\n" +
            "                     \"days_of_drip_extension\":null,\n" +
            "                     \"coin_price\":125,\n" +
            "                     \"id\":\"award_5f123e3d-4f48-42f4-9c11-e98b566d5897\",\n" +
            "                     \"penny_donate\":null,\n" +
            "                     \"award_sub_type\":\"GLOBAL\",\n" +
            "                     \"coin_reward\":0,\n" +
            "                     \"icon_url\":\"https://i.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png\",\n" +
            "                     \"days_of_premium\":null,\n" +
            "                     \"tiers_by_required_awardings\":null,\n" +
            "                     \"resized_icons\":[\n" +
            "                        {\n" +
            "                           \"url\":\"https://preview.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png?width=16&amp;height=16&amp;auto=webp&amp;s=92932f465d58e4c16b12b6eac4ca07d27e3d11c0\",\n" +
            "                           \"width\":16,\n" +
            "                           \"height\":16\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://preview.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png?width=32&amp;height=32&amp;auto=webp&amp;s=d11484a208d68a318bf9d4fcf371171a1cb6a7ef\",\n" +
            "                           \"width\":32,\n" +
            "                           \"height\":32\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://preview.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png?width=48&amp;height=48&amp;auto=webp&amp;s=febdf28b6f39f7da7eb1365325b85e0bb49a9f63\",\n" +
            "                           \"width\":48,\n" +
            "                           \"height\":48\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://preview.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png?width=64&amp;height=64&amp;auto=webp&amp;s=b4406a2d88bf86fa3dc8a45aacf7e0c7bdccc4fb\",\n" +
            "                           \"width\":64,\n" +
            "                           \"height\":64\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://preview.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png?width=128&amp;height=128&amp;auto=webp&amp;s=19555b13e3e196b62eeb9160d1ac1d1b372dcb0b\",\n" +
            "                           \"width\":128,\n" +
            "                           \"height\":128\n" +
            "                        }\n" +
            "                     ],\n" +
            "                     \"icon_width\":2048,\n" +
            "                     \"static_icon_width\":2048,\n" +
            "                     \"start_date\":null,\n" +
            "                     \"is_enabled\":true,\n" +
            "                     \"awardings_required_to_grant_benefits\":null,\n" +
            "                     \"description\":\"When you come across a feel-good thing.\",\n" +
            "                     \"end_date\":null,\n" +
            "                     \"sticky_duration_seconds\":null,\n" +
            "                     \"subreddit_coin_reward\":0,\n" +
            "                     \"count\":1,\n" +
            "                     \"static_icon_height\":2048,\n" +
            "                     \"name\":\"Wholesome\",\n" +
            "                     \"resized_static_icons\":[\n" +
            "                        {\n" +
            "                           \"url\":\"https://preview.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png?width=16&amp;height=16&amp;auto=webp&amp;s=92932f465d58e4c16b12b6eac4ca07d27e3d11c0\",\n" +
            "                           \"width\":16,\n" +
            "                           \"height\":16\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://preview.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png?width=32&amp;height=32&amp;auto=webp&amp;s=d11484a208d68a318bf9d4fcf371171a1cb6a7ef\",\n" +
            "                           \"width\":32,\n" +
            "                           \"height\":32\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://preview.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png?width=48&amp;height=48&amp;auto=webp&amp;s=febdf28b6f39f7da7eb1365325b85e0bb49a9f63\",\n" +
            "                           \"width\":48,\n" +
            "                           \"height\":48\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://preview.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png?width=64&amp;height=64&amp;auto=webp&amp;s=b4406a2d88bf86fa3dc8a45aacf7e0c7bdccc4fb\",\n" +
            "                           \"width\":64,\n" +
            "                           \"height\":64\n" +
            "                        },\n" +
            "                        {\n" +
            "                           \"url\":\"https://preview.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png?width=128&amp;height=128&amp;auto=webp&amp;s=19555b13e3e196b62eeb9160d1ac1d1b372dcb0b\",\n" +
            "                           \"width\":128,\n" +
            "                           \"height\":128\n" +
            "                        }\n" +
            "                     ],\n" +
            "                     \"icon_format\":null,\n" +
            "                     \"icon_height\":2048,\n" +
            "                     \"penny_price\":null,\n" +
            "                     \"award_type\":\"global\",\n" +
            "                     \"static_icon_url\":\"https://i.redd.it/award_images/t5_22cerq/5izbv4fn0md41_Wholesome.png\"\n" +
            "                  }\n" +
            "               ],\n" +
            "               \"awarders\":[\n" +
            "                  \n" +
            "               ],\n" +
            "               \"media_only\":false,\n" +
            "               \"can_gild\":true,\n" +
            "               \"spoiler\":false,\n" +
            "               \"locked\":false,\n" +
            "               \"author_flair_text\":null,\n" +
            "               \"treatment_tags\":[\n" +
            "                  \n" +
            "               ],\n" +
            "               \"visited\":false,\n" +
            "               \"removed_by\":null,\n" +
            "               \"num_reports\":null,\n" +
            "               \"distinguished\":null,\n" +
            "               \"subreddit_id\":\"t5_2qh1e\",\n" +
            "               \"author_is_blocked\":false,\n" +
            "               \"mod_reason_by\":null,\n" +
            "               \"removal_reason\":null,\n" +
            "               \"link_flair_background_color\":\"\",\n" +
            "               \"id\":\"wfmzno\",\n" +
            "               \"is_robot_indexable\":true,\n" +
            "               \"report_reasons\":null,\n" +
            "               \"author\":\"xxStefanxx1\",\n" +
            "               \"discussion_type\":null,\n" +
            "               \"num_comments\":338,\n" +
            "               \"send_replies\":true,\n" +
            "               \"whitelist_status\":\"all_ads\",\n" +
            "               \"contest_mode\":false,\n" +
            "               \"mod_reports\":[\n" +
            "                  \n" +
            "               ],\n" +
            "               \"author_patreon_flair\":false,\n" +
            "               \"author_flair_text_color\":null,\n" +
            "               \"permalink\":\"/r/videos/comments/wfmzno/random_japanese_guy_vs_my_wife_at_an_arcade/\",\n" +
            "               \"parent_whitelist_status\":\"all_ads\",\n" +
            "               \"stickied\":false,\n" +
            "               \"url\":\"https://www.youtube.com/watch?v=LPpW_8c5jE4\",\n" +
            "               \"subreddit_subscribers\":26540434,\n" +
            "               \"created_utc\":1659573423.0,\n" +
            "               \"num_crossposts\":0,\n" +
            "               \"media\":{\n" +
            "                  \"type\":\"youtube.com\",\n" +
            "                  \"oembed\":{\n" +
            "                     \"provider_url\":\"https://www.youtube.com/\",\n" +
            "                     \"version\":\"1.0\",\n" +
            "                     \"title\":\"Random Japanese guy VS my wife\",\n" +
            "                     \"type\":\"video\",\n" +
            "                     \"thumbnail_width\":480,\n" +
            "                     \"height\":200,\n" +
            "                     \"width\":356,\n" +
            "                     \"html\":\"&lt;iframe width=\\\"356\\\" height=\\\"200\\\" src=\\\"https://www.youtube.com/embed/LPpW_8c5jE4?feature=oembed&amp;enablejsapi=1\\\" frameborder=\\\"0\\\" allow=\\\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\\\" allowfullscreen title=\\\"Random Japanese guy VS my wife\\\"&gt;&lt;/iframe&gt;\",\n" +
            "                     \"author_name\":\"Mat Hanson\",\n" +
            "                     \"provider_name\":\"YouTube\",\n" +
            "                     \"thumbnail_url\":\"https://i.ytimg.com/vi/LPpW_8c5jE4/hqdefault.jpg\",\n" +
            "                     \"thumbnail_height\":360,\n" +
            "                     \"author_url\":\"https://www.youtube.com/user/matrh\"\n" +
            "                  }\n" +
            "               },\n" +
            "               \"is_video\":false\n" +
            "            }\n" +
            "         }"
        return Gson().fromJson(postJson, RedditPost::class.java)
    }
}
