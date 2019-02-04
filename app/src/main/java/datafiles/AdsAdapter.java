package datafiles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import secured.tips.R;

public class AdsAdapter extends PagerAdapter {

    private Context context;
    //private List<Integer> imageDrawable;

    private List<String> imageUrl;
    private List<String> linkUrl;

    public AdsAdapter(Context context, List<String> imageUrl, List<String> linkUrl){
        this.context = context;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
    }
    
    @Override
    public int getCount() {
        return imageUrl.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ads_slider, null);

        ImageView adsImage = view.findViewById(R.id.adsImage);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.bg);
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(imageUrl.get(position)).into(adsImage);
        adsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = linkUrl.get(position).toLowerCase().trim();
                if(!text.equals("none")){
                    Uri webpage = Uri.parse(text);
                    if (!text.startsWith("http://") && !text.startsWith("https://")) {
                        webpage = Uri.parse("http://" + text);
                    }
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW, webpage);
                    context.startActivity(i);
                }
            }
        });

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
