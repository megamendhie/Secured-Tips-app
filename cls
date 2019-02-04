[33mcommit 26176ff07e793b3b3d133aaa4ddc3bde01dcca2b[m[33m ([m[1;36mHEAD -> [m[1;32mmaster[m[33m, [m[1;31morigin/master[m[33m)[m
Author: Mendhie <imendhie@yahoo.com>
Date:   Tue Jul 31 17:45:47 2018 +0100

    Verification activity modified
    MyProfile Activity modified, and ProfileEditActivity added

[1mdiff --git a/.idea/misc.xml b/.idea/misc.xml[m
[1mindex 6faa303..47eabe0 100644[m
[1m--- a/.idea/misc.xml[m
[1m+++ b/.idea/misc.xml[m
[36m@@ -24,7 +24,7 @@[m
       </value>[m
     </option>[m
   </component>[m
[31m-  <component name="ProjectRootManager" version="2" languageLevel="JDK_1_7" default="true" project-jdk-name="JDK" project-jdk-type="JavaSDK">[m
[32m+[m[32m  <component name="ProjectRootManager" version="2" languageLevel="JDK_1_8" default="true" project-jdk-name="JDK" project-jdk-type="JavaSDK">[m
     <output url="file://$PROJECT_DIR$/build/classes" />[m
   </component>[m
   <component name="ProjectType">[m
[1mdiff --git a/app/build.gradle b/app/build.gradle[m
[1mindex d30a7b1..af78922 100644[m
[1m--- a/app/build.gradle[m
[1m+++ b/app/build.gradle[m
[36m@@ -21,25 +21,27 @@[m [mandroid {[m
 }[m
 [m
 dependencies {[m
[31m-    compile fileTree(dir: 'libs', include: ['*.jar'])[m
[31m-    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {[m
[32m+[m[32m    implementation fileTree(dir: 'libs', include: ['*.jar'])[m
[32m+[m[32m    androidTestImplementation('com.android.support.test.espresso:espresso-core:2.2.2', {[m
         exclude group: 'com.android.support', module: 'support-annotations'[m
     })[m
 [m
[31m-    compile 'com.google.firebase:firebase-core:15.0.0'[m
[31m-    compile 'com.google.firebase:firebase-ads:15.0.0'[m
[31m-    compile 'com.google.firebase:firebase-database:15.0.0'[m
[31m-    compile 'com.google.firebase:firebase-messaging:15.0.2'[m
[31m-    compile 'com.firebaseui:firebase-ui-auth:3.3.1'[m
[31m-    compile 'com.firebaseui:firebase-ui-database:3.3.1'[m
[31m-    compile 'com.android.support:appcompat-v7:26.1.0'[m
[31m-    compile 'com.android.support:cardview-v7:26.1.0'[m
[31m-    compile 'com.android.support:design:26.1.0'[m
[31m-    compile 'io.github.luizgrp.sectionedrecyclerviewadapter:sectionedrecyclerviewadapter:1.1.3'[m
[31m-    compile 'com.android.support.constraint:constraint-layout:+'[m
[31m-    testCompile 'junit:junit:4.12'[m
[32m+[m[32m    implementation 'com.google.firebase:firebase-core:15.0.0'[m
[32m+[m[32m    implementation 'com.google.firebase:firebase-ads:15.0.0'[m
[32m+[m[32m    implementation 'com.google.firebase:firebase-database:15.0.0'[m
[32m+[m[32m    implementation 'com.google.firebase:firebase-messaging:15.0.2'[m
[32m+[m[32m    implementation 'com.google.firebase:firebase-storage:15.0.0'[m
[32m+[m[32m    implementation 'com.firebaseui:firebase-ui-auth:3.3.1'[m
[32m+[m[32m    implementation 'com.firebaseui:firebase-ui-database:3.3.1'[m
[32m+[m[32m    implementation 'com.firebaseui:firebase-ui-storage:0.6.0'[m
[32m+[m[32m    implementation 'com.android.support:appcompat-v7:26.1.0'[m
[32m+[m[32m    implementation 'com.android.support:cardview-v7:26.1.0'[m
[32m+[m[32m    implementation 'com.android.support:design:26.1.0'[m
[32m+[m[32m    implementation 'io.github.luizgrp.sectionedrecyclerviewadapter:sectionedrecyclerviewadapter:1.1.3'[m
[32m+[m[32m    implementation 'com.android.support.constraint:constraint-layout:+'[m
[32m+[m[32m    implementation 'com.github.bumptech.glide:glide:3.7.0'[m
[32m+[m[32m    implementation 'com.theartofdev.edmodo:android-image-cropper:2.7.+'[m
[32m+[m[32m    testImplementation 'junit:junit:4.12'[m
 }[m
 [m
[31m-[m
[31m-[m
 apply plugin: 'com.google.gms.google-services'[m
[1mdiff --git a/app/src/main/AndroidManifest.xml b/app/src/main/AndroidManifest.xml[m
[1mindex a2295c2..19f0816 100644[m
[1m--- a/app/src/main/AndroidManifest.xml[m
[1m+++ b/app/src/main/AndroidManifest.xml[m
[36m@@ -48,13 +48,16 @@[m
         <activity[m
             android:name=".FloatingActivity"[m
             android:theme="@android:style/Theme.Holo.Light.Dialog.NoActionBar" />[m
[32m+[m[32m        <activity[m
[32m+[m[32m            android:name="com.theartofdev.edmodo.cropper.CropImageActivity"[m
[32m+[m[32m            android:theme="@style/Base.Theme.AppCompat" />[m
 [m
[31m-        <service android:name=".MyFirebaseServicee">[m
[32m+[m[32m        <service android:name="datafiles.MyFirebaseServicee">[m
             <intent-filter>[m
                 <action android:name="com.google.firebase.MESSAGING_EVENT" />[m
             </intent-filter>[m
         </service>[m
[31m-        <service android:name=".MyService">[m
[32m+[m[32m        <service android:name="datafiles.MyService">[m
             <intent-filter>[m
                 <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />[m
             </intent-filter>[m
[36m@@ -62,9 +65,15 @@[m
 [m
         <activity android:name=".PaystackActivity" />[m
         <activity android:name=".FreePageActivity" />[m
[31m-        <activity android:name=".VipPageActivity" />[m
[32m+[m[32m        <activity[m
[32m+[m[32m            android:name=".VipPageActivity"[m
[32m+[m[32m            android:label="   TipZone" />[m
         <activity android:name=".MyProfileActivity" />[m
[31m-        <activity android:name=".RoomsPageActivity"></activity>[m
[32m+[m[32m        <activity android:name=".RoomsPageActivity" />[m
[32m+[m[32m        <activity android:name=".AboutTipzoneActivity" />[m
[32m+[m[32m        <activity android:name=".ChatRoomActivity" />[m
[32m+[m[32m        <activity android:name=".MemberProfileActivity" />[m
[32m+[m[32m        <activity android:name=".ProfileEditActivity"></activity>[m
     </application>[m
 [m
 </manifest>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/java/datafiles/AdsAdapter.java b/app/src/main/java/datafiles/AdsAdapter.java[m
[1mnew file mode 100644[m
[1mindex 0000000..210806e[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/datafiles/AdsAdapter.java[m
[36m@@ -0,0 +1,63 @@[m
[32m+[m[32mpackage datafiles;[m
[32m+[m
[32m+[m[32mimport android.content.Context;[m
[32m+[m[32mimport android.support.annotation.NonNull;[m
[32m+[m[32mimport android.support.v4.view.PagerAdapter;[m
[32m+[m[32mimport android.support.v4.view.ViewPager;[m
[32m+[m[32mimport android.view.LayoutInflater;[m
[32m+[m[32mimport android.view.View;[m
[32m+[m[32mimport android.view.ViewGroup;[m
[32m+[m[32mimport android.widget.Button;[m
[32m+[m[32mimport android.widget.ImageView;[m
[32m+[m
[32m+[m[32mimport java.util.List;[m
[32m+[m
[32m+[m[32mimport secured.tips.R;[m
[32m+[m
[32m+[m[32mpublic class AdsAdapter extends PagerAdapter {[m
[32m+[m
[32m+[m[32m    private Context context;[m
[32m+[m[32m    private List<Integer> imageDrawable;[m
[32m+[m[32m    private List<String> buttonText;[m
[32m+[m
[32m+[m[32m    public AdsAdapter(Context context, List<Integer> imageDrawable, List<String> buttonText){[m
[32m+[m[32m        this.context = context;[m
[32m+[m[32m        this.imageDrawable = imageDrawable;[m
[32m+[m[32m        this.buttonText = buttonText;[m
[32m+[m[32m    }[m
[32m+[m[41m    [m
[32m+[m[32m    @Override[m
[32m+[m[32m    public int getCount() {[m
[32m+[m[32m        return imageDrawable.size();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {[m
[32m+[m[32m        return view==object;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public Object instantiateItem(ViewGroup container, int position){[m
[32m+[m[32m        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);[m
[32m+[m[32m        View view = inflater.inflate(R.layout.ads_slider, null);[m
[32m+[m
[32m+[m[32m        ImageView adsImage = view.findViewById(R.id.adsImage);[m
[32m+[m[32m        Button adsButton = view.findViewById(R.id.adsLinkButton);[m
[32m+[m
[32m+[m[32m        adsImage.setImageResource(imageDrawable.get(position));[m
[32m+[m[32m        adsButton.setText(buttonText.get(position));[m
[32m+[m
[32m+[m[32m        ViewPager viewPager = (ViewPager) container;[m
[32m+[m[32m        viewPager.addView(view, 0);[m
[32m+[m
[32m+[m[32m        return view;[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public void destroyItem(ViewGroup container, int position, Object object) {[m
[32m+[m[32m        ViewPager viewPager = (ViewPager) container;[m
[32m+[m[32m        View view = (View) object;[m
[32m+[m[32m        viewPager.removeView(view);[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/app/src/main/java/datafiles/Cache.java b/app/src/main/java/datafiles/Cache.java[m
[1mnew file mode 100644[m
[1mindex 0000000..3bb787e[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/datafiles/Cache.java[m
[36m@@ -0,0 +1,29 @@[m
[32m+[m[32mpackage datafiles;[m
[32m+[m
[32m+[m[32mpublic class Cache {[m
[32m+[m[32m    private static String userID;[m
[32m+[m
[32m+[m[32m    static String room;[m
[32m+[m
[32m+[m[32m    public Cache(){}[m
[32m+[m
[32m+[m[32m    public Cache(String userID){[m
[32m+[m[32m        this.userID = userID;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public static String getUserID() {[m
[32m+[m[32m        return userID;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public static void setUserID(String userID) {[m
[32m+[m[32m        Cache.userID = userID;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public static String getRoom() {[m
[32m+[m[32m        return room;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public static void setRoom(String room) {[m
[32m+[m[32m        Cache.room = room;[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/app/src/main/java/datafiles/ChatMessage.java b/app/src/main/java/datafiles/ChatMessage.java[m
[1mnew file mode 100644[m
[1mindex 0000000..8af2aab[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/datafiles/ChatMessage.java[m
[36m@@ -0,0 +1,87 @@[m
[32m+[m[32mpackage datafiles;[m
[32m+[m
[32m+[m
[32m+[m[32mimport java.util.Date;[m
[32m+[m[32mimport java.util.HashMap;[m
[32m+[m[32mimport java.util.Map;[m
[32m+[m
[32m+[m
[32m+[m[32mpublic class ChatMessage {[m
[32m+[m[32m    private String messageUser;[m
[32m+[m[32m    private String messageText;[m
[32m+[m[32m    private String messageUserId;[m
[32m+[m[32m    private String messageStatus;[m
[32m+[m[32m    private long messageTime;[m
[32m+[m[32m    private long  messageLikesCount;[m
[32m+[m[32m    public Map<String, Boolean> messageLikes = new HashMap<>();[m
[32m+[m
[32m+[m[32m    public ChatMessage(String messageUser, String messageText, String messageUserId, String messageStatus, long messageLikesCount, Map<String, Boolean> messageLikes) {[m
[32m+[m[32m        this.messageUser = messageUser;[m
[32m+[m[32m        this.messageText = messageText;[m
[32m+[m[32m        messageTime = new Date().getTime();[m
[32m+[m[32m        this.messageUserId = messageUserId;[m
[32m+[m[32m        this.messageStatus = messageStatus;[m
[32m+[m[32m        this.messageLikesCount = messageLikesCount;[m
[32m+[m[32m        this.messageLikes= messageLikes;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public ChatMessage(){[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String getMessageUserId() {[m
[32m+[m[32m        return messageUserId;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setMessageUserId(String messageUserId) {[m
[32m+[m[32m        this.messageUserId = messageUserId;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String getMessageText() {[m
[32m+[m[32m        return messageText;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setMessageText(String messageText) {[m
[32m+[m[32m        this.messageText = messageText;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public long getMessageTime() {[m
[32m+[m[32m        return messageTime;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setMessageTime(long messageTime) {[m
[32m+[m[32m        this.messageTime = messageTime;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String getMessageStatus() {[m
[32m+[m[32m        return messageStatus;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setMessageStatus(String messageStatus) {[m
[32m+[m[32m        this.messageStatus = messageStatus;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String getMessageUser() {[m
[32m+[m[32m        return messageUser;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setMessageUser(String messageUser) {[m
[32m+[m[32m        this.messageUser = messageUser;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public Map<String, Boolean> getMessageLikes() {[m
[32m+[m[32m        return messageLikes;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setMessageLikes(Map<String, Boolean> messageLikes) {[m
[32m+[m[32m        this.messageLikes = messageLikes;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public long getMessageLikesCount() {[m
[32m+[m[32m        return messageLikesCount;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setMessageLikesCount(long messageLikesCount) {[m
[32m+[m[32m        this.messageLikesCount = messageLikesCount;[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/app/src/main/java/datafiles/MessageAdapter.java b/app/src/main/java/datafiles/MessageAdapter.java[m
[1mnew file mode 100644[m
[1mindex 0000000..79aec34[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/datafiles/MessageAdapter.java[m
[36m@@ -0,0 +1,251 @@[m
[32m+[m[32mpackage datafiles;[m
[32m+[m
[32m+[m[32m/**[m
[32m+[m[32m * Created by Mendhie on 4/21/2018.[m
[32m+[m[32m */[m
[32m+[m
[32m+[m[32mimport android.app.Activity;[m
[32m+[m[32mimport android.content.Context;[m
[32m+[m[32mimport android.content.DialogInterface;[m
[32m+[m[32mimport android.content.Intent;[m
[32m+[m[32mimport android.support.v7.app.AlertDialog;[m
[32m+[m[32mimport android.text.format.DateFormat;[m
[32m+[m[32mimport android.util.Log;[m
[32m+[m[32mimport android.view.LayoutInflater;[m
[32m+[m[32mimport android.view.MotionEvent;[m
[32m+[m[32mimport android.view.View;[m
[32m+[m[32mimport android.view.ViewGroup;[m
[32m+[m[32mimport android.widget.Button;[m
[32m+[m[32mimport android.widget.ImageView;[m
[32m+[m[32mimport android.widget.TextView;[m
[32m+[m[32mimport android.widget.Toast;[m
[32m+[m
[32m+[m[32mimport com.firebase.ui.database.FirebaseListAdapter;[m
[32m+[m[32mimport com.firebase.ui.database.FirebaseListOptions;[m
[32m+[m[32mimport com.google.firebase.database.DataSnapshot;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseError;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseReference;[m
[32m+[m[32mimport com.google.firebase.database.FirebaseDatabase;[m
[32m+[m[32mimport com.google.firebase.database.MutableData;[m
[32m+[m[32mimport com.google.firebase.database.Transaction;[m
[32m+[m
[32m+[m[32mimport java.util.HashMap;[m
[32m+[m[32mimport java.util.Map;[m
[32m+[m
[32m+[m[32mimport secured.tips.LoginActivity;[m
[32m+[m[32mimport secured.tips.MemberProfileActivity;[m
[32m+[m[32mimport secured.tips.MyProfileActivity;[m
[32m+[m[32mimport secured.tips.R;[m
[32m+[m
[32m+[m[32mpublic class MessageAdapter extends FirebaseListAdapter<ChatMessage> {[m
[32m+[m[32m    Activity activity;[m
[32m+[m[32m    Context context;[m
[32m+[m[32m    String UserID;[m
[32m+[m[32m    String subRoom;[m
[32m+[m[32m    DatabaseReference mRef;[m
[32m+[m[32m    Cache cache = new Cache();[m
[32m+[m[32m    private final String TAG = "MyChatApp";[m
[32m+[m
[32m+[m[32m    public MessageAdapter(Activity activity, Class<ChatMessage> modelClass, int modelLayout, DatabaseReference ref, String userID, String room, Context context) {[m
[32m+[m[32m       super(new FirebaseListOptions.Builder<ChatMessage>()[m
[32m+[m[32m               .setQuery(ref, modelClass)[m
[32m+[m[32m               .setLayout(modelLayout)[m
[32m+[m[32m               .build());[m
[32m+[m
[32m+[m[32m        this.activity = activity;[m
[32m+[m[32m        this.context = context;[m
[32m+[m[32m        this.UserID = userID;[m
[32m+[m[32m        mRef = ref;[m
[32m+[m[32m        subRoom = room;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m
[32m+[m[32m    public void popUp(){[m
[32m+[m[32m        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert);[m
[32m+[m[32m        builder.setMessage("You must login first")[m
[32m+[m[32m                .setPositiveButton("Login", new DialogInterface.OnClickListener() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onClick(DialogInterface dialogInterface, int i) {[m
[32m+[m[32m                        context.startActivity(new Intent(context, LoginActivity.class));[m
[32m+[m[32m                    }[m
[32m+[m[32m                })[m
[32m+[m[32m                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onClick(DialogInterface dialogInterface, int i) {[m
[32m+[m[32m                        //do nothing[m
[32m+[m[32m                    }[m
[32m+[m[32m                })[m
[32m+[m[32m                .show();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void populateView(View v, final ChatMessage model, final int position) {[m
[32m+[m[32m        TextView messageText = v.findViewById(R.id.message_text);[m
[32m+[m[32m        TextView messageUser = v.findViewById(R.id.message_user);[m
[32m+[m[32m        TextView messageTime = v.findViewById(R.id.message_time);[m
[32m+[m[32m        TextView messageStatus = v.findViewById(R.id.message_status);[m
[32m+[m[32m        final TextView messageLikes = v.findViewById(R.id.message_Likes);[m
[32m+[m[32m        ImageView imgStatus = v.findViewById(R.id.imgStatus);[m
[32m+[m[32m        final ImageView imgDropdown = v.findViewById(R.id.imgDropdwon);[m
[32m+[m[32m        final ImageView imgLikes = v.findViewById(R.id.imgLikes);[m
[32m+[m
[32m+[m[32m        messageText.setText(model.getMessageText());[m
[32m+[m[32m        messageUser.setText(model.getMessageUser());[m
[32m+[m[32m        messageLikes.setText(String.valueOf(model.getMessageLikesCount()));[m
[32m+[m
[32m+[m[32m        if(model.getMessageLikes().containsKey(UserID)){[m
[32m+[m[32m            imgLikes.setImageResource(R.drawable.love);[m
[32m+[m[32m            imgLikes.setTag("love");[m
[32m+[m[32m        }else{[m
[32m+[m[32m            imgLikes.setImageResource(R.drawable.no_love);[m
[32m+[m[32m            imgLikes.setTag("no_love");[m
[32m+[m[32m        }[m
[32m+[m[32m        // Display of 'GAME WON' message[m
[32m+[m[32m        if(String.valueOf(model.getMessageStatus().charAt(0)).equals("1")){[m
[32m+[m[32m            messageStatus.setText("WON");[m
[32m+[m[32m            messageStatus.setVisibility(View.VISIBLE);[m
[32m+[m[32m            imgStatus.setVisibility(View.VISIBLE);[m
[32m+[m[32m        }[m
[32m+[m[32m        else{[m
[32m+[m[32m            messageStatus.setVisibility(View.GONE);[m
[32m+[m[32m            imgStatus.setVisibility(View.GONE);[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        // Format the date before showing it[m
[32m+[m[32m        messageTime.setText(DateFormat.format("dd MMM  (h:mm a)", model.getMessageTime()));[m
[32m+[m[32m       //getRef(position).child("messageLikes").child(UserID).equalTo(true);[m
[32m+[m[32m        imgDropdown.setOnClickListener(new View.OnClickListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onClick(View view) {[m
[32m+[m[32m                AlertDialog.Builder builder = new AlertDialog.Builder(context);[m
[32m+[m[32m                LayoutInflater inflater = activity.getLayoutInflater();[m
[32m+[m[32m                View dialogView;[m
[32m+[m[32m                if(imgDropdown.getTag().equals("in")){[m
[32m+[m[32m                    dialogView = inflater.inflate(R.layout.room_dialog2, null);[m
[32m+[m[32m                }else{[m
[32m+[m[32m                    dialogView = inflater.inflate(R.layout.room_dialog, null);[m
[32m+[m[32m                }[m
[32m+[m[32m                builder.setView(dialogView);[m
[32m+[m
[32m+[m[32m                final AlertDialog dialog= builder.create();[m
[32m+[m[32m                dialog.show();[m
[32m+[m
[32m+[m[32m                Button btnSubmit, btnDelete;[m
[32m+[m[32m                btnSubmit = dialog.findViewById(R.id.btnSubmit);[m
[32m+[m[32m                btnDelete = dialog.findViewById(R.id.btnDelete);[m
[32m+[m[32m                btnSubmit.setOnClickListener(new View.OnClickListener() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onClick(View view) {[m
[32m+[m[32m                        String ref = getRef(position).toString();[m
[32m+[m[32m                        submit(getRef(position), ref, model.getMessageText(), model.getMessageUserId());[m
[32m+[m[32m                        Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT).show();[m
[32m+[m[32m                        dialog.cancel();[m
[32m+[m[32m                    }[m
[32m+[m[32m                });[m
[32m+[m[32m                btnDelete.setOnClickListener(new View.OnClickListener() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onClick(View view) {[m
[32m+[m[32m                        getRef(position).removeValue();[m
[32m+[m[32m                        dialog.cancel();[m
[32m+[m[32m                        Toast.makeText(context, "Deleted ", Toast.LENGTH_SHORT).show();[m
[32m+[m[32m                    }[m
[32m+[m[32m                });[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m
[32m+[m[32m        messageUser.setOnClickListener(new View.OnClickListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onClick(View view) {[m
[32m+[m[32m                if(model.getMessageUserId().equals(UserID)){[m
[32m+[m[32m                    cache.setUserID(model.getMessageUserId());[m
[32m+[m[32m                    context.startActivity(new Intent(context, MyProfileActivity.class));[m
[32m+[m[32m                }[m
[32m+[m[32m                else {[m
[32m+[m[32m                    cache.setUserID(model.getMessageUserId());[m
[32m+[m[32m                    context.startActivity(new Intent(context, MemberProfileActivity.class));[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m[32m        imgLikes.setOnTouchListener(new View.OnTouchListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public boolean onTouch(View view, MotionEvent motionEvent) {[m
[32m+[m[32m                if(UserID.equals("x")){[m
[32m+[m[32m                    popUp();}[m
[32m+[m[32m                else {[m
[32m+[m[32m                    onLikeClicked(getRef(position));[m
[32m+[m[32m                }[m
[32m+[m[32m                return false;[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void onLikeClicked(DatabaseReference dRef){[m
[32m+[m[32m        dRef.runTransaction(new Transaction.Handler() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public Transaction.Result doTransaction(MutableData mutableData) {[m
[32m+[m[32m                ChatMessage message = mutableData.getValue(ChatMessage.class);[m
[32m+[m[32m                if(message!=null){[m
[32m+[m[32m                    if(message.getMessageLikes().containsKey(UserID)){[m
[32m+[m[32m                        long i = message.getMessageLikesCount()-1;[m
[32m+[m[32m                        message.setMessageLikesCount(i);[m
[32m+[m[32m                        message.getMessageLikes().remove(UserID);[m
[32m+[m[32m                    }else{[m
[32m+[m[32m                        long i = message.getMessageLikesCount()+1;[m
[32m+[m[32m                        message.setMessageLikesCount(i);[m
[32m+[m[32m                        message.messageLikes.put(UserID, true);[m
[32m+[m
[32m+[m[32m                    }[m
[32m+[m[32m                    mutableData.setValue(message);[m
[32m+[m
[32m+[m[32m                }[m
[32m+[m[32m                return Transaction.success(mutableData);[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {[m
[32m+[m[32m                Log.i(TAG, "Updating likes count transaction is completed.");[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void submit(DatabaseReference dataRef, String ref, String messageText, String userID){[m
[32m+[m[32m        DatabaseReference wonGameRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference().child("wonGames").child(subRoom);[m
[32m+[m[32m        Map<String, String> submitted = new HashMap<>();[m
[32m+[m[32m        submitted.put("databaseRef", ref);[m
[32m+[m[32m        submitted.put("messageText", messageText);[m
[32m+[m[32m        submitted.put("userID", userID);[m
[32m+[m[32m        wonGameRef.push().setValue(submitted);[m
[32m+[m[32m        dataRef.child("messageStatus").setValue("3"+userID);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public View getView(int position, View view, ViewGroup viewGroup) {[m
[32m+[m[32m        ChatMessage chatMessage = getItem(position);[m
[32m+[m[32m        if (chatMessage.getMessageUserId().equals(UserID))[m
[32m+[m[32m            view = activity.getLayoutInflater().inflate(R.layout.mssg_out, viewGroup, false);[m
[32m+[m[32m        else[m
[32m+[m[32m            view = activity.getLayoutInflater().inflate(R.layout.mssg_in, viewGroup, false);[m
[32m+[m
[32m+[m
[32m+[m[32m        //generating view[m
[32m+[m[32m        populateView(view, chatMessage, position);[m
[32m+[m
[32m+[m
[32m+[m[32m        return view;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public int getViewTypeCount() {[m
[32m+[m[32m        // return the total number of view types. this value should never change[m
[32m+[m[32m        // at runtime[m
[32m+[m[32m        return 2;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public int getItemViewType(int position) {[m
[32m+[m[32m        // return a value between 0 and (getViewTypeCount - 1)[m
[32m+[m[32m        return position % 2;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m}[m
[1mdiff --git a/app/src/main/java/datafiles/PostAdapter.java b/app/src/main/java/datafiles/PostAdapter.java[m
[1mnew file mode 100644[m
[1mindex 0000000..865b1a8[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/datafiles/PostAdapter.java[m
[36m@@ -0,0 +1,130 @@[m
[32m+[m[32mpackage datafiles;[m
[32m+[m
[32m+[m[32mimport android.content.Context;[m
[32m+[m[32mimport android.support.annotation.NonNull;[m
[32m+[m[32mimport android.support.v7.widget.RecyclerView;[m
[32m+[m[32mimport android.text.format.DateFormat;[m
[32m+[m[32mimport android.util.Log;[m
[32m+[m[32mimport android.view.LayoutInflater;[m
[32m+[m[32mimport android.view.MotionEvent;[m
[32m+[m[32mimport android.view.View;[m
[32m+[m[32mimport android.view.ViewGroup;[m
[32m+[m
[32m+[m[32mimport com.google.firebase.database.DataSnapshot;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseError;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseReference;[m
[32m+[m[32mimport com.google.firebase.database.MutableData;[m
[32m+[m[32mimport com.google.firebase.database.Transaction;[m
[32m+[m
[32m+[m[32mimport java.util.List;[m
[32m+[m
[32m+[m[32mimport secured.tips.R;[m
[32m+[m
[32m+[m[32mpublic class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {[m
[32m+[m[32m    List<ChatMessage> posts;[m
[32m+[m[32m    List<DatabaseReference> Refs;[m
[32m+[m[32m    Context context;[m
[32m+[m[32m    String UserID;[m
[32m+[m[32m    Cache cache = new Cache();[m
[32m+[m[32m    private final String TAG = "MyChatApp";[m
[32m+[m
[32m+[m[32m    public PostAdapter(){}[m
[32m+[m
[32m+[m[32m    public PostAdapter(Context context, List<ChatMessage> posts, String UserID, List<DatabaseReference> Refs){[m
[32m+[m[32m        this.context = context;[m
[32m+[m[32m        this.posts = posts;[m
[32m+[m[32m        this.Refs = Refs;[m
[32m+[m[32m        this.UserID = UserID;[m
[32m+[m[32m    }[m
[32m+[m[32m    @NonNull[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {[m
[32m+[m[32m        Log.i(TAG, "onCreateViewHolder method called");[m
[32m+[m[32m        View view = LayoutInflater.from(parent.getContext())[m
[32m+[m[32m                .inflate(R.layout.posts, parent, false);[m
[32m+[m[32m        return new PostViewHolder(view);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {[m
[32m+[m[32m        Log.i(TAG, "onBindViewHolder method called");[m
[32m+[m[32m        holder.messageUser.setText(posts.get(position).getMessageUser());[m
[32m+[m[32m        holder.messageText.setText(posts.get(position).getMessageText());[m
[32m+[m[32m        // Format the date before showing it[m
[32m+[m[32m        holder.messageTime.setText(DateFormat.format("dd MMM  (h:mm a)", posts.get(position).getMessageTime()));[m
[32m+[m
[32m+[m[32m        holder.messageLikes.setText(String.valueOf(posts.get(position).getMessageLikesCount()));[m
[32m+[m[32m        if(posts.get(position).getMessageLikes().containsKey(UserID)){[m
[32m+[m[32m            holder.imgLikes.setImageResource(R.drawable.love);[m
[32m+[m[32m            holder.imgLikes.setTag("love");[m
[32m+[m[32m        }else{[m
[32m+[m[32m            holder.imgLikes.setImageResource(R.drawable.no_love);[m
[32m+[m[32m            holder.imgLikes.setTag("no_love");[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m
[32m+[m[32m        // Display of 'GAME WON' message[m
[32m+[m[32m        if(String.valueOf(posts.get(position).getMessageStatus().charAt(0)).equals("1")){[m
[32m+[m[32m            holder.messageStatus.setText("WON");[m
[32m+[m[32m            holder.messageStatus.setVisibility(View.VISIBLE);[m
[32m+[m[32m            holder.imgStatus.setVisibility(View.VISIBLE);[m
[32m+[m[32m        }[m
[32m+[m[32m        else{[m
[32m+[m[32m            holder.messageStatus.setVisibility(View.GONE);[m
[32m+[m[32m            holder.imgStatus.setVisibility(View.GONE);[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        holder.imgLikes.setOnTouchListener(new View.OnTouchListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public boolean onTouch(View view, MotionEvent motionEvent) {[m
[32m+[m[32m                onLikeClicked(getRef(position));[m
[32m+[m[32m                return false;[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public DatabaseReference getRef(int position){[m
[32m+[m[32m        return  Refs.get(position);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m
[32m+[m[32m    public void onLikeClicked(DatabaseReference dRef){[m
[32m+[m[32m        dRef.runTransaction(new Transaction.Handler() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public Transaction.Result doTransaction(MutableData mutableData) {[m
[32m+[m[32m                ChatMessage message = mutableData.getValue(ChatMessage.class);[m
[32m+[m[32m                if(message!=null){[m
[32m+[m[32m                    if(message.getMessageLikes().containsKey(UserID)){[m
[32m+[m[32m                        long i = message.getMessageLikesCount()-1;[m
[32m+[m[32m                        message.setMessageLikesCount(i);[m
[32m+[m[32m                        message.getMessageLikes().remove(UserID);[m
[32m+[m[32m                    }else{[m
[32m+[m[32m                        long i = message.getMessageLikesCount()+1;[m
[32m+[m[32m                        message.setMessageLikesCount(i);[m
[32m+[m[32m                        message.messageLikes.put(UserID, true);[m
[32m+[m
[32m+[m[32m                    }[m
[32m+[m[32m                    mutableData.setValue(message);[m
[32m+[m[32m                }[m
[32m+[m[32m                return Transaction.success(mutableData);[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {[m
[32m+[m[32m                Log.i(TAG, "Updating likes count transaction is completed.");[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public long getItemId(int position){[m
[32m+[m[32m        return position;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public int getItemCount() {[m
[32m+[m[32m        return this.posts.size();[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/app/src/main/java/datafiles/PostViewHolder.java b/app/src/main/java/datafiles/PostViewHolder.java[m
[1mnew file mode 100644[m
[1mindex 0000000..c1f1379[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/datafiles/PostViewHolder.java[m
[36m@@ -0,0 +1,29 @@[m
[32m+[m[32mpackage datafiles;[m
[32m+[m
[32m+[m[32mimport android.support.v7.widget.RecyclerView;[m
[32m+[m[32mimport android.view.View;[m
[32m+[m[32mimport android.widget.ImageView;[m
[32m+[m[32mimport android.widget.TextView;[m
[32m+[m
[32m+[m[32mimport secured.tips.R;[m
[32m+[m
[32m+[m[32m/**[m
[32m+[m[32m * Created by Mendhie on 4/30/2018.[m
[32m+[m[32m */[m
[32m+[m
[32m+[m[32mpublic class PostViewHolder extends RecyclerView.ViewHolder{[m
[32m+[m[32m    TextView messageText, messageUser;[m
[32m+[m[32m    TextView messageTime, messageStatus, messageLikes;[m
[32m+[m[32m    ImageView imgStatus, imgLikes;[m
[32m+[m[32m    public PostViewHolder(View itemView) {[m
[32m+[m[32m        super(itemView);[m
[32m+[m[32m        messageText = itemView.findViewById(R.id.message_text);[m
[32m+[m[32m        messageUser = itemView.findViewById(R.id.message_user);[m
[32m+[m[32m        messageTime = itemView.findViewById(R.id.message_time);[m
[32m+[m[32m        messageStatus = itemView.findViewById(R.id.message_status);[m
[32m+[m[32m        messageLikes = itemView.findViewById(R.id.message_Likes);[m
[32m+[m[32m        imgStatus = itemView.findViewById(R.id.imgStatus);[m
[32m+[m[32m        imgLikes = itemView.findViewById(R.id.imgLikes);[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/app/src/main/java/secured/tips/AboutTipzoneActivity.java b/app/src/main/java/secured/tips/AboutTipzoneActivity.java[m
[1mnew file mode 100644[m
[1mindex 0000000..efe6b9a[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/secured/tips/AboutTipzoneActivity.java[m
[36m@@ -0,0 +1,64 @@[m
[32m+[m[32mpackage secured.tips;[m
[32m+[m
[32m+[m[32mimport android.os.Bundle;[m
[32m+[m[32mimport android.support.v7.app.ActionBar;[m
[32m+[m[32mimport android.support.v7.app.AppCompatActivity;[m
[32m+[m[32mimport android.text.Html;[m
[32m+[m[32mimport android.view.MenuItem;[m
[32m+[m[32mimport android.widget.TextView;[m
[32m+[m
[32m+[m[32mpublic class AboutTipzoneActivity extends AppCompatActivity {[m
[32m+[m[32m    TextView txtGuidelines, txtGuidelines2,txtGuidelines3, txtGuidelines4;[m
[32m+[m[32m    ActionBar actionBar;[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void onCreate(Bundle savedInstanceState) {[m
[32m+[m[32m        super.onCreate(savedInstanceState);[m
[32m+[m[32m        setContentView(R.layout.activity_about_tipzone);[m
[32m+[m[32m        actionBar = getSupportActionBar();[m
[32m+[m[32m        actionBar.setDisplayHomeAsUpEnabled(true);[m
[32m+[m[32m        txtGuidelines = findViewById(R.id.txtGuideline);[m
[32m+[m[32m        txtGuidelines2 = findViewById(R.id.txtGuideline2);[m
[32m+[m[32m        txtGuidelines3 = findViewById(R.id.txtGuideline3);[m
[32m+[m[32m        txtGuidelines4 = findViewById(R.id.txtGuideline4);[m
[32m+[m[32m        String guidelines = "<p><strong>Welcome to TipZone</strong></p>\n" +[m
[32m+[m[32m                "<p>TipZone is an online forum where tipsters help each other win big by sharing their surest tips. This is where you will enjoy first class predictions from good punters. If you are good as well, also share your tips for others to benefit.</p>\n" +[m
[32m+[m[32m                "<p>Your tips will help others make wise betting decisions and you also benefit from that of others.</p>\n" +[m
[32m+[m[32m                "<p>To encourage you and other tipsters, we reward you with points and cash whenever your tips win. Read about our <strong><u>Rewards</u></strong>(below) for more details.</p>";[m
[32m+[m[32m        String guidelines2 = "<p><strong>Guidelines</strong></p>\n" +[m
[32m+[m[32m                        "<ol>\n" +[m
[32m+[m[32m                        "<li>In accordance with gambling policy, you must be up to 18 years to join our forum.</li>\n" +[m
[32m+[m[32m                        "<li>Before you share your tips, confirm that you have at least 90% confidence in your tips. Don&rsquo;t post random tips. This is for the good of everybody.</li>\n" +[m
[32m+[m[32m                        "</ol>\n" +[m
[32m+[m[32m                        "<ul>\n" +[m
[32m+[m[32m                        "<li>There is no limit to the tips you can post.</li>\n" +[m
[32m+[m[32m                        "<li>Always indicate the total odds of your tips to help us reward you appropriately when it wins.</li>\n" +[m
[32m+[m[32m                        "</ul>\n" +[m
[32m+[m[32m                        "<ol>\n" +[m
[32m+[m[32m                        "<li>Do not persuade anyone to stake on your game. Let them decide.</li>\n" +[m
[32m+[m[32m                        "<li>Post your tips in the room that matches the total odds of your tips. E.g if your tips is 28.89, then post it in 10-50 odds room, if it&rsquo;s 78.43, post it in 50-150 odds room, and so on. Coupon draws have it&rsquo;s on room too.</li>\n" +[m
[32m+[m[32m                        "<li>We don&rsquo;t want any kind of marketing, preaching, broadcast, relationship or political gist, gossips, etc in the forum. That&rsquo;s not the purpose.</li>\n" +[m
[32m+[m[32m                        "</ol>";[m
[32m+[m[32m        String guidelines3 = "<p><strong>Payment</strong></p>\n" +[m
[32m+[m[32m                "<p>Secured Tips Forum is open to everyone (18+). It is completely free for Secured Tips VIP subscribers. Non-VIP subscribers will join for only N1500 (i.e $4) monthly. The charge is for 2 reasons: to reward tipsters when they win, and also for maintenance.</p>";[m
[32m+[m[32m        String guidelines4 =  "<p><strong>Rewards</strong></p>\n" +[m
[32m+[m[32m                "<p>You will be rewarded with points and cash every time your tips deliver. The points help others know how good you are in prediction, and the areas you are very good at. The cash reward is our appreciation for your contribution. It will be forwarded to your account every time you get up to 100 points. <strong>(100 points = NGN 10,000)</strong>. Note that points depend on the odds of your tips.</p>\n" +[m
[32m+[m[32m                "<p>10-50 odds = 10 points</p>\n" +[m
[32m+[m[32m                "<p>51-150 odds = 20 points</p>\n" +[m
[32m+[m[32m                "<p>151-500 odds = 30 points</p>\n" +[m
[32m+[m[32m                "<p>500 and above = 50 points</p>\n" +[m
[32m+[m[32m                "<p>Pools* = 50 points</p>\n" +[m
[32m+[m[32m                "<p>(For pools, your draws must deliver all or cut not more than one(1) before we reward you. Note: we don&rsquo;t reward single draw, 2/2 or 2/3 ).</p>";[m
[32m+[m
[32m+[m[32m        txtGuidelines.setText(Html.fromHtml(guidelines));[m
[32m+[m[32m        txtGuidelines2.setText(Html.fromHtml(guidelines2));[m
[32m+[m[32m        txtGuidelines3.setText(Html.fromHtml(guidelines3));[m
[32m+[m[32m        txtGuidelines4.setText(Html.fromHtml(guidelines4));[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public boolean onOptionsItemSelected(MenuItem item){[m
[32m+[m[32m        finish();[m
[32m+[m[32m        return true;[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/app/src/main/java/secured/tips/ChatRoomActivity.java b/app/src/main/java/secured/tips/ChatRoomActivity.java[m
[1mnew file mode 100644[m
[1mindex 0000000..4999961[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/secured/tips/ChatRoomActivity.java[m
[36m@@ -0,0 +1,192 @@[m
[32m+[m[32mpackage secured.tips;[m
[32m+[m
[32m+[m[32mimport android.content.DialogInterface;[m
[32m+[m[32mimport android.content.Intent;[m
[32m+[m[32mimport android.os.Bundle;[m
[32m+[m[32mimport android.support.design.widget.FloatingActionButton;[m
[32m+[m[32mimport android.support.v7.app.ActionBar;[m
[32m+[m[32mimport android.support.v7.app.AlertDialog;[m
[32m+[m[32mimport android.support.v7.app.AppCompatActivity;[m
[32m+[m[32mimport android.text.Editable;[m
[32m+[m[32mimport android.text.TextWatcher;[m
[32m+[m[32mimport android.util.Log;[m
[32m+[m[32mimport android.view.MenuItem;[m
[32m+[m[32mimport android.view.View;[m
[32m+[m[32mimport android.widget.EditText;[m
[32m+[m[32mimport android.widget.ListView;[m
[32m+[m
[32m+[m[32mimport com.firebase.ui.database.FirebaseListAdapter;[m
[32m+[m[32mimport com.google.firebase.auth.FirebaseAuth;[m
[32m+[m[32mimport com.google.firebase.auth.FirebaseUser;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseReference;[m
[32m+[m[32mimport com.google.firebase.database.FirebaseDatabase;[m
[32m+[m
[32m+[m[32mimport java.util.HashMap;[m
[32m+[m[32mimport java.util.Map;[m
[32m+[m
[32m+[m[32mimport datafiles.Cache;[m
[32m+[m[32mimport datafiles.ChatMessage;[m
[32m+[m[32mimport datafiles.MessageAdapter;[m
[32m+[m
[32m+[m[32mpublic class ChatRoomActivity extends AppCompatActivity {[m
[32m+[m[32m    static int room = 1;[m
[32m+[m[32m    private boolean login = false;[m
[32m+[m[32m    FirebaseUser user;[m
[32m+[m[32m    String userID="x";[m
[32m+[m[32m    String userName;[m
[32m+[m[32m    Cache cacher = new Cache();[m
[32m+[m[32m    private Map<String, Boolean> message_likes = new HashMap<>();;[m
[32m+[m[32m    private final String TAG = "SecuredChat";[m
[32m+[m[32m    private ListView listView;[m
[32m+[m[32m    private DatabaseReference mRef;[m
[32m+[m[32m    private FirebaseListAdapter<ChatMessage> adapter;[m
[32m+[m[32m    private FloatingActionButton btnSend;[m
[32m+[m[32m    ActionBar actionBar;[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void onCreate(Bundle savedInstanceState) {[m
[32m+[m[32m        super.onCreate(savedInstanceState);[m
[32m+[m[32m        setContentView(R.layout.activity_chat_room);[m
[32m+[m
[32m+[m[32m        actionBar = getSupportActionBar();[m
[32m+[m[32m        actionBar.setDisplayHomeAsUpEnabled(true);[m
[32m+[m[32m        message_likes.put("00default", true);[m
[32m+[m
[32m+[m[32m        final EditText input = findViewById(R.id.input);[m
[32m+[m[32m        user = FirebaseAuth.getInstance().getCurrentUser();[m
[32m+[m[32m        String subRoom = selectDatabase();[m
[32m+[m[32m        mRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference().child("chatrooms").child(subRoom);[m
[32m+[m[32m        mRef.keepSynced(true);[m
[32m+[m[32m        if(user!=null){[m
[32m+[m[32m            login = true;[m
[32m+[m[32m            userID = user.getUid();[m
[32m+[m[32m            userName = user.getDisplayName();[m
[32m+[m[32m        }[m
[32m+[m[32m        listView = findViewById(R.id.list);[m
[32m+[m[32m        btnSend = findViewById(R.id.fab);[m
[32m+[m[32m        showOldMesseges();[m
[32m+[m
[32m+[m[32m        btnSend.setOnClickListener(new View.OnClickListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onClick(View view) {[m
[32m+[m[32m                if(user!=null){[m
[32m+[m[32m                    if (input.getText().toString().trim().equals("")) {[m
[32m+[m[32m                    } else {[m
[32m+[m[32m                        mRef[m
[32m+[m[32m                                .push()[m
[32m+[m[32m                                .setValue(new ChatMessage(userName, input.getText().toString(),[m
[32m+[m[32m                                        userID, "2_"+userID, 0, message_likes));[m
[32m+[m[32m                        input.setText("");[m
[32m+[m[32m                    }[m
[32m+[m[32m                }[m
[32m+[m[32m                else{[m
[32m+[m[32m                    popUp();[m
[32m+[m[32m                }[m
[32m+[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m
[32m+[m[32m        input.addTextChangedListener(new TextWatcher() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {[m
[32m+[m[32m                if (charSequence.toString().trim().length() > 0) {[m
[32m+[m[32m                    btnSend.setEnabled(true);[m
[32m+[m[32m                } else {[m
[32m+[m[32m                    btnSend.setEnabled(false);[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void afterTextChanged(Editable editable) {[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void popUp(){[m
[32m+[m[32m        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);[m
[32m+[m[32m        builder.setMessage("You must login first")[m
[32m+[m[32m                .setPositiveButton("Login", new DialogInterface.OnClickListener() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onClick(DialogInterface dialogInterface, int i) {[m
[32m+[m[32m                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));[m
[32m+[m[32m                    }[m
[32m+[m[32m                })[m
[32m+[m[32m                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onClick(DialogInterface dialogInterface, int i) {[m
[32m+[m[32m                        //do nothing[m
[32m+[m[32m                    }[m
[32m+[m[32m                })[m
[32m+[m[32m                .show();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String selectDatabase(){[m
[32m+[m[32m        room = getIntent().getIntExtra("ROOM", 1);[m
[32m+[m[32m        switch (room){[m
[32m+[m[32m            case 1:[m
[32m+[m[32m                return "room_1";[m
[32m+[m[32m            case 2:[m
[32m+[m[32m                return "room_2";[m
[32m+[m[32m            case 3:[m
[32m+[m[32m                return "room_3";[m
[32m+[m[32m            case 4:[m
[32m+[m[32m                return "room_4";[m
[32m+[m[32m            case 5:[m
[32m+[m[32m                return "room_5";[m
[32m+[m[32m            case 6:[m
[32m+[m[32m                return "room_6";[m
[32m+[m[32m            default:[m
[32m+[m[32m                return "room_1";[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public FirebaseUser getUser(){[m
[32m+[m[32m        return user;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m
[32m+[m[32m    private void showOldMesseges(){[m
[32m+[m[32m        adapter = new MessageAdapter(this, ChatMessage.class, R.layout.mammy, mRef, userID, selectDatabase(), this);[m
[32m+[m[32m        listView.setAdapter(adapter);[m
[32m+[m[32m        Log.i(TAG, "messages shown");[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public boolean onOptionsItemSelected(MenuItem item){[m
[32m+[m[32m        finish();[m
[32m+[m[32m        return true;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void onResume(){[m
[32m+[m[32m        super.onResume();[m
[32m+[m[32m        user = FirebaseAuth.getInstance().getCurrentUser();[m
[32m+[m[32m        if(user!=null){[m
[32m+[m[32m            if(login!=true){[m
[32m+[m[32m                userID = user.getUid();[m
[32m+[m[32m                userName = user.getDisplayName();[m
[32m+[m[32m                login=true;[m
[32m+[m[32m                showOldMesseges();[m
[32m+[m[32m                adapter.startListening();[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void onStart() {[m
[32m+[m[32m        super.onStart();[m
[32m+[m[32m        adapter.startListening();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void onStop() {[m
[32m+[m[32m        super.onStop();[m
[32m+[m[32m        adapter.stopListening();[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/app/src/main/java/secured/tips/FreeActivity.java b/app/src/main/java/secured/tips/FreeActivity.java[m
[1mindex 5b4c288..8e94ca2 100644[m
[1m--- a/app/src/main/java/secured/tips/FreeActivity.java[m
[1m+++ b/app/src/main/java/secured/tips/FreeActivity.java[m
[36m@@ -65,7 +65,6 @@[m [mpublic class FreeActivity extends AppCompatActivity {[m
 [m
         adapt = new SectionedRecyclerViewAdapter();[m
         mRef = FirebaseDatabase.getInstance().getReference(database_path);[m
[31m-        mRef.keepSynced(true);[m
 [m
         loadTips();[m
 [m
[1mdiff --git a/app/src/main/java/secured/tips/LoginActivity.java b/app/src/main/java/secured/tips/LoginActivity.java[m
[1mindex abbb0ab..2e210e2 100644[m
[1m--- a/app/src/main/java/secured/tips/LoginActivity.java[m
[1m+++ b/app/src/main/java/secured/tips/LoginActivity.java[m
[36m@@ -40,13 +40,14 @@[m [mpublic class LoginActivity extends AppCompatActivity implements View.OnClickList[m
     ProgressDialog progressDialog;[m
     FirebaseUser user;[m
     int intType;[m
[31m-    boolean active = true;[m
[32m+[m[32m    boolean active = false;[m
 [m
     SharedPreferences prefs;[m
     SharedPreferences.Editor editor;[m
 [m
     ActionBar actionBar;[m
 [m
[32m+[m
     @Override[m
     protected void onCreate(Bundle savedInstanceState) {[m
         super.onCreate(savedInstanceState);[m
[36m@@ -106,7 +107,7 @@[m [mpublic class LoginActivity extends AppCompatActivity implements View.OnClickList[m
             public void onComplete(@NonNull Task<AuthResult> task) {[m
                 progressDialog.dismiss();[m
                 if(task.isSuccessful()){[m
[31m-                    startActivity(new Intent(getApplicationContext(), SubscribeActivity.class));[m
[32m+[m[32m                    //startActivity(new Intent(getApplicationContext(), SubscribeActivity.class));[m
                     editor.putString("PASSWORD", edtPassword.getText().toString().trim());[m
                     editor.putString("EMAIL", edtEmail.getText().toString().trim());[m
                     editor.apply();[m
[36m@@ -128,9 +129,6 @@[m [mpublic class LoginActivity extends AppCompatActivity implements View.OnClickList[m
         }else if(intType==2){[m
             startActivity(new Intent(this, DrawActivity.class));[m
         }[m
[31m-        if(checkSub()){[m
[31m-            startActivity(new Intent(getApplicationContext(), SubscribeActivity.class));[m
[31m-        }[m
     }[m
 [m
     public boolean onOptionsItemSelected(MenuItem item){[m
[1mdiff --git a/app/src/main/java/secured/tips/MainActivity.java b/app/src/main/java/secured/tips/MainActivity.java[m
[1mindex 6f39d7a..ef1d3fa 100644[m
[1m--- a/app/src/main/java/secured/tips/MainActivity.java[m
[1m+++ b/app/src/main/java/secured/tips/MainActivity.java[m
[36m@@ -1,9 +1,13 @@[m
 package secured.tips;[m
 [m
[32m+[m[32mimport android.content.DialogInterface;[m
 import android.content.Intent;[m
 import android.content.SharedPreferences;[m
 import android.os.Bundle;[m
 import android.preference.PreferenceManager;[m
[32m+[m[32mimport android.support.design.widget.TabLayout;[m
[32m+[m[32mimport android.support.v4.view.ViewPager;[m
[32m+[m[32mimport android.support.v7.app.AlertDialog;[m
 import android.support.v7.app.AppCompatActivity;[m
 import android.support.v7.widget.CardView;[m
 import android.util.Log;[m
[36m@@ -23,7 +27,14 @@[m [mimport com.google.firebase.database.ValueEventListener;[m
 [m
 import java.text.ParsePosition;[m
 import java.text.SimpleDateFormat;[m
[32m+[m[32mimport java.util.ArrayList;[m
 import java.util.Date;[m
[32m+[m[32mimport java.util.List;[m
[32m+[m[32mimport java.util.Timer;[m
[32m+[m[32mimport java.util.TimerTask;[m
[32m+[m
[32m+[m[32mimport datafiles.AdsAdapter;[m
[32m+[m[32mimport datafiles.Cache;[m
 [m
 public class MainActivity extends AppCompatActivity implements View.OnClickListener {[m
 [m
[36m@@ -33,14 +44,23 @@[m [mpublic class MainActivity extends AppCompatActivity implements View.OnClickListe[m
     FirebaseUser user;[m
     SharedPreferences prefs;[m
     SharedPreferences.Editor editor;[m
[32m+[m[32m    FirebaseDatabase tipsDatabase, usersDatabse, chatDatabase;[m
     DatabaseReference mRef;[m
[32m+[m[32m    Cache cache = new Cache();[m
     static InterstitialAd mInterstitialAd;[m
     static boolean calledAlready = false;[m
[32m+[m[32m    ViewPager viewPager;[m
[32m+[m[32m    TabLayout indicator;[m
[32m+[m
[32m+[m[32m    private List<Integer> imageDrawable;[m
[32m+[m[32m    private List<String> buttonText;[m
 [m
     @Override[m
     protected void onCreate(Bundle savedInstanceState) {[m
         super.onCreate(savedInstanceState);[m
[31m-        setContentView(R.layout.activity_main);[m
[32m+[m[32m        setContentView(R.layout.activity_main2);[m
[32m+[m[32m        viewPager = findViewById(R.id.adsViewPager);[m
[32m+[m[32m        indicator = findViewById(R.id.indicator);[m
         crdProfile = findViewById(R.id.crdProfile); crdProfile.setOnClickListener(this);[m
         crdForum = findViewById(R.id.crdForum); crdForum.setOnClickListener(this);[m
         crdFree = findViewById(R.id.crdFree); crdFree.setOnClickListener(this);[m
[36m@@ -48,23 +68,47 @@[m [mpublic class MainActivity extends AppCompatActivity implements View.OnClickListe[m
         crdNews = findViewById(R.id.crdNews); crdNews.setOnClickListener(this);[m
         crdPayment = findViewById(R.id.crdPayment); crdPayment.setOnClickListener(this);[m
 [m
[32m+[m[32m        tipsDatabase = FirebaseDatabase.getInstance();[m
[32m+[m[32m        usersDatabse = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/");[m
[32m+[m[32m        chatDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/");[m
 [m
         prefs = PreferenceManager.getDefaultSharedPreferences(this);[m
[32m+[m[32m        calledAlready = prefs.getBoolean("CALLED", false);if(!calledAlready){[m
[32m+[m[32m            tipsDatabase.setPersistenceEnabled(true);[m
[32m+[m[32m            usersDatabse.setPersistenceEnabled(true);[m
[32m+[m[32m            chatDatabase.setPersistenceEnabled(true);[m
[32m+[m[32m            editor = prefs.edit();[m
[32m+[m[32m            editor.putBoolean("CALLED", true);[m
[32m+[m[32m        }[m
[32m+[m
         //MobileAds.initialize(getApplicationContext(), "ca-app-pub-4597711656812814~3843067047");[m
         //mInterstitialAd = new InterstitialAd(this);[m
         //mInterstitialAd.setAdUnitId("ca-app-pub-4597711656812814/1799989807");[m
[31m-        if(!calledAlready){[m
[31m-            FirebaseDatabase.getInstance().setPersistenceEnabled(true);[m
[31m-            calledAlready = true;[m
[31m-        }[m
[31m-        prefs= PreferenceManager.getDefaultSharedPreferences(this);[m
[32m+[m
[32m+[m[32m        activateSlider();[m
         mfirebaseAuth = FirebaseAuth.getInstance();[m
         user = mfirebaseAuth.getCurrentUser(); if (user != null) {[m
             checkSub();[m
         }[m
     }[m
 [m
[32m+[m[32m    public  void activateSlider(){[m
[32m+[m[32m        imageDrawable = new ArrayList<>();[m
[32m+[m[32m        imageDrawable.add(R.drawable.bg);[m
[32m+[m[32m        imageDrawable.add(R.drawable.bg_one);[m
[32m+[m[32m        imageDrawable.add(R.drawable.ads);[m
 [m
[32m+[m[32m        buttonText = new ArrayList<>();[m
[32m+[m[32m        buttonText.add("Enter");[m
[32m+[m[32m        buttonText.add("Purcase here");[m
[32m+[m[32m        buttonText.add("Contact us");[m
[32m+[m
[32m+[m[32m        viewPager.setAdapter(new AdsAdapter(this, imageDrawable, buttonText));[m
[32m+[m[32m        indicator.setupWithViewPager(viewPager, true);[m
[32m+[m[32m        Timer timer = new Timer();[m
[32m+[m[32m        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 8000);[m
[32m+[m
[32m+[m[32m    }[m
     public void checkSub(){[m
         final String id = user.getUid();[m
         final Date today = new Date();[m
[36m@@ -102,8 +146,29 @@[m [mpublic class MainActivity extends AppCompatActivity implements View.OnClickListe[m
     @Override[m
     public boolean onOptionsItemSelected(MenuItem item) {[m
         if (item.getItemId() == R.id.menu_sign_out) {[m
[31m-            Toast.makeText(getApplicationContext(), "Don't fuck with Mega M.. hahaha", Toast.LENGTH_SHORT).show();[m
[31m-[m
[32m+[m[32m            if(user!=null){[m
[32m+[m[32m                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);[m
[32m+[m[32m                builder.setTitle("Sign Out")[m
[32m+[m[32m                        .setMessage("You want to signout?")[m
[32m+[m[32m                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {[m
[32m+[m[32m                            @Override[m
[32m+[m[32m                            public void onClick(DialogInterface dialogInterface, int i) {[m
[32m+[m[32m                                mfirebaseAuth.signOut();[m
[32m+[m[32m                                user=null;[m
[32m+[m[32m                                Toast.makeText(getApplicationContext(), "You have signed out", Toast.LENGTH_SHORT).show();[m
[32m+[m[32m                            }[m
[32m+[m[32m                        })[m
[32m+[m[32m                        .setNegativeButton("Not now", new DialogInterface.OnClickListener() {[m
[32m+[m[32m                            @Override[m
[32m+[m[32m                            public void onClick(DialogInterface dialogInterface, int i) {[m
[32m+[m[32m                                //do nothing[m
[32m+[m[32m                            }[m
[32m+[m[32m                        })[m
[32m+[m[32m                        .show();[m
[32m+[m[32m            }[m
[32m+[m[32m            else{[m
[32m+[m[32m                Toast.makeText(getApplicationContext(), "Already out", Toast.LENGTH_SHORT).show();[m
[32m+[m[32m            }[m
         }[m
         return true;[m
     }[m
[36m@@ -118,11 +183,20 @@[m [mpublic class MainActivity extends AppCompatActivity implements View.OnClickListe[m
                 startActivity(new Intent(getApplicationContext(), VipPageActivity.class));[m
                 break;[m
             case R.id.crdProfile:[m
[31m-                startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));[m
[32m+[m[32m                if(user!=null) {[m
[32m+[m[32m                    cache.setUserID(user.getUid());[m
[32m+[m[32m                    startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));[m
[32m+[m[32m                }[m
[32m+[m[32m                else{[m
[32m+[m[32m                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));[m
[32m+[m[32m                }[m
                 break;[m
             case R.id.crdForum:[m
                 startActivity(new Intent(getApplicationContext(), RoomsPageActivity.class));[m
                 break;[m
[32m+[m[32m            case R.id.crdPayment:[m
[32m+[m[32m                startActivity(new Intent(getApplicationContext(), SubscribeActivity.class));[m
[32m+[m[32m                break;[m
         }[m
     }[m
     public void onResume(){[m
[36m@@ -131,4 +205,22 @@[m [mpublic class MainActivity extends AppCompatActivity implements View.OnClickListe[m
         user = mfirebaseAuth.getCurrentUser();[m
         super.onResume();[m
     }[m
[32m+[m
[32m+[m
[32m+[m[32m    private class SliderTimer extends TimerTask {[m
[32m+[m
[32m+[m[32m        @Override[m
[32m+[m[32m        public void run() {[m
[32m+[m[32m            MainActivity.this.runOnUiThread(new Runnable() {[m
[32m+[m[32m                @Override[m
[32m+[m[32m                public void run() {[m
[32m+[m[32m                    if (viewPager.getCurrentItem() < imageDrawable.size() - 1) {[m
[32m+[m[32m                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);[m
[32m+[m[32m                    } else {[m
[32m+[m[32m                        viewPager.setCurrentItem(0);[m
[32m+[m[32m                    }[m
[32m+[m[32m                }[m
[32m+[m[32m            });[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
 }[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/java/secured/tips/MemberProfileActivity.java b/app/src/main/java/secured/tips/MemberProfileActivity.java[m
[1mnew file mode 100644[m
[1mindex 0000000..bf61bd0[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/secured/tips/MemberProfileActivity.java[m
[36m@@ -0,0 +1,123 @@[m
[32m+[m[32mpackage secured.tips;[m
[32m+[m
[32m+[m[32mimport android.os.Bundle;[m
[32m+[m[32mimport android.support.design.widget.TabLayout;[m
[32m+[m[32mimport android.support.v4.app.Fragment;[m
[32m+[m[32mimport android.support.v4.app.FragmentManager;[m
[32m+[m[32mimport android.support.v4.app.FragmentPagerAdapter;[m
[32m+[m[32mimport android.support.v4.view.ViewPager;[m
[32m+[m[32mimport android.support.v7.app.ActionBar;[m
[32m+[m[32mimport android.support.v7.app.AppCompatActivity;[m
[32m+[m[32mimport android.view.MenuItem;[m
[32m+[m[32mimport android.widget.TextView;[m
[32m+[m
[32m+[m[32mimport com.google.firebase.auth.FirebaseAuth;[m
[32m+[m[32mimport com.google.firebase.database.DataSnapshot;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseError;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseReference;[m
[32m+[m[32mimport com.google.firebase.database.FirebaseDatabase;[m
[32m+[m[32mimport com.google.firebase.database.ValueEventListener;[m
[32m+[m
[32m+[m[32mimport java.util.ArrayList;[m
[32m+[m[32mimport java.util.List;[m
[32m+[m
[32m+[m[32mimport datafiles.Cache;[m
[32m+[m
[32m+[m[32mpublic class MemberProfileActivity extends AppCompatActivity {[m
[32m+[m[32m    ActionBar actionBar;[m
[32m+[m[32m    private TabLayout tabLayout;[m
[32m+[m[32m    String firstName, lastName, Username, gender;[m
[32m+[m[32m    long points;[m
[32m+[m[32m    private ViewPager viewPager;[m
[32m+[m[32m    Cache cache = new Cache();[m
[32m+[m[32m    TextView txtName, txtUsername, txtGender, txtPoint;[m
[32m+[m[32m    DatabaseReference mDatabase;[m
[32m+[m[32m    FirebaseAuth mfirebaseAuth;[m
[32m+[m[32m    String userID;[m
[32m+[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void onCreate(Bundle savedInstanceState) {[m
[32m+[m[32m        super.onCreate(savedInstanceState);[m
[32m+[m[32m        setContentView(R.layout.activity_member_profile);[m
[32m+[m
[32m+[m[32m        txtName = findViewById(R.id.txtName);[m
[32m+[m[32m        txtGender = findViewById(R.id.txtGender);[m
[32m+[m[32m        txtUsername= findViewById(R.id.txtUsername);[m
[32m+[m[32m        txtPoint = findViewById(R.id.txtPoint);[m
[32m+[m
[32m+[m[32m        actionBar = getSupportActionBar();[m
[32m+[m[32m        actionBar.setDisplayHomeAsUpEnabled(true);[m
[32m+[m[32m        viewPager = findViewById(R.id.viewpager);[m
[32m+[m[32m        setupViewPager(viewPager);[m
[32m+[m[32m        tabLayout = findViewById(R.id.tabs);[m
[32m+[m[32m        tabLayout.setupWithViewPager(viewPager);[m
[32m+[m
[32m+[m
[32m+[m[32m        mfirebaseAuth = FirebaseAuth.getInstance();[m
[32m+[m[32m        userID = cache.getUserID();[m
[32m+[m[32m        mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);[m
[32m+[m[32m        mDatabase.addValueEventListener(new ValueEventListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onDataChange(DataSnapshot dataSnapshot) {[m
[32m+[m[32m                firstName= dataSnapshot.child("a1_firstname").getValue(String.class);[m
[32m+[m[32m                lastName= dataSnapshot.child("a2_lastname").getValue(String.class);[m
[32m+[m[32m                Username= dataSnapshot.child("a3_username").getValue(String.class);[m
[32m+[m[32m                gender= dataSnapshot.child("a6_gender").getValue(String.class);[m
[32m+[m[32m                points= dataSnapshot.child("a8_points").getValue(long.class);[m
[32m+[m
[32m+[m[32m                txtName.setText(firstName + " " + lastName);[m
[32m+[m[32m                txtUsername.setText(Username);[m
[32m+[m[32m                txtGender.setText(gender);[m
[32m+[m[32m                txtPoint.setText(String.valueOf(points) + " points");[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onCancelled(DatabaseError databaseError) {[m
[32m+[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private void setupViewPager(ViewPager viewPager) {[m
[32m+[m[32m        MemberProfileActivity.ViewPagerAdapter adapter = new MemberProfileActivity.ViewPagerAdapter(getSupportFragmentManager());[m
[32m+[m[32m        adapter.addFragment(new RecentpostFragment(), "RECENT POSTS");[m
[32m+[m[32m        adapter.addFragment(new WonGamesFragment(), "WON GAMES");[m
[32m+[m[32m        viewPager.setAdapter(adapter);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public boolean onOptionsItemSelected(MenuItem item){[m
[32m+[m[32m        finish();[m
[32m+[m[32m        return true;[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    class ViewPagerAdapter extends FragmentPagerAdapter {[m
[32m+[m[32m        private final List<Fragment> mFragmentList = new ArrayList<>();[m
[32m+[m[32m        private final List<String> mFragmentTitleList = new ArrayList<>();[m
[32m+[m
[32m+[m[32m        public ViewPagerAdapter(FragmentManager manager) {[m
[32m+[m[32m            super(manager);[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        @Override[m
[32m+[m[32m        public Fragment getItem(int position) {[m
[32m+[m[32m            return mFragmentList.get(position);[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        @Override[m
[32m+[m[32m        public int getCount() {[m
[32m+[m[32m            return mFragmentList.size();[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        public void addFragment(Fragment fragment, String title) {[m
[32m+[m[32m            mFragmentList.add(fragment);[m
[32m+[m[32m            mFragmentTitleList.add(title);[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        @Override[m
[32m+[m[32m        public CharSequence getPageTitle(int position) {[m
[32m+[m[32m            return mFragmentTitleList.get(position);[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/java/secured/tips/MyProfileActivity.java b/app/src/main/java/secured/tips/MyProfileActivity.java[m
[1mindex 5a3283d..c8089a4 100644[m
[1m--- a/app/src/main/java/secured/tips/MyProfileActivity.java[m
[1m+++ b/app/src/main/java/secured/tips/MyProfileActivity.java[m
[36m@@ -1,27 +1,271 @@[m
 package secured.tips;[m
 [m
[32m+[m[32mimport android.app.ProgressDialog;[m
[32m+[m[32mimport android.content.ActivityNotFoundException;[m
[32m+[m[32mimport android.content.Intent;[m
[32m+[m[32mimport android.graphics.Bitmap;[m
[32m+[m[32mimport android.net.Uri;[m
 import android.os.Bundle;[m
[32m+[m[32mimport android.support.annotation.NonNull;[m
[32m+[m[32mimport android.support.design.widget.TabLayout;[m
[32m+[m[32mimport android.support.v4.app.Fragment;[m
[32m+[m[32mimport android.support.v4.app.FragmentManager;[m
[32m+[m[32mimport android.support.v4.app.FragmentPagerAdapter;[m
[32m+[m[32mimport android.support.v4.view.ViewPager;[m
 import android.support.v7.app.ActionBar;[m
 import android.support.v7.app.AppCompatActivity;[m
 import android.view.MenuItem;[m
[32m+[m[32mimport android.view.View;[m
[32m+[m[32mimport android.widget.ImageView;[m
[32m+[m[32mimport android.widget.TextView;[m
[32m+[m[32mimport android.widget.Toast;[m
[32m+[m
[32m+[m[32mimport com.bumptech.glide.Glide;[m
[32m+[m[32mimport com.google.android.gms.tasks.OnFailureListener;[m
[32m+[m[32mimport com.google.android.gms.tasks.OnSuccessListener;[m
[32m+[m[32mimport com.google.firebase.auth.FirebaseAuth;[m
[32m+[m[32mimport com.google.firebase.auth.FirebaseUser;[m
[32m+[m[32mimport com.google.firebase.database.DataSnapshot;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseError;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseReference;[m
[32m+[m[32mimport com.google.firebase.database.FirebaseDatabase;[m
[32m+[m[32mimport com.google.firebase.database.ValueEventListener;[m
[32m+[m[32mimport com.google.firebase.storage.FirebaseStorage;[m
[32m+[m[32mimport com.google.firebase.storage.OnProgressListener;[m
[32m+[m[32mimport com.google.firebase.storage.StorageReference;[m
[32m+[m[32mimport com.google.firebase.storage.UploadTask;[m
[32m+[m[32mimport com.theartofdev.edmodo.cropper.CropImage;[m
[32m+[m
[32m+[m[32mimport java.util.ArrayList;[m
[32m+[m[32mimport java.util.List;[m
 [m
 public class MyProfileActivity extends AppCompatActivity {[m
     ActionBar actionBar;[m
[32m+[m[32m    private TabLayout tabLayout;[m
[32m+[m[32m    String firstName, lastName, Username, gender, imageURL;[m
[32m+[m[32m    long points;[m
[32m+[m[32m    ImageView imgEdit;[m
[32m+[m[32m    private ViewPager viewPager;[m
[32m+[m[32m    TextView txtName, txtUsername, txtGender, txtPoint;[m
[32m+[m[32m    DatabaseReference mDatabase;[m
[32m+[m[32m    FirebaseAuth mfirebaseAuth;[m
[32m+[m[32m    FirebaseUser user;[m
[32m+[m[32m    String userID;[m
[32m+[m[32m    private Uri filePath;[m
[32m+[m[32m    ImageView imgProfile;[m
[32m+[m[32m    FirebaseStorage storage;[m
[32m+[m[32m    StorageReference storageReference;[m
 [m
     @Override[m
     protected void onCreate(Bundle savedInstanceState) {[m
         super.onCreate(savedInstanceState);[m
         setContentView(R.layout.activity_my_profile);[m
 [m
[32m+[m[32m        storage = FirebaseStorage.getInstance();[m
[32m+[m[32m        storageReference = storage.getReference().child("profile_images");[m
[32m+[m
[32m+[m[32m        txtName = findViewById(R.id.txtName);[m
[32m+[m[32m        txtGender = findViewById(R.id.txtGender);[m
[32m+[m[32m        txtUsername= findViewById(R.id.txtUsername);[m
[32m+[m[32m        txtPoint = findViewById(R.id.txtPoint);[m
[32m+[m[32m        imgEdit = findViewById(R.id.imgEdit);[m
[32m+[m[32m        imgProfile = findViewById(R.id.imgProfilePic);[m
[32m+[m[32m        imgEdit.setOnClickListener(new View.OnClickListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onClick(View view) {[m
[32m+[m[32m                //grabImage();[m
[32m+[m[32m                //cropActivity();[m
[32m+[m[32m                startActivity(new Intent(getApplicationContext(), ProfileEditActivity.class));[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
 [m
         actionBar = getSupportActionBar();[m
         actionBar.setDisplayHomeAsUpEnabled(true);[m
[32m+[m[32m        viewPager = findViewById(R.id.viewpager);[m
[32m+[m[32m        setupViewPager(viewPager);[m
[32m+[m[32m        tabLayout = findViewById(R.id.tabs);[m
[32m+[m[32m        tabLayout.setupWithViewPager(viewPager);[m
[32m+[m
[32m+[m[32m        mfirebaseAuth = FirebaseAuth.getInstance();[m
[32m+[m[32m        user = mfirebaseAuth.getCurrentUser();[m
[32m+[m[32m        userID = user.getUid();[m
[32m+[m[32m        mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);[m
[32m+[m[32m        mDatabase.addValueEventListener(new ValueEventListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onDataChange(DataSnapshot dataSnapshot) {[m
[32m+[m[32m                firstName= dataSnapshot.child("a1_firstname").getValue(String.class);[m
[32m+[m[32m                lastName= dataSnapshot.child("a2_lastname").getValue(String.class);[m
[32m+[m[32m                Username= dataSnapshot.child("a3_username").getValue(String.class);[m
[32m+[m[32m                gender= dataSnapshot.child("a6_gender").getValue(String.class);[m
[32m+[m[32m                points= dataSnapshot.child("a8_points").getValue(long.class);[m
[32m+[m[32m                imageURL= dataSnapshot.child("a7_imageURL").getValue(String.class);[m
[32m+[m
[32m+[m[32m                txtName.setText(firstName + " " + lastName);[m
[32m+[m[32m                txtUsername.setText(Username);[m
[32m+[m[32m                txtGender.setText(gender);[m
[32m+[m[32m                txtPoint.setText(String.valueOf(points) + " points");[m
[32m+[m[32m                StorageReference sRef = storageReference.child(userID);[m
[32m+[m
[32m+[m[32m                if(imageURL!="none"){[m
[32m+[m[32m                    Glide.with(getApplicationContext()).load(imageURL).into(imgProfile);[m
[32m+[m
[32m+[m[32m                    /*[m
[32m+[m[32m                    sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {[m
[32m+[m[32m                        @Override[m
[32m+[m[32m                        public void onSuccess(Uri uri) {[m
[32m+[m[32m                            String imUri = uri.toString();[m
[32m+[m[32m                            Glide.with(getApplicationContext()).load(imUri).into(imgProfile);[m
[32m+[m[32m                        }[m
[32m+[m[32m                    });[m
[32m+[m[32m                    */[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onCancelled(DatabaseError databaseError) {[m
[32m+[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
     }[m
 [m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void onActivityResult(int requestCode, int resultCode, Intent data){[m
[32m+[m[32m        super.onActivityResult(requestCode, resultCode, data);[m
[32m+[m[32m        if(requestCode==1){[m
[32m+[m[32m            if(data!=null){[m
[32m+[m[32m                try{[m
[32m+[m[32m                Bundle bundle = data.getExtras();[m
[32m+[m[32m                Bitmap bitmap = bundle.getParcelable("data");[m
[32m+[m[32m                imgProfile.setImageBitmap(bitmap);[m
[32m+[m[32m                }[m
[32m+[m[32m                catch (NullPointerException e){[m
[32m+[m[32m                    Toast.makeText(getApplicationContext(), "null error", Toast.LENGTH_SHORT).show();[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {[m
[32m+[m[32m            CropImage.ActivityResult result = CropImage.getActivityResult(data);[m
[32m+[m[32m            if (resultCode == RESULT_OK) {[m
[32m+[m[32m                filePath = result.getUri();[m
[32m+[m[32m                uploadImage();[m
[32m+[m[32m            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {[m
[32m+[m[32m                Exception error = result.getError();[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private void setupViewPager(ViewPager viewPager) {[m
[32m+[m[32m        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());[m
[32m+[m[32m        adapter.addFragment(new RecentpostFragment(), "RECENT POSTS");[m
[32m+[m[32m        adapter.addFragment(new WonGamesFragment(), "WON GAMES");[m
[32m+[m[32m        viewPager.setAdapter(adapter);[m
[32m+[m[32m    }[m
 [m
     public boolean onOptionsItemSelected(MenuItem item){[m
         finish();[m
         return true;[m
 [m
     }[m
[31m-}[m
[32m+[m
[32m+[m[32m    public void uploadImage(){[m
[32m+[m[32m        final ProgressDialog progressDialog = new ProgressDialog(this);[m
[32m+[m[32m        progressDialog.setTitle("Uploading...");[m
[32m+[m[32m        progressDialog.show();[m
[32m+[m
[32m+[m[32m        StorageReference ref = storageReference.child(userID);[m
[32m+[m[32m        ref.putFile(filePath)[m
[32m+[m[32m                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {[m
[32m+[m[32m                        String url = taskSnapshot.getDownloadUrl().toString();[m
[32m+[m[32m                        progressDialog.dismiss();[m
[32m+[m[32m                        mDatabase.child("a7_imageURL").setValue(url);[m
[32m+[m[32m                        Toast.makeText(MyProfileActivity.this, "Profile picture saved", Toast.LENGTH_SHORT).show();[m
[32m+[m[32m                        imgProfile.setImageURI(filePath);[m
[32m+[m[32m                    }[m
[32m+[m[32m                })[m
[32m+[m[32m                .addOnFailureListener(new OnFailureListener() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onFailure(@NonNull Exception e) {[m
[32m+[m[32m                        progressDialog.dismiss();[m
[32m+[m[32m                        Toast.makeText(MyProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();[m
[32m+[m[32m                    }[m
[32m+[m[32m                })[m
[32m+[m[32m                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {[m
[32m+[m[32m                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot[m
[32m+[m[32m                                .getTotalByteCount());[m
[32m+[m[32m                        progressDialog.setMessage((int) progress + "%" + " uploaded" );[m
[32m+[m[32m                    }[m
[32m+[m[32m                })[m
[32m+[m[32m        ;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void cropActivity(){[m
[32m+[m[32m       CropImage.activity()[m
[32m+[m[32m                .setFixAspectRatio(true)[m
[32m+[m[32m                .start(this);[m
[32m+[m
[32m+[m
[32m+[m[32m        //Start cropping activity for already acquired image[m
[32m+[m[32m        /*[m
[32m+[m[32m        CropImage.activity(filePath)[m
[32m+[m[32m                .setFixAspectRatio(true)[m
[32m+[m[32m                .start(this);[m
[32m+[m[32m        */[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void ImageCropFunction() {[m
[32m+[m
[32m+[m[32m        // Image Crop Code[m
[32m+[m[32m        Intent CropIntent;[m
[32m+[m[32m        try {[m
[32m+[m[32m            CropIntent = new Intent("com.android.camera.action.CROP");[m
[32m+[m
[32m+[m[32m            CropIntent.setDataAndType(filePath, "image/*");[m
[32m+[m
[32m+[m[32m            CropIntent.putExtra("crop", "true");[m
[32m+[m[32m            CropIntent.putExtra("outputX", 300);[m
[32m+[m[32m            CropIntent.putExtra("outputY", 300);[m
[32m+[m[32m            CropIntent.putExtra("aspectX", 1);[m
[32m+[m[32m            CropIntent.putExtra("aspectY", 1);[m
[32m+[m[32m            CropIntent.putExtra("scaleUpIfNeeded", true);[m
[32m+[m[32m            CropIntent.putExtra("return-data", true);[m
[32m+[m
[32m+[m[32m            startActivityForResult(CropIntent, 1);[m
[32m+[m
[32m+[m[32m        } catch (ActivityNotFoundException e) {[m
[32m+[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    class ViewPagerAdapter extends FragmentPagerAdapter {[m
[32m+[m[32m        private final List<Fragment> mFragmentList = new ArrayList<>();[m
[32m+[m[32m        private final List<String> mFragmentTitleList = new ArrayList<>();[m
[32m+[m
[32m+[m[32m        public ViewPagerAdapter(FragmentManager manager) {[m
[32m+[m[32m            super(manager);[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        @Override[m
[32m+[m[32m        public Fragment getItem(int position) {[m
[32m+[m[32m            return mFragmentList.get(position);[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        @Override[m
[32m+[m[32m        public int getCount() {[m
[32m+[m[32m            return mFragmentList.size();[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        public void addFragment(Fragment fragment, String title) {[m
[32m+[m[32m            mFragmentList.add(fragment);[m
[32m+[m[32m            mFragmentTitleList.add(title);[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        @Override[m
[32m+[m[32m        public CharSequence getPageTitle(int position) {[m
[32m+[m[32m            return mFragmentTitleList.get(position);[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/java/secured/tips/ProfileEditActivity.java b/app/src/main/java/secured/tips/ProfileEditActivity.java[m
[1mnew file mode 100644[m
[1mindex 0000000..930671c[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/secured/tips/ProfileEditActivity.java[m
[36m@@ -0,0 +1,135 @@[m
[32m+[m[32mpackage secured.tips;[m
[32m+[m
[32m+[m[32mimport android.net.Uri;[m
[32m+[m[32mimport android.os.Bundle;[m
[32m+[m[32mimport android.support.design.widget.FloatingActionButton;[m
[32m+[m[32mimport android.support.v7.app.ActionBar;[m
[32m+[m[32mimport android.support.v7.app.AppCompatActivity;[m
[32m+[m[32mimport android.view.MenuItem;[m
[32m+[m[32mimport android.view.View;[m
[32m+[m[32mimport android.widget.EditText;[m
[32m+[m[32mimport android.widget.ImageView;[m
[32m+[m[32mimport android.widget.RadioButton;[m
[32m+[m
[32m+[m[32mimport com.bumptech.glide.Glide;[m
[32m+[m[32mimport com.google.firebase.auth.FirebaseAuth;[m
[32m+[m[32mimport com.google.firebase.auth.FirebaseUser;[m
[32m+[m[32mimport com.google.firebase.database.DataSnapshot;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseError;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseReference;[m
[32m+[m[32mimport com.google.firebase.database.FirebaseDatabase;[m
[32m+[m[32mimport com.google.firebase.database.ValueEventListener;[m
[32m+[m[32mimport com.google.firebase.storage.FirebaseStorage;[m
[32m+[m[32mimport com.google.firebase.storage.StorageReference;[m
[32m+[m
[32m+[m[32mpublic class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener{[m
[32m+[m
[32m+[m
[32m+[m[32m    ActionBar actionBar;[m
[32m+[m[32m    EditText edtFirstName, edtLastName, edtUsername, edtPhone, edtEmail, edtAccount;[m
[32m+[m[32m    RadioButton rdbMale, rdbFemale;[m
[32m+[m[32m    ImageView imgAdd, imgProfilePic;[m
[32m+[m[32m    String firstName, lastName, Username, gender, imageURL, email, account, phone;[m
[32m+[m[32m    long points;[m
[32m+[m[32m    FloatingActionButton btnSave;[m
[32m+[m[32m    DatabaseReference mDatabase;[m
[32m+[m[32m    FirebaseAuth mfirebaseAuth;[m
[32m+[m[32m    FirebaseUser user;[m
[32m+[m[32m    String userID;[m
[32m+[m[32m    private Uri filePath;[m
[32m+[m[32m    FirebaseStorage storage;[m
[32m+[m[32m    StorageReference storageReference;[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void onCreate(Bundle savedInstanceState) {[m
[32m+[m[32m        super.onCreate(savedInstanceState);[m
[32m+[m[32m        setContentView(R.layout.activity_profile_edit);[m
[32m+[m
[32m+[m[32m        actionBar = getSupportActionBar();[m
[32m+[m[32m        actionBar.setDisplayHomeAsUpEnabled(true);[m
[32m+[m
[32m+[m[32m        edtFirstName = findViewById(R.id.edtFirstName);[m
[32m+[m[32m        edtLastName = findViewById(R.id.edtLastName);[m
[32m+[m[32m        edtUsername = findViewById(R.id.edtUsername);[m
[32m+[m[32m        edtPhone = findViewById(R.id.edtPhone);[m
[32m+[m[32m        edtEmail = findViewById(R.id.edtEmail);[m
[32m+[m[32m        edtAccount = findViewById(R.id.edtAccountDetails);[m
[32m+[m
[32m+[m[32m        rdbFemale = findViewById(R.id.rdbFemale);[m
[32m+[m[32m        rdbMale = findViewById(R.id.rdbMale);[m
[32m+[m
[32m+[m[32m        imgAdd = findViewById(R.id.imgAdd); imgAdd.setOnClickListener(this);[m
[32m+[m[32m        imgProfilePic = findViewById(R.id.imgProfilePic); imgProfilePic.setOnClickListener(this);[m
[32m+[m
[32m+[m[32m        FloatingActionButton btnSave = findViewById(R.id.fltButton);[m
[32m+[m[32m        btnSave.setOnClickListener(this);[m
[32m+[m
[32m+[m[32m        fillContent();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private void fillContent() {[m
[32m+[m[32m        storage = FirebaseStorage.getInstance();[m
[32m+[m[32m        storageReference = storage.getReference().child("profile_images");[m
[32m+[m
[32m+[m[32m        mfirebaseAuth = FirebaseAuth.getInstance();[m
[32m+[m[32m        user = mfirebaseAuth.getCurrentUser();[m
[32m+[m[32m        userID = user.getUid();[m
[32m+[m[32m        mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);[m
[32m+[m
[32m+[m[32m        mDatabase.addValueEventListener(new ValueEventListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onDataChange(DataSnapshot dataSnapshot) {[m
[32m+[m[32m                firstName= dataSnapshot.child("a1_firstname").getValue(String.class);[m
[32m+[m[32m                lastName= dataSnapshot.child("a2_lastname").getValue(String.class);[m
[32m+[m[32m                Username= dataSnapshot.child("a3_username").getValue(String.class);[m
[32m+[m[32m                gender= dataSnapshot.child("a6_gender").getValue(String.class);[m
[32m+[m[32m                points= dataSnapshot.child("a8_points").getValue(long.class);[m
[32m+[m[32m                imageURL= dataSnapshot.child("a7_imageURL").getValue(String.class);[m
[32m+[m[32m                phone = dataSnapshot.child("a5_phone").getValue(String.class);[m
[32m+[m[32m                email= dataSnapshot.child("a4_email").getValue(String.class);[m
[32m+[m[32m                account= dataSnapshot.child("b1_account").getValue(String.class);[m
[32m+[m
[32m+[m[32m                edtFirstName.setText(firstName);[m
[32m+[m[32m                edtLastName.setText(lastName);[m
[32m+[m[32m                edtUsername.setText(Username);[m
[32m+[m[32m                edtPhone.setText(phone);[m
[32m+[m[32m                edtEmail.setText(email);[m
[32m+[m[32m                edtAccount.setText(account);[m
[32m+[m
[32m+[m[32m                if(gender.toLowerCase().equals("male")){[m
[32m+[m[32m                    rdbMale.setChecked(true);[m
[32m+[m[32m                }else if(gender.toLowerCase().equals("female")){[m
[32m+[m[32m                    rdbFemale.setChecked(true);[m
[32m+[m[32m                }[m
[32m+[m[32m                StorageReference sRef = storageReference.child(userID);[m
[32m+[m
[32m+[m[32m                if(imageURL!="none"){[m
[32m+[m[32m                    Glide.with(getApplicationContext()).load(imageURL).into(imgProfilePic);[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onCancelled(DatabaseError databaseError) {[m
[32m+[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public boolean onOptionsItemSelected(MenuItem item){[m
[32m+[m[32m        finish();[m
[32m+[m[32m        return true;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public void onClick(View view) {[m
[32m+[m[32m        switch (view.getId()){[m
[32m+[m[32m            case R.id.fltButton:[m
[32m+[m[32m                break;[m
[32m+[m[32m            case R.id.imgAdd:[m
[32m+[m[32m                break;[m
[32m+[m[32m            case R.id.imgProfilePic:[m
[32m+[m[32m                break;[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/app/src/main/java/secured/tips/RecentpostFragment.java b/app/src/main/java/secured/tips/RecentpostFragment.java[m
[1mnew file mode 100644[m
[1mindex 0000000..4c947c8[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/secured/tips/RecentpostFragment.java[m
[36m@@ -0,0 +1,109 @@[m
[32m+[m[32mpackage secured.tips;[m
[32m+[m
[32m+[m[32mimport android.os.Bundle;[m
[32m+[m[32mimport android.support.v4.app.Fragment;[m
[32m+[m[32mimport android.support.v7.widget.LinearLayoutManager;[m
[32m+[m[32mimport android.support.v7.widget.RecyclerView;[m
[32m+[m[32mimport android.view.LayoutInflater;[m
[32m+[m[32mimport android.view.View;[m
[32m+[m[32mimport android.view.ViewGroup;[m
[32m+[m[32mimport android.widget.TextView;[m
[32m+[m
[32m+[m[32mimport com.google.firebase.auth.FirebaseAuth;[m
[32m+[m[32mimport com.google.firebase.database.DataSnapshot;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseError;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseReference;[m
[32m+[m[32mimport com.google.firebase.database.FirebaseDatabase;[m
[32m+[m[32mimport com.google.firebase.database.ValueEventListener;[m
[32m+[m
[32m+[m[32mimport java.util.ArrayList;[m
[32m+[m[32mimport java.util.List;[m
[32m+[m
[32m+[m[32mimport datafiles.Cache;[m
[32m+[m[32mimport datafiles.ChatMessage;[m
[32m+[m[32mimport datafiles.PostAdapter;[m
[32m+[m
[32m+[m
[32m+[m[32m/**[m
[32m+[m[32m * A simple {@link Fragment} subclass.[m
[32m+[m[32m */[m
[32m+[m[32mpublic class RecentpostFragment extends Fragment {[m
[32m+[m[32m    String userID, myUserID;[m
[32m+[m[32m    private final String TAG = "MyChatApp";[m
[32m+[m[32m    private RecyclerView recyclerView;[m
[32m+[m[32m    private DatabaseReference mRef;[m
[32m+[m[32m    TextView txtNotice;[m
[32m+[m[32m    Cache cache = new Cache();[m
[32m+[m[32m    List<ChatMessage> chatMessages;[m
[32m+[m[32m    List<DatabaseReference> Refs = new ArrayList<DatabaseReference>();[m
[32m+[m[32m    private PostAdapter adapter;[m
[32m+[m[32m    FirebaseAuth auth;[m
[32m+[m
[32m+[m
[32m+[m[32m    public RecentpostFragment() {[m
[32m+[m[32m        // Required empty public constructor[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public void onCreate(Bundle savedInstanceState) {[m
[32m+[m[32m        super.onCreate(savedInstanceState);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public View onCreateView(LayoutInflater inflater, ViewGroup container,[m
[32m+[m[32m                             Bundle savedInstanceState) {[m
[32m+[m[32m        // Inflate the layout for this fragment[m
[32m+[m[32m        View rootView=inflater.inflate(R.layout.fragment_recentpost, container, false);[m
[32m+[m[32m        recyclerView = rootView.findViewById(R.id.rvRecent);[m
[32m+[m[32m        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());[m
[32m+[m[32m        txtNotice = rootView.findViewById(R.id.txtNotice);[m
[32m+[m[32m        chatMessages = new ArrayList<ChatMessage>();[m
[32m+[m[32m        userID = cache.getUserID();[m
[32m+[m[32m        mRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference().child("chatrooms");[m
[32m+[m[32m        auth = FirebaseAuth.getInstance();[m
[32m+[m[32m        myUserID = auth.getUid().toString();[m
[32m+[m
[32m+[m[32m        mRef.keepSynced(true);[m
[32m+[m[32m        mRef.addValueEventListener(new ValueEventListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onDataChange(DataSnapshot dataSnapshot) {[m
[32m+[m[32m                Refs.clear();[m
[32m+[m[32m                chatMessages.clear();[m
[32m+[m[32m                for(DataSnapshot snapshot: dataSnapshot.getChildren()){[m
[32m+[m[32m                    snapshot.getRef().orderByChild("messageUserId").equalTo(userID).limitToLast(10).addValueEventListener(new ValueEventListener() {[m
[32m+[m[32m                        @Override[m
[32m+[m[32m                        public void onDataChange(DataSnapshot dataSnapshot) {[m
[32m+[m[32m                            for(DataSnapshot snap : dataSnapshot.getChildren()){[m
[32m+[m[32m                                    chatMessages.add(snap.getValue(ChatMessage.class));[m
[32m+[m[32m                                    Refs.add(snap.getRef());[m
[32m+[m[32m                            }[m
[32m+[m[32m                            //Collections.reverse(chatMessages);[m
[32m+[m[32m                            //Collections.reverse(Refs);[m
[32m+[m[32m                            adapter = new PostAdapter(getContext(), chatMessages, myUserID, Refs);[m
[32m+[m[32m                            recyclerView.setLayoutManager(layoutManager);[m
[32m+[m[32m                            recyclerView.setAdapter(adapter);[m
[32m+[m[32m                            if(adapter.getItemCount()==0){[m
[32m+[m[32m                                txtNotice.setVisibility(View.VISIBLE);[m
[32m+[m[32m                            }[m
[32m+[m[32m                            else {[m
[32m+[m[32m                                txtNotice.setVisibility(View.GONE);[m
[32m+[m[32m                            }[m
[32m+[m[32m                        }[m
[32m+[m[32m                        @Override[m
[32m+[m[32m                        public void onCancelled(DatabaseError databaseError) {[m
[32m+[m
[32m+[m[32m                        }[m
[32m+[m[32m                    });[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onCancelled(DatabaseError databaseError) {[m
[32m+[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m
[32m+[m[32m        return rootView;[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/app/src/main/java/secured/tips/RoomsPageActivity.java b/app/src/main/java/secured/tips/RoomsPageActivity.java[m
[1mindex 1f6ff56..4016a47 100644[m
[1m--- a/app/src/main/java/secured/tips/RoomsPageActivity.java[m
[1m+++ b/app/src/main/java/secured/tips/RoomsPageActivity.java[m
[36m@@ -1,25 +1,198 @@[m
 package secured.tips;[m
 [m
[32m+[m[32mimport android.content.DialogInterface;[m
[32m+[m[32mimport android.content.Intent;[m
 import android.os.Bundle;[m
 import android.support.v7.app.ActionBar;[m
[32m+[m[32mimport android.support.v7.app.AlertDialog;[m
 import android.support.v7.app.AppCompatActivity;[m
[32m+[m[32mimport android.support.v7.widget.CardView;[m
[32m+[m[32mimport android.util.Log;[m
 import android.view.MenuItem;[m
[32m+[m[32mimport android.view.View;[m
[32m+[m[32mimport android.widget.Button;[m
[32m+[m[32mimport android.widget.Toast;[m
 [m
[31m-public class RoomsPageActivity extends AppCompatActivity {[m
[32m+[m[32mimport com.google.firebase.auth.FirebaseAuth;[m
[32m+[m[32mimport com.google.firebase.auth.FirebaseUser;[m
[32m+[m[32mimport com.google.firebase.database.DataSnapshot;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseError;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseReference;[m
[32m+[m[32mimport com.google.firebase.database.FirebaseDatabase;[m
[32m+[m[32mimport com.google.firebase.database.ValueEventListener;[m
 [m
[32m+[m[32mpublic class RoomsPageActivity extends AppCompatActivity implements View.OnClickListener {[m
[32m+[m
[32m+[m[32m    private FirebaseAuth mfirebaseAuth;[m
[32m+[m[32m    DatabaseReference mDatabase;[m
[32m+[m[32m    FirebaseUser user;[m
     ActionBar actionBar;[m
[32m+[m[32m    Button btnAbout;[m
[32m+[m[32m    boolean VipSub;[m
[32m+[m[32m    boolean ChatSub;[m
[32m+[m[32m    String firstName =",Great Tipster";[m
[32m+[m[32m    Button btnSubmit, btnShare, btnDelete;[m
[32m+[m[32m    CardView rm1, rm2,rm3,rm4, rm5, rm6, rm7;[m
     @Override[m
     protected void onCreate(Bundle savedInstanceState) {[m
         super.onCreate(savedInstanceState);[m
         setContentView(R.layout.activity_rooms_page);[m
[32m+[m[32m        mfirebaseAuth = FirebaseAuth.getInstance();[m
[32m+[m[32m        user = mfirebaseAuth.getCurrentUser();[m
[32m+[m
[32m+[m[32m        rm1 = findViewById(R.id.crdRoomOne); rm1.setOnClickListener(this);[m
[32m+[m[32m        rm2 = findViewById(R.id.crdRoomTwo); rm2.setOnClickListener(this);[m
[32m+[m[32m        rm3 = findViewById(R.id.crdRoomThree); rm3.setOnClickListener(this);[m
[32m+[m[32m        rm4 = findViewById(R.id.crdRoomFour); rm4.setOnClickListener(this);[m
[32m+[m[32m        rm5 = findViewById(R.id.crdRoomFive); rm5.setOnClickListener(this);[m
[32m+[m[32m        rm6 = findViewById(R.id.crdRoomSix); rm6.setOnClickListener(this);[m
[32m+[m[32m        rm7 = findViewById(R.id.crdRoomSeven); rm7.setOnClickListener(this);[m
 [m
         actionBar = getSupportActionBar();[m
         actionBar.setDisplayHomeAsUpEnabled(true);[m
[32m+[m[32m        btnAbout = findViewById(R.id.btnAbout);[m
[32m+[m[32m        btnAbout.setOnClickListener(this);[m
     }[m
 [m
     public boolean onOptionsItemSelected(MenuItem item){[m
         finish();[m
         return true;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void popUp(){[m
[32m+[m[32m        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);[m
[32m+[m[32m        builder.setMessage("You must login first")[m
[32m+[m[32m                .setPositiveButton("Login", new DialogInterface.OnClickListener() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onClick(DialogInterface dialogInterface, int i) {[m
[32m+[m[32m                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));[m
[32m+[m[32m                    }[m
[32m+[m[32m                })[m
[32m+[m[32m                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onClick(DialogInterface dialogInterface, int i) {[m
[32m+[m[32m                        //do nothing[m
[32m+[m[32m                    }[m
[32m+[m[32m                })[m
[32m+[m[32m                .show();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void popUp2(){[m
[32m+[m[32m        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);[m
[32m+[m[32m        builder.setMessage("Welcome " + firstName[m
[32m+[m[32m                +".\n\nYou are either new here or your subscription just expired. The big odd rooms are only for VIP or Forum subscribers.\n\n"[m
[32m+[m[32m                +"These rooms are more organized, more accurate, and has many other features to help you win big.\n"[m
[32m+[m[32m                +"Subscribe to access all the big odd rooms.")[m
[32m+[m[32m                .setPositiveButton("Subscribe now", new DialogInterface.OnClickListener() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onClick(DialogInterface dialogInterface, int i) {[m
[32m+[m[32m                        startActivity(new Intent(getApplicationContext(), SubscribeActivity.class));[m
[32m+[m[32m                    }[m
[32m+[m[32m                })[m
[32m+[m[32m                .setNegativeButton("Later", new DialogInterface.OnClickListener() {[m
[32m+[m[32m                    @Override[m
[32m+[m[32m                    public void onClick(DialogInterface dialogInterface, int i) {[m
[32m+[m[32m                        //do nothing[m
[32m+[m[32m                    }[m
[32m+[m[32m                })[m
[32m+[m[32m                .show();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public void onClick(View view) {[m
[32m+[m[32m        Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);[m
[32m+[m[32m        switch (view.getId()){[m
[32m+[m[32m            case R.id.crdRoomOne:[m
[32m+[m[32m                intent.putExtra("ROOM",1);[m
[32m+[m[32m                startActivity(intent);[m
[32m+[m[32m                break;[m
[32m+[m[32m            case R.id.crdRoomTwo:[m
[32m+[m[32m                intent.putExtra("ROOM",2);[m
[32m+[m[32m                startActivity(intent);[m
[32m+[m[32m                break;[m
[32m+[m[32m            case R.id.crdRoomThree:[m
[32m+[m[32m                if(user==null){[m
[32m+[m[32m                    popUp();[m
[32m+[m[32m                    return;[m
[32m+[m[32m                }[m
[32m+[m[32m                if(!confirmSubscribtion()){[m
[32m+[m[32m                    return;[m
[32m+[m[32m                }[m
[32m+[m[32m                intent.putExtra("ROOM",3);[m
[32m+[m[32m                startActivity(intent);[m
[32m+[m[32m                break;[m
[32m+[m[32m            case R.id.crdRoomFour:[m
[32m+[m[32m                if(user==null){[m
[32m+[m[32m                    popUp();[m
[32m+[m[32m                    return;[m
[32m+[m[32m                }[m
[32m+[m[32m                if(!confirmSubscribtion()){[m
[32m+[m[32m                    return;[m
[32m+[m[32m                }[m
[32m+[m[32m                intent.putExtra("ROOM",4);[m
[32m+[m[32m                startActivity(intent);[m
[32m+[m[32m                break;[m
[32m+[m[32m            case R.id.crdRoomFive:[m
[32m+[m[32m                if(user==null){[m
[32m+[m[32m                    popUp();[m
[32m+[m[32m                    return;[m
[32m+[m[32m                }[m
[32m+[m[32m                if(!confirmSubscribtion()){[m
[32m+[m[32m                    return;[m
[32m+[m[32m                }[m
[32m+[m[32m                intent.putExtra("ROOM",5);[m
[32m+[m[32m                startActivity(intent);[m
[32m+[m[32m                break;[m
[32m+[m[32m            case R.id.crdRoomSix:[m
[32m+[m[32m                if(user==null){[m
[32m+[m[32m                    popUp();[m
[32m+[m[32m                    return;[m
[32m+[m[32m                }[m
[32m+[m[32m                if(!confirmSubscribtion()){[m
[32m+[m[32m                    return;[m
[32m+[m[32m                }[m
[32m+[m[32m                intent.putExtra("ROOM",6);[m
[32m+[m[32m                startActivity(intent);[m
[32m+[m[32m                break;[m
[32m+[m[32m            case R.id.crdRoomSeven:[m
[32m+[m[32m                if(user==null){[m
[32m+[m[32m                    popUp();[m
[32m+[m[32m                    return;[m
[32m+[m[32m                }[m
[32m+[m[32m                if(!confirmSubscribtion()){[m
[32m+[m[32m                    return;[m
[32m+[m[32m                }[m
[32m+[m[32m                intent.putExtra("ROOM",7);[m
[32m+[m[32m                startActivity(intent);[m
[32m+[m[32m                break;[m
[32m+[m[32m            case R.id.btnSubmit:[m
[32m+[m[32m                Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();[m
[32m+[m[32m            case R.id.btnAbout:[m
[32m+[m[32m                startActivity(new Intent(getApplicationContext(), AboutTipzoneActivity.class));[m
[32m+[m[32m                break;[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
 [m
[32m+[m[32m    public boolean confirmSubscribtion(){[m
[32m+[m[32m        boolean subscriber = false;[m
[32m+[m[32m        mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(user.getUid());[m
[32m+[m[32m        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onDataChange(DataSnapshot dataSnapshot) {[m
[32m+[m[32m                VipSub = dataSnapshot.child("b2_vip").getValue(boolean.class);[m
[32m+[m[32m                ChatSub =  dataSnapshot.child("b4_chat").getValue(boolean.class);[m
[32m+[m[32m                firstName = dataSnapshot.child("a1_firstname").getValue(String.class);[m
[32m+[m[32m                Log.i("Faster", "vipsub is " + String.valueOf(VipSub) + ", chatsub is " + String.valueOf(ChatSub));[m
[32m+[m[32m            }[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onCancelled(DatabaseError databaseError) { }[m
[32m+[m[32m        });[m
[32m+[m[32m        if(VipSub!=true|| ChatSub!=true){[m
[32m+[m[32m            popUp2();[m
[32m+[m[32m        }[m
[32m+[m[32m        else{[m
[32m+[m[32m            subscriber = true;[m
[32m+[m[32m        }[m
[32m+[m[32m        return subscriber;[m
     }[m
 }[m
[1mdiff --git a/app/src/main/java/secured/tips/SignUpActivity.java b/app/src/main/java/secured/tips/SignUpActivity.java[m
[1mindex 1e17e4e..30752d6 100644[m
[1m--- a/app/src/main/java/secured/tips/SignUpActivity.java[m
[1m+++ b/app/src/main/java/secured/tips/SignUpActivity.java[m
[36m@@ -69,7 +69,7 @@[m [mpublic class SignUpActivity extends AppCompatActivity implements View.OnClickLis[m
         actionBar.setDisplayHomeAsUpEnabled(true);[m
 [m
         mfirebaseAuth = FirebaseAuth.getInstance();[m
[31m-        mDatabase = FirebaseDatabase.getInstance().getReference();[m
[32m+[m[32m        mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference();[m
         prefs = PreferenceManager.getDefaultSharedPreferences(this);[m
         editor = prefs.edit();[m
 [m
[36m@@ -95,9 +95,9 @@[m [mpublic class SignUpActivity extends AppCompatActivity implements View.OnClickLis[m
     }[m
 [m
     public void registerUser(){[m
[31m-        a3_username = edtUsername.getText().toString().trim();[m
         a1_firstName = edtFirstName.getText().toString().trim();[m
         a2_lastName = edtLastName.getText().toString().trim();[m
[32m+[m[32m        a3_username = edtUsername.getText().toString().trim();[m
         a4_email = edtEmail.getText().toString().trim();[m
         a5_phone = edtPhone.getText().toString().trim();[m
 [m
[1mdiff --git a/app/src/main/java/secured/tips/WonGamesFragment.java b/app/src/main/java/secured/tips/WonGamesFragment.java[m
[1mnew file mode 100644[m
[1mindex 0000000..24e9c89[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/secured/tips/WonGamesFragment.java[m
[36m@@ -0,0 +1,107 @@[m
[32m+[m[32mpackage secured.tips;[m
[32m+[m
[32m+[m
[32m+[m[32mimport android.os.Bundle;[m
[32m+[m[32mimport android.support.v4.app.Fragment;[m
[32m+[m[32mimport android.support.v7.widget.LinearLayoutManager;[m
[32m+[m[32mimport android.support.v7.widget.RecyclerView;[m
[32m+[m[32mimport android.view.LayoutInflater;[m
[32m+[m[32mimport android.view.View;[m
[32m+[m[32mimport android.view.ViewGroup;[m
[32m+[m[32mimport android.widget.TextView;[m
[32m+[m
[32m+[m[32mimport com.google.firebase.auth.FirebaseAuth;[m
[32m+[m[32mimport com.google.firebase.database.DataSnapshot;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseError;[m
[32m+[m[32mimport com.google.firebase.database.DatabaseReference;[m
[32m+[m[32mimport com.google.firebase.database.FirebaseDatabase;[m
[32m+[m[32mimport com.google.firebase.database.ValueEventListener;[m
[32m+[m
[32m+[m[32mimport java.util.ArrayList;[m
[32m+[m[32mimport java.util.List;[m
[32m+[m
[32m+[m[32mimport datafiles.Cache;[m
[32m+[m[32mimport datafiles.ChatMessage;[m
[32m+[m[32mimport datafiles.PostAdapter;[m
[32m+[m
[32m+[m[32mpublic class WonGamesFragment extends Fragment {[m
[32m+[m[32m    String userID, myUserID;[m
[32m+[m[32m    private final String TAG = "MyChatApp";[m
[32m+[m[32m    private RecyclerView recyclerView;[m
[32m+[m[32m    private DatabaseReference mRef;[m
[32m+[m[32m    TextView txtNotice;[m
[32m+[m[32m    Cache cache = new Cache();[m
[32m+[m[32m    List<DatabaseReference> Refs = new ArrayList<DatabaseReference>();[m
[32m+[m[32m    List<ChatMessage> chatMessages;[m
[32m+[m[32m    private PostAdapter adapter;[m
[32m+[m[32m    FirebaseAuth auth;[m
[32m+[m
[32m+[m
[32m+[m[32m    public WonGamesFragment() {[m
[32m+[m[32m        // Required empty public constructor[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public void onCreate(Bundle savedInstanceState) {[m
[32m+[m[32m        super.onCreate(savedInstanceState);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public View onCreateView(LayoutInflater inflater, ViewGroup container,[m
[32m+[m[32m                             Bundle savedInstanceState) {[m
[32m+[m[32m        // Inflate the layout for this fragment[m
[32m+[m[32m        View rootView=inflater.inflate(R.layout.fragment_recentpost, container, false);[m
[32m+[m[32m        recyclerView = rootView.findViewById(R.id.rvRecent);[m
[32m+[m
[32m+[m[32m        txtNotice = rootView.findViewById(R.id.txtNotice);[m
[32m+[m[32m        chatMessages = new ArrayList<ChatMessage>();[m
[32m+[m[32m        userID = cache.getUserID();[m
[32m+[m[32m        mRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference().child("chatrooms");[m
[32m+[m[32m        auth = FirebaseAuth.getInstance();[m
[32m+[m[32m        myUserID = auth.getUid().toString();[m
[32m+[m
[32m+[m[32m        mRef.keepSynced(true);[m
[32m+[m[32m        mRef.addValueEventListener(new ValueEventListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onDataChange(DataSnapshot dataSnapshot) {[m
[32m+[m[32m                for(DataSnapshot snapshot: dataSnapshot.getChildren()){[m
[32m+[m[32m                    snapshot.getRef().orderByChild("messageStatus").equalTo("1_"+userID).addValueEventListener(new ValueEventListener() {[m
[32m+[m[32m                        @Override[m
[32m+[m[32m                        public void onDataChange(DataSnapshot dataSnapshot) {[m
[32m+[m[32m                            for(DataSnapshot snap : dataSnapshot.getChildren()){[m
[32m+[m[32m                                chatMessages.add(snap.getValue(ChatMessage.class));[m
[32m+[m[32m                                Refs.add(snap.getRef());[m
[32m+[m[32m                                adapter.setHasStableIds(true);[m
[32m+[m[32m                                adapter = new PostAdapter(getContext(), chatMessages, myUserID, Refs);[m
[32m+[m[32m                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());[m
[32m+[m[32m                                recyclerView.setLayoutManager(layoutManager);[m
[32m+[m[32m                                recyclerView.setAdapter(adapter);[m
[32m+[m[32m                                if(adapter.getItemCount()==0){[m
[32m+[m[32m                                    txtNotice.setVisibility(View.VISIBLE);[m
[32m+[m[32m                                }[m
[32m+[m[32m                                else {[m
[32m+[m[32m                                    txtNotice.setVisibility(View.GONE);[m
[32m+[m[32m                                }[m
[32m+[m[32m                            }[m
[32m+[m[32m                        }[m
[32m+[m
[32m+[m[32m                        @Override[m
[32m+[m[32m                        public void onCancelled(DatabaseError databaseError) {[m
[32m+[m
[32m+[m[32m                        }[m
[32m+[m[32m                    });[m
[32m+[m[32m                }[m
[32m+[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onCancelled(DatabaseError databaseError) {[m
[32m+[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m
[32m+[m[32m        return rootView;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m}[m
[1mdiff --git a/app/src/main/res/drawable/ads.jpg b/app/src/main/res/drawable/ads.jpg[m
[1mnew file mode 100644[m
[1mindex 0000000..87b41ad[m
Binary files /dev/null and b/app/src/main/res/drawable/ads.jpg differ
[1mdiff --git a/app/src/main/res/drawable/ball.png b/app/src/main/res/drawable/ball.png[m
[1mnew file mode 100644[m
[1mindex 0000000..0829fd1[m
Binary files /dev/null and b/app/src/main/res/drawable/ball.png differ
[1mdiff --git a/app/src/main/res/drawable/border.xml b/app/src/main/res/drawable/border.xml[m
[1mindex c36e1a1..e4c0f3e 100644[m
[1m--- a/app/src/main/res/drawable/border.xml[m
[1m+++ b/app/src/main/res/drawable/border.xml[m
[36m@@ -13,11 +13,11 @@[m
 [m
     <item>[m
         <shape android:shape="rectangle">[m
[31m-            <gradient android:startColor="#1a6814"[m
[31m-                android:endColor="#84c47f"[m
[32m+[m[32m            <gradient android:startColor="@android:color/holo_orange_light"[m
[32m+[m[32m                android:endColor="@android:color/holo_orange_light"[m
                 android:angle="270" />[m
[31m-            <corners android:radius="16dp"/>[m
[31m-            <stroke android:width="3dp" android:color="#790808" />[m
[32m+[m[32m            <corners android:radius="10dp"/>[m
[32m+[m[32m            <stroke android:width="1dp" android:color="@android:color/transparent" />[m
         </shape>[m
     </item>[m
 [m
[1mdiff --git a/app/src/main/res/drawable/chat.png b/app/src/main/res/drawable/chat.png[m
[1mnew file mode 100644[m
[1mindex 0000000..aa1e8ea[m
Binary files /dev/null and b/app/src/main/res/drawable/chat.png differ
[1mdiff --git a/app/src/main/res/drawable/chatroom_bg.jpg b/app/src/main/res/drawable/chatroom_bg.jpg[m
[1mnew file mode 100644[m
[1mindex 0000000..8d2588a[m
Binary files /dev/null and b/app/src/main/res/drawable/chatroom_bg.jpg differ
[1mdiff --git a/app/src/main/res/drawable/dropdown.png b/app/src/main/res/drawable/dropdown.png[m
[1mnew file mode 100644[m
[1mindex 0000000..96f405a[m
Binary files /dev/null and b/app/src/main/res/drawable/dropdown.png differ
[1mdiff --git a/app/src/main/res/drawable/dspc.png b/app/src/main/res/drawable/dspc.png[m
[1mnew file mode 100644[m
[1mindex 0000000..610459b[m
Binary files /dev/null and b/app/src/main/res/drawable/dspc.png differ
[1mdiff --git a/app/src/main/res/drawable/ic_send_black_24dp.png b/app/src/main/res/drawable/ic_send_black_24dp.png[m
[1mindex 926627b..a0f39e1 100644[m
Binary files a/app/src/main/res/drawable/ic_send_black_24dp.png and b/app/src/main/res/drawable/ic_send_black_24dp.png differ
[1mdiff --git a/app/src/main/res/drawable/indicator_default.xml b/app/src/main/res/drawable/indicator_default.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..330805c[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/drawable/indicator_default.xml[m
[36m@@ -0,0 +1,13 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<layer-list xmlns:android="http://schemas.android.com/apk/res/android">[m
[32m+[m[32m    <item>[m
[32m+[m[32m        <shape[m
[32m+[m[32m            android:innerRadius="0dp"[m
[32m+[m[32m            android:shape="ring"[m
[32m+[m[32m            android:thickness="4dp"[m
[32m+[m[32m            android:useLevel="false">[m
[32m+[m[32m            <solid android:color="@android:color/darker_gray"/>[m
[32m+[m[32m        </shape>[m
[32m+[m[32m    </item>[m
[32m+[m
[32m+[m[32m</layer-list>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/drawable/indicator_selected.xml b/app/src/main/res/drawable/indicator_selected.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..e9b3ac9[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/drawable/indicator_selected.xml[m
[36m@@ -0,0 +1,13 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<layer-list xmlns:android="http://schemas.android.com/apk/res/android">[m
[32m+[m[32m    <item>[m
[32m+[m[32m        <shape[m
[32m+[m[32m            android:innerRadius="0dp"[m
[32m+[m[32m            android:shape="ring"[m
[32m+[m[32m            android:thickness="4dp"[m
[32m+[m[32m            android:useLevel="false">[m
[32m+[m[32m            <solid android:color="@color/colorPrimary"/>[m
[32m+[m[32m        </shape>[m
[32m+[m[32m    </item>[m
[32m+[m
[32m+[m[32m</layer-list>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/drawable/indicator_selector.xml b/app/src/main/res/drawable/indicator_selector.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..4406e9c[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/drawable/indicator_selector.xml[m
[36m@@ -0,0 +1,8 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<selector xmlns:android="http://schemas.android.com/apk/res/android">[m
[32m+[m[32m    <item android:drawable="@drawable/indicator_selected"[m
[32m+[m[32m        android:state_selected="true"/>[m
[32m+[m
[32m+[m[32m    <item android:drawable="@drawable/indicator_default"/>[m
[32m+[m
[32m+[m[32m</selector>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/drawable/love.png b/app/src/main/res/drawable/love.png[m
[1mnew file mode 100644[m
[1mindex 0000000..ad5a03a[m
Binary files /dev/null and b/app/src/main/res/drawable/love.png differ
[1mdiff --git a/app/src/main/res/drawable/no_love.png b/app/src/main/res/drawable/no_love.png[m
[1mnew file mode 100644[m
[1mindex 0000000..51da48a[m
Binary files /dev/null and b/app/src/main/res/drawable/no_love.png differ
[1mdiff --git a/app/src/main/res/drawable/sean.jpg b/app/src/main/res/drawable/sean.jpg[m
[1mdeleted file mode 100644[m
[1mindex a2e1065..0000000[m
Binary files a/app/src/main/res/drawable/sean.jpg and /dev/null differ
[1mdiff --git a/app/src/main/res/drawable/send.png b/app/src/main/res/drawable/send.png[m
[1mnew file mode 100644[m
[1mindex 0000000..f6cdf9b[m
Binary files /dev/null and b/app/src/main/res/drawable/send.png differ
[1mdiff --git a/app/src/main/res/layout/activity_about_tipzone.xml b/app/src/main/res/layout/activity_about_tipzone.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..d5ea74f[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/activity_about_tipzone.xml[m
[36m@@ -0,0 +1,60 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"[m
[32m+[m[32m    xmlns:app="http://schemas.android.com/apk/res-auto"[m
[32m+[m[32m    xmlns:tools="http://schemas.android.com/tools"[m
[32m+[m[32m    android:layout_width="match_parent"[m
[32m+[m[32m    android:layout_height="match_parent"[m
[32m+[m[32m    tools:context=".AboutTipzoneActivity">[m
[32m+[m
[32m+[m[32m    <android.support.v4.widget.NestedScrollView[m
[32m+[m[32m        android:layout_width="match_parent"[m
[32m+[m[32m        android:layout_height="match_parent">[m
[32m+[m
[32m+[m[32m        <LinearLayout[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="match_parent"[m
[32m+[m[32m            android:orientation="vertical">[m
[32m+[m[32m        <TextView[m
[32m+[m[32m            android:id="@+id/txtGuideline"[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="wrap_content"[m
[32m+[m[32m            android:padding="10dp"[m
[32m+[m[32m            android:background="#C5E1A5"[m
[32m+[m[32m            android:textColor="@android:color/black"/>[m
[32m+[m
[32m+[m[32m        <ImageView[m
[32m+[m[32m            android:layout_width="wrap_content"[m
[32m+[m[32m            android:layout_height="wrap_content"[m
[32m+[m[32m            android:background="#BBDEFB"[m
[32m+[m[32m            android:src="@drawable/chat"/>[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:id="@+id/txtGuideline2"[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:background="#FFF59D"[m
[32m+[m[32m                android:padding="10dp"[m
[32m+[m[32m                android:textColor="@android:color/black" />[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:id="@+id/txtGuideline3"[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:padding="10dp"[m
[32m+[m[32m                android:background="#FFAB91"[m
[32m+[m[32m                android:textColor="@android:color/black"/>[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:id="@+id/txtGuideline4"[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:padding="10dp"[m
[32m+[m[32m                android:background="#BBDEFB"[m
[32m+[m[32m                android:textColor="@android:color/black"/>[m
[32m+[m
[32m+[m[32m        </LinearLayout>[m
[32m+[m
[32m+[m[32m    </android.support.v4.widget.NestedScrollView>[m
[32m+[m
[32m+[m
[32m+[m[32m</android.support.constraint.ConstraintLayout>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/layout/activity_chat_room.xml b/app/src/main/res/layout/activity_chat_room.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..9b2e023[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/activity_chat_room.xml[m
[36m@@ -0,0 +1,51 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"[m
[32m+[m[32m    xmlns:tools="http://schemas.android.com/tools"[m
[32m+[m[32m    android:layout_width="match_parent"[m
[32m+[m[32m    android:layout_height="match_parent"[m
[32m+[m[32m    android:background="@drawable/chatroom_bg"[m
[32m+[m[32m    tools:context=".ChatRoomActivity"[m
[32m+[m[32m    android:paddingBottom="@dimen/activity_vertical_margin"[m
[32m+[m[32m    android:paddingLeft="@dimen/activity_horizontal_margin"[m
[32m+[m[32m    android:paddingRight="@dimen/activity_horizontal_margin"[m
[32m+[m[32m    android:paddingTop="@dimen/activity_vertical_margin">[m
[32m+[m
[32m+[m
[32m+[m[32m    <android.support.design.widget.FloatingActionButton[m
[32m+[m[32m        android:id="@+id/fab"[m
[32m+[m[32m        android:layout_width="wrap_content"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:layout_alignParentBottom="true"[m
[32m+[m[32m        android:layout_alignParentEnd="true"[m
[32m+[m[32m        android:tint="@color/colorPrimaryDark"[m
[32m+[m[32m        android:src="@android:drawable/ic_menu_send" />[m
[32m+[m
[32m+[m[32m    <android.support.design.widget.TextInputLayout[m
[32m+[m[32m        android:layout_width="match_parent"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:layout_alignParentBottom="true"[m
[32m+[m[32m        android:layout_toStartOf="@id/fab">[m
[32m+[m
[32m+[m[32m        <EditText[m
[32m+[m[32m            android:id="@+id/input"[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="wrap_content"[m
[32m+[m[32m            android:background="@android:drawable/editbox_background_normal"[m
[32m+[m[32m            android:hint="Post your tips"[m
[32m+[m[32m            android:inputType="text"/>[m
[32m+[m[32m    </android.support.design.widget.TextInputLayout>[m
[32m+[m
[32m+[m[32m    <ListView[m
[32m+[m[32m        android:id="@+id/list"[m
[32m+[m[32m        android:layout_width="match_parent"[m
[32m+[m[32m        android:layout_height="match_parent"[m
[32m+[m[32m        android:layout_above="@id/fab"[m
[32m+[m[32m        android:scrollbars="none"[m
[32m+[m[32m        android:layout_alignParentTop="true"[m
[32m+[m[32m        android:layout_marginBottom="16dp"[m
[32m+[m[32m        android:divider="@android:color/transparent"[m
[32m+[m[32m        android:dividerHeight="16dp"[m
[32m+[m[32m        android:stackFromBottom="true"[m
[32m+[m[32m        android:transcriptMode="alwaysScroll" />[m
[32m+[m
[32m+[m[32m</RelativeLayout>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/layout/activity_main.xml b/app/src/main/res/layout/activity_main.xml[m
[1mindex c4e40ea..79c3eab 100644[m
[1m--- a/app/src/main/res/layout/activity_main.xml[m
[1m+++ b/app/src/main/res/layout/activity_main.xml[m
[36m@@ -145,7 +145,7 @@[m
                             android:layout_width="wrap_content"[m
                             android:layout_height="wrap_content"[m
                             android:textSize="14sp"[m
[31m-                            android:text="World of tipsters"/>[m
[32m+[m[32m                            android:text="Tipster's community"/>[m
                     </LinearLayout>[m
                 </android.support.v7.widget.CardView>[m
 [m
[36m@@ -171,7 +171,8 @@[m
                         <ImageView[m
                             android:layout_width="64dp"[m
                             android:layout_height="64dp"[m
[31m-                            android:src="@drawable/free"[m
[32m+[m[32m                            android:padding="5dp"[m
[32m+[m[32m                            android:src="@drawable/ball"[m
                             android:background="@color/bckTint"/>[m
                         <TextView[m
                             android:layout_width="wrap_content"[m
[1mdiff --git a/app/src/main/res/layout/activity_member_profile.xml b/app/src/main/res/layout/activity_member_profile.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..fe346fd[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/activity_member_profile.xml[m
[36m@@ -0,0 +1,148 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<android.support.design.widget.CoordinatorLayout[m
[32m+[m[32m    xmlns:android="http://schemas.android.com/apk/res/android"[m
[32m+[m[32m    xmlns:app="http://schemas.android.com/apk/res-auto"[m
[32m+[m[32m    android:layout_width="match_parent"[m
[32m+[m[32m    android:layout_height="match_parent"[m
[32m+[m[32m    android:fitsSystemWindows="true">[m
[32m+[m
[32m+[m[32m    <android.support.design.widget.AppBarLayout[m
[32m+[m[32m        android:id="@+id/layBar"[m
[32m+[m[32m        android:layout_width="match_parent"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:fitsSystemWindows="true">[m
[32m+[m
[32m+[m[32m        <android.support.design.widget.CollapsingToolbarLayout[m
[32m+[m[32m            android:id="@+id/CollapsingToolbar"[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="wrap_content"[m
[32m+[m[32m            android:fitsSystemWindows="true"[m
[32m+[m[32m            app:contentScrim="?attr/colorPrimary"[m
[32m+[m[32m            app:expandedTitleMarginEnd="64dp"[m
[32m+[m[32m            app:expandedTitleMarginStart="48dp"[m
[32m+[m[32m            app:layout_scrollFlags="scroll|exitUntilCollapsed">[m
[32m+[m
[32m+[m[32m            <RelativeLayout[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:background="#ffffff"[m
[32m+[m[32m                app:layout_collapseMode="parallax">[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgCover"[m
[32m+[m[32m                    android:layout_width="match_parent"[m
[32m+[m[32m                    android:layout_height="100dp"[m
[32m+[m[32m                    android:layout_weight="1"[m
[32m+[m[32m                    android:scaleType="centerCrop"[m
[32m+[m[32m                    app:srcCompat="@drawable/bg_one" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgProfilePic"[m
[32m+[m[32m                    android:layout_width="160dp"[m
[32m+[m[32m                    android:layout_height="160dp"[m
[32m+[m[32m                    android:layout_alignParentTop="true"[m
[32m+[m[32m                    android:layout_centerHorizontal="true"[m
[32m+[m[32m                    android:layout_marginTop="30dp"[m
[32m+[m[32m                    android:scaleType="fitXY"[m
[32m+[m[32m                    app:srcCompat="@drawable/dspc" />[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/txtName"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_below="@+id/imgProfilePic"[m
[32m+[m[32m                    android:layout_centerHorizontal="true"[m
[32m+[m[32m                    android:layout_marginTop="10dp"[m
[32m+[m[32m                    android:text="_"[m
[32m+[m[32m                    android:textColor="@color/color_text"[m
[32m+[m[32m                    android:textSize="24sp"[m
[32m+[m[32m                    android:textStyle="normal|bold" />[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/txtUsername"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_alignStart="@+id/imgProfilePic"[m
[32m+[m[32m                    android:layout_below="@+id/txtName"[m
[32m+[m[32m                    android:inputType="textPersonName"[m
[32m+[m[32m                    android:text="username"[m
[32m+[m[32m                    android:textColor="#424242"[m
[32m+[m[32m                    android:textSize="18sp" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgUsernameIcon"[m
[32m+[m[32m                    android:layout_width="18dp"[m
[32m+[m[32m                    android:layout_height="18dp"[m
[32m+[m[32m                    android:layout_below="@id/txtName"[m
[32m+[m[32m                    android:layout_marginEnd="8dp"[m
[32m+[m[32m                    android:layout_marginTop="5dp"[m
[32m+[m[32m                    android:layout_toLeftOf="@id/txtUsername"[m
[32m+[m[32m                    app:srcCompat="@drawable/symbol_at" />[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/txtGender"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_alignStart="@+id/imgProfilePic"[m
[32m+[m[32m                    android:layout_below="@+id/txtUsername"[m
[32m+[m[32m                    android:inputType="textPersonName"[m
[32m+[m[32m                    android:text="gender"[m
[32m+[m[32m                    android:textColor="@color/color_text"[m
[32m+[m[32m                    android:textSize="18sp" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgGenderIcon"[m
[32m+[m[32m                    android:layout_width="18dp"[m
[32m+[m[32m                    android:layout_height="18dp"[m
[32m+[m[32m                    android:layout_alignStart="@+id/imgUsernameIcon"[m
[32m+[m[32m                    android:layout_below="@+id/txtUsername"[m
[32m+[m[32m                    android:layout_marginTop="5dp"[m
[32m+[m[32m                    app:srcCompat="@drawable/fui_ic_facebook_white_22dp" />[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/txtPoint"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_alignStart="@+id/imgProfilePic"[m
[32m+[m[32m                    android:layout_below="@+id/txtGender"[m
[32m+[m[32m                    android:text="0 points"[m
[32m+[m[32m                    android:textColor="@color/color_text"[m
[32m+[m[32m                    android:textSize="18sp" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgPointIcon"[m
[32m+[m[32m                    android:layout_width="18dp"[m
[32m+[m[32m                    android:layout_height="18dp"[m
[32m+[m[32m                    android:layout_alignStart="@+id/imgUsernameIcon"[m
[32m+[m[32m                    android:layout_below="@+id/imgGenderIcon"[m
[32m+[m[32m                    android:layout_marginTop="5dp"[m
[32m+[m[32m                    app:srcCompat="@android:drawable/btn_star_big_on" />[m
[32m+[m
[32m+[m[32m                <View[m
[32m+[m[32m                    android:id="@+id/viewLine"[m
[32m+[m[32m                    android:layout_width="match_parent"[m
[32m+[m[32m                    android:layout_height="1dp"[m
[32m+[m[32m                    android:layout_below="@+id/imgPointIcon"[m
[32m+[m[32m                    android:layout_marginTop="10dp"[m
[32m+[m[32m                    android:background="@color/colorPrimaryDark" />[m
[32m+[m[32m            </RelativeLayout>[m
[32m+[m[32m        </android.support.design.widget.CollapsingToolbarLayout>[m
[32m+[m
[32m+[m[32m        <android.support.design.widget.TabLayout[m
[32m+[m[32m            android:id="@+id/tabs"[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="wrap_content"[m
[32m+[m[32m            android:background="#9BC996"[m
[32m+[m[32m            app:tabGravity="fill"[m
[32m+[m[32m            app:tabMode="fixed" />[m
[32m+[m
[32m+[m[32m    </android.support.design.widget.AppBarLayout>[m
[32m+[m
[32m+[m[32m    <android.support.v4.view.ViewPager[m
[32m+[m[32m        android:id="@+id/viewpager"[m
[32m+[m[32m        android:layout_width="match_parent"[m
[32m+[m[32m        android:layout_height="match_parent"[m
[32m+[m[32m        app:layout_behavior="@string/appbar_scrolling_view_behavior" />[m
[32m+[m
[32m+[m[32m</android.support.design.widget.CoordinatorLayout>[m
[32m+[m
[1mdiff --git a/app/src/main/res/layout/activity_my_profile.xml b/app/src/main/res/layout/activity_my_profile.xml[m
[1mindex 26376b7..2653e5c 100644[m
[1m--- a/app/src/main/res/layout/activity_my_profile.xml[m
[1m+++ b/app/src/main/res/layout/activity_my_profile.xml[m
[36m@@ -6,139 +6,150 @@[m [mandroid:layout_width="match_parent"[m
 android:layout_height="match_parent"[m
 android:fitsSystemWindows="true">[m
 [m
[31m-<android.support.design.widget.AppBarLayout[m
[31m-    android:id="@+id/layBar"[m
[31m-    android:layout_width="match_parent"[m
[31m-    android:layout_height="wrap_content"[m
[31m-    android:fitsSystemWindows="true">[m
[31m-[m
[31m-    <android.support.design.widget.CollapsingToolbarLayout[m
[31m-        android:id="@+id/CollapsingToolbar"[m
[32m+[m[32m    <android.support.design.widget.AppBarLayout[m
[32m+[m[32m        android:id="@+id/layBar"[m
         android:layout_width="match_parent"[m
         android:layout_height="wrap_content"[m
[31m-        app:expandedTitleMarginStart="48dp"[m
[31m-        app:expandedTitleMarginEnd="64dp"[m
[31m-        android:fitsSystemWindows="true"[m
[31m-        app:contentScrim="?attr/colorPrimary"[m
[31m-        app:layout_scrollFlags="scroll|exitUntilCollapsed">[m
[32m+[m[32m        android:fitsSystemWindows="true">[m
 [m
[31m-        <RelativeLayout[m
[32m+[m[32m        <android.support.design.widget.CollapsingToolbarLayout[m
[32m+[m[32m            android:id="@+id/CollapsingToolbar"[m
             android:layout_width="match_parent"[m
             android:layout_height="wrap_content"[m
[31m-            android:background="#ffffff"[m
[31m-            app:layout_collapseMode="parallax">[m
[32m+[m[32m            android:fitsSystemWindows="true"[m
[32m+[m[32m            app:contentScrim="?attr/colorPrimary"[m
[32m+[m[32m            app:expandedTitleMarginEnd="64dp"[m
[32m+[m[32m            app:expandedTitleMarginStart="48dp"[m
[32m+[m[32m            app:layout_scrollFlags="scroll|exitUntilCollapsed">[m
 [m
[31m-            <ImageView[m
[31m-                android:id="@+id/imgCover"[m
[32m+[m[32m            <RelativeLayout[m
                 android:layout_width="match_parent"[m
[31m-                android:layout_height="100dp"[m
[31m-                android:scaleType="centerCrop"[m
[31m-                android:layout_weight="1"[m
[31m-                app:srcCompat="@drawable/bg_one" />[m
[31m-[m
[31m-            <ImageView[m
[31m-                android:id="@+id/imgProfilePic"[m
[31m-                android:layout_width="160dp"[m
[31m-                android:layout_height="160dp"[m
[31m-                android:layout_alignParentTop="true"[m
[31m-                android:layout_centerHorizontal="true"[m
[31m-                android:scaleType="fitXY"[m
[31m-                android:layout_marginTop="30dp"[m
[31m-                app:srcCompat="@drawable/sean" />[m
[31m-[m
[31m-            <TextView[m
[31m-                android:id="@+id/txtName"[m
[31m-                android:layout_width="wrap_content"[m
[31m-                android:layout_height="wrap_content"[m
[31m-                android:layout_below="@+id/imgProfilePic"[m
[31m-                android:text="Rihanna Emmanuel"[m
[31m-                android:textColor="@color/color_text"[m
[31m-                android:layout_marginTop="10dp"[m
[31m-                android:textStyle="normal|bold"[m
[31m-                android:textSize="28sp"[m
[31m-                android:layout_centerHorizontal="true" />[m
[31m-[m
[31m-            <TextView[m
[31m-                android:id="@+id/txtUsername"[m
[31m-                android:layout_width="wrap_content"[m
[31m-                android:layout_height="wrap_content"[m
[31m-                android:inputType="textPersonName"[m
[31m-                android:layout_marginTop="7dp"[m
[31m-                android:textColor="#424242"[m
[31m-                android:text="rihanna"[m
[31m-                android:textSize="18sp"[m
[31m-                android:layout_below="@id/txtName"[m
[31m-                android:layout_alignStart="@id/imgProfilePic"/>[m
[31m-[m
[31m-            <ImageView[m
[31m-                android:id="@+id/imgUsernameIcon"[m
[31m-                android:layout_width="wrap_content"[m
[31m-                android:layout_height="wrap_content"[m
[31m-                android:layout_marginTop="8dp"[m
[31m-                android:layout_marginEnd="16dp"[m
[31m-                android:layout_toLeftOf="@id/txtUsername"[m
[31m-                android:layout_below="@id/txtName"[m
[31m-                app:srcCompat="@drawable/symbol_at" />[m
[31m-[m
[31m-            <TextView[m
[31m-                android:id="@+id/txtGender"[m
[31m-                android:layout_width="wrap_content"[m
[31m-                android:layout_height="wrap_content"[m
[31m-                android:inputType="textPersonName"[m
[31m-                android:textColor="@color/color_text"[m
[31m-                android:textSize="18sp"[m
[31m-                android:text="female"[m
[31m-                android:layout_alignTop="@+id/imgGenderIcon"[m
[31m-                android:layout_alignStart="@+id/txtUsername" />[m
[31m-[m
[31m-            <ImageView[m
[31m-                android:id="@+id/imgGenderIcon"[m
[31m-                android:layout_width="wrap_content"[m
[31m-                android:layout_height="wrap_content"[m
[31m-                android:layout_below="@+id/txtUsername"[m
[31m-                android:layout_marginTop="8dp"[m
[31m-                app:srcCompat="@drawable/fui_ic_facebook_white_22dp"[m
[31m-                android:layout_alignStart="@+id/imgUsernameIcon" />[m
[31m-[m
[31m-            <TextView[m
[31m-                android:id="@+id/txtPoint"[m
[31m-                android:layout_width="wrap_content"[m
                 android:layout_height="wrap_content"[m
[31m-                android:layout_marginTop="3dp"[m
[31m-                android:textSize="18sp"[m
[31m-                android:textColor="@color/color_text"[m
[31m-                android:text="280 points"[m
[31m-                android:layout_alignTop="@+id/imgPointIcon"[m
[31m-                android:layout_alignStart="@+id/txtUsername" />[m
[31m-[m
[31m-            <ImageView[m
[31m-                android:id="@+id/imgPointIcon"[m
[31m-                android:layout_width="wrap_content"[m
[31m-                android:layout_height="wrap_content"[m
[31m-                android:layout_below="@+id/imgGenderIcon"[m
[31m-                android:layout_marginTop="8dp"[m
[31m-                app:srcCompat="@android:drawable/btn_star_big_on"[m
[31m-                android:layout_alignStart="@+id/imgUsernameIcon" />[m
[31m-[m
[31m-            <View[m
[31m-                android:id="@+id/viewLine"[m
[31m-                android:layout_width="match_parent"[m
[31m-                android:layout_height="1dp"[m
[31m-                android:layout_below="@+id/imgPointIcon"[m
[31m-                android:layout_marginTop="10dp"[m
[31m-                android:background="@color/colorPrimaryDark"/>[m
[31m-        </RelativeLayout>[m
[31m-    </android.support.design.widget.CollapsingToolbarLayout>[m
[31m-[m
[31m-    <android.support.design.widget.TabLayout[m
[31m-        android:id="@+id/tabs"[m
[31m-        android:layout_width="match_parent"[m
[31m-        android:layout_height="wrap_content"[m
[31m-        android:background="#9BC996"[m
[31m-        app:tabGravity="fill"[m
[31m-        app:tabMode="fixed"/>[m
[32m+[m[32m                android:background="#ffffff"[m
[32m+[m[32m                app:layout_collapseMode="parallax">[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgCover"[m
[32m+[m[32m                    android:layout_width="match_parent"[m
[32m+[m[32m                    android:layout_height="100dp"[m
[32m+[m[32m                    android:layout_weight="1"[m
[32m+[m[32m                    android:scaleType="centerCrop"[m
[32m+[m[32m                    app:srcCompat="@drawable/bg_one" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgProfilePic"[m
[32m+[m[32m                    android:layout_width="160dp"[m
[32m+[m[32m                    android:layout_height="160dp"[m
[32m+[m[32m                    android:layout_alignParentTop="true"[m
[32m+[m[32m                    android:layout_centerHorizontal="true"[m
[32m+[m[32m                    android:background="@android:drawable/gallery_thumb"[m
[32m+[m[32m                    app:srcCompat="@drawable/dspc"[m
[32m+[m[32m                    android:padding="3dp"[m
[32m+[m[32m                    android:layout_marginTop="30dp"[m
[32m+[m[32m                    android:scaleType="centerCrop"/>[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/txtName"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_below="@+id/imgProfilePic"[m
[32m+[m[32m                    android:layout_centerHorizontal="true"[m
[32m+[m[32m                    android:layout_marginTop="10dp"[m
[32m+[m[32m                    android:text="name"[m
[32m+[m[32m                    android:textColor="@color/color_text"[m
[32m+[m[32m                    android:textSize="24sp"[m
[32m+[m[32m                    android:textStyle="normal|bold" />[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/txtUsername"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_alignStart="@+id/imgProfilePic"[m
[32m+[m[32m                    android:layout_below="@+id/txtName"[m
[32m+[m[32m                    android:inputType="textPersonName"[m
[32m+[m[32m                    android:text="username"[m
[32m+[m[32m                    android:textColor="#424242"[m
[32m+[m[32m                    android:textSize="18sp" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgUsernameIcon"[m
[32m+[m[32m                    android:layout_width="18dp"[m
[32m+[m[32m                    android:layout_height="18dp"[m
[32m+[m[32m                    android:layout_below="@id/txtName"[m
[32m+[m[32m                    android:layout_marginEnd="8dp"[m
[32m+[m[32m                    android:layout_marginTop="5dp"[m
[32m+[m[32m                    android:layout_toLeftOf="@id/txtUsername"[m
[32m+[m[32m                    app:srcCompat="@drawable/symbol_at" />[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/txtGender"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_alignStart="@+id/imgProfilePic"[m
[32m+[m[32m                    android:layout_below="@+id/txtUsername"[m
[32m+[m[32m                    android:inputType="textPersonName"[m
[32m+[m[32m                    android:text="gender"[m
[32m+[m[32m                    android:textColor="@color/color_text"[m
[32m+[m[32m                    android:textSize="18sp" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgGenderIcon"[m
[32m+[m[32m                    android:layout_width="18dp"[m
[32m+[m[32m                    android:layout_height="18dp"[m
[32m+[m[32m                    android:layout_alignStart="@+id/imgUsernameIcon"[m
[32m+[m[32m                    android:layout_below="@+id/txtUsername"[m
[32m+[m[32m                    android:layout_marginTop="5dp"[m
[32m+[m[32m                    app:srcCompat="@drawable/fui_ic_facebook_white_22dp" />[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/txtPoint"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_alignStart="@+id/imgProfilePic"[m
[32m+[m[32m                    android:layout_below="@+id/txtGender"[m
[32m+[m[32m                    android:text="0 points"[m
[32m+[m[32m                    android:textColor="@color/color_text"[m
[32m+[m[32m                    android:textSize="18sp" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgPointIcon"[m
[32m+[m[32m                    android:layout_width="18dp"[m
[32m+[m[32m                    android:layout_height="18dp"[m
[32m+[m[32m                    android:layout_alignStart="@+id/imgUsernameIcon"[m
[32m+[m[32m                    android:layout_below="@+id/imgGenderIcon"[m
[32m+[m[32m                    android:layout_marginTop="5dp"[m
[32m+[m[32m                    app:srcCompat="@android:drawable/btn_star_big_on" />[m
[32m+[m
[32m+[m[32m                <View[m
[32m+[m[32m                    android:id="@+id/viewLine"[m
[32m+[m[32m                    android:layout_width="match_parent"[m
[32m+[m[32m                    android:layout_height="1dp"[m
[32m+[m[32m                    android:layout_below="@+id/imgPointIcon"[m
[32m+[m[32m                    android:layout_marginTop="10dp"[m
[32m+[m[32m                    android:background="@color/colorPrimaryDark" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgEdit"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:tint="@color/colorAccent"[m
[32m+[m[32m                    android:layout_alignParentEnd="true"[m
[32m+[m[32m                    android:layout_alignParentTop="true"[m
[32m+[m[32m                    app:srcCompat="@android:drawable/ic_menu_edit" />[m
[32m+[m
[32m+[m
[32m+[m[32m            </RelativeLayout>[m
[32m+[m[32m        </android.support.design.widget.CollapsingToolbarLayout>[m
[32m+[m
[32m+[m[32m        <android.support.design.widget.TabLayout[m
[32m+[m[32m            android:id="@+id/tabs"[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="wrap_content"[m
[32m+[m[32m            android:background="#9BC996"[m
[32m+[m[32m            app:tabGravity="fill"[m
[32m+[m[32m            app:tabMode="fixed" />[m
 [m
[31m-</android.support.design.widget.AppBarLayout>[m
[32m+[m[32m    </android.support.design.widget.AppBarLayout>[m
 [m
 <android.support.v4.view.ViewPager[m
     android:id="@+id/viewpager"[m
[1mdiff --git a/app/src/main/res/layout/activity_profile_edit.xml b/app/src/main/res/layout/activity_profile_edit.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..53f3696[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/activity_profile_edit.xml[m
[36m@@ -0,0 +1,190 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"[m
[32m+[m[32m    xmlns:app="http://schemas.android.com/apk/res-auto"[m
[32m+[m[32m    xmlns:tools="http://schemas.android.com/tools"[m
[32m+[m[32m    android:layout_width="match_parent"[m
[32m+[m[32m    android:layout_height="match_parent"[m
[32m+[m[32m    android:paddingLeft="@dimen/activity_horizontal_margin"[m
[32m+[m[32m    android:paddingRight="@dimen/activity_horizontal_margin"[m
[32m+[m[32m    android:background="#dadeda"[m
[32m+[m[32m    tools:context=".ProfileEditActivity">[m
[32m+[m
[32m+[m
[32m+[m[32m    <android.support.v4.widget.NestedScrollView[m
[32m+[m[32m        android:layout_width="match_parent"[m
[32m+[m[32m        android:layout_height="match_parent">[m
[32m+[m
[32m+[m[32m        <LinearLayout[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="match_parent"[m
[32m+[m[32m            android:orientation="vertical">[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:layout_width="wrap_content"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:text="Display picture"[m
[32m+[m[32m                android:textStyle="bold"[m
[32m+[m[32m                android:layout_marginTop="6dp"[m
[32m+[m[32m                android:textColor="@color/colorAccent"/>[m
[32m+[m
[32m+[m[32m            <android.support.v7.widget.CardView[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                app:cardCornerRadius="6dp">[m
[32m+[m
[32m+[m[32m                <FrameLayout[m
[32m+[m[32m                    android:id="@+id/frmPix"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_gravity="center"[m
[32m+[m[32m                    android:background="#dadeda"[m
[32m+[m[32m                    android:layout_margin="6dp">[m
[32m+[m
[32m+[m[32m                    <ImageView[m
[32m+[m[32m                        android:id="@+id/imgProfilePic"[m
[32m+[m[32m                        android:layout_width="200dp"[m
[32m+[m[32m                        android:layout_height="200dp"[m
[32m+[m[32m                        android:src="@drawable/dspc" />[m
[32m+[m
[32m+[m[32m                    <ImageView[m
[32m+[m[32m                        android:id="@+id/imgAdd"[m
[32m+[m[32m                        android:layout_width="40dp"[m
[32m+[m[32m                        android:layout_height="40dp"[m
[32m+[m[32m                        android:background="@android:drawable/gallery_thumb"[m
[32m+[m[32m                        android:src="@android:drawable/ic_input_add" />[m
[32m+[m
[32m+[m[32m                </FrameLayout>[m
[32m+[m[32m            </android.support.v7.widget.CardView>[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:layout_width="wrap_content"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:text="Personal info"[m
[32m+[m[32m                android:textStyle="bold"[m
[32m+[m[32m                android:layout_marginTop="16dp"[m
[32m+[m[32m                android:textColor="@color/colorAccent"/>[m
[32m+[m
[32m+[m[32m            <android.support.v7.widget.CardView[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:padding="4dp"[m
[32m+[m[32m                app:cardCornerRadius="6dp">[m
[32m+[m
[32m+[m[32m                <LinearLayout[m
[32m+[m[32m                    android:layout_width="match_parent"[m
[32m+[m[32m                    android:layout_height="match_parent"[m
[32m+[m[32m                    android:orientation="vertical">[m
[32m+[m
[32m+[m[32m                    <EditText[m
[32m+[m[32m                        android:id="@+id/edtFirstName"[m
[32m+[m[32m                        android:layout_width="match_parent"[m
[32m+[m[32m                        android:layout_height="wrap_content"[m
[32m+[m[32m                        android:background="@android:drawable/editbox_background_normal"[m
[32m+[m[32m                        android:ems="10"[m
[32m+[m[32m                        android:hint="first name"[m
[32m+[m[32m                        android:inputType="textPersonName" />[m
[32m+[m
[32m+[m[32m                    <EditText[m
[32m+[m[32m                        android:id="@+id/edtLastName"[m
[32m+[m[32m                        android:layout_width="match_parent"[m
[32m+[m[32m                        android:layout_height="wrap_content"[m
[32m+[m[32m                        android:background="@android:drawable/editbox_background_normal"[m
[32m+[m[32m                        android:ems="10"[m
[32m+[m[32m                        android:hint="last name"[m
[32m+[m[32m                        android:inputType="textPersonName" />[m
[32m+[m
[32m+[m[32m                    <EditText[m
[32m+[m[32m                        android:id="@+id/edtUsername"[m
[32m+[m[32m                        android:layout_width="match_parent"[m
[32m+[m[32m                        android:layout_height="wrap_content"[m
[32m+[m[32m                        android:background="@android:drawable/editbox_background_normal"[m
[32m+[m[32m                        android:ems="10"[m
[32m+[m[32m                        android:hint="username"[m
[32m+[m[32m                        android:inputType="textPersonName" />[m
[32m+[m
[32m+[m[32m                    <RadioGroup[m
[32m+[m[32m                        android:layout_width="match_parent"[m
[32m+[m[32m                        android:layout_height="wrap_content"[m
[32m+[m[32m                        android:orientation="horizontal">[m
[32m+[m
[32m+[m[32m                        <RadioButton[m
[32m+[m[32m                            android:id="@+id/rdbMale"[m
[32m+[m[32m                            android:layout_width="wrap_content"[m
[32m+[m[32m                            android:layout_height="wrap_content"[m
[32m+[m[32m                            android:text="Male" />[m
[32m+[m
[32m+[m[32m                        <RadioButton[m
[32m+[m[32m                            android:id="@+id/rdbFemale"[m
[32m+[m[32m                            android:layout_width="wrap_content"[m
[32m+[m[32m                            android:layout_height="wrap_content"[m
[32m+[m[32m                            android:layout_marginStart="5dp"[m
[32m+[m[32m                            android:text="Female" />[m
[32m+[m[32m                    </RadioGroup>[m
[32m+[m[32m                </LinearLayout>[m
[32m+[m
[32m+[m[32m            </android.support.v7.widget.CardView>[m
[32m+[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:layout_width="wrap_content"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:text="Contact and payment details"[m
[32m+[m[32m                android:textStyle="bold"[m
[32m+[m[32m                android:layout_marginTop="16dp"[m
[32m+[m[32m                android:textColor="@color/colorAccent"/>[m
[32m+[m
[32m+[m[32m            <android.support.v7.widget.CardView[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                app:cardCornerRadius="6dp">[m
[32m+[m
[32m+[m[32m                <LinearLayout[m
[32m+[m[32m                    android:layout_width="match_parent"[m
[32m+[m[32m                    android:layout_height="match_parent"[m
[32m+[m[32m                    android:orientation="vertical">[m
[32m+[m
[32m+[m[32m                    <EditText[m
[32m+[m[32m                        android:id="@+id/edtPhone"[m
[32m+[m[32m                        android:layout_width="match_parent"[m
[32m+[m[32m                        android:layout_height="wrap_content"[m
[32m+[m[32m                        android:background="@android:drawable/editbox_background_normal"[m
[32m+[m[32m                        android:ems="10"[m
[32m+[m[32m                        android:hint="phone number 1"[m
[32m+[m[32m                        android:inputType="textPersonName" />[m
[32m+[m
[32m+[m
[32m+[m[32m                    <EditText[m
[32m+[m[32m                        android:id="@+id/edtEmail"[m
[32m+[m[32m                        android:layout_width="match_parent"[m
[32m+[m[32m                        android:layout_height="wrap_content"[m
[32m+[m[32m                        android:background="@android:drawable/editbox_background_normal"[m
[32m+[m[32m                        android:ems="10"[m
[32m+[m[32m                        android:hint="email"[m
[32m+[m[32m                        android:enabled="false"[m
[32m+[m[32m                        android:inputType="textPersonName" />[m
[32m+[m
[32m+[m[32m                    <EditText[m
[32m+[m[32m                        android:id="@+id/edtAccountDetails"[m
[32m+[m[32m                        android:layout_width="match_parent"[m
[32m+[m[32m                        android:layout_height="wrap_content"[m
[32m+[m[32m                        android:background="@android:drawable/editbox_background_normal"[m
[32m+[m[32m                        android:ems="10"[m
[32m+[m[32m                        android:hint="account details:\n(account name, number, bank name and country)"[m
[32m+[m[32m                        android:inputType="textPersonName" />[m
[32m+[m
[32m+[m[32m                </LinearLayout>[m
[32m+[m[32m            </android.support.v7.widget.CardView>[m
[32m+[m
[32m+[m[32m        </LinearLayout>[m
[32m+[m[32m    </android.support.v4.widget.NestedScrollView>[m
[32m+[m
[32m+[m[32m    <android.support.design.widget.FloatingActionButton[m
[32m+[m[32m        android:id="@+id/fltButton"[m
[32m+[m[32m        android:layout_width="match_parent"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:layout_alignParentBottom="true"[m
[32m+[m[32m        android:layout_alignParentEnd="true"[m
[32m+[m[32m        android:layout_marginBottom="39dp"[m
[32m+[m[32m        android:clickable="true"[m
[32m+[m[32m        app:srcCompat="@android:drawable/ic_menu_save" />[m
[32m+[m[32m</RelativeLayout>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/layout/activity_rooms_page.xml b/app/src/main/res/layout/activity_rooms_page.xml[m
[1mindex 5f4d125..9d6fbc4 100644[m
[1m--- a/app/src/main/res/layout/activity_rooms_page.xml[m
[1m+++ b/app/src/main/res/layout/activity_rooms_page.xml[m
[36m@@ -13,6 +13,7 @@[m
     android:layout_width="match_parent"[m
     android:layout_height="wrap_content"[m
     android:fitsSystemWindows="true">[m
[32m+[m
     <android.support.design.widget.CollapsingToolbarLayout[m
         android:id="@+id/CollapsingToolbar"[m
         android:layout_width="match_parent"[m
[36m@@ -36,6 +37,14 @@[m
                 android:layout_width="match_parent"[m
                 android:layout_height="match_parent">[m
 [m
[32m+[m[32m                <Button[m
[32m+[m[32m                    android:id="@+id/btnAbout"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_margin="4dp"[m
[32m+[m[32m                    android:theme="@style/bale"[m
[32m+[m[32m                    android:text="About TipZone"/>[m
[32m+[m
             </RelativeLayout>[m
 [m
         </FrameLayout>[m
[36m@@ -51,12 +60,13 @@[m
         android:layout_width="match_parent"[m
         android:layout_height="match_parent"[m
         android:orientation="vertical">[m
[32m+[m
         <LinearLayout[m
             android:layout_width="match_parent"[m
             android:layout_height="wrap_content"[m
             android:gravity="center_horizontal">[m
             <android.support.v7.widget.CardView[m
[31m-                android:id="@+id/crdProfile"[m
[32m+[m[32m                android:id="@+id/crdRoomOne"[m
                 android:layout_width="160dp"[m
                 android:layout_height="180dp"[m
                 android:layout_margin="10dp"[m
[36m@@ -80,7 +90,7 @@[m
                         android:layout_height="wrap_content"[m
                         android:textSize="18sp"[m
                         android:textStyle="bold|normal"[m
[31m-                        android:text="10-50 odds"[m
[32m+[m[32m                        android:text="General"[m
                         android:layout_marginTop="10dp"/>[m
                     <View[m
                         android:layout_width="match_parent"[m
[36m@@ -92,12 +102,12 @@[m
                         android:layout_width="wrap_content"[m
                         android:layout_height="wrap_content"[m
                         android:textSize="14sp"[m
[31m-                        android:text="View/edit your profile"/>[m
[32m+[m[32m                        android:text="General discussion"/>[m
                 </LinearLayout>[m
             </android.support.v7.widget.CardView>[m
 [m
             <android.support.v7.widget.CardView[m
[31m-                android:id="@+id/crdForum"[m
[32m+[m[32m                android:id="@+id/crdRoomTwo"[m
                 android:layout_width="160dp"[m
                 android:layout_height="180dp"[m
                 android:layout_margin="10dp"[m
[36m@@ -121,7 +131,7 @@[m
                         android:layout_height="wrap_content"[m
                         android:textSize="18sp"[m
                         android:textStyle="bold|normal"[m
[31m-                        android:text="51-200 odds"[m
[32m+[m[32m                        android:text="3-10 odds"[m
                         android:layout_marginTop="10dp"/>[m
                     <View[m
                         android:layout_width="match_parent"[m
[36m@@ -133,17 +143,24 @@[m
                         android:layout_width="wrap_content"[m
                         android:layout_height="wrap_content"[m
                         android:textSize="14sp"[m
[31m-                        android:text="World of tipsters"/>[m
[32m+[m[32m                        android:text="Normal"/>[m
                 </LinearLayout>[m
             </android.support.v7.widget.CardView>[m
 [m
         </LinearLayout>[m
[32m+[m
[32m+[m[32m        <View[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="1.5dp"[m
[32m+[m[32m            android:layout_below="@+id/imgPointIcon"[m
[32m+[m[32m            android:background="@color/colorPrimaryDark" />[m
[32m+[m
         <LinearLayout[m
             android:layout_width="match_parent"[m
             android:layout_height="wrap_content"[m
             android:gravity="center_horizontal">[m
             <android.support.v7.widget.CardView[m
[31m-                android:id="@+id/crdFree"[m
[32m+[m[32m                android:id="@+id/crdRoomThree"[m
                 android:layout_width="160dp"[m
                 android:layout_height="180dp"[m
                 android:layout_margin="10dp"[m
[36m@@ -167,7 +184,7 @@[m
                         android:layout_height="wrap_content"[m
                         android:textSize="18sp"[m
                         android:textStyle="bold|normal"[m
[31m-                        android:text="201-500 odds"[m
[32m+[m[32m                        android:text="11-50 odds"[m
                         android:layout_marginTop="10dp"/>[m
                     <View[m
                         android:layout_width="match_parent"[m
[36m@@ -179,12 +196,12 @@[m
                         android:layout_width="wrap_content"[m
                         android:layout_height="wrap_content"[m
                         android:textSize="14sp"[m
[31m-                        android:text="Free daily tips"/>[m
[32m+[m[32m                        android:text="Fantastic"/>[m
                 </LinearLayout>[m
             </android.support.v7.widget.CardView>[m
 [m
             <android.support.v7.widget.CardView[m
[31m-                android:id="@+id/crdVIP"[m
[32m+[m[32m                android:id="@+id/crdRoomFour"[m
                 android:layout_width="160dp"[m
                 android:layout_height="180dp"[m
                 android:layout_margin="10dp"[m
[36m@@ -208,7 +225,7 @@[m
                         android:layout_height="wrap_content"[m
                         android:textSize="18sp"[m
                         android:textStyle="bold|normal"[m
[31m-                        android:text="500+"[m
[32m+[m[32m                        android:text="51-150 odds"[m
                         android:layout_marginTop="10dp"/>[m
                     <View[m
                         android:layout_width="match_parent"[m
[36m@@ -220,7 +237,7 @@[m
                         android:layout_width="wrap_content"[m
                         android:layout_height="wrap_content"[m
                         android:textSize="14sp"[m
[31m-                        android:text="Sure tips + draws"/>[m
[32m+[m[32m                        android:text="Expert"/>[m
                 </LinearLayout>[m
             </android.support.v7.widget.CardView>[m
 [m
[36m@@ -230,7 +247,7 @@[m
             android:layout_height="wrap_content"[m
             android:gravity="center_horizontal">[m
             <android.support.v7.widget.CardView[m
[31m-                android:id="@+id/crdNews"[m
[32m+[m[32m                android:id="@+id/crdRoomFive"[m
                 android:layout_width="160dp"[m
                 android:layout_height="180dp"[m
                 android:layout_margin="10dp"[m
[36m@@ -254,7 +271,7 @@[m
                         android:layout_height="wrap_content"[m
                         android:textSize="18sp"[m
                         android:textStyle="bold|normal"[m
[31m-                        android:text="Draws/Pools"[m
[32m+[m[32m                        android:text="151-500 odds"[m
                         android:layout_marginTop="10dp"/>[m
                     <View[m
                         android:layout_width="match_parent"[m
[36m@@ -266,12 +283,12 @@[m
                         android:layout_width="wrap_content"[m
                         android:layout_height="wrap_content"[m
                         android:textSize="14sp"[m
[31m-                        android:text="Sports news &amp; analysis"/>[m
[32m+[m[32m                        android:text="Pro"/>[m
                 </LinearLayout>[m
             </android.support.v7.widget.CardView>[m
 [m
             <android.support.v7.widget.CardView[m
[31m-                android:id="@+id/crdPayment"[m
[32m+[m[32m                android:id="@+id/crdRoomSix"[m
                 android:layout_width="160dp"[m
                 android:layout_height="180dp"[m
                 android:layout_margin="10dp"[m
[36m@@ -295,7 +312,58 @@[m
                         android:layout_height="wrap_content"[m
                         android:textSize="18sp"[m
                         android:textStyle="bold|normal"[m
[31m-                        android:text="General"[m
[32m+[m[32m                        android:text="500+"[m
[32m+[m[32m                        android:layout_marginTop="10dp"/>[m
[32m+[m[32m                    <View[m
[32m+[m[32m                        android:layout_width="match_parent"[m
[32m+[m[32m                        android:layout_height="1dp"[m
[32m+[m[32m                        android:layout_margin="10dp"[m
[32m+[m[32m                        android:background="#37474f"/>[m
[32m+[m
[32m+[m[32m                    <TextView[m
[32m+[m[32m                        android:layout_width="wrap_content"[m
[32m+[m[32m                        android:layout_height="wrap_content"[m
[32m+[m[32m                        android:textSize="14sp"[m
[32m+[m[32m                        android:text="Genius"/>[m
[32m+[m[32m                </LinearLayout>[m
[32m+[m[32m            </android.support.v7.widget.CardView>[m
[32m+[m
[32m+[m[32m        </LinearLayout>[m
[32m+[m[32m        <View[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="1.5dp"[m
[32m+[m[32m            android:layout_below="@+id/imgPointIcon"[m
[32m+[m[32m            android:background="@color/colorPrimaryDark" />[m
[32m+[m[32m        <LinearLayout[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="wrap_content"[m
[32m+[m[32m            android:gravity="center_horizontal">[m
[32m+[m[32m            <android.support.v7.widget.CardView[m
[32m+[m[32m                android:id="@+id/crdRoomSeven"[m
[32m+[m[32m                android:layout_width="160dp"[m
[32m+[m[32m                android:layout_height="180dp"[m
[32m+[m[32m                android:layout_margin="10dp"[m
[32m+[m[32m                android:elevation="6dp"[m
[32m+[m[32m                android:foreground="?android:attr/selectableItemBackground"[m
[32m+[m[32m                android:clickable="true"[m
[32m+[m[32m                card_view:cardUseCompatPadding="true">[m
[32m+[m[32m                <LinearLayout[m
[32m+[m[32m                    android:layout_width="match_parent"[m
[32m+[m[32m                    android:layout_height="match_parent"[m
[32m+[m[32m                    android:orientation="vertical"[m
[32m+[m[32m                    android:gravity="center">[m
[32m+[m[32m                    <ImageView[m
[32m+[m[32m                        android:layout_width="64dp"[m
[32m+[m[32m                        android:layout_height="64dp"[m
[32m+[m[32m                        android:src="@drawable/forum"[m
[32m+[m[32m                        android:padding="6dp"[m
[32m+[m[32m                        android:background="@color/bckTint"/>[m
[32m+[m[32m                    <TextView[m
[32m+[m[32m                        android:layout_width="wrap_content"[m
[32m+[m[32m                        android:layout_height="wrap_content"[m
[32m+[m[32m                        android:textSize="18sp"[m
[32m+[m[32m                        android:textStyle="bold|normal"[m
[32m+[m[32m                        android:text="Draws"[m
                         android:layout_marginTop="10dp"/>[m
                     <View[m
                         android:layout_width="match_parent"[m
[36m@@ -307,7 +375,7 @@[m
                         android:layout_width="wrap_content"[m
                         android:layout_height="wrap_content"[m
                         android:textSize="14sp"[m
[31m-                        android:text="Subscription details"/>[m
[32m+[m[32m                        android:text="Pools &amp; draws"/>[m
                 </LinearLayout>[m
             </android.support.v7.widget.CardView>[m
 [m
[1mdiff --git a/app/src/main/res/layout/ads_slider.xml b/app/src/main/res/layout/ads_slider.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..c28e118[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/ads_slider.xml[m
[36m@@ -0,0 +1,25 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<FrameLayout[m
[32m+[m[32m    xmlns:android="http://schemas.android.com/apk/res/android"[m
[32m+[m[32m    android:layout_width="match_parent"[m
[32m+[m[32m    android:layout_height="130dp">[m
[32m+[m
[32m+[m[32m    <ImageView[m
[32m+[m[32m        android:id="@+id/adsImage"[m
[32m+[m[32m        android:layout_width="match_parent"[m
[32m+[m[32m        android:layout_height="match_parent"[m
[32m+[m[32m        android:adjustViewBounds="true"[m
[32m+[m[32m        android:layout_gravity="center"[m
[32m+[m[32m        android:scaleType="centerCrop"/>[m
[32m+[m
[32m+[m[32m    <Button[m
[32m+[m[32m        android:id="@+id/adsLinkButton"[m
[32m+[m[32m        android:layout_width="wrap_content"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:theme="@style/bale"[m
[32m+[m[32m        android:layout_margin="2dp"[m
[32m+[m[32m        android:text="Enter"[m
[32m+[m[32m        android:layout_gravity="bottom|end"[m
[32m+[m[32m        android:textColor="@android:color/white" />[m
[32m+[m
[32m+[m[32m</FrameLayout>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/layout/fragment_recentpost.xml b/app/src/main/res/layout/fragment_recentpost.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..3934720[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/fragment_recentpost.xml[m
[36m@@ -0,0 +1,34 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"[m
[32m+[m[32mxmlns:app="http://schemas.android.com/apk/res-auto"[m
[32m+[m[32mxmlns:tools="http://schemas.android.com/tools"[m
[32m+[m[32mandroid:id="@+id/activity_main"[m
[32m+[m[32mandroid:layout_width="match_parent"[m
[32m+[m[32mandroid:layout_height="match_parent"[m
[32m+[m[32mandroid:background="#EEEEEE"[m
[32m+[m[32mandroid:paddingBottom="@dimen/activity_vertical_margin"[m
[32m+[m[32mandroid:paddingLeft="@dimen/activity_horizontal_margin"[m
[32m+[m[32mandroid:paddingRight="@dimen/activity_horizontal_margin"[m
[32m+[m[32mandroid:paddingTop="@dimen/activity_vertical_margin"[m
[32m+[m[32mtools:context=".RecentpostFragment">[m
[32m+[m
[32m+[m[32m<android.support.v7.widget.RecyclerView[m
[32m+[m[32m    android:id="@+id/rvRecent"[m
[32m+[m[32m    android:layout_width="match_parent"[m
[32m+[m[32m    android:layout_height="match_parent"[m
[32m+[m[32m    android:scrollbars="none"[m
[32m+[m[32m    app:stackFromEnd="true"/>[m
[32m+[m
[32m+[m[32m<TextView[m
[32m+[m[32m    android:id="@+id/txtNotice"[m
[32m+[m[32m    android:layout_width="wrap_content"[m
[32m+[m[32m    android:layout_height="wrap_content"[m
[32m+[m[32m    android:layout_marginTop="10dp"[m
[32m+[m[32m    android:textSize="18sp"[m
[32m+[m[32m    android:background="@android:drawable/editbox_background_normal"[m
[32m+[m[32m    android:visibility="gone"[m
[32m+[m[32m    android:layout_centerHorizontal="true"[m
[32m+[m[32m    android:layout_below="@id/rvRecent"[m
[32m+[m[32m    android:text="No recent post."/>[m
[32m+[m[32m</RelativeLayout>[m
[32m+[m
[1mdiff --git a/app/src/main/res/layout/fragment_won_games.xml b/app/src/main/res/layout/fragment_won_games.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..ea73740[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/fragment_won_games.xml[m
[36m@@ -0,0 +1,36 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"[m
[32m+[m[32mxmlns:app="http://schemas.android.com/apk/res-auto"[m
[32m+[m[32mxmlns:tools="http://schemas.android.com/tools"[m
[32m+[m[32mandroid:id="@+id/activity_main"[m
[32m+[m[32mandroid:layout_width="match_parent"[m
[32m+[m[32mandroid:layout_height="match_parent"[m
[32m+[m[32mandroid:background="#EEEEEE"[m
[32m+[m[32mandroid:paddingBottom="@dimen/activity_vertical_margin"[m
[32m+[m[32mandroid:paddingLeft="@dimen/activity_horizontal_margin"[m
[32m+[m[32mandroid:paddingRight="@dimen/activity_horizontal_margin"[m
[32m+[m[32mandroid:paddingTop="@dimen/activity_vertical_margin"[m
[32m+[m[32mtools:context=".WonGamesFragment">[m
[32m+[m
[32m+[m[32m<android.support.v7.widget.RecyclerView[m
[32m+[m[32m    android:id="@+id/rvRecent"[m
[32m+[m[32m    android:layout_width="match_parent"[m
[32m+[m[32m    android:layout_height="wrap_content"[m
[32m+[m[32m    android:scrollbars="none"[m
[32m+[m[32m    app:stackFromEnd="false"/>[m
[32m+[m
[32m+[m[32m<TextView[m
[32m+[m[32m    android:id="@+id/txtNotice2"[m
[32m+[m[32m    android:layout_width="wrap_content"[m
[32m+[m[32m    android:layout_height="wrap_content"[m
[32m+[m[32m    android:textSize="18sp"[m
[32m+[m[32m    android:background="@android:drawable/editbox_background_normal"[m
[32m+[m[32m    android:visibility="gone"[m
[32m+[m[32m    android:text="No won games yet."[m
[32m+[m[32m    android:layout_below="@+id/rvRecent"[m
[32m+[m[32m    android:layout_centerHorizontal="true"[m
[32m+[m[32m    android:layout_marginTop="10dp" />[m
[32m+[m[32m</RelativeLayout>[m
[32m+[m
[32m+[m
[32m+[m
[1mdiff --git a/app/src/main/res/layout/mssg_in.xml b/app/src/main/res/layout/mssg_in.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..1ecf4cf[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/mssg_in.xml[m
[36m@@ -0,0 +1,138 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"[m
[32m+[m[32m    android:layout_width="match_parent"[m
[32m+[m[32m    android:layout_height="wrap_content"[m
[32m+[m[32m    xmlns:card_view="http://schemas.android.com/tools"[m
[32m+[m[32m    xmlns:app="http://schemas.android.com/apk/res-auto">[m
[32m+[m
[32m+[m
[32m+[m[32m    <android.support.v7.widget.CardView[m
[32m+[m[32m        android:layout_width="wrap_content"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:layout_marginEnd="30dp"[m
[32m+[m[32m        card_view:cardUseCompatPadding="true"[m
[32m+[m[32m        app:cardCornerRadius="5dp"[m
[32m+[m[32m        android:elevation="10dp">[m
[32m+[m
[32m+[m[32m        <LinearLayout[m
[32m+[m[32m            android:id="@+id/rltParent"[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="wrap_content"[m
[32m+[m[32m            android:orientation="vertical">[m
[32m+[m
[32m+[m
[32m+[m[32m            <LinearLayout[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:id="@+id/lnrContr"[m
[32m+[m[32m                android:orientation="horizontal"[m
[32m+[m[32m                android:minWidth="210dp"[m
[32m+[m[32m                android:background="#d8e4e7">[m
[32m+[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/message_user"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_weight="1"[m
[32m+[m[32m                    android:paddingStart="6dp"[m
[32m+[m[32m                    android:textColor="@color/colorAccent"[m
[32m+[m[32m                    android:text="Motherfuker"[m
[32m+[m[32m                    android:textStyle="normal|bold" />[m
[32m+[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgStatus"[m
[32m+[m[32m                    android:layout_width="15dp"[m
[32m+[m[32m                    android:layout_height="15dp"[m
[32m+[m[32m                    android:layout_margin="1dp"[m
[32m+[m[32m                    android:src="@drawable/right"/>[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/message_status"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_marginStart="4dp"[m
[32m+[m[32m                    android:gravity="right"[m
[32m+[m[32m                    android:paddingEnd="6dp"[m
[32m+[m[32m                    android:text="WON"[m
[32m+[m[32m                    android:textColor="#038b1e"[m
[32m+[m[32m                    android:textStyle="normal|bold" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgDropdwon"[m
[32m+[m[32m                    android:layout_width="18dp"[m
[32m+[m[32m                    android:layout_height="18dp"[m
[32m+[m[32m                    android:scaleType="fitXY"[m
[32m+[m[32m                    android:tag="in"[m
[32m+[m[32m                    android:layout_margin="1dp"[m
[32m+[m[32m                    android:src="@drawable/dropdown"/>[m
[32m+[m
[32m+[m[32m            </LinearLayout>[m
[32m+[m
[32m+[m[32m            <View[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="1dp"[m
[32m+[m[32m                android:background="#26abc2"/>[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:id="@+id/message_text"[m
[32m+[m[32m                android:layout_width="wrap_content"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:paddingStart="6dp"[m
[32m+[m[32m                android:paddingEnd="6dp"[m
[32m+[m[32m                android:layout_marginTop="2dp"[m
[32m+[m[32m                android:textIsSelectable="true"[m
[32m+[m[32m                android:autoLink="web"[m
[32m+[m[32m                android:textColorLink="#1564d4"[m
[32m+[m[32m                android:text="Okadjdjd FISTh"[m
[32m+[m[32m                android:textAppearance="@style/TextAppearance.AppCompat.Body1"[m
[32m+[m[32m                android:textSize="16sp" />[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:layout_width="wrap_content"[m
[32m+[m[32m                android:layout_height="0dp"[m
[32m+[m[32m                android:minWidth="200dp"/>[m
[32m+[m
[32m+[m[32m            <LinearLayout[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:id="@+id/lnrContainer"[m
[32m+[m[32m                android:gravity="center_vertical"[m
[32m+[m[32m                android:orientation="horizontal"[m
[32m+[m[32m                android:minWidth="210dp"[m
[32m+[m[32m                android:layout_marginTop="3dp">[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/message_time"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:paddingStart="6dp"[m
[32m+[m[32m                    android:layout_weight="1.5"[m
[32m+[m[32m                    android:text="23-04-2018(01:23am)" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgLikes"[m
[32m+[m[32m                    android:layout_width="18dp"[m
[32m+[m[32m                    android:layout_height="18dp"[m
[32m+[m[32m                    android:layout_margin="1dp"[m
[32m+[m[32m                    android:tag="no_love"[m
[32m+[m[32m                    android:src="@drawable/no_love"/>[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/message_Likes"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:paddingStart="12dp"[m
[32m+[m[32m                    android:paddingEnd="12dp"[m
[32m+[m[32m                    android:text="0"[m
[32m+[m[32m                    android:textStyle="normal|bold" />[m
[32m+[m
[32m+[m
[32m+[m[32m            </LinearLayout>[m
[32m+[m
[32m+[m[32m        </LinearLayout>[m
[32m+[m
[32m+[m
[32m+[m[32m    </android.support.v7.widget.CardView>[m
[32m+[m[32m</RelativeLayout>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/layout/mssg_out.xml b/app/src/main/res/layout/mssg_out.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..148634a[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/mssg_out.xml[m
[36m@@ -0,0 +1,133 @@[m
[32m+[m[32m<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"[m
[32m+[m[32m    android:layout_width="match_parent"[m
[32m+[m[32m    android:layout_height="wrap_content"[m
[32m+[m[32m    xmlns:card_view="http://schemas.android.com/tools"[m
[32m+[m[32m    xmlns:app="http://schemas.android.com/apk/res-auto">[m
[32m+[m
[32m+[m[32m    <android.support.v7.widget.CardView[m
[32m+[m[32m        android:layout_width="wrap_content"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:layout_marginStart="30dp"[m
[32m+[m[32m        android:layout_alignParentEnd="true"[m
[32m+[m[32m        card_view:cardUseCompatPadding="true"[m
[32m+[m[32m        app:cardCornerRadius="5dp"[m
[32m+[m[32m        android:elevation="10dp">[m
[32m+[m
[32m+[m[32m        <LinearLayout[m
[32m+[m[32m            android:id="@+id/rltParent"[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="wrap_content"[m
[32m+[m[32m            android:orientation="vertical">[m
[32m+[m
[32m+[m[32m        <LinearLayout[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="wrap_content"[m
[32m+[m[32m            android:gravity="center_vertical"[m
[32m+[m[32m            android:id="@+id/lnrContr"[m
[32m+[m[32m            android:orientation="horizontal"[m
[32m+[m[32m            android:minWidth="210dp"[m
[32m+[m[32m            android:background="#C4E2B7">[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:id="@+id/message_user"[m
[32m+[m[32m                android:layout_width="wrap_content"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:layout_weight="1"[m
[32m+[m[32m                android:paddingLeft="6dp"[m
[32m+[m[32m                android:textColor="#990fb9"[m
[32m+[m[32m                android:text="Motherfuker"[m
[32m+[m[32m                android:textStyle="normal|bold" />[m
[32m+[m
[32m+[m
[32m+[m[32m            <ImageView[m
[32m+[m[32m                android:id="@+id/imgStatus"[m
[32m+[m[32m                android:layout_width="15dp"[m
[32m+[m[32m                android:layout_height="15dp"[m
[32m+[m[32m                android:layout_margin="1dp"[m
[32m+[m[32m                android:src="@drawable/right"/>[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:id="@+id/message_status"[m
[32m+[m[32m                android:layout_width="wrap_content"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:layout_marginStart="4dp"[m
[32m+[m[32m                android:gravity="right"[m
[32m+[m[32m                android:paddingRight="6dp"[m
[32m+[m[32m                android:text="WON"[m
[32m+[m[32m                android:textColor="#038b1e"[m
[32m+[m[32m                android:textStyle="normal|bold" />[m
[32m+[m
[32m+[m[32m            <ImageView[m
[32m+[m[32m                android:id="@+id/imgDropdwon"[m
[32m+[m[32m                android:layout_width="18dp"[m
[32m+[m[32m                android:layout_height="18dp"[m
[32m+[m[32m                android:scaleType="fitXY"[m
[32m+[m[32m                android:tag="out"[m
[32m+[m[32m                android:layout_margin="1dp"[m
[32m+[m[32m                android:src="@drawable/dropdown"/>[m
[32m+[m[32m        </LinearLayout>[m
[32m+[m
[32m+[m[32m            <View[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="1dp"[m
[32m+[m[32m                android:background="#43A047"/>[m
[32m+[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                    android:id="@+id/message_text"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:paddingLeft="6dp"[m
[32m+[m[32m                    android:paddingTop="2dp"[m
[32m+[m[32m                    android:textIsSelectable="true"[m
[32m+[m[32m                android:text="Okadjdjd FISTh live line is not a love nnn.[m
[32m+[m[32mI love you real good i jgf jj is wich his the origi I love so much. Give me my accolades"[m
[32m+[m[32m                    android:autoLink="web"[m
[32m+[m[32m                    android:textColorLink="#1564d4"[m
[32m+[m[32m                    android:textAppearance="@style/TextAppearance.AppCompat.Body1"[m
[32m+[m[32m                    android:textSize="16sp" />[m
[32m+[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:layout_width="wrap_content"[m
[32m+[m[32m                android:layout_height="0dp"[m
[32m+[m[32m                android:minWidth="200dp"/>[m
[32m+[m
[32m+[m[32m            <LinearLayout[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:id="@+id/lnrContainer"[m
[32m+[m[32m                android:gravity="center_vertical"[m
[32m+[m[32m                android:paddingTop="2dp">[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/message_time"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_weight="1.5"[m
[32m+[m[32m                    android:paddingLeft="6dp"[m
[32m+[m[32m                    android:text="23-04-2018(01:23am)" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgLikes"[m
[32m+[m[32m                    android:layout_width="18dp"[m
[32m+[m[32m                    android:layout_height="18dp"[m
[32m+[m[32m                    android:layout_margin="1dp"[m
[32m+[m[32m                    android:tag="no_love"[m
[32m+[m[32m                    android:src="@drawable/no_love"/>[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/message_Likes"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:paddingLeft="12dp"[m
[32m+[m[32m                    android:paddingRight="12dp"[m
[32m+[m[32m                    android:text="0"[m
[32m+[m[32m                    android:textStyle="normal|bold" />[m
[32m+[m
[32m+[m[32m            </LinearLayout>[m
[32m+[m
[32m+[m[32m        </LinearLayout>[m
[32m+[m[32m    </android.support.v7.widget.CardView>[m
[32m+[m
[32m+[m[32m</RelativeLayout>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/layout/posts.xml b/app/src/main/res/layout/posts.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..d9b95c6[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/posts.xml[m
[36m@@ -0,0 +1,129 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"[m
[32m+[m[32m    android:layout_width="match_parent"[m
[32m+[m[32m    android:layout_height="wrap_content"[m
[32m+[m[32m    android:layout_marginBottom="10dp"[m
[32m+[m[32m    xmlns:card_view="http://schemas.android.com/tools"[m
[32m+[m[32m    xmlns:app="http://schemas.android.com/apk/res-auto">[m
[32m+[m
[32m+[m
[32m+[m[32m    <android.support.v7.widget.CardView[m
[32m+[m[32m        android:layout_width="wrap_content"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        card_view:cardUseCompatPadding="true"[m
[32m+[m[32m        app:cardCornerRadius="5dp"[m
[32m+[m[32m        android:elevation="10dp">[m
[32m+[m
[32m+[m[32m        <LinearLayout[m
[32m+[m[32m            android:id="@+id/rltParent"[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="wrap_content"[m
[32m+[m[32m            android:orientation="vertical">[m
[32m+[m
[32m+[m
[32m+[m[32m            <LinearLayout[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:id="@+id/lnrContr"[m
[32m+[m[32m                android:orientation="horizontal"[m
[32m+[m[32m                android:minWidth="210dp"[m
[32m+[m[32m                android:background="#d8e4e7">[m
[32m+[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/message_user"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_weight="1"[m
[32m+[m[32m                    android:paddingStart="6dp"[m
[32m+[m[32m                    android:textColor="#C51162"[m
[32m+[m[32m                    android:text="Motherfuker"[m
[32m+[m[32m                    android:textStyle="normal|bold" />[m
[32m+[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgStatus"[m
[32m+[m[32m                    android:layout_width="15dp"[m
[32m+[m[32m                    android:layout_height="15dp"[m
[32m+[m[32m                    android:layout_margin="1dp"[m
[32m+[m[32m                    android:src="@drawable/right"/>[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/message_status"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:layout_marginStart="4dp"[m
[32m+[m[32m                    android:gravity="right"[m
[32m+[m[32m                    android:paddingEnd="6dp"[m
[32m+[m[32m                    android:text="WON"[m
[32m+[m[32m                    android:textColor="#038b1e"[m
[32m+[m[32m                    android:textStyle="normal|bold" />[m
[32m+[m
[32m+[m[32m            </LinearLayout>[m
[32m+[m
[32m+[m[32m            <View[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="1dp"[m
[32m+[m[32m                android:background="#26abc2"/>[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:id="@+id/message_text"[m
[32m+[m[32m                android:layout_width="wrap_content"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:paddingStart="6dp"[m
[32m+[m[32m                android:paddingEnd="6dp"[m
[32m+[m[32m                android:layout_marginTop="2dp"[m
[32m+[m[32m                android:textIsSelectable="true"[m
[32m+[m[32m                android:autoLink="web"[m
[32m+[m[32m                android:textColorLink="#1564d4"[m
[32m+[m[32m                android:text="Okadjdjd FISTh live if your sitll wann be stupid, then feel free, but thdo m o ng hgjdn "[m
[32m+[m[32m                android:textAppearance="@style/TextAppearance.AppCompat.Body1"[m
[32m+[m[32m                android:textSize="16sp" />[m
[32m+[m
[32m+[m[32m            <TextView[m
[32m+[m[32m                android:layout_width="wrap_content"[m
[32m+[m[32m                android:layout_height="0dp"[m
[32m+[m[32m                android:minWidth="200dp"/>[m
[32m+[m
[32m+[m[32m            <LinearLayout[m
[32m+[m[32m                android:layout_width="match_parent"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:id="@+id/lnrContainer"[m
[32m+[m[32m                android:gravity="center_vertical"[m
[32m+[m[32m                android:orientation="horizontal"[m
[32m+[m[32m                android:minWidth="210dp"[m
[32m+[m[32m                android:layout_marginTop="3dp">[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/message_time"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:paddingStart="6dp"[m
[32m+[m[32m                    android:layout_weight="1.5"[m
[32m+[m[32m                    android:text="23-04-2018(01:23am)" />[m
[32m+[m
[32m+[m[32m                <ImageView[m
[32m+[m[32m                    android:id="@+id/imgLikes"[m
[32m+[m[32m                    android:layout_width="18dp"[m
[32m+[m[32m                    android:layout_height="18dp"[m
[32m+[m[32m                    android:layout_margin="1dp"[m
[32m+[m[32m                    android:tag="no_love"[m
[32m+[m[32m                    android:src="@drawable/no_love"/>[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/message_Likes"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:paddingStart="12dp"[m
[32m+[m[32m                    android:paddingEnd="12dp"[m
[32m+[m[32m                    android:text="0"[m
[32m+[m[32m                    android:textStyle="normal|bold" />[m
[32m+[m
[32m+[m
[32m+[m[32m            </LinearLayout>[m
[32m+[m
[32m+[m[32m        </LinearLayout>[m
[32m+[m
[32m+[m
[32m+[m[32m    </android.support.v7.widget.CardView>[m
[32m+[m[32m</RelativeLayout>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/layout/room_dialog.xml b/app/src/main/res/layout/room_dialog.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..2022065[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/room_dialog.xml[m
[36m@@ -0,0 +1,35 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<LinearLayout[m
[32m+[m[32m    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"[m
[32m+[m[32m    android:id="@+id/lnrRoomsDialog"[m
[32m+[m[32m    android:layout_height="wrap_content"[m
[32m+[m[32m    android:orientation="vertical"[m
[32m+[m[32m    android:padding="@dimen/activity_horizontal_margin"[m
[32m+[m[32m    android:gravity="center">[m
[32m+[m[32m    <TextView[m
[32m+[m[32m        android:layout_width="wrap_content"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:textColor="@android:color/black"[m
[32m+[m[32m        android:text="*Click 'submit' if only your tips delivered"/>[m
[32m+[m[32m    <Button[m
[32m+[m[32m        android:id="@+id/btnSubmit"[m
[32m+[m[32m        android:layout_width="180dp"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:textColor="@color/colorPrimary"[m
[32m+[m[32m        android:text="Yeah...  Submit"/>[m
[32m+[m
[32m+[m[32m    <Button[m
[32m+[m[32m        android:id="@+id/btnShare"[m
[32m+[m[32m        android:layout_width="180dp"[m
[32m+[m[32m        android:textColor="@color/colorPrimary"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:text="Share"/>[m
[32m+[m
[32m+[m[32m    <Button[m
[32m+[m[32m        android:id="@+id/btnDelete"[m
[32m+[m[32m        android:layout_width="180dp"[m
[32m+[m[32m        android:textColor="@color/colorPrimary"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:text="Delete"/>[m
[32m+[m
[32m+[m[32m</LinearLayout>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/layout/room_dialog2.xml b/app/src/main/res/layout/room_dialog2.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..633e9e3[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/res/layout/room_dialog2.xml[m
[36m@@ -0,0 +1,38 @@[m
[32m+[m[32m<?xml version="1.0" encoding="utf-8"?>[m
[32m+[m[32m<LinearLayout[m
[32m+[m[32m    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"[m
[32m+[m[32m    android:id="@+id/lnrRoomsDialog"[m
[32m+[m[32m    android:layout_height="wrap_content"[m
[32m+[m[32m    android:orientation="vertical"[m
[32m+[m[32m    android:padding="@dimen/activity_horizontal_margin"[m
[32m+[m[32m    android:gravity="center">[m
[32m+[m[32m    <TextView[m
[32m+[m[32m        android:layout_width="wrap_content"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:textColor="@android:color/black"[m
[32m+[m[32m        android:visibility="gone"[m
[32m+[m[32m        android:text="*Click 'submit' if only your tips delivered"/>[m
[32m+[m[32m    <Button[m
[32m+[m[32m        android:id="@+id/btnSubmit"[m
[32m+[m[32m        android:layout_width="180dp"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:textColor="@color/colorPrimary"[m
[32m+[m[32m        android:visibility="gone"[m
[32m+[m[32m        android:text="Yeah...  Submit"/>[m
[32m+[m
[32m+[m[32m    <Button[m
[32m+[m[32m        android:id="@+id/btnShare"[m
[32m+[m[32m        android:layout_width="180dp"[m
[32m+[m[32m        android:textColor="@color/colorPrimary"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:text="Share"/>[m
[32m+[m
[32m+[m[32m    <Button[m
[32m+[m[32m        android:id="@+id/btnDelete"[m
[32m+[m[32m        android:layout_width="180dp"[m
[32m+[m[32m        android:textColor="@color/colorPrimary"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:enabled="false"[m
[32m+[m[32m        android:text="Delete"/>[m
[32m+[m
[32m+[m[32m</LinearLayout>[m
\ No newline at end of file[m
[1mdiff --git a/build.gradle b/build.gradle[m
[1mindex 0d67541..824a1d4 100644[m
[1m--- a/build.gradle[m
[1m+++ b/build.gradle[m
[36m@@ -25,4 +25,4 @@[m [mallprojects {[m
 [m
 task clean(type: Delete) {[m
     delete rootProject.buildDir[m
[31m-}[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/gradle/wrapper/gradle-wrapper.jar b/gradle/wrapper/gradle-wrapper.jar[m
[1mindex 13372ae..7a3265e 100644[m
Binary files a/gradle/wrapper/gradle-wrapper.jar and b/gradle/wrapper/gradle-wrapper.jar differ
[1mdiff --git a/gradle/wrapper/gradle-wrapper.properties b/gradle/wrapper/gradle-wrapper.properties[m
[1mindex 844dbe9..3f8e837 100644[m
[1m--- a/gradle/wrapper/gradle-wrapper.properties[m
[1m+++ b/gradle/wrapper/gradle-wrapper.properties[m
[36m@@ -1,6 +1,6 @@[m
[31m-#Tue May 15 12:29:22 WAT 2018[m
[32m+[m[32m#Mon Jul 23 14:14:07 WAT 2018[m
 distributionBase=GRADLE_USER_HOME[m
 distributionPath=wrapper/dists[m
 zipStoreBase=GRADLE_USER_HOME[m
 zipStorePath=wrapper/dists[m
[31m-distributionUrl=https\://services.gradle.org/distributions/gradle-4.7-all.zip[m
[32m+[m[32mdistributionUrl=https\://services.gradle.org/distributions/gradle-4.4-all.zip[m
[1mdiff --git a/gradlew b/gradlew[m
[1mindex 9d82f78..cccdd3d 100644[m
[1m--- a/gradlew[m
[1m+++ b/gradlew[m
[36m@@ -1,4 +1,4 @@[m
[31m-#!/usr/bin/env bash[m
[32m+[m[32m#!/usr/bin/env sh[m
 [m
 ##############################################################################[m
 ##[m
[36m@@ -6,20 +6,38 @@[m
 ##[m
 ##############################################################################[m
 [m
[31m-# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.[m
[31m-DEFAULT_JVM_OPTS=""[m
[32m+[m[32m# Attempt to set APP_HOME[m
[32m+[m[32m# Resolve links: $0 may be a link[m
[32m+[m[32mPRG="$0"[m
[32m+[m[32m# Need this for relative symlinks.[m
[32m+[m[32mwhile [ -h "$PRG" ] ; do[m
[32m+[m[32m    ls=`ls -ld "$PRG"`[m
[32m+[m[32m    link=`expr "$ls" : '.*-> \(.*\)$'`[m
[32m+[m[32m    if expr "$link" : '/.*' > /dev/null; then[m
[32m+[m[32m        PRG="$link"[m
[32m+[m[32m    else[m
[32m+[m[32m        PRG=`dirname "$PRG"`"/$link"[m
[32m+[m[32m    fi[m
[32m+[m[32mdone[m
[32m+[m[32mSAVED="`pwd`"[m
[32m+[m[32mcd "`dirname \"$PRG\"`/" >/dev/null[m
[32m+[m[32mAPP_HOME="`pwd -P`"[m
[32m+[m[32mcd "$SAVED" >/dev/null[m
 [m
 APP_NAME="Gradle"[m
 APP_BASE_NAME=`basename "$0"`[m
 [m
[32m+[m[32m# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.[m
[32m+[m[32mDEFAULT_JVM_OPTS=""[m
[32m+[m
 # Use the maximum available, or set MAX_FD != -1 to use that value.[m
 MAX_FD="maximum"[m
 [m
[31m-warn ( ) {[m
[32m+[m[32mwarn () {[m
     echo "$*"[m
 }[m
 [m
[31m-die ( ) {[m
[32m+[m[32mdie () {[m
     echo[m
     echo "$*"[m
     echo[m
[36m@@ -30,6 +48,7 @@[m [mdie ( ) {[m
 cygwin=false[m
 msys=false[m
 darwin=false[m
[32m+[m[32mnonstop=false[m
 case "`uname`" in[m
   CYGWIN* )[m
     cygwin=true[m
[36m@@ -40,26 +59,11 @@[m [mcase "`uname`" in[m
   MINGW* )[m
     msys=true[m
     ;;[m
[32m+[m[32m  NONSTOP* )[m
[32m+[m[32m    nonstop=true[m
[32m+[m[32m    ;;[m
 esac[m
 [m
[31m-# Attempt to set APP_HOME[m
[31m-# Resolve links: $0 may be a link[m
[31m-PRG="$0"[m
[31m-# Need this for relative symlinks.[m
[31m-while [ -h "$PRG" ] ; do[m
[31m-    ls=`ls -ld "$PRG"`[m
[31m-    link=`expr "$ls" : '.*-> \(.*\)$'`[m
[31m-    if expr "$link" : '/.*' > /dev/null; then[m
[31m-        PRG="$link"[m
[31m-    else[m
[31m-        PRG=`dirname "$PRG"`"/$link"[m
[31m-    fi[m
[31m-done[m
[31m-SAVED="`pwd`"[m
[31m-cd "`dirname \"$PRG\"`/" >/dev/null[m
[31m-APP_HOME="`pwd -P`"[m
[31m-cd "$SAVED" >/dev/null[m
[31m-[m
 CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar[m
 [m
 # Determine the Java command to use to start the JVM.[m
[36m@@ -85,7 +89,7 @@[m [mlocation of your Java installation."[m
 fi[m
 [m
 # Increase the maximum file descriptors if we can.[m
[31m-if [ "$cygwin" = "false" -a "$darwin" = "false" ] ; then[m
[32m+[m[32mif [ "$cygwin" = "false" -a "$darwin" = "false" -a "$nonstop" = "false" ] ; then[m
     MAX_FD_LIMIT=`ulimit -H -n`[m
     if [ $? -eq 0 ] ; then[m
         if [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ] ; then[m
[36m@@ -150,11 +154,19 @@[m [mif $cygwin ; then[m
     esac[m
 fi[m
 [m
[31m-# Split up the JVM_OPTS And GRADLE_OPTS values into an array, following the shell quoting and substitution rules[m
[31m-function splitJvmOpts() {[m
[31m-    JVM_OPTS=("$@")[m
[32m+[m[32m# Escape application args[m
[32m+[m[32msave () {[m
[32m+[m[32m    for i do printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;\$s/\$/' \\\\/" ; done[m
[32m+[m[32m    echo " "[m
 }[m
[31m-eval splitJvmOpts $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS[m
[31m-JVM_OPTS[${#JVM_OPTS[*]}]="-Dorg.gradle.appname=$APP_BASE_NAME"[m
[32m+[m[32mAPP_ARGS=$(save "$@")[m
[32m+[m
[32m+[m[32m# Collect all arguments for the java command, following the shell quoting and substitution rules[m
[32m+[m[32meval set -- $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "\"-Dorg.gradle.appname=$APP_BASE_NAME\"" -classpath "\"$CLASSPATH\"" org.gradle.wrapper.GradleWrapperMain "$APP_ARGS"[m
[32m+[m
[32m+[m[32m# by default we should be in the correct project dir, but when run from Finder on Mac, the cwd is wrong[m
[32m+[m[32mif [ "$(uname)" = "Darwin" ] && [ "$HOME" = "$PWD" ]; then[m
[32m+[m[32m  cd "$(dirname "$0")"[m
[32m+[m[32mfi[m
 [m
[31m-exec "$JAVACMD" "${JVM_OPTS[@]}" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"[m
[32m+[m[32mexec "$JAVACMD" "$@"[m
[1mdiff --git a/gradlew.bat b/gradlew.bat[m
[1mindex 8a0b282..f955316 100644[m
[1m--- a/gradlew.bat[m
[1m+++ b/gradlew.bat[m
[36m@@ -8,14 +8,14 @@[m
 @rem Set local scope for the variables with windows NT shell[m
 if "%OS%"=="Windows_NT" setlocal[m
 [m
[31m-@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.[m
[31m-set DEFAULT_JVM_OPTS=[m
[31m-[m
 set DIRNAME=%~dp0[m
 if "%DIRNAME%" == "" set DIRNAME=.[m
 set APP_BASE_NAME=%~n0[m
 set APP_HOME=%DIRNAME%[m
 [m
[32m+[m[32m@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.[m
[32m+[m[32mset DEFAULT_JVM_OPTS=[m
[32m+[m
 @rem Find java.exe[m
 if defined JAVA_HOME goto findJavaFromJavaHome[m
 [m
[36m@@ -46,10 +46,9 @@[m [mecho location of your Java installation.[m
 goto fail[m
 [m
 :init[m
[31m-@rem Get command-line arguments, handling Windowz variants[m
[32m+[m[32m@rem Get command-line arguments, handling Windows variants[m
 [m
 if not "%OS%" == "Windows_NT" goto win9xME_args[m
[31m-if "%@eval[2+2]" == "4" goto 4NT_args[m
 [m
 :win9xME_args[m
 @rem Slurp the command line arguments.[m
[36m@@ -60,11 +59,6 @@[m [mset _SKIP=2[m
 if "x%~1" == "x" goto execute[m
 [m
 set CMD_LINE_ARGS=%*[m
[31m-goto execute[m
[31m-[m
[31m-:4NT_args[m
[31m-@rem Get arguments from the 4NT Shell from JP Software[m
[31m-set CMD_LINE_ARGS=%$[m
 [m
 :execute[m
 @rem Setup the command line[m
