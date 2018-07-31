package datafiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

import secured.tips.R;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    List<ChatMessage> posts;
    List<DatabaseReference> Refs;
    Context context;
    String UserID;
    Cache cache = new Cache();
    private final String TAG = "MyChatApp";

    public PostAdapter(){}

    public PostAdapter(Context context, List<ChatMessage> posts, String UserID, List<DatabaseReference> Refs){
        this.context = context;
        this.posts = posts;
        this.Refs = Refs;
        this.UserID = UserID;
    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder method called");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posts, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder method called");
        holder.messageUser.setText(posts.get(position).getMessageUser());
        holder.messageText.setText(posts.get(position).getMessageText());
        // Format the date before showing it
        holder.messageTime.setText(DateFormat.format("dd MMM  (h:mm a)", posts.get(position).getMessageTime()));

        holder.messageLikes.setText(String.valueOf(posts.get(position).getMessageLikesCount()));
        if(posts.get(position).getMessageLikes().containsKey(UserID)){
            holder.imgLikes.setImageResource(R.drawable.love);
            holder.imgLikes.setTag("love");
        }else{
            holder.imgLikes.setImageResource(R.drawable.no_love);
            holder.imgLikes.setTag("no_love");
        }


        // Display of 'GAME WON' message
        if(String.valueOf(posts.get(position).getMessageStatus().charAt(0)).equals("1")){
            holder.messageStatus.setText("WON");
            holder.messageStatus.setVisibility(View.VISIBLE);
            holder.imgStatus.setVisibility(View.VISIBLE);
        }
        else{
            holder.messageStatus.setVisibility(View.GONE);
            holder.imgStatus.setVisibility(View.GONE);
        }

        holder.imgLikes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                onLikeClicked(getRef(position));
                return false;
            }
        });
    }

    public DatabaseReference getRef(int position){
        return  Refs.get(position);
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


    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public int getItemCount() {
        return this.posts.size();
    }
}
