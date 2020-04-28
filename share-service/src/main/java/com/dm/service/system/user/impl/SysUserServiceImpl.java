package com.dm.service.system.user.impl;

import com.dm.beans.AuthToken;
import com.dm.common.GlobalConstant;
import com.dm.domain.system.user.SysUser;
import com.dm.enums.AccountType;
import com.dm.utils.MD5;
import com.dm.enums.MD5ProcessType;
import com.dm.mapper.system.user.SysUserMapper;
import com.dm.service.system.user.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
/**
 * @author hu.yuhao
 * */
@Service("sysUserService")
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Autowired
    private SysUserMapper userMapper;
    /**
     * 向数据库中插入一条数据
     *
     * @param uer 待添加对象
     * @return int  添加成功条数
     */
    @Transactional
    @Override
    public int addUser(SysUser uer) {
        int i = userMapper.addUser(uer);
        int j = 1/0;
        return i;
    }

    /**
     * 向数据库中插入多条数据
     *
     * @param users user集合
     * @return int  插入成功条数
     */
    @Override
    public int addAllUser(Collection<SysUser> users) {
        return userMapper.addAllUser(users);
    }

    /**
     * 通过id删除指定数据
     *
     * @param id 数据ID
     * @return int  删除成功条数
     */
    @Override
    public int delById(BigInteger id) {
        return userMapper.delById(id);
    }

    /**
     * 删除指定名字的数据
     *
     * @param name 指定名字
     * @return int  删除成功条数
     */
    @Override
    public int delByLoginName(String name) {
        return userMapper.delByLoginName(name);
    }

    /**
     * 跟新数据
     *
     * @param user 新数据对象
     */
    @Override
    public int update(SysUser user) {
        return userMapper.update(user);
    }

    /**
     * 查找满足条件的所有对象
     *
     * @param user 条件合集
     * @return list 满足条件的集合
     */
    @Override
    public List<SysUser> find(SysUser user) {
        return userMapper.find(user);
    }

    /**
     * 根据id查找
     *
     * @param id id
     * @return SysUser 满足条件对象
     */
    @Override
    public SysUser findById(BigInteger id) {
        return userMapper.findById(id);
    }

    /**
     * 根据name查找
     *
     * @param name name
     * @return SysUser 满足条件对象
     */
    @Transactional
    @Override
    public SysUser findByLoginName(String name) {
        return userMapper.findByLoginName(name);
    }

    /**
     * 用户登录服务
     *
     * @param user 用户登录信息
     * @return AuthToken    授权信息
     */
    @Override
    public AuthToken login(SysUser user) {
        SysUser sysUser = findByLoginName(user.getLoginName());
        if (sysUser == null) {
            return AuthToken.unauth(GlobalConstant.LOGIN_ACCOUNT_NOT_REGISTER, null);
        }else {
            //存在该用户，检查账户状态
            char status = sysUser.getStatus();
            if (status == AccountType.LOCKED.getStatus()) {
                return AuthToken.unauth(GlobalConstant.LOGIN_ACCOUNT_LOCKED, null);
            }
            if (status == AccountType.INVALID.getStatus()) {
                return AuthToken.unauth(GlobalConstant.LOGIN_ACCOUNT_INVALID);
            }
            //存在该用户，检查角色状态

            //存在该用户，则进行密码匹配
            String password = sysUser.getPassword();
            String md5WithSalt = MD5.MD5WithSalt(password, user.getPassword(), MD5ProcessType.LOGIN);
            //md5WithSalt不可能为null
            if (!md5WithSalt.equals(password)) {
                return AuthToken.unauth(GlobalConstant.LOGIN_ACCOUNT_ERROR, null);
            }
            return AuthToken.auth(GlobalConstant.LOGIN_SUCCESS, sysUser);
        }
    }

    /**
     * 用户注册服务
     *
     * @param user 用户注册信息
     * @return AuthToken    注册信息
     */
    @Transactional
    @Override
    public AuthToken register(SysUser user) {
        SysUser sysUser = findByLoginName(user.getLoginName());
        //null 为没有同名注册，执行注册
        if (sysUser == null) {
            user.setPassword(MD5.MD5WithSalt(null, user.getPassword(), MD5ProcessType.REGISTER));
            int i = addUser(user);
            if (i > 0) {
                return AuthToken.auth("注册成功", null);
            } else {
                return AuthToken.unauth("注册失败", null);
            }
        }else {
            return AuthToken.unauth("用户名相同，重新输入", null);
        }
    }
}
