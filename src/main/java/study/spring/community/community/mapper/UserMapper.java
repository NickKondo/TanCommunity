package study.spring.community.community.mapper;

import study.spring.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmt_modified})")
    void insert(User user);

    User findByToken(String token);
}