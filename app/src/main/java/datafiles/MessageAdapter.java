package datafiles;

/**
 * Created by Mendhie on 4/21/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.HashMap;
import java.util.Map;

import secured.tips.LoginActivity;
import secured.tips.MemberProfileActivity;
import secured.tips.MyProfileActivity;
import secured.tips.R;

public class MessageAdapter extends FirebaseListAdapter<ChatMessage> {
    Activity activity;
    Context context;
    String UserID;
    String subRoom;
    DatabaseReference mRef;
    Cache cache = new Cache();
    private final String TAG = "MyChatApp";

    public MessageAdapter(Activity activity, Class<ChatMessage> modelClass, int modelLayout, DatabaseReference ref, String userID, String room, Context context) {
       super(new FirebaseListOptions.Builder<ChatMessage>()
               .setQuery(ref, modelClass)
               .setLayout(modelLayout)
               .build());

        this.activity = activity;
        this.context = context;
        this.UserID = userID;
        mRef = ref;
        subRoom = room;
    }


    public void popUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("You must login first")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .show();
    }

    @Override
    protected void populateView(View v, final ChatMessage model, final int position) {
        TextView messageText = v.findViewById(R.id.message_text);
        TextView messageUser = v.findViewById(R.id.message_user);
        TextView messageTime = v.findViewById(R.id.message_time);
        TextView messageStatus = v.findViewById(R.id.message_status);
        final TextView messageLikes = v.findViewById(R.id.message_Likes);
        ImageView imgStatus = v.findViewById(R.id.imgStatus);
        final ImageView imgDropdown = v.findViewById(R.id.imgDropdwon);
        final ImageView imgLikes = v.findViewById(R.id.imgLikes);

        messageText.setText(model.getMessageText());
        messageUser.setText(model.getMessageUser());
        messageLikes.setText(String.valueOf(model.getMessageLikesCount()));

        if(model.getMessageLikes().containsKey(UserID)){
            imgLikes.setImageResource(R.drawable.love);
            imgLikes.setTag("love");
        }else{
            imgLikes.setImageResource(R.drawable.no_love);
            imgLikes.setTag("no_love");
        }
        // Display of 'GAME WON' message
        if(String.valueOf(model.getMessageStatus().charAt(0)).equals("1")){
            messageStatus.setText("WON");
            messageStatus.setVisibility(View.VISIBLE);
            imgStatus.setVisibility(View.VISIBLE);
        }
        else{
            messageStatus.setVisibility(View.GONE);
            imgStatus.setVisibility(View.GONE);
        }

        // Format the date before showing it
        messageTime.setText(DateFormat.format("dd MMM  (h:mm a)", model.getMessageTime()));
       //getRef(position).child("messageLikes").child(UserID).equalTo(true);
        imgDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = activity.getLayoutInflater();
                View dialogView;
                if(imgDropdown.getTag().equals("in")){
                    dialogView = inflater.inflate(R.layout.room_dialog2, null);
                }else{
                    dialogView = inflater.inflate(R.layout.room_dialog, null);
                }
                builder.setView(dialogView);

                final AlertDialog dialog= builder.create();
                dialog.show();

                Button btnSubmit, btnDelete;
                btnSubmit = dialog.findViewById(R.id.btnSubmit);
                btnDelete = dialog.findViewById(R.id.btnDelete);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ref = getRef(position).toString();
                        submit(getRef(position), ref, model.getMessageText(), model.getMessageUserId());
                        Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getRef(position).removeValue();
                        dialog.cancel();
                        Toast.makeText(context, "Deleted ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        messageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getMessageUserId().equals(UserID)){
                    cache.setUserID(model.getMessageUserId());
                    context.startActivity(new Intent(context, MyProfileActivity.class));
                }
                else {
                    cache.setUserID(model.getMessageUserId());
                    context.startActivity(new Intent(context, MemberProfileActivity.class));
                }
            }
        });
        imgLikes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(UserID.equals("x")){
                    popUp();}
                else {
                    onLikeClicked(getRef(position));
                }
                return false;
            }
        });
    }

    public void onLikeClicked(DatabaseReference dRef){
        dRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ChatMessage message = mutableData.getValue(ChatMessage.class);
                if(message!=null){
                    if(message.getMessageLikes().containsKey(UserID)){
                        long i = message.getMessageLikesCount()-1;
                        message.setMessageLikesCount(i);
                        message.getMessageLikes().remove(UserID);
                    }else{
                        long i = message.getMessageLikesCount()+1;
                        message.setMessageLikesCount(i);
                        message.messageLikes.put(UserID, true);

                    }
                    mutableData.setValue(message);

                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.i(TAG, "Updating likes count transaction is completed.");
            }
        });
    }

    public void submit(DatabaseReference dataRef, String ref, String messageText, String userID){
        DatabaseReference wonGameRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference().child("wonGames").child(subRoom);
        Map<String, String> submitted = new HashMap<>();
        submitted.put("databaseRef", ref);
        submitted.put("messageText", messageText);
        submitted.put("userID", userID);
        wonGameRef.push().setValue(submitted);
        dataRef.child("messageStatus").setValue("3"+userID);
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage = getItem(position);
        if (chatMessage.getMessageUserId().equals(UserID))
            view = activity.getLayoutInflater().inflate(R.layout.mssg_out, viewGroup, false);
        else
            view = activity.getLayoutInflater().inflate(R.layout.mssg_in, viewGroup, false);


        //generating view
        populateView(view, chatMessage, position);


        return view;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

}
