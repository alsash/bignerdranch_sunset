package com.bignerdranch.android.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

public class SunsetFragment extends Fragment {

    private View sceneView;
    private View sunView;
    private View skyView;

    private int blueSkyColor;
    private int sunsetSkyColor;
    private int nightSkyColor;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sunset, container, false);
        sceneView = rootView;
        sceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
        sunView = rootView.findViewById(R.id.sun);
        skyView = rootView.findViewById(R.id.sky);

        Resources resources = getResources();
        blueSkyColor = ResourcesCompat.getColor(resources, R.color.blue_sky, null);
        sunsetSkyColor = ResourcesCompat.getColor(resources, R.color.sunset_sky, null);
        nightSkyColor = ResourcesCompat.getColor(resources, R.color.night_sky, null);


        return rootView;
    }

    private void startAnimation() {
        float sunYStart = sunView.getTop();
        float sunYEnd = skyView.getHeight();

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(sunView, "y", sunYStart, sunYEnd)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor)
                .setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor)
                .setDuration(3000);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();
    }
}
