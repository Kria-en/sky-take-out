package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 动态条件查询购物车列表
     *
     * @param shoppingCartDTO 购物车
     */
    @Override
    @Transactional
    public void list(ShoppingCartDTO shoppingCartDTO) {
        //判断当前加入到购物车的商品是否存在
        ShoppingCart shoppingCart=new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        long userId= BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        //如果已经存在了,只需要将数量加一
        if(list != null && list.size()>0){
            ShoppingCart cart = list.get(0);
            //将数量加一
            cart.setNumber(cart.getNumber()+1);
            //将购物车更新到数据库
            shoppingCartMapper.updateNumberById(cart);
        }else{
            //如果不存在,则直接加入购物车

            //判断本次添加到购物车的是菜品还是套餐
            Long dishId = shoppingCartDTO.getDishId();
            if(dishId != null){
                //是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setDishId(dishId);
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setName(dish.getName());
            }else{
                //是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                SetmealVO setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setSetmealId(setmealId);
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setName(setmeal.getName());
            }
            shoppingCart.setNumber(1);
            shoppingCartMapper.insert(shoppingCart);
        }
    }
    /**
     * 查看当前用户的购物车列表
     */
    @Override
    public List<ShoppingCart> getShoppingCartList() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart queryCart = new ShoppingCart();
        queryCart.setUserId(userId);
        return shoppingCartMapper.list(queryCart);
    }

    /**
     * 清空当前用户的购物车
     */
    @Override
    public void cleanShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }

    /**
     * 删除购物车中一个商品
     * @param id 购物车商品id
     */

    @Override
    @Transactional
    public void deleteCart(Long id) {
        shoppingCartMapper.deleteById(id);
    }
}
