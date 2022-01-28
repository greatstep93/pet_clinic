package com.vet24.web.controllers.user;

import com.github.database.rider.core.api.dataset.DataSet;
import com.vet24.dao.user.CommentDao;
import com.vet24.models.user.Comment;
import com.vet24.service.user.UserServiceImpl;
import com.vet24.web.ControllerAbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserCommentControllerTest extends ControllerAbstractIntegrationTest {

    final String URI = "/api/user/comment/{commentId}";
    private String token;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserServiceImpl userService;

    @Before
    public void setToken() throws Exception {
        token = getAccessToken("user3@gmail.com","user3");
    }

    @Test
    @DataSet(cleanBefore = true, value = {"/datasets/user-entities.yml", "/datasets/comments.yml"})
    public void likeOrDislikeComment() throws Exception {
        assertTrue(userService
                .getWithAllCommentReactions("user3@gmail.com")
                .getCommentReactions().isEmpty());
        mockMvc.perform(MockMvcRequestBuilders.put(URI + "/{positive}", 101, true)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertEquals(1, userService
                .getWithAllCommentReactions("user3@gmail.com")
                .getCommentReactions().size());
        Assert.assertEquals(true, userService
                .getWithAllCommentReactions("user3@gmail.com")
                .getCommentReactions().get(0).getPositive());
    }

    @Test
    @DataSet(cleanBefore = true, value = {"/datasets/user-entities.yml", "/datasets/comments.yml"})
    public void updateComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(URI, 101)
                        .header("Authorization", "Bearer " + token)
                        .content("{\n" +
                            "  \"id\": 0,\n" +
                            "  \"content\": \"aaassdd\",\n" +
                            "  \"dateTime\": \"2021-09-28T15:04:12.327Z\",\n" +
                            "  \"likes\": 0,\n" +
                            "  \"dislike\": 0,\n" +
                            "  \"userInfoDto\": {\n" +
                            "    \"id\": 0,\n" +
                            "    \"email\": \"string\",\n" +
                            "    \"firstname\": \"string\",\n" +
                            "    \"lastname\": \"string\"\n" +
                            "  }\n" +
                            "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Comment after = commentDao.getByKey(101L);
        Assert.assertEquals("aaassdd", after.getContent());
        Assert.assertEquals(Long.valueOf(3L), after.getUser().getId());
    }

    @Test
    @DataSet(cleanBefore = true, value = {"/datasets/user-entities.yml", "/datasets/comments.yml"})
    public void updateNotYoursComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(URI, 104)
                        .header("Authorization", "Bearer " + token)
                        .content("{\n" +
                                "  \"id\": 0,\n" +
                                "  \"content\": \"Текст которого не должно быть в content\",\n" +
                                "  \"dateTime\": \"2021-09-28T15:04:12.327Z\",\n" +
                                "  \"likes\": 0,\n" +
                                "  \"dislike\": 0,\n" +
                                "  \"userInfoDto\": {\n" +
                                "    \"id\": 0,\n" +
                                "    \"email\": \"string\",\n" +
                                "    \"firstname\": \"string\",\n" +
                                "    \"lastname\": \"string\"\n" +
                                "  }\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        Comment after = commentDao.getByKey(104L);
        Assert.assertNotEquals("Текст которого не должно быть в content", after.getContent());
        Assert.assertNotEquals(Long.valueOf(3L), after.getUser().getId());
    }

    @Test
    @DataSet(cleanBefore = true, value = {"/datasets/user-entities.yml", "/datasets/comments.yml"})
    public void removeComment() throws Exception {
        assertTrue(commentDao.isExistByKey(101L));
        mockMvc.perform(MockMvcRequestBuilders.delete(URI, 101)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertFalse(commentDao.isExistByKey(101L));
    }

    @Test
    @DataSet(cleanBefore = true, value = {"/datasets/user-entities.yml", "/datasets/comments.yml"})
    public void removeNotYoursComment() throws Exception {
        assertTrue(commentDao.isExistByKey(104L));
        mockMvc.perform(MockMvcRequestBuilders.delete(URI, 104)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        assertTrue(commentDao.isExistByKey(104L));
    }

    @Test
    @DataSet(cleanBefore = true, value = {"/datasets/user-entities.yml", "/datasets/comments.yml"})
    public void shouldNotFoundResponse() throws Exception {
        assertFalse(commentDao.isExistByKey(245L));
        mockMvc.perform(MockMvcRequestBuilders.delete(URI, 245)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        assertFalse(commentDao.isExistByKey(350L));
        mockMvc.perform(MockMvcRequestBuilders.put(URI + "/{positive}", 350, false)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}