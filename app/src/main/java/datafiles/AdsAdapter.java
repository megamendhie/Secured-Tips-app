package datafiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import secured.tips.R;

public class AdsAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> imageDrawable;
    private List<String> buttonText;

    public AdsAdapter(Context context, List<Integer> imageDrawable, List<String> buttonText){
        this.context = context;
        this.imageDrawable = imageDrawable;
        this.buttonText = buttonText;
    }
    
    @Override
    public int getCount() {
        return imageDrawable.size();
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
        Button adsButton = view.findViewById(R.id.adsLinkButton);

        adsImage.setImageResource(imageDrawable.get(position));
        adsButton.setText(buttonText.get(position));

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
