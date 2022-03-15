package com.vet24.web.controllers.notification;


import com.vet24.models.dto.notification.UserNotificationDto;
import com.vet24.models.mappers.notification.UserNotificationMapper;
import com.vet24.models.notification.UserNotification;
import com.vet24.models.user.User;
import com.vet24.service.notification.UserNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user/notification")
public class UserNotificationController {

    private final UserNotificationService userNotificationService;
    private final UserNotificationMapper userNotificationMapper;

    @Autowired
    public UserNotificationController(UserNotificationService userNotificationService,
                                      UserNotificationMapper userNotificationMapper) {
        this.userNotificationService = userNotificationService;
        this.userNotificationMapper = userNotificationMapper;
    }
    @Operation(summary = "Getting all notifications for the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification list received"),
            @ApiResponse(responseCode = "404", description = "Notification list not found")
    })
    @GetMapping("")
    public ResponseEntity<List<UserNotificationDto>> getAllNotifications() {

        List<UserNotification> userNotificationList = userNotificationService.getAll();

        return new ResponseEntity<>(userNotificationMapper.toDto(userNotificationList), HttpStatus.OK);
    }

    @Operation(summary = "Receive notification by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification received"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @GetMapping("/{notificationId}")
    public ResponseEntity<UserNotificationDto> getUserNotificationById(@PathVariable("notificationId") Long notificationId) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserNotification userNotification = userNotificationService.getByKey(notificationId);

        if (user.equals(userNotification.getUser())) {
            return new ResponseEntity<>(userNotificationMapper.toDto(userNotification), HttpStatus.OK);
        } else {
            log.info("Notification is not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{notificationId}")
    public void notificationsStatus(@PathVariable("notificationId") Long notificationId) {

        UserNotification userNotification = userNotificationService.getByKey(notificationId);
        userNotification.setShow(false);
        userNotificationService.update(userNotification);

    }
}
