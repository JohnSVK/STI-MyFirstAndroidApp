package expert.sti.myfirstapp.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import expert.sti.myfirstapp.MyApplication;
import expert.sti.myfirstapp.R;
import expert.sti.myfirstapp.entities.Actor;
import expert.sti.myfirstapp.ui.activities.WikiActivity;

public class ActorAdapter extends BaseAdapter {

    private ArrayList<Actor> mActors;
    private LayoutInflater mInflater;
    private Activity mContext;
    private ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public ActorAdapter(Activity context, ArrayList<Actor> actors) {
        this.mActors = actors;
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mActors.size();
    }

    @Override
    public Object getItem(int position) {
        return mActors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Actor actor = mActors.get(position);

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.row_actor, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ivPhoto.setImageUrl(actor.getImage(), imageLoader);

        holder.tvName.setText(actor.getName());
        holder.tvDescription.setText(actor.getDescription());
        holder.tvDOB.setText(mContext.getString(R.string.actor_dob) + actor.getDob());
        holder.tvCountry.setText(actor.getCountry());
        holder.tvHeight.setText(mContext.getString(R.string.actor_height) + actor.getHeight());
        holder.tvSpouse.setText(mContext.getString(R.string.actor_spouse) + actor.getSpouse());
        holder.tvChildren.setText(mContext.getString(R.string.actor_children) + actor.getChildren());

        holder.bWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wiki = actor.getWiki();
                mContext.startActivity(new Intent(mContext, WikiActivity.class).putExtra("WIKI_URI", wiki));
                mContext.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ivPhoto) NetworkImageView ivPhoto;
        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvDescriptionn) TextView tvDescription;
        @BindView(R.id.tvDateOfBirth) TextView tvDOB;
        @BindView(R.id.tvCountry) TextView tvCountry;
        @BindView(R.id.tvHeight) TextView tvHeight;
        @BindView(R.id.tvSpouse) TextView tvSpouse;
        @BindView(R.id.tvChildren) TextView tvChildren;
        @BindView(R.id.bWiki) Button bWiki;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
