package datafiles;

/**
 * Created by Mendhie on 4/21/2018.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import secured.tips.LoginActivity;
import secured.tips.MemberProfileActivity;
import secured.tips.MyProfileActivity;
import secured.tips.R;

public class RecommendedAdapter extends StatelessSection {

    String UserID;
    List<ChatMessage> posts;
    Context context;
    Activity activity;
    Reuseable reuseable;
    List<DatabaseReference> Refs;
    StorageReference storageReference;
    private final String TAG = "MyChatApp";
    Cache cache;
    RequestOptions requestOptions = new RequestOptions();

    public RecommendedAdapter(List<ChatMessage> post, Context context, Activity activity, String userID,
                              List<DatabaseReference> Refs) {
        super(new SectionParameters.Builder(R.layout.recommended_post)
                .headerResourceId(R.layout.recommended_header)
                .build());

        Log.i(TAG, post.toString());
        reuseable = new Reuseable();
        this.posts = post;
        this.context = context;
        this.activity = activity;
        this.UserID = userID;
        this.Refs = Refs;
        cache = new Cache(context);
        requestOptions.placeholder(R.drawable.dspc);
        storageReference = FirebaseStorage.getInstance().getReference().child("profile_images");
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
    public void onBindItemViewHolder(RecyclerView.ViewHolder nholder, int position) {
        final ItemViewHolder holder = (ItemViewHolder) nholder;
        final ChatMessage model = posts.get(position);

        TextView messageText = holder.messageText;
        TextView messageUser = holder.messageUser;
        TextView messageTime = holder.messageTime;
        TextView messageStatus = holder.messageStatus;
        final TextView messageLikes = holder.messageLikes;
        ImageView imgStatus = holder.imgStatus;
        final ImageView imgLikes = holder.imgLikes;
        final CircleImageView imgDp = holder.imgDp;
        final char uName = model.getMessageUserId().charAt(0);

        GlideApp.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(storageReference.child(model.getMessageUserId()))
                .signature(new ObjectKey(model.getMessageUserId()+"_"+reuseable.getSignature()))
                .into(imgDp);

        storageReference.child(model.getMessageUserId()+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Log.i(TAG, "onSuccess: url is "+ url);
                Glide.with(context)
                        .setDefaultRequestOptions(requestOptions)
                        .load(url)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgDp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i(TAG, "onFailure: error handled");
            }
        });

        messageText.setText(model.getMessageText());
        reuseable.Linkfiy(activity, model.getMessageText(), messageText);
        messageUser.setText(model.getMessageUser());
        messageLikes.setText(String.valueOf(model.getMessageLikesCount()));

        //Set username textView colour
        if((!model.getMessageUserId().equals(UserID))
                &&(!model.getMessageUserId().toLowerCase().contains("admin_"))){
            reuseable.setTextColor(context, messageUser, uName);
        }

        if(model.getMessageLikes().containsKey(UserID)){
            imgLikes.setImageResource(R.drawable.love); //delete soon
            imgLikes.setTag("love"); //delete soon
        }else{
            imgLikes.setImageResource(R.drawable.no_love); //delete soon
            imgLikes.setTag("no_love"); //delete soon
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
        reuseable.setTime(messageTime, model.getMessageTime());

        imgDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile(model.getMessageUserId());
            }
        });
        messageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile(model.getMessageUserId());
            }
        });

        imgLikes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(UserID.equals("x")) {
                    popUp();
                } else {
                    onLikeClicked(getRef(position));
                }
                return false;
            }
        });

        messageLikes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(UserID.equals("x")){
                    popUp();}
                else {
                    onLikeClicked(getRef(position));
                }
                return false;
            }
        });
    }

    public void openProfile(String MessageUserId){
        if(UserID.equals("x")){
            popUp();
            return;}
        if(MessageUserId.toLowerCase().contains("admin_")){
            return;
        }
        if(MessageUserId.equals(UserID)){
            cache.setUserID(UserID);
            context.startActivity(new Intent(context, MyProfileActivity.class));
        }
        else {
            cache.setUserID(MessageUserId);
            context.startActivity(new Intent(context, MemberProfileActivity.class));
        }
    }

    public DatabaseReference getRef(int position){
        return  Refs.get(position);
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
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView messageUser;
        TextView messageTime;
        TextView messageStatus;
        TextView messageLikes;
        ImageView imgStatus;
        ImageView imgLikes;
        CircleImageView imgDp;
        public ItemViewHolder(View v) {
            super(v);
            messageText = v.findViewById(R.id.message_text);
            messageUser = v.findViewById(R.id.message_user);
            messageTime = v.findViewById(R.id.message_time);
            messageStatus = v.findViewById(R.id.message_status);
            messageLikes = v.findViewById(R.id.message_Likes);
            imgStatus = v.findViewById(R.id.imgStatus);
            imgLikes = v.findViewById(R.id.imgLikes);
            imgDp = v.findViewById(R.id.imgDp);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout lnrHeader;
        HeaderViewHolder(View view) {
            super(view);
            lnrHeader = view.findViewById(R.id.lnrHeader);
        }
    }

}