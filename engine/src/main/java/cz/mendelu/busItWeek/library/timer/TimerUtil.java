package cz.mendelu.busItWeek.library.timer;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import cz.mendelu.busItWeek.library.StoryLine;
import cz.mendelu.busItWeek.library.Task;

/**
 * The utility for measuring the time of the task.
 */
public class TimerUtil {

    // the count down timer
    private static CountDownTimer timer = null;
    // milliseconds remaining
    private static long millisecondsRemaining = -1;

    /**
     * Starts the timer.
     * @param timeInMilliseconds timer time in milliseconds
     * @param textView textview to diaplay the time
     * @param activity the activity running the timer
     * @param databaseClass the database class for skipping the task
     */
    public static void startTimer(long timeInMilliseconds, final TextView textView, final Activity activity, final Class databaseClass){

        if (timer != null){
            timer.cancel();
            timer = null;
        }

        if (timer == null){
            timer = new CountDownTimer(timeInMilliseconds, 1000) {
                @Override
                public void onTick(long l) {
                    if (textView != null){
                        textView.setText(millisecondsToString(l));

                    }
                    millisecondsRemaining = l;
                }

                @Override
                public void onFinish() {
                    millisecondsRemaining = -1;
                    createFinishTimeDialog(activity, databaseClass).show();
                }
            };
            timer.start();
        }

    }

    /**
     * Stops the timer.
     */
    public static void stopTimer(){
        timer.cancel();
        timer = null;
        millisecondsRemaining = -1;
    }

    /**
     * Returns the String representing the time for the textview
     * @param milliseconds
     * @return
     */
    private static String millisecondsToString(long milliseconds){
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds))
        );
    }

    /**
     * Creates a dialog shown at the finish of the time
     * @param activity the activity to display dialog in
     * @param databaseClass the database class
     * @return the dialog to be shown
     */
    private static Dialog createFinishTimeDialog(final Activity activity, final Class databaseClass){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("No more time");
        alertDialogBuilder
                .setMessage("Your time has ended!")
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        StoryLine storyLine = StoryLine.open(activity, databaseClass);
                        Task currentTask = storyLine.currentTask();
                        currentTask.skip();
                        activity.finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        return alertDialog;
    }

}
