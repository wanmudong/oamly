package top.wanmudong.oamly.modules.announce.service;


import com.baomidou.mybatisplus.service.IService;
import top.wanmudong.oamly.modules.announce.entity.Announce;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-12
 */
public interface AnnounceService extends IService<Announce> {

    List<Announce> getAnnounceList();

    Boolean setAnnounce(Announce announce);
}
