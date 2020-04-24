package com.dm.service.system.user;

import com.dm.beans.AuthToken;
import com.dm.domain.system.user.SysUser;
import com.dm.exception.runtime.NoSuchTypeException;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public interface ISysUserService {
    /**
     * 向数据库中插入一条数据
     * @param uer   待添加对象
     * @return int  添加成功条数
     * */
    int addUser(SysUser uer);

    /**
     * 向数据库中插入多条数据
     * @param users user集合
     * @return int  插入成功条数
     * */
    int addAllUser(Collection<SysUser> users);

    /**
     * 通过id删除指定数据
     * @param id    数据ID
     * @return int  删除成功条数
     * */
    int delById(BigInteger id);

    /**
     * 删除指定名字的数据
     * @param name  指定名字
     * @return int  删除成功条数
     * */
    int delByLoginName(String name);

    /**
     * 跟新数据
     * @param user  新数据对象
     * */
    int update(SysUser user);

    /**
     * 查找满足条件的所有对象
     * @param user  条件合集
     * @return list 满足条件的集合
     * */
    List<SysUser> find(SysUser user);

    /**
     * 根据id查找
     * @param id  id
     * @return SysUser 满足条件对象
     * */
    SysUser findById(BigInteger id);

    /**
     * 根据name查找
     * @param name  name
     * @return SysUser 满足条件对象
     * */
    SysUser findByLoginName(String name);


    ///////////////以下定制服务///////////////
    /**
     * 用户登录服务
     * @param user  用户登录信息
     * @return AuthToken    授权信息
     * */
    AuthToken login(SysUser user);

    /**
     * 用户注册服务
     * @param user  用户注册信息
     * @return AuthToken    注册信息
     * */
    AuthToken register(SysUser user) throws NoSuchTypeException;
}
