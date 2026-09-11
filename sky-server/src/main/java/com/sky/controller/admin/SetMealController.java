package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        setMealService.insert(setmealDTO);
        return Result.success();
    }





}
