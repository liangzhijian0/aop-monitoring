package com.aop.monitoring.controller;

import com.aop.monitoring.service.ActivityService;
import com.aop.monitoring.dto.AcitivitySearchCriteria;
import com.aop.monitoring.dto.ResponseDto;
import com.aop.monitoring.po.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Ocean Liang
 * @date 3/8/2019
 */
@Slf4j
@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Resource
    private ActivityService activityService;

    @PostMapping
    public ResponseDto createActivity(@RequestBody Activity activity) {
        if (Objects.isNull(activity.getAuthor()) || Objects.isNull(activity.getTitle())
                || Objects.isNull(activity.getContent())) {
            return ResponseDto.fail("author,title,content should not be empty");
        }
        activityService.createActivity(activity);
        return ResponseDto.success(activity);
    }

    @GetMapping("/{activityId}")
    public ResponseDto getActivityById(@PathVariable String activityId) {
        Activity activity = activityService.getActivityById(activityId);
        if (Objects.isNull(activity)) {
            return ResponseDto.fail("can not find activity");
        }
        return ResponseDto.success(activity);
    }

    @GetMapping
    public ResponseDto getActivityByCriteria(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "author", required = false) String author) {
        AcitivitySearchCriteria searchCriteria = new AcitivitySearchCriteria();
        searchCriteria.setTitle(title);
        searchCriteria.setAuthor(author);
        return ResponseDto.success(activityService.getActivityByCriteria(searchCriteria));
    }

    @PutMapping("/{activityId}")
    public ResponseDto updateActivity(@PathVariable String activityId, @RequestBody Activity newActivity) {
        if (activityService.updateActivity(activityId, newActivity)) {
            return ResponseDto.success();
        }
        return ResponseDto.fail("update activity failed");
    }

    @DeleteMapping("/{activityId}")
    public ResponseDto deleteActivity(@PathVariable String activityId) {
        Activity deletedActivity = activityService.deleteActivity(activityId);
        if (Objects.isNull(deletedActivity)) {
            return ResponseDto.fail("delete activity failed");
        }
        return ResponseDto.success(deletedActivity);
    }
}
