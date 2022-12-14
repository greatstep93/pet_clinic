package com.vet24.dao.news;

import com.vet24.dao.ReadWriteDao;
import com.vet24.models.dto.user.ClientNewsResponseDto;
import com.vet24.models.news.News;

import java.util.List;


public interface NewsDao extends ReadWriteDao<Long, News> {
    List<ClientNewsResponseDto> getClientNewsResponseDto();

    List<News> getNewsById(List<Long> ids);

    void publishNews(List<Long> ids);

    void unpublishNews(List<Long> ids);
}
