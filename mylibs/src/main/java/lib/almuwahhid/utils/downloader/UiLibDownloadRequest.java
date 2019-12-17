package lib.almuwahhid.utils.downloader;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;

public class UiLibDownloadRequest {
    private Uri uri;
    private String fileName;
    private Long id;

    private Uri destinationUri = null;
    private int notificationVisibility = DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED;
    private boolean allowOverMeter = true;
    private boolean allowRoaming = true;

    public UiLibDownloadRequest(Uri uri, String fileName) {
        this.uri = uri;
        this.fileName = fileName;
    }

    public UiLibDownloadRequest(Uri uri) {
        this.uri = uri;
        this.fileName = uri.getLastPathSegment();
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uri getDestinationUri() {
        if(destinationUri == null){
            destinationUri = Uri.withAppendedPath(
                    Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
                    ,getFileName());
        }
        return destinationUri;
    }

    public UiLibDownloadRequest setDestinationUri(Uri destinationUri) {
        this.destinationUri = Uri.withAppendedPath(destinationUri,getFileName());
        return this;
    }

    public UiLibDownloadRequest setNotificationVisibility(int notificationVisibility) {
        this.notificationVisibility = notificationVisibility;
        return this;
    }

    public UiLibDownloadRequest setAllowOverMeter(boolean allowOverMeter) {
        this.allowOverMeter = allowOverMeter;
        return this;
    }

    public UiLibDownloadRequest setAllowRoaming(boolean allowRoaming) {
        this.allowRoaming = allowRoaming;
        return this;
    }

    public DownloadManager.Request getDownloadRequest(){
        if(destinationUri == null){
            destinationUri = Uri.withAppendedPath(
                    Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
                    ,getFileName());
        }

        return new DownloadManager.Request(getUri())
                .setNotificationVisibility(notificationVisibility)
                .setDestinationUri(destinationUri)
                .setAllowedOverMetered(allowOverMeter)
                .setAllowedOverRoaming(allowRoaming);
    }
}
