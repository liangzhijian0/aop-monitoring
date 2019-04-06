package com.aop.monitoring.service;

import com.aop.monitoring.aspect.Monitoring;
import com.aop.monitoring.dto.AcitivitySearchCriteria;
import com.aop.monitoring.dto.ActivityDto;
import com.aop.monitoring.po.Activity;
import com.aop.monitoring.repository.ActivityRepository;
import com.aop.monitoring.utils.WrappedBeanCopier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Ocean Liang
 * @date 3/8/2019
 */
@Service
public class ActivityService {

    @Resource
    private ActivityRepository activityRepository;

    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Monitoring
    public Activity getActivityById(String activityId) {
        return activityRepository.findById(activityId).orElse(null);
    }

    public boolean updateActivity(String activityId, Activity newActivity) {
        Activity existedActivity = activityRepository.findById(activityId).orElse(null);
        if (Objects.isNull(existedActivity)) {
            return false;
        }
        existedActivity.setTitle(newActivity.getTitle());
        existedActivity.setContent(newActivity.getContent());
        existedActivity.setStartDate(newActivity.getStartDate());
        existedActivity.setClosingDate(newActivity.getClosingDate());
        activityRepository.save(existedActivity);
        return true;
    }

    public Activity deleteActivity(String activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (Objects.isNull(activity)) {
            return null;
        }
        activityRepository.delete(activity);
        return activity;
    }

    public List<ActivityDto> getActivityByCriteria(AcitivitySearchCriteria searchCriteria) {
        List<Activity> activities;
        if (searchCriteria.getAuthor() != null) {
            if (searchCriteria.getTitle() == null) {
                activities = activityRepository.findAllByAuthor(searchCriteria.getAuthor());
            } else {
                activities = activityRepository.findAllByTitleLikeAndAuthor(searchCriteria.getTitle(), searchCriteria.getAuthor());
            }
        } else {
            if (searchCriteria.getTitle() != null) {
                activities = activityRepository.findAllByTitleLike(searchCriteria.getTitle());
            } else {
                activities = activityRepository.findAll();
            }
        }
        List<ActivityDto> activityDtos = WrappedBeanCopier.copyPropertiesOfList(activities, ActivityDto.class);
        return activityDtos;
    }

}
