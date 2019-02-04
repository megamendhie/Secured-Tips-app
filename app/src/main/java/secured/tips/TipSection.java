package secured.tips;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by Mendhie on 2/7/2018.
 */

public class TipSection extends StatelessSection {
    String title;
    List<TipDetails> tipDetails;
    Context context;

    public TipSection(String title, List<TipDetails> tipDetails, Context context){
        super(new SectionParameters.Builder(R.layout.container_template)
            .footerResourceId(R.layout.header)
            .build());

        this.title = title;
        this.tipDetails = tipDetails;
        this.context = context;

    }

    @Override
    public int getContentItemsTotal() {
        return tipDetails.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        final TipDetails Details = tipDetails.get(position);

        itemHolder.txtLeague.setText(Details.getLeague());
        itemHolder.txtMatch.setText(Details.getMatches());
        itemHolder.txtTip.setText(Details.getTip());
        itemHolder.txtOdd.setText(String.valueOf(Details.getOdd()));
        int result = Details.getResult().intValue();
        itemHolder.imgResult.setImageResource(getResult(result));

        itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Details.getHth().toLowerCase().equals("nothing")){
                    Intent intent = new Intent(context, FloatingActivity.class);
                    String hth = Details.getHth();
                    intent.putExtra("address", hth);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Stat not available now", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public int getResult(int Result){
        switch (Result){
            case 1:
                return R.drawable.right;
            case 2:
                return R.drawable.wrong;
            case 3:
                return R.drawable.pending;
        }
        return R.drawable.pending;
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        FooterViewHolder footerHolder = (FooterViewHolder) holder;
        footerHolder.txtFooter.setText(title);
    }


    private class FooterViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtFooter;

        FooterViewHolder(View view) {
            super(view);

            txtFooter = view.findViewById(R.id.txtTitle);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        public final View rootView;
        public TextView txtLeague, txtMatch, txtTip, txtOdd;
        public ImageView imgResult;

        public ItemViewHolder(View itemView){
            super(itemView);

            rootView = itemView;
            txtLeague = (TextView)itemView.findViewById(R.id.txtLeague);
            txtMatch = (TextView)itemView.findViewById(R.id.txtMatch);
            txtOdd = (TextView)itemView.findViewById(R.id.txtOdd);
            txtTip = (TextView)itemView.findViewById(R.id.txtTip);
            imgResult = (ImageView)itemView.findViewById(R.id.imgResult);
        }
    }

}
