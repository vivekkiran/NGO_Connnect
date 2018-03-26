package com.example.preethi.ngo_connnect;

/**
 * Created by mahathi on 26/3/18.
 */
import android.os.AsyncTask;
import android.util.Log;

public class MailOperation extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        try {
            GMailSender sender = new GMailSender("team.unitedly@gmail.com", "unitedly.team5");
            sender.sendMail("Hearty Welcome!",
                    "You have successfully joined the event", "team.unitedly@gmail.com",
                    "mahathivavilala97@gmail.com, chasesaphira@gmail.com, anualek123@gmail.com, team.unitedly@gmail.com");
        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
            return "Email Not Sent";
        }
        return "Email Sent";
    }
    @Override
    protected void onPostExecute(String result) {
        Log.e("MailOperation", result + "");
    }
    @Override
    protected void onPreExecute() {
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
