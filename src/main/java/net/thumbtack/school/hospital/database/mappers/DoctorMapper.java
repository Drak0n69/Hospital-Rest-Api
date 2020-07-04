package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DoctorMapper {

    @Insert("INSERT INTO doctor SET " +
            "user_id = #{user.id}, " +
            "speciality_id = (SELECT id FROM doctor_speciality WHERE speciality = #{doctor.speciality}), " +
            "room_id = (SELECT id FROM doctor_room WHERE room = #{doctor.room})")
    @Options(useGeneratedKeys = true, keyProperty = "doctor.id")
    void insertDoctor(@Param("user") User user, @Param("doctor") Doctor doctor);

    @Insert("INSERT INTO daySchedule(doctor_id, date) VALUES (#{doctor.id}, #{schedule.date})")
    @Options(useGeneratedKeys = true, keyProperty = "schedule.id")
    void insertSchedule(@Param("doctor") Doctor doctor, @Param("schedule") DaySchedule schedule);

    @Insert("INSERT INTO appointment(schedule_id, time_slot, duration, patient_id, status) VALUES " +
            "(#{schedule.id}, #{appointment.slot}, #{appointment.duration}, #{appointment.patient.id}, #{appointment.status})")
    @Options(useGeneratedKeys = true, keyProperty = "appointment.id")
    void insertAppointments(@Param("schedule") DaySchedule schedule, @Param("appointment") Appointment appointment);

    @Select("SELECT doctor.id, speciality, room, first_name AS firstName, last_name AS lastName, patronymic, " +
            "login, password, user_type AS userType " +
            "FROM doctor " +
            "JOIN user ON doctor.user_id = user.id " +
            "JOIN doctor_speciality ON doctor.speciality_id = doctor_speciality.id " +
            "JOIN doctor_room ON doctor.room_id = doctor_room.id " +
            "WHERE user_id = #{id}")
    @Results(id = "getByUserId", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "speciality", column = "speciality"),
            @Result(property = "room", column = "room"),
            @Result(property = "daySchedule", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.database.mappers.DoctorMapper.getScheduleById", fetchType = FetchType.LAZY)),
            @Result(property = "firstname", column = "first_name"),
            @Result(property = "lastname", column = "last_name"),
            @Result(property = "patronymic", column = "patronymic"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "password"),
            @Result(property = "usertype", column = "user_type")
    })
    Doctor getByUserId(int userId);

    @Select("SELECT doctor.id, speciality, room, first_name AS firstName, last_name AS lastName, patronymic, " +
            "login, password, user_type AS userType " +
            "FROM doctor " +
            "JOIN user ON doctor.user_id = user.id " +
            "JOIN doctor_speciality ON doctor.speciality_id = doctor_speciality.id " +
            "JOIN doctor_room ON doctor.room_id = doctor_room.id " +
            "WHERE user_id = (SELECT user_id FROM doctor WHERE id = #{id})")
    @ResultMap("getByUserId")
    Doctor getById(int id);

    @Select("SELECT doctor.id, speciality, room, first_name AS firstName, last_name AS lastName, patronymic, " +
            "login, password, user_type AS userType " +
            "FROM doctor " +
            "JOIN user ON doctor.user_id = user.id " +
            "JOIN doctor_speciality ON doctor.speciality_id = doctor_speciality.id " +
            "JOIN doctor_room ON doctor.room_id = doctor_room.id")
    @ResultMap("getByUserId")
    List<Doctor> getAll();

    @Select("SELECT id, date FROM daySchedule WHERE doctor_id = #{doctorId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "appointments", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.database.mappers.DoctorMapper.getAppointmentsById", fetchType = FetchType.LAZY)),
    })
    List<DaySchedule> getScheduleById(int doctorId);

    @Select("SELECT id, time_slot, duration, status FROM appointment WHERE schedule_id = #{scheduleId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "slot", column = "time_slot"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "status", column = "status"),
            @Result(property = "patient", column = "patient_id", javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.database.mappers.PatientMapper.getById", fetchType = FetchType.LAZY))

    })
    List<Appointment> getAppointmentsById(int scheduleId);

    @Select("SELECT patient_id FROM appointment WHERE schedule_id IN " +
            "(SELECT id FROM daySchedule WHERE doctor_id = #{id})")
    List<Integer> getPatientsId(@Param("id") int id, @Param("date") LocalDate date);

    @Delete("DELETE FROM doctor")
    void deleteAll();
}
