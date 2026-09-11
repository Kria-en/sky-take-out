package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;


public interface SetMealService {
    /**
     * 新增套餐
     */
    void insert(SetmealDTO setmealDTO);

    /**
     * 分页查询套餐
     *
     * @return
     */
    PageResult page(SetmealPageQueryDTO setmealDTO);

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    SetmealVO getById(Integer id);
}
