package lib.almuwahhid.utils.animation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface ListenableFuture<T> extends Future<T> {

    public interface Listener<T> {
        void onFailure(ExecutionException executionException);

        void onSuccess(T t);
    }

    void addListener(Listener<T> listener);
}
