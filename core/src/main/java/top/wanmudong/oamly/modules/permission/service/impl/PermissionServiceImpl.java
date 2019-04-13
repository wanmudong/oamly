package top.wanmudong.oamly.modules.permission.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.common.Enum.OrderExceptionEnum;
import top.wanmudong.oamly.modules.common.exception.ContentNotExistException;
import top.wanmudong.oamly.modules.common.utils.ContentContext.PermissionContext;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.PageQuery;
import top.wanmudong.oamly.modules.permission.entity.Permission;
import top.wanmudong.oamly.modules.permission.mapper.PermissionMapper;
import top.wanmudong.oamly.modules.permission.service.PermissionService;

/**
 * @author wanmudong
 * @date 14:17 2018/12/12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper,Permission> implements PermissionService {
    @Override
    public MyPageInfo<Permission> selectPermissionList(PageQuery pageQuery) {
        PageQuery.startPage(pageQuery);
        Integer state = PermissionContext.AVIALABLE;
        PageInfo<Permission> pageInfo = new PageInfo<>(baseMapper.selectPermissionList(state));
        return new MyPageInfo<>(pageInfo);
    }

    @Override
    public void isExistPermission(Integer permissionId) {
        Permission permissionExist = selectOne(new EntityWrapper<Permission>().eq("id",permissionId));

        if (permissionExist == null){
            throw new ContentNotExistException(OrderExceptionEnum.PERMISSION_NOT_FOUND_ERROR);
        }

    }

}
