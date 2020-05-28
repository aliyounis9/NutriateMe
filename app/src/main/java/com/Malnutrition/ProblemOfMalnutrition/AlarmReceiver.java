package com.Malnutrition.ProblemOfMalnutrition;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Time For Your Meal", Toast.LENGTH_SHORT).show();
    }
}
