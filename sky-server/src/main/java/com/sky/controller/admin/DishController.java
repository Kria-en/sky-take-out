package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("新增菜品")
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
    @ApiOperation("分页查询菜品")
    public Result page(DishPageQueryDTO dishPageQueryDTO){
        log.info("获取分页参数: {}",dishPageQueryDTO);
        PageResult pageResult=dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    /*
     *批量删除菜品
     *@param ids
     */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result deleteById(@RequestParam("ids") List<Long> ids){
        log.info("批量删除菜品: ids={} ", ids);
        dishService.deleteById(ids);
        return Result.success();
    }



    /*
     *根据ID查询菜品--查询回显
     *@param id
     */
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询菜品--查询回显")
    public Result getById(@PathVariable Long id){
        log.info("根据Id查询菜品,id= {}",id);
        DishVO dishVO =dishService.getById(id);
        return Result.success(dishVO);
    }

    /*
     *修改菜品
     *@param dishDTO
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody @Validated DishDTO dishDTO){
        log.info("修改菜品: {}",dishDTO);
        dishService.update(dishDTO);
        return Result.success(dishDTO);
    }

    /*
     *修改菜品状态
     *@param status
     *@param id
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品状态")
    public Result updateStatus(@PathVariable Integer status,  Long id){
        log.info("修改菜品状态: status={},id= {}",status,id);
        dishService.updateStatus(status,id);
        return Result.success();
    }

    /*
     *根据分类id查询菜品
     *@param categoryId
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result list(@RequestParam Long categoryId){
        log.info("根据分类id查询菜品,categoryId= {}",categoryId);
        List<DishVO> dishVOList = dishService.listByCategoryId(categoryId);
        return Result.success(dishVOList);
    }


}
