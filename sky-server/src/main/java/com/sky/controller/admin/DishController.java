package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;


    /*
     *新增菜品
     *@param dishDTO
      */
    @PostMapping
    public Result save(@RequestBody @Validated DishDTO dishDTO){
        log.info("新增菜品: {}",dishDTO);
        dishService.save(dishDTO);
        return Result.success(dishDTO);
    }

    /*
     *分页查询菜品
     *@param dishPageQueryDTO
     */
    @GetMapping("/page")
    public Result page(DishPageQueryDTO dishPageQueryDTO){
        log.info("获取分页参数: {}",dishPageQueryDTO);
        PageResult pageResult=dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    /*
     *删除菜品
     *@param dishPageQueryDTO
     */
    @DeleteMapping
    public Result deleteById(@RequestParam("ids") List<Integer> ids){
        log.info("批量删除部门: ids={} ", ids);
        dishService.deleteById(ids);
        return Result.success();
    }
}
