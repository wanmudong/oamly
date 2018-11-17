package top.wanmudong.oamly.modules.announce.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import top.wanmudong.oamly.modules.announce.entity.Announce;
import top.wanmudong.oamly.modules.announce.mapper.AnnounceMapper;
import top.wanmudong.oamly.modules.announce.service.AnnounceService;
import org.springframework.stereotype.Service;

import java.util.List;

import static top.wanmudong.oamly.modules.common.utils.timeUtil.getSecondTimeNow;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-12
 */
@Service
public class AnnounceServiceImpl extends ServiceImpl<AnnounceMapper, Announce> implements AnnounceService {

    @Override
    public List getAnnounceList() {
        return  this.baseMapper.selectAnnounceList();
    }

    @Override
    public Boolean setAnnounce(Announce announce) {
        announce.setStatus(1);
        //获取当前时间作为通知的上传时间
        announce.setPublishTime(getSecondTimeNow());
        //获取当前用户id
        //TODO: 获取当前用户
//        SysUser sysuser = (SysUser) SecurityUtils.getSubject().getPrincipal();
//        announce.setUid(sysuser.getId());
        announce.setUid(234);
        Integer success = baseMapper.insert(announce);
        return success > 0;
    }
}
