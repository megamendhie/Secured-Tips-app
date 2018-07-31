package secured.tips;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutTipzoneActivity extends AppCompatActivity {
    TextView txtGuidelines, txtGuidelines2,txtGuidelines3, txtGuidelines4;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_tipzone);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        txtGuidelines = findViewById(R.id.txtGuideline);
        txtGuidelines2 = findViewById(R.id.txtGuideline2);
        txtGuidelines3 = findViewById(R.id.txtGuideline3);
        txtGuidelines4 = findViewById(R.id.txtGuideline4);
        String guidelines = "<p><strong>Welcome to TipZone</strong></p>\n" +
                "<p>TipZone is an online forum where tipsters help each other win big by sharing their surest tips. This is where you will enjoy first class predictions from good punters. If you are good as well, also share your tips for others to benefit.</p>\n" +
                "<p>Your tips will help others make wise betting decisions and you also benefit from that of others.</p>\n" +
                "<p>To encourage you and other tipsters, we reward you with points and cash whenever your tips win. Read about our <strong><u>Rewards</u></strong>(below) for more details.</p>";
        String guidelines2 = "<p><strong>Guidelines</strong></p>\n" +
                        "<ol>\n" +
                        "<li>In accordance with gambling policy, you must be up to 18 years to join our forum.</li>\n" +
                        "<li>Before you share your tips, confirm that you have at least 90% confidence in your tips. Don&rsquo;t post random tips. This is for the good of everybody.</li>\n" +
                        "</ol>\n" +
                        "<ul>\n" +
                        "<li>There is no limit to the tips you can post.</li>\n" +
                        "<li>Always indicate the total odds of your tips to help us reward you appropriately when it wins.</li>\n" +
                        "</ul>\n" +
                        "<ol>\n" +
                        "<li>Do not persuade anyone to stake on your game. Let them decide.</li>\n" +
                        "<li>Post your tips in the room that matches the total odds of your tips. E.g if your tips is 28.89, then post it in 10-50 odds room, if it&rsquo;s 78.43, post it in 50-150 odds room, and so on. Coupon draws have it&rsquo;s on room too.</li>\n" +
                        "<li>We don&rsquo;t want any kind of marketing, preaching, broadcast, relationship or political gist, gossips, etc in the forum. That&rsquo;s not the purpose.</li>\n" +
                        "</ol>";
        String guidelines3 = "<p><strong>Payment</strong></p>\n" +
                "<p>Secured Tips Forum is open to everyone (18+). It is completely free for Secured Tips VIP subscribers. Non-VIP subscribers will join for only N1500 (i.e $4) monthly. The charge is for 2 reasons: to reward tipsters when they win, and also for maintenance.</p>";
        String guidelines4 =  "<p><strong>Rewards</strong></p>\n" +
                "<p>You will be rewarded with points and cash every time your tips deliver. The points help others know how good you are in prediction, and the areas you are very good at. The cash reward is our appreciation for your contribution. It will be forwarded to your account every time you get up to 100 points. <strong>(100 points = NGN 10,000)</strong>. Note that points depend on the odds of your tips.</p>\n" +
                "<p>10-50 odds = 10 points</p>\n" +
                "<p>51-150 odds = 20 points</p>\n" +
                "<p>151-500 odds = 30 points</p>\n" +
                "<p>500 and above = 50 points</p>\n" +
                "<p>Pools* = 50 points</p>\n" +
                "<p>(For pools, your draws must deliver all or cut not more than one(1) before we reward you. Note: we don&rsquo;t reward single draw, 2/2 or 2/3 ).</p>";

        txtGuidelines.setText(Html.fromHtml(guidelines));
        txtGuidelines2.setText(Html.fromHtml(guidelines2));
        txtGuidelines3.setText(Html.fromHtml(guidelines3));
        txtGuidelines4.setText(Html.fromHtml(guidelines4));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}
