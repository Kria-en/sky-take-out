package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {



    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);


    /*
     *新增菜品
     *@param dishDTO
     */
    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into dish(id, name, category_id, price, image, description, status, create_time, update_time, create_user, update_user) " +
            "VALUES (#{id},#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Dish dish);

    /*
     *分页查询菜品
     *@param dishPageQueryDTO
     */
    List<Dish> list(DishPageQueryDTO dishPageQueryDTO);

    /*
     *删除菜品
     *@param dishPageQueryDTO
     */
    void deleteById(List<Long> ids);

    /*
     *根据ID查询菜品--查询回显
     *@param id
     */
    @Select("select * from dish where id=#{id}")
    Dish getById(Long id);

    /*
     *修改菜品
     *@param dishDTO
     */
    @AutoFill(OperationType.UPDATE)
    void updateById(Dish dish);
}
