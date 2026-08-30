package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工管理")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }



    /**
     * 新增员工
     *
     * @param employeeDTO
     * @return
     */

    @PostMapping
    @ApiOperation("新增员工")
    public Result save(@RequestBody @Validated EmployeeDTO employeeDTO){
        log.info("新增员工, {}",employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }



    /**
     * 分页查询员工
     *
     * @param employeePageQueryDTO
     * @return pageResult
     */

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("查询请求参数: {}",employeePageQueryDTO);
        PageResult pageResult=employeeService.page(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用员工账号
     *
     * @Param status
     * @Param id
     * @return
     */
    @PostMapping("status/{status}")
    @ApiOperation("启用禁用")
    public Result startOrStop(@PathVariable Integer status,@RequestParam Long id){
        log.info("启用或禁用员工账号, {},{}",status,id);
        employeeService.startOrStop(status,id);
        return Result.success();

    }

    /**
     * 根据id查询回显
     *
     * @Param id
     */
    @GetMapping("/{id}")
    @ApiOperation("查询回显")
    public Result getById(@PathVariable Long id){
        log.info("根据id查询: {}",id);
        Employee employee=employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 修改员工信息
     *
     * @Param employee
     * @return employee
     */
    @PutMapping
    @ApiOperation("修改员工信息")
    public Result update(@RequestBody Employee employee){
        log.info("修改员工信息: {}", employee);
        employeeService.update(employee);
        return Result.success(employee);
    }

    /**
     * 修改密码
     *
     * @Param password
     * @return
     */
    @PutMapping("/editPassword")
    @ApiOperation("修改密码")
    public Result editPassword(@RequestBody PasswordEditDTO passwordEditDTO){
        Long empId= BaseContext.getCurrentId();
        log.info("修改密码, 员工ID: {}", empId);
        employeeService.updatePassword(passwordEditDTO);
        return Result.success();
    }


}
