package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 新增购物车
     * @param shoppingCartDTO
     */
    void list(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @return
     */
    List<ShoppingCart> getShoppingCartList();

    /**
     * 清空购物车
     */
    void cleanShoppingCart();

    /**
     * 删除购物车中一个商品
     * @param id 购物车商品id
     */
    void deleteCart(Long id);
}
