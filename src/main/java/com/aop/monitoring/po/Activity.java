package com.aop.monitoring.po;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


/**
 * @author Ocean Liang
 * @date 3/8/2019
 */
@Data
public class Activity {
    @Id
    private String id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime closingDate;
    @CreatedDate
    private LocalDateTime createDate;

    public Activity(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}