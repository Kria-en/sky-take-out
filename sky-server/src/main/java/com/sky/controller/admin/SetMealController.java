package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "套餐管理")
@RequestMapping("/admin/setmeal")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;


    /**
     * 新增套餐
     */
    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        setMealService.insert(setmealDTO);
        return Result.success();
    }

    /**
     * 分页查询套餐
     */
    @GetMapping("/page")
    @ApiOperation("分页查询套餐")
    public Result page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询套餐，参数：{}", setmealPageQueryDTO);
        PageResult pageResult=setMealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据id查询套餐--查询回显套餐菜品
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result getById(@PathVariable Long id){
        SetmealVO setmealVO=setMealService.getById(id);
        return Result.success(setmealVO);
    }


    /**
     * 批量删除套餐
     */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result deleteById(@RequestParam("ids") List<Long> ids){
        log.info("批量删除套餐: ids={} ", ids);
        setMealService.deleteById(ids);
        return Result.success();
    }

    /**
     * 修改套餐
     */
    @PutMapping
    @ApiOperation("修改套餐")
    public Result update(@RequestBody SetmealDTO setmealDTO){
        setMealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐状态更新
     */
    @PostMapping("/status/{status}")
    @ApiOperation("套餐状态更新")
    public Result updateStatus(@PathVariable Integer status, @RequestParam Long id){
        setMealService.updateStatus(id, status);
        return Result.success();
    }


}
