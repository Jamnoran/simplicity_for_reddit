package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list;


import android.content.Context;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.RequestManager;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.simplicity.simplicityaclientforreddit.R;
import com.simplicity.simplicityaclientforreddit.main.base.BasePostsListViewModel;
import com.simplicity.simplicityaclientforreddit.main.custom.CustomPlayerEventListener;
import com.simplicity.simplicityaclientforreddit.main.custom.CustomVideoPlayer;
import com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.util.PostViewHolder;
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost;
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetMediaDataUseCase;
import com.simplicity.simplicityaclientforreddit.main.usecases.media.MediaData;
import com.simplicity.simplicityaclientforreddit.main.usecases.post.HasPostVideoUseCase;
import com.simplicity.simplicityaclientforreddit.main.usecases.post.IsPostYoutubeUseCase;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class VideoPlayerRecyclerView extends RecyclerView {

    private static final String TAG = "VideoPlayerRecyclerView";

    private enum VolumeState {ON, OFF}

    private BasePostsListViewModel _viewModel = null;

    // ui
    private ImageView thumbnail, volumeControl;
    private ProgressBar progressBar;
    private View viewHolderParent;
    private CustomVideoPlayer customVideoPlayer;

    private FrameLayout frameLayout;

    // vars
    private ArrayList<RedditPost> _posts = new ArrayList<>();
    private int videoSurfaceDefaultHeight = 0;
    private int screenDefaultHeight = 0;
    private Context context;
    private int playPosition = -1;
    private boolean isVideoViewAdded;
    private RequestManager _requestManager;

    // controlling playback state
    private VolumeState volumeState;

    // Audio
    private MediaPlayer audioMediaPlayer = null;

    public VideoPlayerRecyclerView(@NonNull Context context) {
        super(context);
//        init(context);
    }

    public VideoPlayerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        init(context);
    }

    public void setViewModel(@NotNull BasePostsListViewModel viewModel) {
        _viewModel = viewModel;
    }

    public void setRequestManager(@org.jetbrains.annotations.Nullable RequestManager initGlide) {
        _requestManager = initGlide;
    }

    public void set_posts(ArrayList<RedditPost> posts){
        this._posts = posts;
    }

    public void pauseVideo() {
        customVideoPlayer.pause();
    }

    public void resumeVideo() {
        try {
            customVideoPlayer.play();
        } catch (Exception e) {
            Log.e(TAG, "Could not resume video");
        }
    }

    private void init(Context context){
        this.context = context.getApplicationContext();
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        videoSurfaceDefaultHeight = point.x;
        screenDefaultHeight = point.y;

        CustomPlayerEventListener eventListener = getEventListener();
        customVideoPlayer = new CustomVideoPlayer(new PlayerView(this.context), eventListener);
        customVideoPlayer.init(context);

        setVolumeControl(VolumeState.ON);

        setUpScrollListener();
        addOnChildAttachedListener();
    }

    private CustomPlayerEventListener getEventListener() {
        return new CustomPlayerEventListener(){

            @Override
            public void pauseAudio() {

            }

            @Override
            public void playAudio() {

            }

            @Override
            public void playerStateReady() {
                addVideoView();
            }

            @Override
            public void resetAudio() {

            }

            @Override
            public void hideProgressBar() {

            }

            @Override
            public void showProgressBar() {

            }
        };
    }

    private void addOnChildAttachedListener() {
        addOnChildAttachStateChangeListener(new OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (viewHolderParent != null && viewHolderParent.equals(view)) {
                    resetVideoView();
                }

            }
        });
    }

    private void setUpScrollListener() {
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d(TAG, "onScrollStateChanged: called.");
                    if(thumbnail != null){ // show the old thumbnail
                        thumbnail.setVisibility(VISIBLE);
                    }

                    // There's a special case when the end of the list has been reached.
                    // Need to handle that with this bit of logic
                    playVideo(!recyclerView.canScrollVertically(1));
                    if (!recyclerView.canScrollVertically(1)) {
//                        Toast.makeText(context, "Loading more posts", Toast.LENGTH_LONG).show()
                        if (_viewModel != null) {
                            _viewModel.fetchPosts();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void playVideo(boolean isEndOfList) {
        int targetPosition;
        if(!isEndOfList){
            int startPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            int endPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();

            // if there is more than 2 list-items on the screen, set the difference to be 1
            if (endPosition - startPosition > 1) {
                endPosition = startPosition + 1;
            }

            // something is wrong. return.
            if (startPosition < 0 || endPosition < 0) {
                return;
            }

            // if there is more than 1 list-item on the screen
            if (startPosition != endPosition) {
                int startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition);
                int endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition);

                targetPosition = startPositionVideoHeight > endPositionVideoHeight ? startPosition : endPosition;
            }
            else {
                targetPosition = startPosition;
            }
        }
        else{
            targetPosition = _posts.size() - 1;
        }

//        Log.d(TAG, "playVideo: target position: " + targetPosition);

        // video is already playing so return
        if (targetPosition == playPosition) {
            return;
        }

        // set the position of the list-item that is to be played
        playPosition = targetPosition;
        if (customVideoPlayer == null) {
            return;
        }

        // remove any old surface views from previously playing videos
        customVideoPlayer.clearVideo();
//        videoSurfaceView.setVisibility(INVISIBLE);
//        removeVideoView(videoSurfaceView);

        // Clear audio
        clearAudio();

        int currentPosition = targetPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();

        View child = getChildAt(currentPosition);
        if (child == null) {
            return;
        }

        PostViewHolder holder = (PostViewHolder) child.getTag();
        if (holder == null) {
            playPosition = -1;
            return;
        }
        // Set earlier post holder to focus in false, this should be when we have scrolled past it
        holder.postInFocus(false);

//        thumbnail = holder.getThumbnail();
        progressBar = holder.getProgressBar();
        volumeControl = holder.getVolumeControl();
        viewHolderParent = holder.itemView;
//        requestManager = holder.requestManager;
//        frameLayout = holder.itemView.findViewById(R.id.media_container);
        frameLayout = holder.getFrameLayout();
//
//        videoSurfaceView.setPlayer(videoPlayer);

        viewHolderParent.setOnClickListener(videoViewClickListener);

        RedditPost post = _posts.get(targetPosition);
        if(new HasPostVideoUseCase().execute(post.getData())){
            String mediaUrl = new GetMediaDataUseCase().execute(post.getData()).getMediaUrl();
            MediaItem mediaItem = MediaItem.fromUri(mediaUrl);
            customVideoPlayer.playMediaItem(mediaItem);

            // Prepare audio
            checkIfVideoHasAudio(post.getData());
        }
        if(new IsPostYoutubeUseCase().execute(post.getData())){
            holder.postInFocus(true);
        }
    }

    private void clearAudio() {
        if (audioMediaPlayer != null) {
            audioMediaPlayer.stop();
            audioMediaPlayer.reset();
        }
    }

    private void initAudio(RedditPost.Data data) {
        // Check that we have not started a new video before this tries to play
        MediaData postMediaData = new GetMediaDataUseCase().execute(data);

        // initializing media player
        if (audioMediaPlayer == null) {
            audioMediaPlayer = new MediaPlayer();
        }

        // below line is use to set the audio
        // stream type for our media player.
        audioMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            audioMediaPlayer.setDataSource(postMediaData.getAudioUrl());
            // below line is use to prepare
            // and start our media player.
            audioMediaPlayer.prepare();
            audioMediaPlayer.setOnPreparedListener(mediaPlayer -> {
                Log.i(TAG,"Has prepared audio");
                audioMediaPlayer.start();
                // Start video player
//                videoPlayer.play();
            });
        } catch (IOException e) {
            Log.e(TAG, "Could not play audio" + e);
//            videoPlayer.play();
        }
    }

    private void checkIfVideoHasAudio(RedditPost.Data data) {
        MediaData postMediaData = new GetMediaDataUseCase().execute(data);
        RequestQueue queue = Volley.newRequestQueue(context);
        // Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, postMediaData.getAudioUrl(), response -> {
            Log.i(TAG, "Volley request success");
            initAudio(data);
        }, error -> {
            Log.i(TAG, "Volley request failed");
//            videoPlayer.play();
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private final OnClickListener videoViewClickListener = v -> toggleVolume();

    /**
     * Returns the visible region of the video surface on the screen.
     * if some is cut off, it will return less than the @videoSurfaceDefaultHeight
     * @param playPosition
     * @return
     */
    private int getVisibleVideoSurfaceHeight(int playPosition) {
        int at = playPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
//        Log.d(TAG, "getVisibleVideoSurfaceHeight: at: " + at);

        View child = getChildAt(at);
        if (child == null) {
            return 0;
        }

        int[] location = new int[2];
        child.getLocationInWindow(location);

        if (location[1] < 0) {
            return location[1] + videoSurfaceDefaultHeight;
        } else {
            return screenDefaultHeight - location[1];
        }
    }


    // Remove the old player
    private void removeVideoView(PlayerView videoView) {
        ViewGroup parent = (ViewGroup) videoView.getParent();
        if (parent == null) {
            return;
        }

        int index = parent.indexOfChild(videoView);
        if (index >= 0) {
            parent.removeViewAt(index);
            isVideoViewAdded = false;
            viewHolderParent.setOnClickListener(null);
        }
    }

    private void addVideoView(){
//        PlayerView playerView = customVideoPlayer.getSurfaceView();
//        frameLayout.addView(playerView);
        isVideoViewAdded = true;
//        playerView.requestFocus();
//        playerView.setVisibility(VISIBLE);
//        playerView.setAlpha(1);
        if (frameLayout != null) {
            thumbnail.setVisibility(INVISIBLE);
        }
    }

    private void resetVideoView(){
        if(isVideoViewAdded){
            customVideoPlayer.clearVideo();
//            removeVideoView(videoSurfaceView);
            playPosition = -1;
//            videoSurfaceView.setVisibility(INVISIBLE);

            if (thumbnail != null) {
                thumbnail.setVisibility(VISIBLE);
            }
        }
    }

    public void releasePlayer() {

//        if (videoPlayer != null) {
//            videoPlayer.release();
//            videoPlayer = null;
//        }

        viewHolderParent = null;
    }

    private void toggleVolume() {
        if (customVideoPlayer != null) {
            if (volumeState == VolumeState.OFF) {
                Log.d(TAG, "togglePlaybackState: enabling volume.");
                setVolumeControl(VolumeState.ON);
            } else if(volumeState == VolumeState.ON) {
                Log.d(TAG, "togglePlaybackState: disabling volume.");
                setVolumeControl(VolumeState.OFF);

            }
        }
    }

    private void setVolumeControl(VolumeState state){
        volumeState = state;
        if(state == VolumeState.OFF){
            customVideoPlayer.setVolume(0f);
            if (audioMediaPlayer != null) {
                audioMediaPlayer.setVolume(0f, 0f);
            }
            animateVolumeControl();
        }
        else if(state == VolumeState.ON){
            customVideoPlayer.setVolume(1f);
            if (audioMediaPlayer != null) {
                audioMediaPlayer.setVolume(1f, 1f);
            }
            animateVolumeControl();
        }
    }

    private void animateVolumeControl(){
        if(volumeControl != null){
            volumeControl.bringToFront();
            if(volumeState == VolumeState.OFF){
                _requestManager.load(R.drawable.ic_volume_off_grey_24dp)
                        .into(volumeControl);
//                ImageUtil.load(R.drawable.ic_volume_off_grey_24dp, volumeControl);
            }
            else if(volumeState == VolumeState.ON){
//                ImageUtil.load(R.drawable.ic_volume_up_grey_24dp, volumeControl);
                _requestManager.load(R.drawable.ic_volume_up_grey_24dp)
                        .into(volumeControl);
            }
            volumeControl.animate().cancel();

            volumeControl.setAlpha(1f);

            volumeControl.animate()
                    .alpha(0f)
                    .setDuration(600).setStartDelay(1000);
        }
    }
}