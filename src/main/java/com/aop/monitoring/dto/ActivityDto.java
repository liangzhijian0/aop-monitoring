package com.aop.monitoring.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ocean Liang
 * @date 3/13/2019
 */
@Data
public class ActivityDto {
    private String id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime closingDate;

}
