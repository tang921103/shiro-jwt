package com.sj.config;

import com.sj.entities.UserEntity;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm {
    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String name = (String)principalCollection.getPrimaryPrincipal();
        UserEntity userEntity = new UserEntity();
        userEntity.setRoles("admin");
        userEntity.setPermissions("query");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(userEntity.getRoles());
        simpleAuthorizationInfo.addStringPermission(userEntity.getPermissions());
        return simpleAuthorizationInfo;
    }

    /**
     * 身份校验
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String name = (String)token.getPrincipal();
        //模拟从数据库中获取的加密后的密码,因为配置了HashedCredentialMatcher,这个加密器会对页面传过来的密码进行加密，然后与我们的安全数据对比。
        SimpleHash simpleHash = new SimpleHash("MD5","123456",null,1024);
        //传入比对信息, getName()获取当前的realm
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("tangqiu",simpleHash,getName());
        return simpleAuthenticationInfo;
    }
}
