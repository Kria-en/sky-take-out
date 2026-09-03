package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishMapper dishMapper;
    /*
     *新增菜品
     *@param dishDTO
     */

    @Override
    public void save(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dish.setStatus(1);
        dishMapper.insert(dish);
    }

    /*
     *分页查询菜品
     *@param dishPageQueryDTO
     */

    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        //设置分页参数
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        //执行查询
        List<Dish> dishList=dishMapper.list(dishPageQueryDTO);
        //封装分页结果
        Page<Dish> p=(Page<Dish>)dishList;
        return new PageResult(p.getTotal(),p.getResult());
    }

    /*
     *删除菜品
     *@param dishPageQueryDTO
     */

    @Override
    public void deleteById(List<Integer> ids) {
        dishMapper.deleteById(ids);
        dishService.deleteById(ids);
    }
}
