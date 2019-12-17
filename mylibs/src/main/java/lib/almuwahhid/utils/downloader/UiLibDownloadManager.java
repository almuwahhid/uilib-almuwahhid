package lib.almuwahhid.utils.downloader;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;



import java.util.ArrayList;

import lib.almuwahhid.R;
import lib.almuwahhid.utils.downloader.Exception.UiLibDownloadException;


public class UiLibDownloadManager {

    private Context mContext;
    private DownloadManager dm;
    private ArrayList<UiLibDownloadRequest> mList;
    private UiLibDownloadCallback mCallback;
    private BroadcastReceiver receiver;

    private int notificationId = 1;

    private static final String CHANNEL_ID = "GTDOWNLOADER";
    private static final String GROUP_GTDOWNLOADER = "NOTIFICATION_GTDOWNLOADER";

    public UiLibDownloadManager(Context context, UiLibDownloadCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                if (id != -1) {

                    try {
                        UiLibDownloadRequest downloadRequest = getRequestById(id);
                        mList.remove(downloadRequest);
                        if (mCallback != null) {
                            if (isSuccessful(id)) {
//                                addNotification(downloadRequest,
//                                        mContext.getResources().getString(R.string.download_complete));
                                mCallback.onSuccess(downloadRequest);
                            } else {
//                                addNotification(downloadRequest,
//                                        mContext.getResources().getString(R.string.download_canceled));
                                mCallback.onCancel(downloadRequest);
                            }
                        }
                    } catch (UiLibDownloadException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        mContext.registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        this.mList = new ArrayList<>();
        this.dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }



    public void onDestroy(){
        if(mList!=null) {
            mList.clear();
            mList = null;
        }
        mContext.unregisterReceiver(receiver);
    }

    public void startRequest(UiLibDownloadRequest request) throws UiLibDownloadException {
        if(checkUriInProcess(request)) {
            throw new UiLibDownloadException(UiLibDownloadException.REQUEST_IN_PROCESS);
        } else {
            Long id = dm.enqueue(request.getDownloadRequest());
            request.setId(id);
            mList.add(request);
            if (mCallback != null)
                mCallback.onProcess(request);
        }
    }

    public void cancelDownload(UiLibDownloadRequest request){
        try {
            UiLibDownloadRequest downloadRequest = getRequestByUri(request);
            dm.remove(downloadRequest.getId());
        } catch (UiLibDownloadException e) {
            e.printStackTrace();
        }
    }

    private boolean isSuccessful(long id){
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id).setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
        Cursor cursor = dm.query(query);
        return cursor.moveToFirst();
    }

    private boolean checkUriInProcess(UiLibDownloadRequest request){
        for(UiLibDownloadRequest downloadRequest : mList){
            if (downloadRequest.getUri().equals(request.getUri())){
                return true;
            }
        }
        return false;
    }

    private UiLibDownloadRequest getRequestByUri(UiLibDownloadRequest request) throws UiLibDownloadException {
        for(UiLibDownloadRequest downloadRequest : mList){
            if (downloadRequest.getUri().equals(request.getUri())){
                return downloadRequest;
            }
        }
        throw new UiLibDownloadException(UiLibDownloadException.REQUEST_NOT_IN_PROCESS);
    }

    private UiLibDownloadRequest getRequestById(Long id) throws UiLibDownloadException {
        for(UiLibDownloadRequest downloadRequest : mList){
            if (downloadRequest.getId().equals(id)){
                return downloadRequest;
            }
        }
        throw new UiLibDownloadException(UiLibDownloadException.REQUEST_NOT_IN_PROCESS);
    }
}
