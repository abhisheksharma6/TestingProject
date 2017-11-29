package com.denimhouse.Activity;


public interface AsynResult<TData> {

    void success(TData data);

    void failure(String error);

    void passItemValue(int value);

}
