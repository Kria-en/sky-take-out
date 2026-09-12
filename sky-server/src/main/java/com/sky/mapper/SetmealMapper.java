package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

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
    @Insert("insert into setmeal (name, image, category_id, price, description, status, create_time, update_time,create_user,update_user) " +
            "values (#{name}, #{image}, #{categoryId}, #{price}, #{description}, #{status}, #{createTime}, #{updateTime},#{createUser},#{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Setmeal setmealDTO);

    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    List<SetmealVO> list(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    SetmealVO getById(Long id);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 修改套餐
     * @param setmealVO
     */
    @AutoFill(OperationType.UPDATE)
    void update(SetmealVO setmealVO);

    /**
     * 套餐状态更新
     * @param id
     * @param status
     */
    @AutoFill(OperationType.UPDATE)
    @Update("update setmeal set status = #{status} where id = #{id}")
    void updateStatus(Long id, Integer status);
}
