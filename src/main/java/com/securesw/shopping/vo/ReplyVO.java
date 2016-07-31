package com.securesw.shopping.vo;



public abstract class ReplyVO {

    public String sequenceIdx;

    public abstract int getIdx();
    public abstract String getSequenceIdx();

    public int getLevel(){
        if(sequenceIdx == null)          return -1;
        if(sequenceIdx.length() != 12)   return -1;

        if(sequenceIdx.endsWith("99"))   return 0;   // 루트
        return 1;   
    }

   
}
