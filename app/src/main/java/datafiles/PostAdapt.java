package datafiles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import secured.tips.R;

public class PostAdapt extends StatelessSection {
    String title;
    String UserID;
    List<ChatMessage> posts;
    Context context;
    Activity activity;
    Reuseable reuseable;
    List<DatabaseReference> Refs;
    private final String TAG = "MyChatApp";

    public PostAdapt(String title, List<ChatMessage> post, Context context, Activity activity, String userID,
                     List<DatabaseReference> Refs){
        super(new SectionParameters.Builder(R.layout.posts)
                .headerResourceId(R.layout.room_header)
                .build());
        Log.i(TAG, post.toString());
        reuseable = new Reuseable();
        this.title = title;
        this.posts = post;
        this.context = context;
        this.activity = activity;
        this.UserID = userID;
        this.Refs = Refs;
        //this.setHasStableIds(true);

    }

    @Override
    public int getContentItemsTotal() {
        return posts.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder postHolder = (ItemViewHolder) holder;
        final ChatMessage post = posts.get(position);

        final String status = String.valueOf(post.getMessageStatus().charAt(0));
        //Log.i(TAG, "onBindViewHolder method called");
        postHolder.messageUser.setText(post.getMessageUser());
        postHolder.messageText.setText(post.getMessageText());
        reuseable.Linkfiy(context, post.getMessageText(), postHolder.messageText);
        // Format the date before showing it
        reuseable.setTime(postHolder.messageTime, post.getMessageTime());

        postHolder.messageLikes.setText(String.valueOf(post.getMessageLikesCount()));
        if(post.getMessageLikes().containsKey(UserID)){
            postHolder.imgLikes.setImageResource(R.drawable.love);
            postHolder.imgLikes.setTag("love");
        }else{
            postHolder.imgLikes.setImageResource(R.drawable.no_love);
            postHolder.imgLikes.setTag("no_love");
        }

        // Display of 'GAME WON' message
        if(String.valueOf(post.getMessageStatus().charAt(0)).equals("1")){
            postHolder.messageStatus.setText("WON");
            postHolder.messageStatus.setVisibility(View.VISIBLE);
            postHolder.imgStatus.setVisibility(View.VISIBLE);
        }
        else{
            postHolder.messageStatus.setVisibility(View.GONE);
            postHolder.imgStatus.setVisibility(View.GONE);
        }

        postHolder.messageLikes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onLikeClicked(getRef(position), position);
                return false;
            }
        });

        postHolder.imgLikes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                onLikeClicked(getRef(position), position);
                return false;
            }
        });

        postHolder.imgDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = activity.getLayoutInflater();
                View dialogView;
                if(post.getMessageUserId().equals(UserID)){
                    dialogView = inflater.inflate(R.layout.room_dialog, null);
                }else{
                    dialogView = inflater.inflate(R.layout.room_dialog2, null);
                }
                builder.setView(dialogView);

                final AlertDialog dialog= builder.create();
                dialog.show();

                Button btnSubmit, btnDelete, btnShare, btnReply;
                btnSubmit = dialog.findViewById(R.id.btnSubmit);
                reuseable.setSubmitButton(context, btnSubmit, status);
                btnDelete = dialog.findViewById(R.id.btnDelete);
                btnShare = dialog.findViewById(R.id.btnShare);
                btnReply = dialog.findViewById(R.id.btnReply);
                btnReply.setVisibility(View.GONE);
                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reuseable.shareTips(activity, post.getMessageUser(), post.getMessageText());
                        dialog.cancel();
                    }
                });
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ref = getRef(position).toString();
                        submit(getRef(position), ref, post.getMessageText(), post.getMessageUserId());
                        dialog.cancel();
                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(status.equals("5")){
                            Toast.makeText(context, "Post pending review", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            getRef(position).removeValue();
                            dialog.cancel();
                            Toast.makeText(context, "Deleted ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        postHolder.messageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = activity.getLayoutInflater();
                View dialogView;
                if(post.getMessageUserId().equals(UserID)){
                    dialogView = inflater.inflate(R.layout.room_dialog, null);
                }else{
                    dialogView = inflater.inflate(R.layout.room_dialog2, null);
                }
                builder.setView(dialogView);

                final AlertDialog dialog= builder.create();
                dialog.show();

                Button btnSubmit, btnDelete, btnShare, btnReply;
                btnSubmit = dialog.findViewById(R.id.btnSubmit);
                reuseable.setSubmitButton(context, btnSubmit, status);
                btnDelete = dialog.findViewById(R.id.btnDelete);
                btnShare = dialog.findViewById(R.id.btnShare);
                btnReply = dialog.findViewById(R.id.btnReply);
                btnReply.setVisibility(View.GONE);
                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reuseable.shareTips(activity, post.getMessageUser(), post.getMessageText());
                        dialog.cancel();
                    }
                });
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ref = getRef(position).toString();
                        submit(getRef(position), ref, post.getMessageText(), post.getMessageUserId());
                        dialog.cancel();
                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(status.equals("5")){
                            Toast.makeText(context, "Post pending review", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            getRef(position).removeValue();
                            dialog.cancel();
                            Toast.makeText(context, "Deleted ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void submit(DatabaseReference dataRef, String ref, String messageText, String userID){
        if(!reuseable.getRoom(title).equals("room_1")){
            DatabaseReference wonGameRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference()
                    .child("wonGames").child(reuseable.getRoom(title));
            Map<String, String> submitted = new HashMap<>();
            submitted.put("databaseRef", ref);
            submitted.put("messageText", messageText);
            submitted.put("userID", userID);
            wonGameRef.push().setValue(submitted);
            dataRef.child("messageStatus").setValue("5_"+userID);
            Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT).show();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
            builder.setMessage("You cannot submit post from discussion room")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //do nothing
                        }
                    })
                    .show();
        }
    }

    public DatabaseReference getRef(int position){
        return  Refs.get(position);
    }

    public void onLikeClicked(DatabaseReference dRef, int position){
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
                        Music.like(context, R.raw.like);
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

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        headerHolder.txtHeader.setText(title);
        headerHolder.imgRoomIcn.setColorFilter(reuseable.getTint(activity, title));
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtHeader;
        private final ImageView imgRoomIcn;

        HeaderViewHolder(View view) {
            super(view);
            txtHeader = view.findViewById(R.id.txtTitle);
            imgRoomIcn = view.findViewById(R.id.imgRoomIcn);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        CardView crdPost;
        TextView messageText, messageUser;
        TextView messageTime, messageStatus, messageLikes;
        ImageView imgStatus, imgLikes, imgDropdown;
        public ItemViewHolder(View itemView){
            super(itemView);
            crdPost = itemView.findViewById(R.id.crdPost);
            messageText = itemView.findViewById(R.id.message_text);
            messageUser = itemView.findViewById(R.id.message_user);
            messageTime = itemView.findViewById(R.id.message_time);
            messageStatus = itemView.findViewById(R.id.message_status);
            messageLikes = itemView.findViewById(R.id.message_Likes);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            imgLikes = itemView.findViewById(R.id.imgLikes);
            imgDropdown = itemView.findViewById(R.id.imgDropdwon);
        }
    }

}
