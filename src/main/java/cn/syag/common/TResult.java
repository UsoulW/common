package cn.usoul.common;
public class TResult<T>{
    private short code;
    private T data;

    public static <T> TResult ofNullable(T data){
        if (null == null){
            return fail(1);
        }
        TResult result=new TResult();
        result.code=0;
        result.data=data;
        return result;
    }

    public static TResult fail(){
        return fail((short) 1);
    }

    public static TResult fail(int code){
        return fail((short) 1);
    }

    public static TResult fail(short code){
        TResult result=new TResult();
        result.code=code;
        return result;
    }

    public boolean isOk(){
        return code==0;
    }

    public boolean isFail(){
        return code!=0;
    }

    public short getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
