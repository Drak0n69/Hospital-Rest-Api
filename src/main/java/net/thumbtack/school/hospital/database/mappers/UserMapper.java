package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(first_name, last_name, patronymic, login, password, user_type) VALUES " +
            "(#{user.firstName}, #{user.lastName}, #{user.patronymic}, #{user.login}, #{user.password}, #{user.userType})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    Integer insert(@Param("user") User user);

    @Select("SELECT id, first_name AS firstName, last_name AS lastName, patronymic, login, password, user_type AS userType " +
            "FROM user WHERE login = #{login}")
    User getByLogin(String login);

    @Select("SELECT id, first_name AS firstName, last_name AS lastName, patronymic, login, password, user_type AS userType " +
            "FROM user WHERE id = #{id}")
    User getById(int id);

    @Update("UPDATE user SET password = #{user.password} " +
            "WHERE id = #{user.id}")
    void changePassword(User user);

    @Delete("DELETE FROM user")
    void deleteAll();
}
