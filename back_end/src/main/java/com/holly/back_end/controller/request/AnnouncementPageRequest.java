package com.holly.back_end.controller.request;

import lombok.Data;

@Data
public class AnnouncementPageRequest extends BaseRequest{
    private String name;
    private String title;
    private String content;

}
