package com.vet24.discord.feign;

import com.vet24.discord.service.DiscordService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "api-service",url = "https://discord.com/api/webhooks/1002137746204278864/fFu57DcvOGhFfdI20eEzI6XuWaRdQaFKvo4NPi3P0Ey2T8b_rk3E1p7pTRMZ5vJc_ZDt?wait=true")
public interface DiscordClient extends DiscordService {

}
