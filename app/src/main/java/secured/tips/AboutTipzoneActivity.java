package secured.tips;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutTipzoneActivity extends AppCompatActivity {
    TextView txtGuidelines, txtGuidelines2,txtGuidelines3;
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
        String guidelines = "<p><strong>Introduction</strong></p>\n" +
                "<p>Welcome to Tipzone. This is an online community for tipsters and all soccer lovers in general. You can share your best predictions with others and also benefit from others. The beautiful thing is that we reward you with cash and points when you win. Read more about the <strong><u>Rewards</u></strong> (below).</p>\n" +
                "<p>The first 2 rooms are free but you have to subscribe for other rooms. The subscription is very low and affordable.</p>\n";
        String guidelines2 = "<p><strong>Guidelines</strong></p>\n" +
                        "<ol>\n" +
                        "<li>You must be up to 18 years to join Tipzone.</li>\n" +
                        "<li>Confirm that you have at least 90% confidence in your tips before you share. Don&rsquo;t post random tips. This is for the good of everybody.</li>\n" +
                        "</ol>\n" +
                        "<ul>\n" +
                        "<li>Don&rsquo;t spam the room.</li>\n" +
                        "<li>Always indicate the total odds of your tips. This will help us reward you when it wins.</li>\n" +
                        "</ul>\n" +
                        "<ol>\n" +
                        "<li>Submit your game for review/reward ONLY if it wins.</li>\n" +
                        "<li>Don&rsquo;t persuade anyone to stake on your game. Let them decide.</li>\n" +
                        "<li>Post your tips in the right room depending on the total odd of your game. There is a special room for Pool draws. <b>No reward for tips submitted in the wrong room.</b></li>\n" +
                        "<li>Don&rsquo;t use Tipzone for any kind of marketing, relationship, religious or political discussion. Only sports discussion.</li>\n" +
                        "</ol>";
        String guidelines3 =  "<p><strong>Rewards</strong></p>\n" +
                "<p>3-10 odds\t\t= 4 points\t\t= $1</p>\n" +
                "<p>11-50 odds\t\t= 10 points\t\t= $2.5</p>\n" +
                "<p>51-100 odds\t\t= 20 points\t\t= $5</p>\n" +
                "<p>Pools/draws*\t\t= 40 points\t\t= $10</p>\n" +
                "<p>(*For pools, you get full points if all delivers. If only cuts, you get half points. Note that the least number of draws in a ticket is three(3).</p>" +
                "<p><strong>The cash will be transferred to your bank account every time you get up to $25.</strong></p>";

        txtGuidelines.setText(Html.fromHtml(guidelines));
        txtGuidelines2.setText(Html.fromHtml(guidelines2));
        txtGuidelines3.setText(Html.fromHtml(guidelines3));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}
