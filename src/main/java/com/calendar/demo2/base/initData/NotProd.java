package com.calendar.demo2.base.initData;

import com.calendar.demo2.domain.article.service.ArticleService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.util.stream.IntStream;
import java.time.LocalDate;
@Configuration
@Profile("!prod")
public class NotProd {
    @Bean
    public ApplicationRunner init(ArticleService articleService){
        return args -> {
          LocalDate createDate = LocalDate.now(); // 초기에 작성한 날짜 가져오기.
            IntStream.rangeClosed(1,30).forEach(i->{
                LocalDate FirstDate = createDate.plusDays(i-1);
                String formattedDate = FirstDate.toString();
                articleService.write("제목 "+i,formattedDate);
            });


        LocalDate modifyDate = LocalDate.now(); // 초기에 작성한 날짜 가져오기.

        IntStream.rangeClosed(1,10).forEach( i-> {
            LocalDate SecondDate = modifyDate.plusDays(i-1);
            String formattedDate = modifyDate.toString();
            articleService.write("제목 "+i,formattedDate);
        });

        };
    }
}
