package com.groupnamenotfoundexception.wakeupcall.app.swipeToDismissExtension;

import android.content.Context;
import com.hudomju.swipe.OnItemClickListener;

/**
 * Created by Caner on 16/07/2015.
 */
public class SwipeableItemClickListener extends com.hudomju.swipe.SwipeableItemClickListener {
    public SwipeableItemClickListener(Context context, OnItemClickListener listener) {
        super(context, listener);
    }

    // API 22 Fix
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
