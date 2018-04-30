package com.juanpablofajardo.schoolbuses.utils

import android.os.Build
import android.support.annotation.RequiresApi
import android.transition.*


/**
 * Created by Juan Pablo Fajardo Cano on 4/25/18.
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DetailsTransition : TransitionSet() {
    init {
        ordering = TransitionSet.ORDERING_TOGETHER
        addTransition(ChangeBounds()).addTransition(ChangeTransform())
        duration = 300
    }

}