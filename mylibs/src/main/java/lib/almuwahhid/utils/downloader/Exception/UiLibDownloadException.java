package lib.almuwahhid.utils.downloader.Exception;

public class UiLibDownloadException extends Exception {
    public static final String REQUEST_IN_PROCESS = "URI Request in process";
    public static final String REQUEST_NOT_IN_PROCESS = "URI Request not in process";
    public UiLibDownloadException(String exception){
        super(exception);
    }
}
