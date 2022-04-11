package com.vet24.dao.notification.dto;

import com.vet24.models.dto.notification.MailNotification;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LocalDateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationDtoDaoImpl implements NotificationDtoDao{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<MailNotification> getEmailsAndContentsForNotifications(LocalDate eventDate) {
        List<MailNotification> mailNotifications = new ArrayList<>();
        mailNotifications.addAll(
                manager.createNativeQuery(
                                "SELECT user_notification.id, user_entities.email, Notification.content, Notification.event_date " +
                                        "FROM user_notification INNER JOIN user_entities " +
                                        "ON user_notification.user_id = user_entities.id " +
                                        "INNER JOIN Notification " +
                                        "ON user_notification.notification_id = Notification.id " +
                                        "WHERE Notification.event_date = :event_date")
                        .setParameter("event_date", eventDate)
                        .unwrap(org.hibernate.query.Query.class)
                        .unwrap(SQLQuery.class)
                        .addScalar("id", LongType.INSTANCE)
                        .addScalar("email", StringType.INSTANCE)
                        .addScalar("content", StringType.INSTANCE)
                        .addScalar("event_date", LocalDateType.INSTANCE)
                        .setResultTransformer(Transformers.aliasToBean(MailNotification.class))
                        .getResultList());
        return mailNotifications;
    }
}
