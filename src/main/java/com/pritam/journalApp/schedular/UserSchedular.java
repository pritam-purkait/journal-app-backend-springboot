package com.pritam.journalApp.schedular;

import com.pritam.journalApp.Cache.AppCache;
import com.pritam.journalApp.entity.JournalEntry;
import com.pritam.journalApp.entity.User;
import com.pritam.journalApp.enums.Sentiment;
import com.pritam.journalApp.repository.UserRepositoryImpl;
import com.pritam.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class UserSchedular {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private EmailService emailService;
    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserSendMail(){
        List<User> users = userRepositoryImpl.getUserForSA();
        for(User user:users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate()
                            .isAfter(LocalDateTime.now()
                                    .minus(7, ChronoUnit.DAYS)
                            )
                    ).map(x -> x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiment mostFriquentSentiment = null;

            int mostCount = 0;

            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > mostCount) {
                    mostCount = entry.getValue();
                    mostFriquentSentiment = entry.getKey();
                }
            }

            if (mostFriquentSentiment != null) {

                emailService.sendEmail(
                        user.getEmail(),
                        "Your Weekly Sentiment Report is Here 🧠💬",
                        String.format(
                                "Hi %s,\n\n" +
                                        "Hope you're doing well! Here's a quick reflection of your journal entries from last week.\n\n" +
                                        "📅 Week in Review:\n" +
                                        "Based on what you wrote, your overall sentiment was %s.\n\n" +
                                        "🧠 Summary:\n" +
                                        "This was your last week’s sentiment. It’s always helpful to take a moment to reflect and understand how things have been emotionally.\n\n" +
                                        "💡 Want to keep a more positive trend going? Or maybe you just want to understand yourself better—" +
                                        "keep journaling regularly and let your words show you the way.\n\n" +
                                        "See you again next week with another snapshot of your thoughts.\n\n" +
                                        "Take care,\n" +
                                        "VibeLog – Your day to day web journal app.",
                                user.getUserName(), mostFriquentSentiment
                        )
                );


            }
        }
    }
    @Scheduled(cron="0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }

}
