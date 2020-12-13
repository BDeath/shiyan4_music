package com.example.shiyan4_music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton music_last,music_start,music_next;  //图片按钮初
    private RecyclerView recyclerView;                  //RecyclerView控件
    private TextView music_name,music_time_now,music_time_max;  //文本控件
    private SeekBar sb;                 //SeekBar控件
    private List<Music> musicList=new ArrayList<>(); //音乐清单
    private MediaPlayer mediaPlayer;                //播放器
    private int n=-1,music_id=1,duration;           //辅助变量
    private int[] images=new int[]{R.drawable.music_stop,R.drawable.music_start};
    private Timer timer;
    MediaPlayer mPlayer1,mPlayer2,mPlayer3,mPlayer4,mPlayer5;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();        //隐藏标头
        initList();                         //建立列表
        initView();                         //显示界面，按键实例化，显示内容
        initRecyclerView();                 //实现recyclerview
    }
    public void initList(){
        //设置列表内容
        Music music1=new Music("lemon","米津玄师",0);
        Music music2=new Music("You Leave","高至豪",1);
        Music music3=new Music("世末歌者","益笙菌",2);
        Music music4=new Music("霜雪千年","益笙菌",3);
        Music music5=new Music("You and me","One Two",4);
        musicList.add(music1);
        musicList.add(music2);
        musicList.add(music3);
        musicList.add(music4);
        musicList.add(music5);
    }
    public void initView(){
        //按钮实例化
        music_last=(ImageButton)findViewById(R.id.music_last);
        music_start=(ImageButton)findViewById(R.id.music_start);
        music_next=(ImageButton)findViewById(R.id.music_next);
        //文本实例化
        music_name=(TextView)findViewById(R.id.music_name);
        music_time_now=(TextView)findViewById(R.id.music_time_now);
        music_time_max=(TextView)findViewById(R.id.music_time_max);
        //recyclerView实例化
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        //SeekBar初始化
        sb=(SeekBar)findViewById(R.id.music_sb);
        //对按钮监听
        music_start.setOnClickListener(this);
        music_last.setOnClickListener(this);
        music_next.setOnClickListener(this);
    }
    //bofang音乐函数
    public void bofang(int id){
        //如果有音乐播放，则停止播放
        if(mPlayer1!=null){
            mPlayer1.stop();
            mPlayer1.release();
        }
        //通过id号播放不同歌曲
        if(id==0) {
            MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.lemon);
            mPlayer1 = mPlayer;
            mPlayer1.start();
            music_name.setText("lemon");
        }
        if(id==1){
            MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.tttly);
            mPlayer1 = mPlayer;
            mPlayer1.start();
            music_name.setText("You Leave");
        }
        if(id==2){
            MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.smgz);
            mPlayer1 = mPlayer;
            mPlayer1.start();
            music_name.setText("世末歌者");
        }
        if(id==3){
            MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.sxqn);
            mPlayer1 = mPlayer;
            mPlayer1.start();
            music_name.setText("霜雪千年");
        }
        if(id==4){
 //           new Thread(new Runnable() {
 //               @Override
 //               public void run() {
 //                   Uri local= Uri.parse("https://m7.music.126.net/20201209225437/94ea3be7e3f03a4c2e7d862061789efe/ymusic/85fe/3f6a/9326/47a56542ca64ecc86020d8b05aa47a6c.mp3");
  //                  final MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, local);
//                    Message message=new Message();
//                    message.obj=mPlayer;
//                    handler1.sendMessage(message);
            MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.sxqn);
                    mPlayer1=mPlayer;
                    mPlayer1.start();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {

            music_name.setText("Me and You");
//                        }
 //                   });
  //              }
  //          }).start();

        }
        music_id = id;
        n = 1;
        music_start.setBackgroundResource(R.drawable.music_stop); //改变图标
        duration=mPlayer1.getDuration(); //获取最长时间
        music_time_max.setText(duration/60000+":"+duration/1000%60);//转换成分秒赋值
        sb.setMax(duration);                    //给SeekBar设置最大值
        handler.post(run);                  //启动线程
        setSeekBar();
    }
  //  Handler handler1=new Handler(){
   //     @Override
    //    public void handleMessage(@NonNull Message msg) {
     //       super.handleMessage(msg);
     //       mPlayer1 =(MediaPlayer) msg.obj;
     //   }
   // };
//线程中使进度条和当前播放进度建立关系
    Runnable run=new Runnable() {
        @Override
        public void run() {
            sb.setProgress(mPlayer1.getCurrentPosition());
            handler.postDelayed(run,100);
        }
    };
//实现时间显示变化以及实现进度条拖拽
    public void setSeekBar(){
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress!=0){
                    int s=progress/1000;                //获取当前位置计算成分秒格式显示出来
                    String total=s/60+"："+s%60;
                    music_time_now.setText(total);
                }else {
                    music_time_now.setText("00:00");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {  //开始拖动时停止进度条
                handler.removeCallbacks(run);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {  //停下拖动时
                if(seekBar !=null && mPlayer1!=null){
                    mPlayer1.seekTo(seekBar.getProgress());    //获取当前进度条位置并重置音乐播放进度
                    handler.postDelayed(run,1000);
                }
            }
        });
    }

    public void initRecyclerView(){
        //RecyclerVieW设置竖直布局
        final LinearLayoutManager manager=new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        music_Adapter adapter=new music_Adapter(musicList);             //对RecyclerView适配器初始化，传入播放音乐列表
        adapter.setListener(new music_Adapter.OnItemClickListener() {           //对接口实例化
            @Override
            public void OnItemClickForDelete(View view, int id, int position) {  //删除列表中内容并刷新
                musicList.remove(position);
                initRecyclerView();
            }

            @Override
            public void OnItemClickForSelect(View view, int id) {  //点击某个Item时播放音乐
               bofang(id);
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.music_last:
                music_id=(music_id-1)%4;
                if(music_id==-1){music_id=3;}
                bofang(music_id);
                break;
            case R.id.music_start:
                    if(n==-1){
                        MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.lemon);
                        mPlayer1 = mPlayer;
                        mPlayer1.start();
                        music_start.setBackgroundResource(R.drawable.music_stop);
                        n=1;
                        music_name.setText("lemon");
                        duration=mPlayer1.getDuration();
                        music_time_max.setText(duration/60000+":"+duration/1000%60);
                        sb.setMax(duration);
                        handler.post(run);
                        setSeekBar();
                    }else if (n == 1) {
                        mPlayer1.pause();
                        music_start.setBackgroundResource(R.drawable.music_start1);
                        n=0;
                    }else{
                        mPlayer1.start();
                        music_start.setBackgroundResource(R.drawable.music_stop);
                        n=1;
                    }
                    break;
            case R.id.music_next:
                music_id=(music_id+1)%4;
                bofang(music_id);
                break;
            case R.id.music_sb:
                break;
        }
    }

}