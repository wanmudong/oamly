package top.wanmudong.oamly.modules.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.user.entity.*;
import top.wanmudong.oamly.modules.user.service.DictService;

import java.util.List;
import java.util.Map;

/**
 * Created by chenjiehao on 2018/11/14
 */
@Service
@DependsOn("springContextHolder")
public class UserToUserMapper {
    private static DictService dictService;


    @Autowired
    protected UserToUserMapper(DictService dictService){
        this.dictService=dictService;
    }

    static Map<String, List<Dict>> dict;

    public  static User poToDto(User user){
        if (user==null){
            return user;
        }
        dict= dictService.getAllDict();

        user.setDepart(user.getDepart()==null?"":getValue("departs", Integer.valueOf(user.getDepart())));
        user.setDebitcard(user.getDebitcard()==null?"":user.getDebitcard());
        user.setRole(user.getRole()==null?"":getValue("memberRole", Integer.valueOf(user.getRole())));
        user.setStatus(user.getStatus()==null?"":getValue("memberStatus", Integer.valueOf(user.getStatus())));
        user.setJoinTime(timeUtil.dateTime(Long.parseLong(user.getJoinTime()==null?"0":user.getJoinTime()),"yyyy-MM-dd"));
        user.setExitTime(timeUtil.dateTime(Long.parseLong(user.getExitTime()==null?"0":user.getExitTime()),"yyyy-MM-dd"));
        user.setCampus(user.getCampus()==null?"":getValue("campus", Integer.valueOf(user.getCampus())));
        return user;
    }
    public  static User DtoToPo(User user){
        if (user==null){
            return user;
        }
        dict= dictService.getAllDict();

        user.setDepart(user.getDepart()==null?"":getKey("departs", user.getDepart()));
        user.setRole(user.getDepart()==null?"":getKey("memberRole", user.getRole()));
        user.setStatus(user.getDepart()==null?"":getKey("memberStatus", user.getStatus()));
        user.setJoinTime(timeUtil.dateTimeStamp(user.getJoinTime(),"yyyy-MM-dd"));
        user.setExitTime(timeUtil.dateTimeStamp(user.getExitTime(),"yyyy-MM-dd"));
        user.setCampus(user.getDepart()==null?"":getKey("campus", user.getCampus()));


        return user;
    }
    public  static Recruit poToDtoRecruit(Recruit recruit){
        if (recruit==null){
            return recruit;
        }
        dict= dictService.getAllDict();
        recruit.setKey(recruit.getId());
        recruit.setDepart(recruit.getDepart()==null?"":getValue("departs", Integer.valueOf(recruit.getDepart())));
        recruit.setTime(timeUtil.dateTime(Long.parseLong(recruit.getTime()==null?"0":recruit.getTime()),"yyyy-MM-dd HH:mm:ss"));
        recruit.setCampus(recruit.getCampus()==null?"":getValue("campus", Integer.valueOf(recruit.getCampus())));
        return recruit;
    }
    public  static RecruitDto recruitToDto(Recruit recruit){
        if (recruit==null){
            return null;
        }
        dict= dictService.getAllDict();
        RecruitDto recruitDto = new RecruitDto();

        recruitDto.setKey(recruit.getId());
        recruitDto.setCampus(recruit.getCampus()==null?"":getValue("campus", Integer.valueOf(recruit.getCampus())));
        recruitDto.setDepart(recruit.getDepart()==null?"":getValue("departs", Integer.valueOf(recruit.getDepart())));

        RecruitStatusDto recruitStatusDto = new RecruitStatusDto();
        RecruitStatus recruitStatus = recruit.getRecruitStatus();
        if (recruitStatus!=null){

            recruitStatusDto.setCurrent(recruitStatus.getCurrent()==0?0:recruitStatus.getStatus());
            recruitStatusDto.setStatus(recruit.getStatus()==1?"normal":"");
            recruitStatusDto.setSteps(recruitStatus.getSteps());
        }
        recruitDto.setStatus(recruitStatusDto);

        recruitDto.setCollege(Format.getFormat(recruit.getCollege()));
        recruitDto.setCurrent(recruit.getCurrent());
        recruitDto.setMajor(Format.getFormat(recruit.getMajor()));
        recruitDto.setName(Format.getFormat(recruit.getName()));
        recruitDto.setPhone(Format.getFormat(recruit.getPhone()));
        recruitDto.setQq(Format.getFormat(recruit.getQq()));
        recruitDto.setSay(Format.getFormat(recruit.getSay()));
        recruitDto.setSex(Format.getFormat(recruit.getSex()));
        recruitDto.setStuid(recruit.getStuid());
        recruitDto.setTime(Format.getFormat(recruit.getTime()));

        return recruitDto;




    }

    public  static String getValue(String s,Integer key){
       for (Dict d:dict.get(s)){
           if (d.getKey()==key){
               return d.getValue();
           }
       }
       return "";
    }
    public  static String getKey(String s,String value){
        for (Dict d:dict.get(s)){
            if (d.getValue().equals(value)){
                return String.valueOf(d.getKey());
            }
        }
        return null;
    }
}
