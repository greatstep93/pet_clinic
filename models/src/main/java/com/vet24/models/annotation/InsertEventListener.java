package com.vet24.models.annotation;

import com.vet24.models.user.User;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;

import static com.vet24.models.secutity.SecurityUtil.getPrincipalOrNull;
import static com.vet24.models.secutity.SecurityUtil.getSecurityUserOrNull;

public class InsertEventListener implements PreInsertEventListener {

    public static final InsertEventListener INSTANCE =
            new InsertEventListener();


    @Override
    public boolean onPreInsert(PreInsertEvent preInsertEvent) {
        final Object entity = preInsertEvent.getEntity();

        for (Field fields : entity.getClass().getDeclaredFields()) {
            if (fields.isAnnotationPresent(CreateAuthor.class)) {

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    continue;
                }
                try {

//                    User activeUser = getSecurityUserOrNull();
                    User activeUser = (User) getPrincipalOrNull();


                    fields.setAccessible(true);
                    fields.set(entity, activeUser);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}