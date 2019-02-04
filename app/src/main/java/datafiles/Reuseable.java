package datafiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import secured.tips.R;

public class Reuseable {

    public Reuseable(){}

    public void setSubmitButton(Context context, Button btnSubmit, String status){
        if(status.equals("1")){
            btnSubmit.setText("TIPS WON");
            btnSubmit.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            btnSubmit.setEnabled(false);
        }
        else if (status.equals("2")){
            btnSubmit.setText("SUBMIT");
        }
        else if (status.equals("3")){
            btnSubmit.setText("DISMISSED");
            btnSubmit.setEnabled(false);
            btnSubmit.setTextColor(context.getResources().getColor(R.color.cl_dismissed));
        }
        else if (status.equals("4")){
            btnSubmit.setText("LOST");
            btnSubmit.setEnabled(false);
            btnSubmit.setTextColor(context.getResources().getColor(R.color.rm2));
        }
        else if (status.equals("5")){
            btnSubmit.setText("PENDING");
            btnSubmit.setEnabled(false);
            btnSubmit.setTextColor(context.getResources().getColor(R.color.rm3));
        }
    }

    public void setTime(TextView txtTime, long messageTime){
        // Format the date before showing it
        final long diff = (new Date().getTime())  - messageTime;
        final long diffSeconds = diff / 1000 % 60;
        final long diffMinutes = diff / (60 * 1000) % 60;
        final long diffHours = diff / (60 * 60 * 1000) % 24;
        final long diffDays = diff / (24 * 60 * 60 * 1000);

        if(diffDays > 0){
            txtTime.setText(DateFormat.format("dd MMM  (h:mm a)", messageTime));
        }
        else if(diffHours > 23){
            txtTime.setText(DateFormat.format("dd MMM  (h:mm a)", messageTime));
        }
        else if(diffHours > 0){
            txtTime.setText(diffHours+"h ago");
        }
        else if(diffMinutes >0){
            txtTime.setText(diffMinutes==1? "1min":diffMinutes+"mins");
        }
        else if(diffSeconds > 8){
            txtTime.setText(diffSeconds+"s");
        }
        else{
            txtTime.setText("now");
        }
    }

    public String getRoom(final String title){
        switch (title){
            case "General Discussion":
                return "room_1";
            case "3-10 odds":
                return "room_2";
            case "11-50 odds":
                return "room_3";
            case "51-100 odds":
                return "room_4";
            case "151-350 odds":
                return "room_5";
            case "351+ odds":
                return "room_6";
            case "Draws":
                return "room_7";
        }
        return null;
    }

    public String getSignature(){
        String Signature = "";
        String s = DateFormat.format("HH", new Date().getTime()).toString();
        int sign = Integer.parseInt(s);
        if(sign>=20){
            Signature = "f";
        }else if(sign>=16){
            Signature = "e";
        }else if(sign>=12){
            Signature = "d";
        }else if(sign>=8){
            Signature = "c";
        }else if(sign>=4){
            Signature = "b";
        }else{
            Signature = "a";
        }
        return Signature;
    }

    public void shareTips(Activity activity, String name, String tips){
        String output = "SecuredTips Prediction\n\n"+ tips + "\nPredicted By: "+ name + "\n\nUse the app: http://bit.ly/SecuredTips" ;

        Intent share = new Intent(Intent.ACTION_SEND);share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, output);
        share.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        activity.startActivity(Intent.createChooser(share, "Share via:"));
    }

    public int getTint(Activity activity, String roomName){
        switch (roomName){
            case "General Discussion":
                return activity.getResources().getColor(R.color.rm1);
            case "3-10 odds":
                return activity.getResources().getColor(R.color.rm2);
            case "11-50 odds":
                return activity.getResources().getColor(R.color.rm3);
            case "51-100 odds":
                return R.color.rm4;
            case "151-350 odds":
                return R.color.rm5;
            case "351+ odds":
                return R.color.rm6;
            case "Draws":
                return R.color.rm7;
        }
        return R.color.rm2;
    }

    public void Linkfiy(Context context, String a, TextView textView) {

        Pattern urlPattern = Patterns.WEB_URL;
        Pattern mentionPattern = Pattern.compile("(@[A-Za-z0-9_-]+)");
        Pattern hashtagPattern = Pattern.compile("#(\\w+|\\W+)");

        Matcher o = hashtagPattern.matcher(a);
        Matcher mention = mentionPattern.matcher(a);
        Matcher weblink = urlPattern.matcher(a);


        SpannableString spannableString = new SpannableString(a);
        //#hashtags

        while (o.find()) {

            spannableString.setSpan(new NonUnderlinedClickableSpan(context, o.group(),
                            0), o.start(), o.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // --- @mention
        while (mention.find()) {
            spannableString.setSpan(
                    new NonUnderlinedClickableSpan(context, mention.group(), 1, false), mention.start(), mention.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        //@weblink
        while (weblink.find()) {
            spannableString.setSpan(
                    new NonUnderlinedClickableSpan(context, weblink.group(), 2), weblink.start(), weblink.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


    }

    public void setTextColor(Context context, TextView txtView, char symbol){
        if((symbol >= 'a' && symbol <= 'e')||(symbol >= 'A' && symbol <= 'E')||(symbol >= '0' && symbol <= '1')){
            txtView.setTextColor(context.getResources().getColor(R.color.col1));
        }
        else if((symbol >= 'f' && symbol <= 'j')||(symbol >= 'F' && symbol <= 'J')||(symbol >= '2' && symbol <= '3')){
            txtView.setTextColor(context.getResources().getColor(R.color.col2));
        }
        else if((symbol >= 'k' && symbol <= 'o')||(symbol >= 'K' && symbol <= 'O')||(symbol >= '4' && symbol <= '5')){
            txtView.setTextColor(context.getResources().getColor(R.color.col3));
        }
        else if((symbol >= 'p' && symbol <= 't')||(symbol >= 'P' && symbol <= 'T')||(symbol >= '6' && symbol <= '7')){
            txtView.setTextColor(context.getResources().getColor(R.color.col4));
        }
        else if((symbol >= 'u' && symbol <= 'z')||(symbol >= 'U' && symbol <= 'Z')||(symbol >= '8' && symbol <= '9')){
            txtView.setTextColor(context.getResources().getColor(R.color.col5));
        }
    }
}
