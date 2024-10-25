package com.example.TagFinder;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Tabledata extends AppCompatActivity {

    Notification notification;
    public static String channelId = "CHANNEL_ID";
    public static int massageId = 100;
    Calendar myCalendar1, myCalendar2;
    ImageView calender1, calender2;
    TextView startDate, endDate;
    String myFormat = "dd-MM-yyyy"; // Default format
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private static final int MAX_DAYS_DIFFERENCE = 90;// Maximum allowed difference


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabledata);

        // Initializing views
        calender1 = findViewById(R.id.calender1);
        calender2 = findViewById(R.id.calender2);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);


        // Initializing Calendar instances
        myCalendar1 = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();


        // Start date picker
        calender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalender1();
            }
        });

        // End date picker
        calender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalender2();
            }
        });

//        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tag, null);
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//        Bitmap largeIcon = bitmapDrawable.getBitmap();
//
//        Intent intent = new Intent(this, VideoTagsGet.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//
//        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notification = new Notification.Builder(this)
//                    .setLargeIcon(largeIcon)
//                    .setSmallIcon(R.drawable.bell)
//                    .setContentTitle("notification")
//                    .setContentText("new message")
//                    .setSubText("new message comming from ..")
//                    .setContentIntent(pendingIntent)
//                    .setChannelId(channelId)
//                    .setAutoCancel(true)
//                    .build();
//            nm.createNotificationChannel(new NotificationChannel(channelId, "new massage", NotificationManager.IMPORTANCE_HIGH));
//        } else {
//            notification = new Notification.Builder(this)
//                    .setLargeIcon(largeIcon)
//                    .setSmallIcon(R.drawable.bell)
//                    .setContentText("new message")
//                    .setSubText("new message comming from ..")
//                    .setContentIntent(pendingIntent)
//                    .build();
//        }

//        nm.notify(massageId, notification);

    }

    // Start date picker dialog
    public void openCalender1() {
        int mYear = myCalendar1.get(Calendar.YEAR);
        int mMonth = myCalendar1.get(Calendar.MONTH);
        int mDay = myCalendar1.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Tabledata.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar1.set(year, monthOfYear, dayOfMonth);
                        // Ensure start date is set before today
                        if (myCalendar1.after(Calendar.getInstance())) {
                            Toast.makeText(Tabledata.this, "Start date cannot be in the future!", Toast.LENGTH_SHORT).show();
                        } else {
                            startDate.setText(sdf.format(myCalendar1.getTime()));
                            calculateDaysDifference(); // Recalculate days difference
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); // Restrict to today
        datePickerDialog.show();
    }

    // End date picker dialog
    public void openCalender2() {
        int mYear = myCalendar2.get(Calendar.YEAR);
        int mMonth = myCalendar2.get(Calendar.MONTH);
        int mDay = myCalendar2.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Tabledata.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar2.set(year, monthOfYear, dayOfMonth);
                        // Ensure end date is in the past
                        if (myCalendar2.after(Calendar.getInstance())) {
                            Toast.makeText(Tabledata.this, "End date cannot be in the future!", Toast.LENGTH_SHORT).show();
                        } else {
                            endDate.setText(sdf.format(myCalendar2.getTime()));
                            calculateDaysDifference(); // Recalculate days difference
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); // Restrict to today
        datePickerDialog.show();
    }

    // Calculate the difference between start and end date
    private void calculateDaysDifference() {
        if (myCalendar1 != null && myCalendar2 != null) {
            long differenceInMillis = myCalendar2.getTimeInMillis() - myCalendar1.getTimeInMillis();
            long daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMillis);

            // Validate that the end date is after the start date
            if (daysDifference < 0) {
                Toast.makeText(this, "End date should be after start date!", Toast.LENGTH_SHORT).show();
            } else if (daysDifference > MAX_DAYS_DIFFERENCE) {
                // Validate that the difference is exactly 90 days
                Toast.makeText(this, "The difference between start and end date should not exceed 90 days", Toast.LENGTH_SHORT).show();
            } else {
                // Display the day difference in a log for debugging purposes or further usage
                Log.e("Days Difference", ":= " + daysDifference);
            }
        }
    }


}
