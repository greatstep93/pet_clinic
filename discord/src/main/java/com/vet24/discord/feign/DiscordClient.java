package com.vet24.discord.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("discord")
public interface DiscordClient {

}
