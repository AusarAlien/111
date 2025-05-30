package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/search")
    public String index(@RequestParam(name = "name", required = false) String name,
                        @RequestParam(name = "pageNo", required = false,defaultValue = "1") Integer pageNo,
                        HttpServletRequest request,
                        Model model) {
        List<Movie> movies = null;

        Integer pageSize=3;//每页显示的记录数
        if(pageNo==null) pageNo=1;
        Integer pageCount = 0;

        List<Movie> movies1 = movieService.getMoviesByActorName(name);
        Integer pageCount1 = (movies1.size() % pageSize == 0) ? (movies1.size() / pageSize) : (movies1.size() / pageSize + 1);

        List<Movie> movies2 = movieService.getMoviesByDirectorName(name);
        Integer pageCount2 = (movies2.size() % pageSize == 0) ? (movies2.size() / pageSize) : (movies2.size() / pageSize + 1);



        if (pageCount1>0) {
            movies = movieService.getMoviesByActorNameWithPagination(name, pageNo, pageSize);
            pageCount = pageCount1;
        }
        else if (pageCount2>0) {
            movies = movieService.getMoviesByDirectorNameWithPagination(name, pageNo, pageSize);
            pageCount = pageCount2;
        }


        model.addAttribute("movies", movies);
        model.addAttribute("name", name);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("PageCount", pageCount);
        model.addAttribute("httpServletRequest", request);
        return "search";
    }
}
