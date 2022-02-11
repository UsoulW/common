package cn.syag.common;

public class Result{
    private short code;

    public static Result success(){
        Result result=new Result();
        result.code=0;
        return result;
    }

    public static Result fail(){
        return fail((short) 1);
    }

    public static Result fail(int code){
        return fail((short) 1);
    }

    public static Result fail(short code){
        Result result=new Result();
        result.code=code;
        return result;
    }

    public boolean ifOk(){
        return code==0;
    }

    public boolean ifFail(){
        return code!=0;
    }

    public short getCode() {
        return code;
    }
}
