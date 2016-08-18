package studio.jhl.android4places.Animations;

import android.view.View;

import com.nineoldandroids.animation.Animator;

@Deprecated
public class ChooserAnimatorListener implements Animator.AnimatorListener {
    private int visibility;
    private View chooseLayout;

    public ChooserAnimatorListener(int visibility, View chooseLayout){
        this.visibility = visibility;
        this.chooseLayout = chooseLayout;
    }

    @Override
    public void onAnimationStart(Animator animation) {
        if(visibility==View.VISIBLE)
        chooseLayout.setVisibility(visibility);
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if(visibility==View.GONE){
            chooseLayout.setVisibility(visibility);
        }

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}