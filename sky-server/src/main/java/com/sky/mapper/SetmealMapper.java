package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);


    /**
     * 新增套餐
     * @param setmealDTO
     */
    @AutoFill(OperationType.INSERT)
    @Insert("insert into setmeal (name, image, category_id, price, description, status, create_time, update_time) " +
            "values (#{name}, #{image}, #{categoryId}, #{price}, #{description}, #{status}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Setmeal setmealDTO);

    /**
     * 根据分类id查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    List<SetmealVO> list(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    SetmealVO getById(Integer id);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
