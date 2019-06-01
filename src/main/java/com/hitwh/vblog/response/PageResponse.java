package com.hitwh.vblog.response;

public class PageResponse {
    private Integer start;

    private Integer end;

    private Integer sum;

    private Object arr;

    public PageResponse(Integer start, Integer end, Integer sum, Object arr){
        this.start = start;
        this.end = end;
        this.sum = sum;
        this.arr = arr;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Object getArr() {
        return arr;
    }

    public void setArr(Object arr) {
        this.arr = arr;
    }
}
