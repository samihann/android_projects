package com.samihann.projectthree_a2_sam;

/***
 * By Samihan Nandedkar
 * Project Three
 * CS 478
 *
 * Broadcast receiver for Restaurant broadcast for A2 application.
 *
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RestaurantReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent intent1 = new Intent(context, RestaurantActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }

}
