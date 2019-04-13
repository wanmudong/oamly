package top.wanmudong.oamly.modules.permission.service;

import com.baomidou.mybatisplus.service.IService;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.PageQuery;
import top.wanmudong.oamly.modules.permission.entity.Permission;

/**
 * @author wanmudong
 * @date 14:17 2018/12/12
 */
@Service
public interface PermissionService extends IService<Permission> {
    MyPageInfo<Permission> selectPermissionList(PageQuery pageQuery);

    /**
     * 验证权限是否存在
     * @param permissionId
     */
    void isExistPermission(Integer permissionId);
}
