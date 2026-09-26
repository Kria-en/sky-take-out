package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺状态相关接口")
public class ShopStatusController {

    public static final String SHOP_STATUS = "shop_status";

    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("更新店铺状态")
    public Result updateStatus(@PathVariable Integer status) {
        log.info("更新店铺状态: {}", status == 0 ? "打样中" : "营业中");
        redisTemplate.opsForValue().set(SHOP_STATUS, status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("查询店铺状态")
    public Result getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS);
        log.info("查询店铺状态: {}", status == null ? "数据不存在" : (status == 0 ? "打样中" : "营业中"));
        return Result.success(status);
    }
}