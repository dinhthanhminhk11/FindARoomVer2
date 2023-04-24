package com.example.findaroomver2.ui.customview.autoimageslider.IndicatorView;

import androidx.annotation.Nullable;

import com.example.findaroomver2.ui.customview.autoimageslider.IndicatorView.animation.AnimationManager;
import com.example.findaroomver2.ui.customview.autoimageslider.IndicatorView.animation.controller.ValueController;
import com.example.findaroomver2.ui.customview.autoimageslider.IndicatorView.animation.data.Value;
import com.example.findaroomver2.ui.customview.autoimageslider.IndicatorView.draw.DrawManager;
import com.example.findaroomver2.ui.customview.autoimageslider.IndicatorView.draw.data.Indicator;


public class IndicatorManager implements ValueController.UpdateListener {

    private DrawManager drawManager;
    private AnimationManager animationManager;
    private Listener listener;

    interface Listener {
        void onIndicatorUpdated();
    }

    IndicatorManager(@Nullable Listener listener) {
        this.listener = listener;
        this.drawManager = new DrawManager();
        this.animationManager = new AnimationManager(drawManager.indicator(), this);
    }

    public AnimationManager animate() {
        return animationManager;
    }

    public Indicator indicator() {
        return drawManager.indicator();
    }

    public DrawManager drawer() {
        return drawManager;
    }

    @Override
    public void onValueUpdated(@Nullable Value value) {
        drawManager.updateValue(value);
        if (listener != null) {
            listener.onIndicatorUpdated();
        }
    }
}
