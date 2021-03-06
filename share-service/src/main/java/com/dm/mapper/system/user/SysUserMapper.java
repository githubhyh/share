package com.dm.mapper.system.user;

import com.dm.domain.system.user.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * @author hu.yuhao
 * 用户mapper
 * */
@Component
public interface SysUserMapper {
    int addUser(SysUser uer);

    int addAllUser(Collection<SysUser> users);

    int delById(BigInteger id);

    int delByLoginName(String name);

    int update(SysUser user);

    List<SysUser> find(SysUser user);

    SysUser findById(BigInteger id);

    SysUser findByLoginName(@Param("name") String name);

    SysUser findByLoginID(@Param("loginID") String loginID);

    /**
     * 邮箱登录
     * */
    SysUser findByEmail(@Param("email") String email);

    /**
     * 手机号查找
     * */
    SysUser findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * unique
     * */
    SysUser findUnique(@Param("uniqueParam") String uniqueParam);
    //...
}
