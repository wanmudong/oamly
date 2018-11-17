package top.wanmudong.oamly.modules.report.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import top.wanmudong.oamly.modules.report.entity.Report;
import top.wanmudong.oamly.modules.report.entity.ReportDto;
import top.wanmudong.oamly.modules.user.entity.Recruit;
import top.wanmudong.oamly.modules.user.entity.RecruitDto;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Mapper
public interface ReportDtoConverter {
    ReportDtoConverter INSTANCE = Mappers.getMapper(ReportDtoConverter.class);

    @Mappings({
            @Mapping(target = "end_date", expression = "java(top.wanmudong.oamly.modules.common.utils.timeUtil.dateTime(Long.valueOf(report.getEndDate()),\"yyyy-MM-dd\"))"),
            @Mapping(target = "time_report", expression = "java(top.wanmudong.oamly.modules.common.utils.timeUtil.dateTime(Long.valueOf(report.getTimeReport()),\"yyyy-MM-dd\"))"),
            @Mapping(source = "salary", target = "salary.fina"),
            @Mapping(source = "salarySug", target = "salary.review"),
            @Mapping(source = "name", target = "username"),
            @Mapping(target = "start_date", expression = "java(top.wanmudong.oamly.modules.common.utils.timeUtil.dateTime(Long.valueOf(report.getStartDate()),\"yyyy-MM-dd\"))"),
            @Mapping(source = "uid", target = "stuid")
    })
    ReportDto reportDto(Report report);

}
