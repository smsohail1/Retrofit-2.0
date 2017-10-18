package example.com.retrofit20;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by muhammad.sohail on 10/18/2017.
 */

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    public List<UserList.Datum> mItems;
    private Context mContext;
    private PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleTv;
        private ImageView userImg;
        private Button okBtn;
        PostItemListener mItemListener;

        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.nameTv);
            userImg = (ImageView) itemView.findViewById(R.id.userPic);
            okBtn = (Button) itemView.findViewById(R.id.okBtn);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            UserList.Datum item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(getAdapterPosition());

            notifyDataSetChanged();
        }
    }

    public AnswersAdapter(Context context, List<UserList.Datum> posts, PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.simple_row_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewHolder myHolder = (ViewHolder) holder;

        // UserList.Datum item = mItems.get(position).getFirstName();
        // TextView textView = myHolder.titleTv;
        //  textView.setText(item.firstName);
        myHolder.titleTv.setText(mItems.get(position).getFirstName());

        // loading album cover using Glide library
        Glide.with(mContext).load(mItems.get(position).getAvatar()).placeholder(R.mipmap.ic_launcher).into(holder.userImg);

        //On button listner
        myHolder.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<UserList.Datum> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private UserList.Datum getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
}
