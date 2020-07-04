package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.model.Admin;
import net.thumbtack.school.hospital.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {

    @Insert("INSERT INTO admin(user_id, position) VALUES (#{user.id}, #{admin.position})")
    @Options(useGeneratedKeys = true, keyProperty = "admin.id")
    Integer insert(@Param("user") User user, @Param("admin") Admin admin);

    @Select("SELECT admin.id, position, user.id, first_name AS firstName, " +
            "last_name AS lastName, patronymic, login, password, user_type AS userType " +
            "FROM admin " +
            "JOIN user ON admin.user_id = user.id " +
            "WHERE user_id = #{id}")
    @Results(id = "getByUserId", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "position", column = "position"),
            @Result(property = "firstname", column = "first_name"),
            @Result(property = "lastname", column = "last_name"),
            @Result(property = "patronymic", column = "patronymic"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "password"),
            @Result(property = "usertype", column = "user_type")
    })
    Admin getByUserId(int id);

    @Select("SELECT admin.id, position, user.id, first_name AS firstName, " +
            "last_name AS lastName, patronymic, login, password, user_type AS userType " +
            "FROM admin " +
            "JOIN user ON admin.user_id = user.id " +
            "WHERE user_id = (SELECT user_id FROM admin WHERE id = #{id})")
    @ResultMap("getByUserId")
    Admin getById(int id);

    @Delete("DELETE FROM admin")
    void deleteAll();
}
