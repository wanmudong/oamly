package top.wanmudong.oamly.modules.permission.service;

import com.baomidou.mybatisplus.service.IService;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.PageQuery;
import top.wanmudong.oamly.modules.permission.entity.MemberRole;
import top.wanmudong.oamly.modules.permission.entity.Role;

/**
 * @author wanmudong
 * @date 14:17 2018/12/12
 */
@Service
public interface MemberRoleService extends IService<MemberRole> {
    MyPageInfo<MemberRole> getMemberRoles(PageQuery pageQuery);

    void addMemberRole(MemberRole memberRole);

    /**
     * 删除成员角色对应关系
     * @param memberRole
     */
    void delMemberRole(MemberRole memberRole);

    /**
     * 更新成员角色对应关系
     * @param memberRole
     */
    void updateMemberRole(MemberRole memberRole);
}
