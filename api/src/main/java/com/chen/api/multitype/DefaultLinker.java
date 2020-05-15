package com.chen.api.multitype;

import android.support.annotation.NonNull;

final class DefaultLinker<T> implements Linker<T> {

    @Override
    public int index(int position, @NonNull T t) {
        return 0;
    }
}
