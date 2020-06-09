package com.ming;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ming.entity.User;
import com.ming.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class MpdemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询所有
     */
    @Test
    public void finaAll() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);

    }

    /**
     * 增加操作
     */
    @Test
    public void addUser(){

        User user = new User();
        user.setName("ming123");
        user.setAge(10);
        user.setEmail("alice@qq.com");

        int insert = userMapper.insert(user);
        System.out.println("insert:"+insert);
    }

    /**
     * 修改操作
     */
    @Test
    public void updateUser(){
        User user = new User();
        user.setId(1270200401429311490L);
        user.setAge(120);

        int row = userMapper.updateById(user);
        System.out.println(row);

    }

    /**
     * 测试乐观锁，版本号加 1
     */
    @Test
    public void testOptimisticLocker(){

        //根据id查询数据
        User user = userMapper.selectById(1270200401429311490L);

        //进行修改
        user.setAge(200);
        userMapper.updateById(user);

    }

    /**
     * 多个id批量查询
     */
    @Test
    public void testSelectDemo1(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
        System.out.println(users);

    }

    /**
     * 分页查询
     */
    @Test
    public void testPage(){
        //1.创建page对象
        //传入两个参数：当前页  和   每页显示记录数
        Page<User> page = new Page<>(1, 3);
        //调用mp分页查询的方法
        //调用mp分页查询过程中，底层封装
        //把分页所有数据封装到page对象里面
        userMapper.selectPage(page,null);

        //通过page对象获取分页数据
        System.out.println(page.getCurrent());  //当前页
        System.out.println(page.getRecords());  //每页数据list集合
        System.out.println(page.getSize());     //每页显示记录数
        System.out.println(page.getTotal());    //总记录数
        System.out.println(page.getPages());    //总页数

        //这两个返回true和false，判断用的
        System.out.println(page.hasNext());     //下一页
        System.out.println(page.hasPrevious()); //上一页
    }

    /**
     * 删除操作   物理删除
     */
    @Test
    public void deleteUser(){
        int result = userMapper.deleteById(1270228737849327617L);
        System.out.println(result);

    }

    /**
     * 批量删除
     */
    @Test
    public void deleteBatchIds(){
        int result = userMapper.deleteBatchIds(Arrays.asList(2L, 8L));
        System.out.println(result);
    }

    /**
     * mp实现复杂查询操作
     */
    @Test
    public void testSelectQuery(){
        //创建QueryWrapper对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        //通过QueryWrapper设置条件
        //ge、gt、le、lt
        //>=  >   <=   <
        //查询age>=30的记录  数据库字段的名字
//        wrapper.ge("age",30);

        //eq、ne
        //eq是等于，ne是不等于
//        wrapper.ne("name","tom");

        //between  查询年龄  20-30
//        wrapper.between("age",20,30);

        //like  模糊查询
//        wrapper.like("name","om");

        //orderByDesc       排序操作
//        wrapper.orderByDesc("id");

        //last  拼接sql语句
//        wrapper.last("limit 1");

        //指定要查询的列（指定列）
        wrapper.select("id","name");

        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }

}
