package cn.yinxm.media.video.gesture.touch.anim;



import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.PointF;

import androidx.annotation.CallSuper;

/**
 * 缩放动画
 * <p>
 * 在给定时间内从一个矩阵的变化逐渐动画到另一个矩阵的变化
 * @author yinxuming
 * @date 2020/12/2
 */
public abstract class VideoScaleEndAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener,
        Animator.AnimatorListener {

    /**
     * 图片缩放动画时间
     */
    public static final int SCALE_ANIMATOR_DURATION = 500;

    Matrix mStartMatrix = new Matrix();
    float[] mTransSpan = new float[2];
    PointF mRotateCenter;
    float mRotateDegree;
    float mLastValue;

    public void setScaleEndAnimParams(Matrix startMatrix, float transX, float transY, float rotateFixDegree,
                                      PointF rotateCenter) {
        if (startMatrix == null) {
            mStartMatrix.reset();
        } else {
            mStartMatrix.set(startMatrix);
        }
        mTransSpan[0] = transX;
        mTransSpan[1] = transY;
        mRotateDegree = rotateFixDegree;
        mRotateCenter = rotateCenter;

        setFloatValues(0, 1f);
        setDuration(SCALE_ANIMATOR_DURATION);
        addUpdateListener(this);
        addListener(this);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        // 获取动画进度
        float value = (Float) animation.getAnimatedValue();
        onValueUpdate(value);
    }

    public void onValueUpdate(float value) {
        // 计算相对于上次位置的偏移量
        float transX = mTransSpan[0] * (value - mLastValue);
        float transY = mTransSpan[1] * (value - mLastValue);
        boolean isUpdate = false;
        if (!(transX == 0 && transY == 0)) {
            mStartMatrix.postTranslate(transX, transY);
            isUpdate = true;
        }
        if (mRotateDegree != 0) {
            mStartMatrix.postRotate(mRotateDegree * (value - mLastValue), mRotateCenter.x, mRotateCenter.y);
            isUpdate = true;
        }
        if (isUpdate) {
            updateMatrixToView(mStartMatrix);
        }
        mLastValue = value;
    }


    protected abstract void updateMatrixToView(Matrix transMatrix);

    protected abstract void onFixEndAnim(VideoScaleEndAnimator animator, float fixEndDegrees);

    @Override
    public void onAnimationStart(Animator animation) {
    }

    @CallSuper
    @Override
    public void onAnimationEnd(Animator animation) {
        onFixEndAnim(this, mRotateDegree);
    }

    @CallSuper
    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }
}