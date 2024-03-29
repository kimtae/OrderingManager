package com.example.orderingmanager.view.QRFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderingmanager.R;

import java.util.ArrayList;

public class QrCardViewAdapter extends RecyclerView.Adapter<QrCardViewAdapter.CustomViewHolder> {
    ArrayList<Bitmap> arrayList;
    Context context;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tvTableNum, tvTable, tvTitle, tvSecondary, tvSupport;
        ImageView ivQrImage;
        Button btnPreview;
        LinearLayout llQrCardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ivQrImage = itemView.findViewById(R.id.iv_qrcode_cardview);
            tvTableNum = itemView.findViewById(R.id.tv_tableNum);
            tvTable = itemView.findViewById(R.id.tv_table);
            tvTitle = itemView.findViewById(R.id.tv_cardview_title);
            tvSecondary = itemView.findViewById(R.id.tv_cardview_secondary);
            tvSupport = itemView.findViewById(R.id.tv_cardview_support);
            btnPreview = itemView.findViewById(R.id.btn_preview);
            llQrCardView = itemView.findViewById(R.id.item_qr_cardview);
        }
    }

    public QrCardViewAdapter(ArrayList<Bitmap> arrayList) {
        this.arrayList = arrayList;
    }

    public QrCardViewAdapter(ArrayList<Bitmap> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qr_cardview, parent, false);
        return new CustomViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.ivQrImage.setImageBitmap(QrList.getQrBitmap(position+2));
        holder.tvTitle.setTextSize(14);
        holder.tvTitle.setText("테이블QR");
        holder.tvSupport.setTextSize(12);
        holder.tvTable.setVisibility(View.VISIBLE);
        holder.tvTable.setTextSize(14);
        holder.tvTableNum.setVisibility(View.VISIBLE);
        holder.tvTableNum.setTextSize(14);
        holder.tvTableNum.setText(Integer.toString(position+1));
        holder.tvSecondary.setTextSize(12);
        holder.tvSecondary.setText("테이블용 QR코드입니다");

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        // 테이블 프래그먼트에서만 item_qr_cardview 리니어 레이아웃의 width(=match_parent), height(=wrap_content)로 변경
        holder.llQrCardView.setLayoutParams(parms);
        // 테이블 프래그먼트에서만 qrCode 이미지뷰 width(=match_parent), height(=wrap_content)로 변경
        holder.ivQrImage.setLayoutParams(parms);

        holder.btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,QrPreviewActivity.class);
                intent.putExtra("cardViewType",position+2);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // getItemCount: return the size of the item list
        // item list의 전체 데이터 개수 return
        // arrayList는 전체 Qr의 개수(table을 제외한 포장,웨이팅 qr도 포함 ,,, 따라서 총 size는 tablecount + 2 이다.)
        // 근데 해당 Adapter는 QrTableFragment에서만 사용하기 때문에 2를 빼야한다.
        // 만약 arrayList.size()를 그대로 반환하게 된다면
        // Adapter는 필요한 개수보다 뷰를 2개 더 많이 생성하게 됨 << 이렇게 되면 나머지 2개에 대한 정보가 없기 때문에 에러가 남
        return (arrayList != null ? arrayList.size()-2 : 0);
    }

}

