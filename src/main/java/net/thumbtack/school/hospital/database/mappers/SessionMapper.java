package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.model.Session;
import net.thumbtack.school.hospital.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

@Mapper
public interface SessionMapper {

    @Insert("INSERT INTO session(cookie, user_id) VALUES (#{cookie}, #{user.id}) " +
            "ON DUPLICATE KEY UPDATE cookie = #{cookie}, user_id = #{user.id}")
    void insert(Session session);

    @Delete("DELETE FROM session WHERE cookie = #{cookie}")
    void delete(String cookie);

    @Select("SELECT cookie, user_id FROM session WHERE cookie = #{cookie}")
    @Results({
            @Result(property = "cookie", column = "cookie"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "net.thumbtack.school.hospital.database.mappers.UserMapper.getById", fetchType = FetchType.LAZY))
    })
    Session getByCookie(String cookie);

    @Delete("DELETE FROM session")
    void deleteAll();
}
