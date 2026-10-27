package com.sky.controller.user;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("userShoppingCartController")
@RequestMapping("/user/shoppingCart")
@Api(tags = "C端购物车相关接口")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;



    /**
     * 添加购物车
     * @param shoppingCartDto
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDto) {
        log.info("添加购物车：{}", shoppingCartDto);
        // 调用服务层方法
        shoppingCartService.list(shoppingCartDto);

        return Result.success();
    }
    /**
     * 查看购物车
     */
    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCart>> list() {
        log.info("查看购物车");
        List<ShoppingCart> list = shoppingCartService.getShoppingCartList();
        return Result.success(list);
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clean() {
        log.info("清空购物车");
        shoppingCartService.cleanShoppingCart();
        return Result.success();
    }
    /**
     * 删除购物车中一个商品
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除购物车中一个商品")
    public Result delete(@RequestParam Long id) {
        log.info("删除购物车商品：{}", id);
        shoppingCartService.deleteCart(id);
        return Result.success();
    }
}
