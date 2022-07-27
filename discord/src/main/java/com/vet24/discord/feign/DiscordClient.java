package com.vet24.discord.feign;

import com.vet24.discord.service.DiscordService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "api-service",url = "https://discord.com")
public interface DiscordClient extends DiscordService {

}
