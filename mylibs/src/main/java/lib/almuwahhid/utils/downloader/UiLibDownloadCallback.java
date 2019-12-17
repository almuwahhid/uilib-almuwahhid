package lib.almuwahhid.utils.downloader;

public interface UiLibDownloadCallback {
    void onProcess(UiLibDownloadRequest request);
    void onCancel(UiLibDownloadRequest request);
    void onSuccess(UiLibDownloadRequest request);
}
