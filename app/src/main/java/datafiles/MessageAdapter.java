package datafiles;

/**
 * Created by Mendhie on 4/21/2018.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import secured.tips.LoginActivity;
import secured.tips.MemberProfileActivity;
import secured.tips.MyProfileActivity;
import secured.tips.R;

public class MessageAdapter extends FirebaseListAdapter<ChatMessage> {
    private Activity activity;
    Context context;
    private String UserID;
    private String subRoom;
    private DatabaseReference mRef;
    Cache cache;
    private Reuseable reuseable;
    private final String TAG = "MyChatApp";
    private StorageReference storageReference;
    private RequestOptions requestOptions = new RequestOptions();

    public MessageAdapter(Activity activity, Class<ChatMessage> modelClass, int modelLayout, DatabaseReference ref, String userID, String room, Context context) {
       super(new FirebaseListOptions.Builder<ChatMessage>()
               .setQuery(ref, modelClass)
               .setLayout(modelLayout)
               .build());

        this.activity = activity;
        this.context = context;
        this.UserID = userID;
        reuseable = new Reuseable();
        mRef = ref;
        subRoom = room;
        cache = new Cache(context);
        requestOptions.placeholder(R.drawable.dspc);
        storageReference = FirebaseStorage.getInstance().getReference().child("profile_images");
    }

    private void popUp(){
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

    private void setTextColor(TextView txtView, char symbol){
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void populateView(@NonNull View v, @NonNull final ChatMessage model, final int position) {
        //Initialize the views
        TextView messageText = v.findViewById(R.id.message_text);
        TextView messageUser = v.findViewById(R.id.message_user);
        TextView messageTime = v.findViewById(R.id.message_time);
        TextView messageStatus = v.findViewById(R.id.message_status);
        final TextView messageLikes = v.findViewById(R.id.message_Likes);
        ImageView imgStatus = v.findViewById(R.id.imgStatus);
        final ImageView imgDropdown = v.findViewById(R.id.imgDropdwon);
        final ImageView imgLikes = v.findViewById(R.id.imgLikes);
        CircleImageView imgDp = v.findViewById(R.id.imgDps);
        GlideApp.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(storageReference.child(model.getMessageUserId()))
                .signature(new ObjectKey(model.getMessageUserId()+"_"+reuseable.getSignature()))
                .into(imgDp);

        final String status = String.valueOf(model.getMessageStatus().charAt(0));
        final char uName = model.getMessageUserId().charAt(0);

        messageText.setText(model.getMessageText());
        reuseable.Linkfiy(context, model.getMessageText(), messageText);
        messageUser.setText(model.getMessageUser());
        messageLikes.setText(String.valueOf(model.getMessageLikesCount()));

        //Set username textView colour
        if((!model.getMessageUserId().equals(UserID))
                &&(!model.getMessageUserId().toLowerCase().contains("admin_"))){
            setTextColor(messageUser, uName);
        }

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
        reuseable.setTime(messageTime, model.getMessageTime());
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

                Button btnSubmit, btnDelete, btnShare, btnReply;
                btnSubmit = dialog.findViewById(R.id.btnSubmit);
                btnDelete = dialog.findViewById(R.id.btnDelete);
                btnShare = dialog.findViewById(R.id.btnShare);
                btnReply = dialog.findViewById(R.id.btnReply);
                reuseable.setSubmitButton(context, btnSubmit, status);

                btnShare.setOnClickListener(view1 -> {
                    reuseable.shareTips(activity, model.getMessageUser(), model.getMessageText());
                    dialog.cancel();
                });
                btnSubmit.setOnClickListener(view12 -> {
                    String ref = getRef(position).toString();
                    submit(getRef(position), ref, model.getMessageText(), model.getMessageUserId());
                    dialog.cancel();
                });
                btnDelete.setOnClickListener(view13 -> {
                    if(status.equals("5")){
                        Toast.makeText(context, "Post pending review", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        getRef(position).removeValue();
                        dialog.cancel();
                        Toast.makeText(context, "Deleted ", Toast.LENGTH_SHORT).show();
                    }
                });
                btnReply.setOnClickListener(v1 -> {
                    EditText input = activity.findViewById(R.id.input);
                    String t = input.getText().toString();
                    input.setText(t+"@"+model.getMessageUser()+" ");
                    input.setSelection(input.getText().length());
                    dialog.cancel();

                });
            }
        });

        imgDp.setOnClickListener(v12 -> openProfile(model.getMessageUserId()));
        messageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile(model.getMessageUserId());
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

    public void submit(DatabaseReference dataRef, String ref, String messageText, String userID){
        if(!subRoom.equals("room_1")){
            DatabaseReference wonGameRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference().child("wonGames").child(subRoom);
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

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage = getItem(position);
        if (chatMessage.getMessageUserId().equals(UserID))
            {
                view = activity.getLayoutInflater().inflate(R.layout.mssg_out, viewGroup, false);
            }
        else
            {
                if (chatMessage.getMessageUserId().toLowerCase().contains("admin_")){
                    view = activity.getLayoutInflater().inflate(R.layout.admin_message, viewGroup, false);
                }
                else{
                    view = activity.getLayoutInflater().inflate(R.layout.mssg_in, viewGroup, false);
                }
            }

        //generating view
        populateView(view, chatMessage, position);

        return view;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 3;
    }

}