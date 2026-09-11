package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 新增套餐菜品关系
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);
}
