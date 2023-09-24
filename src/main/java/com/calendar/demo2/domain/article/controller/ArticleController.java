package com.calendar.demo2.domain.article.controller;

import com.calendar.demo2.domain.article.entity.Article;
import com.calendar.demo2.domain.article.service.ArticleService;
import com.calendar.demo2.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final int pageItemCount=50;

    @GetMapping("/list")
    public String showList(){
        return "usr/article/list";
    }

    @GetMapping("/listMore")
    @ResponseBody
    public Map getListMore(long lastId){
        List<Article> articles = articleService.findLatestAfterId(pageItemCount, lastId);

        if (articles.isEmpty())
            return Map.of(
                    "resultCode", "S-2",
                    "msg", "성공"
            );

        return Map.of(
                "resultCode", "S-1",
                "msg", "성공",
                "data", articles,
                "lastId", articles.get(articles.size() - 1).getId()
        );

    }

    @GetMapping("/calendar")
    public String showForm(){
        LocalDate today = LocalDate.now();
        return "redirect:/article/calendar/"+today.getYear()+"/"+ Ut.str.padWithZeros(today.getMonthValue(),2);
    }

    @GetMapping("/calendar/{year}/{month}")
    public String showCalendar(@PathVariable String year, @PathVariable String month, Model model) {
        LocalDate startDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        // articleService의 find 메소드를 사용하여 해당 범위에 포함되는 게시물을 검색
        List<Article> articles = articleService.findByEventDateBetween(startDate, endDate);

        // 기존에 model.addAttribute("articles", articles); 코드를 다음과 같이 변경합니다.
        Map<Integer, List<Article>> forCalendarArticles = articles.stream()
                .collect(Collectors.groupingBy(article -> article.getEventDate().getDayOfMonth()));
        model.addAttribute("forCalendarArticles", forCalendarArticles);

        model.addAttribute("articles", articles);

        DayOfWeek firstDayOfMonth = startDate.getDayOfWeek();
        int daysInMonth = YearMonth.of(startDate.getYear(), startDate.getMonthValue()).lengthOfMonth();

        model.addAttribute("year", startDate.getYear());

        model.addAttribute("formattedMonth", String.format("%02d", Integer.parseInt(month)));
        model.addAttribute("month", startDate.getMonthValue());
        model.addAttribute("firstDayOfMonth", firstDayOfMonth.getValue() + 1);
        model.addAttribute("daysInMonth", daysInMonth);

        LocalDate prevMonth = startDate.minusMonths(1);
        LocalDate nextMonth = startDate.plusMonths(1);

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        model.addAttribute("prevYear", prevMonth.getYear());
        model.addAttribute("prevMonth", Ut.str.padWithZeros(prevMonth.getMonthValue(), 2));
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", Ut.str.padWithZeros(nextMonth.getMonthValue(), 2));

        return "usr/article/calendar";
    }
}
