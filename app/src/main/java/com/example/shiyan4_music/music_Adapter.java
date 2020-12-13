package com.example.shiyan4_music;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class music_Adapter extends RecyclerView.Adapter<music_Adapter.MyHodler> {

    private List<Music> musicList;  //音乐列表
    private OnItemClickListener listener;  //接口

    public music_Adapter(List<Music> musicList) {   //列表构造函数
        this.musicList = musicList;
    }

    public void setListener(OnItemClickListener listener){          //接口赋值函数
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //设置展示形式
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.act_main_item,parent,false);
        return new MyHodler(view);
    }

    public interface OnItemClickListener{
        void OnItemClickForDelete(View view,int id,int position);
        void OnItemClickForSelect(View view,int id);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHodler holder, final int position) {
        Music music = musicList.get(position);
        final int id=music.getId();
        holder.singer.setText(music.getSinger()); //展示每个Item的歌名，演唱者
        holder.song.setText(music.getSong());  //对每个按钮和Item都监听。
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClickForDelete(v,id,position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClickForSelect(v,id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }
//声明Item中控件，对控件初始化
    static class MyHodler extends RecyclerView.ViewHolder{
        private TextView song,singer;
        private ImageButton delete;
        public MyHodler(@NonNull View itemView) {
            super(itemView);
            song=itemView.findViewById(R.id.music_title);
            singer=itemView.findViewById(R.id.music_singer);
            delete=itemView.findViewById(R.id.music_delete);
        }
    }
}
