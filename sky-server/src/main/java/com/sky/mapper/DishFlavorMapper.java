package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface DishFlavorMapper {

    // 批量插入菜品口味
    void insertBatch(List<DishFlavor> flavors);

    // 根据菜品id删除口味（修改菜品时用）
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    // 根据菜品id批量删除口味（批量删除菜品时用）
    void deleteByDishIds(List<Long> dishIds);

    // 根据菜品id查询口味（回显时用，先写好）
    @Select("select dish_flavor.name,dish_flavor.value from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}