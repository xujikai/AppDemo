package com.xjk.android.utils;

import com.xjk.android.R;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by xxx on 2016/12/27.
 */

public class CloseUtils {

    private CloseUtils() {
        throw new UnsupportedOperationException(UIUtils.getString(R.string.error_instantiate));
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

}
