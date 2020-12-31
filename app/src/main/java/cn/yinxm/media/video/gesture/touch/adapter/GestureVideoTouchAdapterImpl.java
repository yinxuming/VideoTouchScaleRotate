package cn.yinxm.media.video.gesture.touch.adapter;

import android.view.TextureView;

import cn.yinxm.media.video.controller.VideoPlayController;
import cn.yinxm.media.video.gesture.touch.anim.IVideoTouchEndAnim;
import cn.yinxm.media.video.gesture.touch.handler.IVideoRotateHandler;


/**
 * 播放器手势触摸适配，兼容HkBaseVideoView升级到新播放器BaseVideoPlayer
 * <p>
 *
 * @author yinxuming
 * @date 2020/5/18
 */
public class GestureVideoTouchAdapterImpl implements IVideoTouchAdapter {
    VideoPlayController mPlayController;
    private IVideoRotateHandler mRotateHandler;
    private IVideoTouchEndAnim mTouchEndAnim;

    public GestureVideoTouchAdapterImpl(VideoPlayController playController) {
        mPlayController = playController;
    }

    public void setTouchEndAnim(IVideoTouchEndAnim touchEndAnim) {
        mTouchEndAnim = touchEndAnim;
    }

    @Override
    public IVideoTouchEndAnim getVideoTouchEndAnim() {
        return mTouchEndAnim;
    }

    @Override
    public IVideoRotateHandler getVideoRotateHandler() {
        return mRotateHandler;
    }

    @Override
    public TextureView getTextureView() {
        if (mPlayController instanceof TextureView) {
            return (TextureView) mPlayController;
        }
        return null;
    }

    @Override
    public boolean isPlaying() {
        return mPlayController.isPlaying();
    }


    @Override
    public boolean isFullScreen() {
        return false;
    }


    public void setVideoRotateHandler(IVideoRotateHandler rotateHandler) {
        mRotateHandler = rotateHandler;
    }
}
