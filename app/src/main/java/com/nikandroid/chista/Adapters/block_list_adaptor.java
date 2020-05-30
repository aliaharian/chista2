package com.nikandroid.chista.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class block_list_adaptor extends RecyclerView.Adapter<block_list_adaptor.MyViewHolder> {

    private JSONObject list;
    private Function fun;
    private Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name,des,price,star;
        public ImageView img,ost;
        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.main_line_adviserbox1_row_name);
            des =  view.findViewById(R.id.main_line_adviserbox1_row_des);
            price =  view.findViewById(R.id.main_line_adviserbox1_row_price);
            img =  view.findViewById(R.id.main_line_adviserbox1_row_img);
            ost =  view.findViewById(R.id.main_line_adviserbox1_row_onlinestatus);
            star =  view.findViewById(R.id.main_line_adviserbox1_row_star);

        }
    }


    public block_list_adaptor(JSONObject list,Context c,Function f) {
        this.list = list;
        context=c;
        fun=f;
    }

    @Override
    public block_list_adaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_adviser1_row, parent, false);
        return new block_list_adaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final block_list_adaptor.MyViewHolder holder, final int position) {


        try {
            final JSONObject tmp = new JSONObject(list.getString("" + (position )));

            Picasso
                    .get()
                    .load(Params.pic_adviseravatae+tmp.getString("avatar"))
                    .error(R.drawable.nopic)
                    .into(holder.img);

            holder.name.setText(tmp.getString("name"));
            //holder.loc.setText(tmp.getString("city"));
            //holder.hr.setText(tmp.getString("chat_time")+"دقیقه");

            holder.des.setVisibility(View.GONE);

            if(tmp.getString("is_online").equals("2")){
                holder.ost.setImageResource(R.drawable.z_circle_busy);
            }else if(tmp.getString("is_online").equals("1")){
                holder.ost.setImageResource(R.drawable.z_circle_online);
            }else{
                holder.ost.setImageResource(R.drawable.z_circle_offline);
            }

            holder.itemView.setOnClickListener(v -> new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("آزاد کردن مشاور!!")
                    .setContentText("آیا از خارج کردن مشاور از حالت مسدود بودن مطمئنید؟")
                    .setConfirmText("بله")
                    .setCancelText("خیر")
                    .setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss())
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        try {
                            fun.block_adviser(tmp.getString("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    })
                    .show());
        }catch (Exception e){
        }
    }

    @Override
    public int getItemCount() {
        return list.length();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
