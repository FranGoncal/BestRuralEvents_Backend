package com.bestRuralEvents.NotificationService.service;

import com.bestRuralEvents.NotificationService.models.DeviceToken;
import com.bestRuralEvents.NotificationService.models.Notification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirebasePushService {

    public void sendPushToUserDevices(Notification notification, List<DeviceToken> tokens) {
        for (DeviceToken deviceToken : tokens) {
            try {
                Message message = Message.builder()
                        .setToken(deviceToken.getToken())
                        .setNotification(
                                com.google.firebase.messaging.Notification.builder()
                                        .setTitle(notification.getTitle())
                                        .setBody(notification.getText())
                                        .build()
                        )
                        .putData("notificationId", String.valueOf(notification.getId()))
                        .putData("type", notification.getType().name())
                        .putData("relatedEntityType", notification.getRelatedEntityType() == null ? "" : notification.getRelatedEntityType())
                        .putData("relatedEntityId", notification.getRelatedEntityId() == null ? "" : String.valueOf(notification.getRelatedEntityId()))
                        .build();

                String response = FirebaseMessaging.getInstance().send(message);
                System.out.println("FCM sent successfully: " + response);

            } catch (Exception e) {
                System.err.println("Failed to send FCM notification to token: " + deviceToken.getToken());
                System.err.println(e.getMessage());
            }
        }
    }
}