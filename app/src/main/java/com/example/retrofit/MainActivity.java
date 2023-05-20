package com.example.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle);

        //Divider_line for every items
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        listingdata();
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
    }

    private void listingdata() {
        ApiInterface apiInterface = Retrofit.getRetrofit().create(ApiInterface.class);
        Call<Pojo> listingdata = apiInterface.getdata();
        listingdata.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {

                if (response.isSuccessful())
                {
                    recycleadapter adapter = new recycleadapter(response.body().getData());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT);
            }
        });
    }


    class recycleadapter extends RecyclerView.Adapter<recycleadapter.MyViewHolder>{

        List<Pojo.Datum> list;

        public recycleadapter(List<Pojo.Datum> list)
        {
            this.list = list;
        }

        @NonNull
        @Override
        public recycleadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout1,null);
            recycleadapter.MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            final Pojo.Datum data_position = list.get(position);

//            holder.userid.setText(list.get(position).getId());
            holder.fstname.setText(list.get(position).getFirstName());
            holder.lstname.setText(list.get(position).getLastName());
            holder.email.setText(list.get(position).getEmail());

            Picasso.with(getApplicationContext())
                    .load(list.get(position).getAvatar())
                    .placeholder(R.drawable.user)
                    .fit()
                    .into(holder.imgs);

            holder.maincard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(MainActivity.this, "Item is Clicked: ", Toast.LENGTH_SHORT).show();

                    Intent myintent = new Intent(view.getContext(),NextActivity.class);

                    myintent.putExtra("first",data_position.getFirstName());
                    myintent.putExtra("second",data_position.getLastName());
                    myintent.putExtra("mail",data_position.getEmail());
                    myintent.putExtra("image",data_position.getAvatar());

                    view.getContext().startActivity(myintent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return list.size();
        }
         class MyViewHolder extends RecyclerView.ViewHolder{

            TextView fstname, lstname, email;
            ImageView imgs;
//            CardView maincard;
            LinearLayout maincard;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                fstname = itemView.findViewById(R.id.fstname);
                lstname = itemView.findViewById(R.id.lstname);
//                userid = itemView.findViewById(R.id.userid);
                email = itemView.findViewById(R.id.email);
                imgs = itemView.findViewById(R.id.image);

                maincard = itemView.findViewById(R.id.maincard);

            }
        }
    }
}