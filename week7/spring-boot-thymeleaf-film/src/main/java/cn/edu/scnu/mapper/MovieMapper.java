package cn.edu.scnu.mapper;

import cn.edu.scnu.entity.Movie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MovieMapper extends BaseMapper<Movie> {

    @Select("SELECT m.* FROM movie m " +
            "JOIN (SELECT movie_id FROM movie_like GROUP BY movie_id ORDER BY COUNT(*) DESC) ml " +
            "ON m.movie_id = ml.movie_id")
    List<Movie> selectMoviesByLikes();

    @Select("SELECT m.* " +
            "FROM movie m " +
            "JOIN (SELECT movie_id " +
            "FROM movie_like " +
            "WHERE like_time >= DATE_SUB('2024-07-01 00:00:00', INTERVAL 7 DAY) " +
            "GROUP BY movie_id " +
            "ORDER BY COUNT(*) DESC) ml " +
            "ON m.movie_id = ml.movie_id " +
            "ORDER BY (SELECT COUNT(*) " +
            "FROM movie_like " +
            "WHERE movie_id = m.movie_id AND like_time >= DATE_SUB('2024-07-01 00:00:00', INTERVAL 7 DAY)) DESC")
    List<Movie> selectMoviesByWeekLikes();

    @Select("SELECT m.* " +
            "FROM movie m " +
            "JOIN (SELECT movie_id " +
            "FROM movie_like " +
            "WHERE like_time >= DATE_SUB('2024-07-01 00:00:00', INTERVAL 1 MONTH) " +
            "GROUP BY movie_id " +
            "ORDER BY COUNT(*) DESC) ml " +
            "ON m.movie_id = ml.movie_id " +
            "ORDER BY (SELECT COUNT(*) " +
            "FROM movie_like " +
            "WHERE movie_id = m.movie_id AND like_time >= DATE_SUB('2024-07-01 00:00:00', INTERVAL 1 MONTH)) DESC")
    List<Movie> selectMoviesByMonthlyLikes();

    @Select("SELECT m.* FROM movie m " +
            "JOIN movie_staff ms ON m.movie_id = ms.movie_id " +
            "JOIN participant p ON p.participant_id = ms.participant_id " +
            "WHERE p.is_director = 0 AND p.name = #{actorName}")
    List<Movie> selectMoviesByActorName(String actorName);

    @Select("SELECT m.* FROM movie m " +
            "JOIN movie_staff ms ON m.movie_id = ms.movie_id " +
            "JOIN participant p ON p.participant_id = ms.participant_id " +
            "WHERE p.is_director = 1 AND p.name = #{directorName}")
    List<Movie> selectMoviesByDirectorName(String directorName);

    @Select("SELECT p.name " +
            "FROM movie m, movie_staff ms, participant p " +
            "WHERE m.movie_id = ms.movie_id AND p.participant_id = ms.participant_id AND p.is_director = 0 AND m.movie_id = #{id}")
    List<String> selectActorsByMoiveId(Integer id);

    @Select("SELECT p.name " +
            "FROM movie m, movie_staff ms, participant p " +
            "WHERE m.movie_id = ms.movie_id AND p.participant_id = ms.participant_id AND p.is_director = 1 AND m.movie_id = #{id}")
    String selectDirectorByMoiveId(Integer id);

    @Select("SELECT m.* FROM movie m " +
            "JOIN movie_staff ms ON m.movie_id = ms.movie_id " +
            "JOIN participant p ON p.participant_id = ms.participant_id " +
            "WHERE p.is_director = 0 AND p.name = #{name} " +
            "LIMIT #{start}, #{pageSize}")
    List<Movie> selectMoviesByActorNameWithPagination(@Param("name") String name, @Param("start") int start, @Param("pageSize") int pageSize);

    @Select("SELECT m.* FROM movie m " +
            "JOIN movie_staff ms ON m.movie_id = ms.movie_id " +
            "JOIN participant p ON p.participant_id = ms.participant_id " +
            "WHERE p.is_director = 1 AND p.name = #{name} " +
            "LIMIT #{start}, #{pageSize}")
    List<Movie> selectMoviesByDirectorNameWithPagination(String name, int start, Integer pageSize);
}
