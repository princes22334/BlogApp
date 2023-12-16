package com.springboot.payload;

import lombok.Data;

import java.util.List;

//Will send this in pagination RestApi
@Data
public class PostResponse {
    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElement;
    private int totalPages;
    private boolean last;

}
