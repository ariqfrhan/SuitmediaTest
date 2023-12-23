package maf.mobile.suitmediatest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import maf.mobile.suitmediatest.Model.UserModel;
import maf.mobile.suitmediatest.OnDataClickListener;
import maf.mobile.suitmediatest.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ItemVH> {

    private Context context;
    private List<UserModel> users;
    private OnDataClickListener listener;

    public UserAdapter(Context context, List<UserModel> users, OnDataClickListener listener){
        this.context = context;
        this.users = users;
        this.listener = listener;
    }
    @NonNull
    @Override
    public UserAdapter.ItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(context)
                .inflate(R.layout.item_user, parent, false);
        return new ItemVH(card);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ItemVH holder, int position) {
        UserModel user = this.users.get(position);
        Picasso.get().load(user.getAvatar()).into(holder.imgAvatar);
        holder.tvFirstname.setText(user.getFirstName());
        holder.tvLastname.setText(user.getLastName());
        holder.tvEmail.setText(user.getEmail());

        holder.cardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnDataClick(user.getFirstName(), user.getLastName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ItemVH extends RecyclerView.ViewHolder {
        private CardView cardUser;
        private CircleImageView imgAvatar;
        private TextView tvFirstname;
        private TextView tvLastname;
        private TextView tvEmail;

        public ItemVH(@NonNull View itemView) {
            super(itemView);
            this.imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            this.tvFirstname = (TextView) itemView.findViewById(R.id.tvFirstname);
            this.tvLastname = (TextView) itemView.findViewById(R.id.tvLastname);
            this.tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            this.cardUser = (CardView) itemView.findViewById(R.id.cardUser);
        }
    }
}
