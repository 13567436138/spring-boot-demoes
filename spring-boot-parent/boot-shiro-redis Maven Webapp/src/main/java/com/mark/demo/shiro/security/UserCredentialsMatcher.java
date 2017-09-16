package com.mark.demo.shiro.security;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;


public class UserCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        return true;
//        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//        if (null != token.getCrm() && token.getCrm()) return true;// 支撑免登陆用户
//        if (null != token.getErp() && token.getErp()) return true;// ERP免登陆用户
//        UserPrincipal principal = (UserPrincipal) getAvailablePrincipal(info.getPrincipals(), token.getUsername());
//        String password = String.valueOf(token.getPassword());
//        StringBuffer buffer = new StringBuffer(password).append(principal.getUserSalt());
//        Object tokenCredentials = EncryptUtils.encrypt(buffer.toString(), ZttxConst.ENCRYPT);
//        Object accountCredentials = getCredentials(info);
//        return equals(tokenCredentials, accountCredentials);
    }

    /**
     * 取可用的的对象
     *
     * @param principals
     * @param realmName
     * @return
     */
    Object getAvailablePrincipal(PrincipalCollection principals, String realmName) {
        Object primary = null;
        if (!CollectionUtils.isEmpty(principals)) {
            Collection thisPrincipals = principals.fromRealm(realmName);
            if (!CollectionUtils.isEmpty(thisPrincipals)) {
                primary = thisPrincipals.iterator().next();
            } else {
                primary = principals.getPrimaryPrincipal();
            }
        }
        return primary;
    }
}
