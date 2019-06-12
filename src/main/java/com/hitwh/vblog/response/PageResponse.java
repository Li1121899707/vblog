package com.hitwh.vblog.response;

public class PageResponse {
    private Integer start;

    private Integer end;

    private Integer sum;

    private Object arr;

    public static PageResponse create(Integer start, Integer end, Integer sum, Object arr){
        PageResponse pageResponse = new PageResponse();
        pageResponse.start = start;
        pageResponse.end = end;
        pageResponse.sum = sum;
        pageResponse.arr = arr;
        return pageResponse;
    }

    public static PageResponse createBlank(Integer sum){
        PageResponse pageResponse = new PageResponse();
        pageResponse.start = 0;
        pageResponse.end = 0;
        pageResponse.sum = sum;
        pageResponse.arr = null;
        return pageResponse;
    }

    public static PageResponse createBlank(){
        PageResponse pageResponse = new PageResponse();
        pageResponse.start = 0;
        pageResponse.end = 0;
        pageResponse.sum = 0;
        pageResponse.arr = null;
        return pageResponse;
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
