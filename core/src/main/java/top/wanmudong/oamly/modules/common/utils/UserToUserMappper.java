//package top.wanmudong.oamly.modules.common.utils;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//
///**
// * Created by chenjiehao on 2018/11/14
// */
//@Mapper
//public interface UserToUserMappper {
//
//    @Mappings({
//            @Mapping(source = "birthdate", target = "birth"),//属性名不一致映射
//            @Mapping(target = "birthformat", expression = "java(org.apache.commons.lang3.time.DateFormatUtils.format(person.getBirthdate(),\"yyyy-MM-dd HH:mm:ss\"))"),//自定义属性通过java代码映射
//
//    })
//    public PersonVo PersonToPersonVo(Person person);
//
//    public List<PersonVo> PersonToPersonVos(List<Person> list);
//
//}
