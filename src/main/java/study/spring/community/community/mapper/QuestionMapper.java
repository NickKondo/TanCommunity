package study.spring.community.community.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public class QuestionMapper {
    @Insert("insert into (title,description,gmt_create)")
}
