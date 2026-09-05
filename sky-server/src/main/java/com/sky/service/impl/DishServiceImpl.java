package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private DishMapper dishMapper;
    /*
     *新增菜品
     *@param dishDTO
     */

    @Override
    @Transactional
    public void save(DishDTO dishDTO) {
        // 1. 保存菜品基本信息到 dish 表
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dish.setStatus(1);
        dishMapper.insert(dish);
        // 2. 拿到菜品自增ID
        Long dishId = dish.getId();

        // 3. 处理口味数据（给每个口味设置 dishId，然后批量插入）
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            // 遍历给每个口味绑定菜品ID
            flavors.forEach(flavor -> flavor.setDishId(dishId));
            // 批量插入 dish_flavor 表
            dishFlavorMapper.insertBatch(flavors);
        }
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
     *批量删除菜品
     *@param dishPageQueryDTO
     */
    @Override
    @Transactional
    public void deleteById(List<Long> ids) {
        dishMapper.deleteById(ids);
        dishFlavorMapper.deleteByDishIds(ids);
    }

    /*
     *根据ID查询菜品--查询回显
     *@param id
     */
    @Override
    public DishVO getById(Long id) {
        Dish dish=dishMapper.getById(id);
        DishVO dishVO=new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavorMapper.getByDishId(id));
        return dishVO;
    }

    /*
     *修改菜品
     *@param dishDTO
     */
    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        // 1. 更新保存菜品基本信息到 dish 表
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.updateById(dish);
        // 2. 处理口味数据（给每个口味设置 dishId，然后批量插入）
        List<DishFlavor> flavors = dishDTO.getFlavors();
        // 3. 先根据菜品id删除口味（修改菜品时用）
        dishFlavorMapper.deleteByDishId(dish.getId());
        // 4. 再根据菜品id批量插入口味（修改菜品时用）
        if (flavors != null && flavors.size() > 0) {
            // 遍历给每个口味绑定菜品ID
            flavors.forEach(flavor -> flavor.setDishId(dish.getId()));
            // 批量插入 dish_flavor 表
            dishFlavorMapper.insertBatch(flavors);
        }
    }
    /*
     *修改菜品状态
     *@param status
     *@param id
     */
    @Override
    @Transactional
    public void updateStatus(Integer status, Long id) {
        dishMapper.updateStatus(status,id);
    }


    /*
     *根据分类id查询菜品
     *@param categoryId
     */
    @Override
    public List<DishVO> listByCategoryId(Long categoryId) {
        return dishMapper.listByCategoryId(categoryId);
    }
}
