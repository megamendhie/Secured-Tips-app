package datafiles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import secured.tips.MemberProfileActivity;
import secured.tips.R;

public class NonUnderlinedClickableSpan extends ClickableSpan {

    private int type;// 0-hashtag , 1- mention, 2- url link
    private String text;// Keyword or url
    private Context context;
    private boolean allow;
    private String TAG = "NonUnderlinedSpan";
    boolean fire = false;

    public NonUnderlinedClickableSpan(Context context, String text, int type) {
        this.text = text;
        this.type = type;
        this.context = context;
    }
    public NonUnderlinedClickableSpan(Context context, String text, int type, boolean allow) {
        this.text = text;
        this.type = type;
        this.context = context;
        this.allow = allow;
    }

    @Override
    public void onClick(View widget) {
        Log.d(TAG, "onClick: ok" + text);
        switch (type){
            case 0:
                break;
            case 1:
                if(!allow){
                    return;
                }
                String username = text.substring(1);
                Query fbDb = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/")
                        .getReference().child("Users").orderByChild("a3_username").equalTo(username);
                openAccount(fbDb);
                break;
            default:
                Uri webpage = Uri.parse(text);
                if (!text.startsWith("http://") && !text.startsWith("https://")) {
                    webpage = Uri.parse("http://" + text);
                }
                Intent i = new Intent(android.content.Intent.ACTION_VIEW, webpage);
                context.startActivity(i);
                break;
        }
        return;

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
        ds.setColor(context.getResources().getColor(
                R.color.hastag));
        // ds.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void openAccount(Query fbDb){
        final ValueEventListener mListener = fbDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                int size = (int) dataSnapshot.getChildrenCount();
                if(size==1){
                    String userID="";
                    int k =0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        k++;
                        userID = snapshot.getKey().toString();
                        if(k==1){
                            continue;
                        }
                    }
                    Cache.setUserID(userID);
                    Log.i(TAG, "onDataChange: Open Account");
                    context.startActivity(new Intent(context, MemberProfileActivity.class));
                }
                else if(size==0){
                    Toast.makeText(context, "unknown username", Toast.LENGTH_SHORT).show();
                }
                if(size>1){
                    Toast.makeText(context, "Username conflict", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}