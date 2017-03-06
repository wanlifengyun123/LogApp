package com.yajun.logapp.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;

import com.yajun.logapp.log.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by yajun on 2016/10/18.
 *
 */
public class DownloadHelper {

    private Context mContext;
    private ProgressBar mProgressBar;

    private DownloadManager downloadManager;
    private SharedPreferences prefs;
    private static final String DL_ID = "downloadId";
    private static final String DOWN_LOAD_URL = "http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk";

    public DownloadHelper (Context context,ProgressBar progressBar) {
        this.mContext = context;
        this.mProgressBar = progressBar;
        downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void downLoad(){
        if(prefs.contains(DL_ID)){
            //下载已经开始，检查状态
            queryDownloadStatus();
        }else {
            //开始下载
            Uri resource = Uri.parse(DOWN_LOAD_URL);
            DownloadManager.Request request = new DownloadManager.Request(resource);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setAllowedOverRoaming(false);
            //设置文件类型
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(DOWN_LOAD_URL));
            request.setMimeType(mimeString);
            //在通知栏中显示
            request.setShowRunningNotification(true);
            request.setVisibleInDownloadsUi(true);
            //sdcard的目录下的download文件夹
            request.setDestinationInExternalPublicDir("/download/", "G3.mp4");
            request.setTitle("移动G3广告");
            long id = downloadManager.enqueue(request);
            //保存id
            prefs.edit().putLong(DL_ID, id).apply();
        }
        registerReceiver();
    }

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(prefs.getLong(DL_ID, 0));
        Cursor c = downloadManager.query(query);
        if(c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String downId= c.getString(c.getColumnIndex(DownloadManager.COLUMN_ID));
            String title = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));
            String address = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String size= c.getString(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            String sizeTotal = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

            switch(status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.i("down", "STATUS_PAUSED");
                case DownloadManager.STATUS_PENDING:
                    Log.i("down", "STATUS_PENDING");
                case DownloadManager.STATUS_RUNNING:
                    //正在下载，不做任何事情
                    Log.i("down", "STATUS_RUNNING");
                    Log.i("down", "STATUS_RUNNING :" + sizeTotal+":"+size);
                    int i = Integer.parseInt(size) / Integer.parseInt(sizeTotal);
                    mProgressBar.setProgress(i);
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    //完成
                    Log.i("down", "下载完成");
                    break;
                case DownloadManager.STATUS_FAILED:
                    //清除已下载的内容，重新下载
                    Log.i("down", "STATUS_FAILED");
                    downloadManager.remove(prefs.getLong(DL_ID, 0));
                    prefs.edit().clear().apply();
                    break;
            }
        }
        c.close();
    }

    public void registerReceiver(){
        mContext.registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void unregisterReceiver(){
        mContext.unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //这里可以取得下载的id，这样就可以知道哪个文件下载完成了。适用与多个下载任务的监听
            Log.i("intent", ""+intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
            queryDownloadStatus();
        }
    };

    /**
     * 如果服务器不支持中文路径的情况下需要转换url的编码。
     * @param string
     * @return
     */
    public String encodeGB(String string)
    {
        //转换中文编码
        String split[] = string.split("/");
        for (int i = 1; i < split.length; i++) {
            try {
                split[i] = URLEncoder.encode(split[i], "GB2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            split[0] = split[0]+"/"+split[i];
        }
        split[0] = split[0].replaceAll("\\+", "%20");//处理空格
        return split[0];
    }

}
