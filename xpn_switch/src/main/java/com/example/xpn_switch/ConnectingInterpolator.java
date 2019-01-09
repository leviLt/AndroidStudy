package com.example.xpn_switch;

import android.os.Build;
import android.view.animation.Interpolator;

import androidx.annotation.RequiresApi;

/**
 * @author lt
 * @package com.example.xpn_switch
 * @date 2018/11/20 9:59 AM
 * @describe TODO
 * @project
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
public class ConnectingInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        return input;
    }
}