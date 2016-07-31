package com.securesw.shopping.error;


public class MemberIdNotFoundException extends NotEqualsException {
    public MemberIdNotFoundException(String msg){
        super(msg);
    }
}
