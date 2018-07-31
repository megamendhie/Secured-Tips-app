package datafiles;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import secured.tips.R;

/**
 * Created by Mendhie on 4/30/2018.
 */

public class PostViewHolder extends RecyclerView.ViewHolder{
    TextView messageText, messageUser;
    TextView messageTime, messageStatus, messageLikes;
    ImageView imgStatus, imgLikes;
    public PostViewHolder(View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.message_text);
        messageUser = itemView.findViewById(R.id.message_user);
        messageTime = itemView.findViewById(R.id.message_time);
        messageStatus = itemView.findViewById(R.id.message_status);
        messageLikes = itemView.findViewById(R.id.message_Likes);
        imgStatus = itemView.findViewById(R.id.imgStatus);
        imgLikes = itemView.findViewById(R.id.imgLikes);

    }
}
