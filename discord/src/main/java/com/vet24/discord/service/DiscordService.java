//package com.vet24.discord.service;
//
//import com.fasterxml.jackson.annotation.JsonView;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.vet24.discord.models.dto.discord.MessageDto;
//import com.vet24.models.util.View;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//public interface DiscordService {
//    @RequestMapping(
//            produces = "application/json",
//            method = RequestMethod.GET)
//    String getWebhook() throws JsonProcessingException;
//
//    @RequestMapping(
//            consumes = "application/json",
//            method = RequestMethod.POST)
//    ResponseEntity<MessageDto> send(@RequestBody MessageDto message,
//                                    @RequestParam(required = false) Long thread_id) throws JsonProcessingException;
//    @RequestMapping( value = "/messages/{message_id}",
//            produces = "application/json",
//            method = RequestMethod.GET)
//    ResponseEntity<MessageDto> getMessageToId(@PathVariable Long message_id,
//                                              @RequestParam(required = false) Long thread_id) throws JsonProcessingException;
//
//    @RequestMapping( value = "/messages/{message_id}",
//            produces = "application/json",
//            method = RequestMethod.PATCH)
//    ResponseEntity<MessageDto> updateMessageToId(@RequestBody MessageDto message,
//                                                 @PathVariable Long message_id,
//                                                 @RequestParam(required = false) Long thread_id) throws JsonProcessingException;
//    @RequestMapping( value = "/messages/{message_id}",
//            produces = "application/json",
//            method = RequestMethod.DELETE)
//    ResponseEntity<MessageDto> deleteMessageToId(@PathVariable Long message_id,
//                                                 @RequestParam(required = false) Long thread_id) throws JsonProcessingException;
//
//}
