package me.gaoleng.libnetworkframework.widget;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;

/**
 * loading.
 *
 * @author gaoleng
 * @date 2019-08-21
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context);
        ProgressBar progressBar = new ProgressBar(context);
        setContentView(progressBar);
    }
}
