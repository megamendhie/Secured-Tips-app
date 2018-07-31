package secured.tips;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import datafiles.Cache;
import datafiles.ChatMessage;
import datafiles.PostAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentpostFragment extends Fragment {
    String userID, myUserID;
    private final String TAG = "MyChatApp";
    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    TextView txtNotice;
    Cache cache = new Cache();
    List<ChatMessage> chatMessages;
    List<DatabaseReference> Refs = new ArrayList<DatabaseReference>();
    private PostAdapter adapter;
    FirebaseAuth auth;


    public RecentpostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_recentpost, container, false);
        recyclerView = rootView.findViewById(R.id.rvRecent);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        txtNotice = rootView.findViewById(R.id.txtNotice);
        chatMessages = new ArrayList<ChatMessage>();
        userID = cache.getUserID();
        mRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference().child("chatrooms");
        auth = FirebaseAuth.getInstance();
        myUserID = auth.getUid().toString();

        mRef.keepSynced(true);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Refs.clear();
                chatMessages.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    snapshot.getRef().orderByChild("messageUserId").equalTo(userID).limitToLast(10).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snap : dataSnapshot.getChildren()){
                                    chatMessages.add(snap.getValue(ChatMessage.class));
                                    Refs.add(snap.getRef());
                            }
                            //Collections.reverse(chatMessages);
                            //Collections.reverse(Refs);
                            adapter = new PostAdapter(getContext(), chatMessages, myUserID, Refs);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                            if(adapter.getItemCount()==0){
                                txtNotice.setVisibility(View.VISIBLE);
                            }
                            else {
                                txtNotice.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
