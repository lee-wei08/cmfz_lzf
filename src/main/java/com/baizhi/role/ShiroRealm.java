package com.baizhi.role;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.AdminRoleDao;
import com.baizhi.dao.RoleDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminRole;
import com.baizhi.entity.Role;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminRoleDao adminRoleDao;
    @Autowired
    private RoleDao roleDao;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //  根据身份集合获取用户的主分身
        String name = (String) principalCollection.getPrimaryPrincipal();
        //  根据名字查询对应管理员
        Admin adminName = new Admin();
        adminName.setUsername(name);
        Admin admin = adminDao.selectOne(adminName);
        // 根据admin的id 查询对应的角色 可能一个admin存在多个角色所以用集合来接收
        List<AdminRole> list = adminRoleDao.select(new AdminRole(null, admin.getId(), null));
        // 创建一个list集合
        ArrayList<String> list1 = new ArrayList<>();
        // 遍历集合根据关系表的roleid 获取role对象
        for (AdminRole role : list) {
            Role role1 = roleDao.selectOne(new Role(role.getRoleId(), null));
            // 把拿到的role对象的角色属性 放入新创建的集合
            list1.add(role1.getName());
        }
        // 获取授权信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(list1);

        for (AdminRole adminRole : list) {
            Role role = roleDao.selectOne(new Role(adminRole.getRoleId(), null));
//            //增删改权限控制
            if (role.getName().equals("admin")) {
            /*
                    数据库里所存字符串若为select,这里就为select;
                    同理若为user:select，这为user:select   推荐
                    info.addStringPermission("select");
            */
                info.addStringPermission("user:select");
            }
            if (role.getName().equals("super")) {
                //info.addStringPermission("*");
                info.addStringPermission("user:*");
            }
        }


        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 强转为usernam。。。 token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Admin admin = new Admin();
        admin.setUsername(token.getUsername());
        Admin admin1 = adminDao.selectOne(admin);
        if (admin1 == null) {
            return null;
        } else {
//            SimpleAccount account = new SimpleAccount(admin1.getUsername(), admin1.getPassword(), ByteSource.Util.bytes(admin1.getSalt()), "com.baizhi.role.ShiroRealm");
//            System.out.println(admin1.getUsername());
//            System.out.println(admin1.getPassword());
//            System.out.println(admin1.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin1.getUsername(), admin1.getPassword(), ByteSource.Util.bytes(admin1.getSalt()), this.getName());

//            System.out.println("111afaf111"+info);
//            System.out.println("info.getCredentials():"+info.getCredentials());
//            System.out.println("info.getCredentialsSalt():"+info.getCredentialsSalt());
            return info;
        }
    }
}
