package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Ticket;
import net.thumbtack.school.hospital.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface PatientMapper {

    @Insert("INSERT INTO patient(user_id, email, address, phone) VALUES " +
            "(#{user.id}, #{patient.email}, #{patient.address}, #{patient.phone})")
    @Options(useGeneratedKeys = true, keyProperty = "patient.id")
    Integer insert(@Param("user") User user, @Param("patient") Patient patient);

    @Select("SELECT patient.id, email, address, phone, first_name AS firstName, last_name AS lastName, patronymic, " +
            "login, password, user_type AS userType " +
            "FROM patient " +
            "JOIN user on patient.user_id = user.id " +
            "WHERE user_id = #{id}")
    @Results(id = "getByUserId", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "address", column = "address"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "firstname", column = "first_name"),
            @Result(property = "lastname", column = "last_name"),
            @Result(property = "patronymic", column = "patronymic"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "password"),
            @Result(property = "usertype", column = "user_type")
    })
    Patient getByUserId(int id);

    @Select("SELECT patient.id, email, address, phone, first_name AS firstName, last_name AS lastName, patronymic, " +
            "login, password, user_type AS userType " +
            "FROM patient " +
            "JOIN user on patient.user_id = user.id " +
            "WHERE user_id = (SELECT user_id FROM patient WHERE id = #{id})")
    @ResultMap("getByUserId")
    Patient getById(int id);

    @Select("SELECT id, number, daySchedule_id AS dayScheduleId, commission_id AS commissionId, time_start AS timeStart, " +
            "time_end AS timeEnd, status " +
            "FROM patient_ticket" +
            "WHERE patient_id = #{patientId}")
    List<Ticket> getTicketsById(int patientId);

    @Delete("DELETE FROM patient")
    void deleteAll();
}
