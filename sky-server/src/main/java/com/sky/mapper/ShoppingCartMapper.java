package com.sky.mapper;


import com.sky.annotation.AutoFill;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


import java.util.List;

@Mapper
public interface ShoppingCartMapper {


    /**
     * 动态条件查询购物车列表
     *
     * @param shoppingCart 购物车
     * @return 购物车列表
     */
    public List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新购物车数量
     *
     * @param cart 购物车
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    public void updateNumberById(ShoppingCart cart);

    /**
     * 插入购物车
     *
     * @param shoppingCart 购物车
     */
    @AutoFill(OperationType.INSERT)
    @Insert("insert into shopping_cart (user_id, dish_id, setmeal_id, dish_flavor, number, amount, image, name, create_time) values (#{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{image}, #{name}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    /**
     * 根据用户id删除购物车
     *
     * @param userId 用户id
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteByUserId(Long userId);

    /**
     * 根据购物车记录id删除一条
     *
     * @param id 购物车记录id
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);

}
