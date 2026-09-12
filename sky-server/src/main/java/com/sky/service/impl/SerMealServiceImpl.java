package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SerMealServiceImpl implements SetMealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增套餐
     */
    @Override
    @Transactional
    public void insert(SetmealDTO setmealDTO) {
        // 1. DTO 拷贝成 Setmeal 实体
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);
        Long setmealId = setmeal.getId();
        List<SetmealDish> dishes = setmealDTO.getSetmealDishes();
        if (dishes != null && !dishes.isEmpty()) {
            dishes.forEach(d -> d.setSetmealId(setmealId));
            // 3. 批量插入套餐菜品关系
            setmealDishMapper.insertBatch(dishes);
        }

    }


    /**
     * 分页查询套餐
     *
     * @return
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        //1.设置分页参数
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        //2.根据分类id查询套餐
        List<SetmealVO> setmealList = setmealMapper.list(setmealPageQueryDTO);
        //3.返回分页结果
        Page<SetmealVO> pageResult = (Page<SetmealVO>) setmealList;
        return new PageResult(pageResult.getTotal(), pageResult.getResult());
    }


    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @Override
    public SetmealVO getById(Long id) {
        SetmealVO setmealVO = setmealMapper.getById(id);
        // 1. 根据套餐id查询套餐菜品关系
        List<SetmealDish> setmealDishList = setmealDishMapper.listBySetmealId(id);
        // 2. 拷贝到套餐VO
        setmealVO.setSetmealDishes(setmealDishList);
        return setmealVO;
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    @Override
    @Transactional
    public void deleteById(List<Long> ids) {
        // 1. 批量删除套餐菜品关系
        setmealDishMapper.deleteBatch(ids);
        // 2. 批量删除套餐
        setmealMapper.deleteBatch(ids);
    }


     /**
      * 修改套餐
      */
    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
       SetmealVO setmealVO=new SetmealVO();
       BeanUtils.copyProperties(setmealDTO, setmealVO);
       setmealMapper.update(setmealVO);
       //1.处理套餐菜品关系
        List<SetmealDish> dishes = setmealDTO.getSetmealDishes();
        //2.批量删除套餐菜品关系
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());
        // 3. 还要给每个 dish 设置套餐ID，否则插入时 setmeal_id 是 null
        dishes.forEach(dish -> dish.setSetmealId(setmealDTO.getId()));
        //4.批量新增套餐菜品关系
        setmealDishMapper.insertBatch(dishes);

    }



}
