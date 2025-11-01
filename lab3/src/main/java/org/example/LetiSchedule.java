package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class LetiSchedule {
    public static String getWeekScheduleJson(String groupNumber) {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("digital.etu.ru")
                .addPathSegment("api")
                .addPathSegment("mobile")
                .addPathSegment("schedule")
                .addQueryParameter("groupNumber", groupNumber)
                .build();

        Request requestHttp = new Request.Builder()
                .url(httpUrl)
                .build();

        System.out.println("ULR: " + requestHttp);

        OkHttpClient httpClient = new OkHttpClient();
        try (Response response = httpClient.newCall(requestHttp).execute()) {
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "";
    }

    public static JsonObject getDays(String scheduleJson, String groupNumber) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(scheduleJson, JsonObject.class)
                .get(groupNumber).getAsJsonObject()
                .get("days").getAsJsonObject();
    }

    public static String getWeekSchedule(String scheduleJson, String groupNumber, String weekNumber) {
        JsonObject days = getDays(scheduleJson, groupNumber);
        StringBuilder schedule = new StringBuilder();
        JsonObject day;

        for (int i = 0; i < 7; i++) {
            day = days.get(Integer.toString(i)).getAsJsonObject();
            schedule
                    .append(getDayLessons(day, weekNumber))
                    .append("\n");
        }

        return schedule.toString();
    }

    public static String getNextLessonForToday(JsonArray lessons, String week, byte currentHour, byte currentMinute) {
        SimpleDateFormat sdf;
        Calendar tmp = new Calendar.Builder().build();
        JsonObject lesson;
        byte lessonHour;
        byte lessonMinute;

        for (int i = 0; i < lessons.size(); i++) {
            lesson = lessons.get(i).getAsJsonObject();
            if (lesson.getAsJsonObject().get("week").getAsString().equals(week)) {
                sdf = new SimpleDateFormat("HH:mm");

                try {
                    tmp.setTime(sdf.parse(lesson.get("start_time").getAsString()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                lessonHour = (byte) tmp.get(Calendar.HOUR_OF_DAY);
                lessonMinute = (byte) tmp.get(Calendar.HOUR_OF_DAY);

                if (lessonHour > currentHour || (lessonHour == currentHour && lessonMinute > currentMinute)) {
                    return getLessonString(lesson);
                }
            }
        }

        return "";
    }

    public static String getDayLessons(JsonObject day, String weekNumber) {
        StringBuilder schedule = new StringBuilder();
        JsonArray lessons = day.get("lessons").getAsJsonArray();
        JsonObject lesson;

        String dayName = day.get("name").toString();
        schedule.append(dayName.replace("\"", ""))
                .append(":\n");

        byte lessonNumber = 1;
        for (int i = 0; i < lessons.size(); i++) {
            lesson = lessons.get(i).getAsJsonObject();
            if (lesson.getAsJsonObject().get("week").getAsString().equals(weekNumber)) {
                schedule.append(lessonNumber).append(". ")
                        .append(getLessonString(lesson))
                        .append("\n");
                lessonNumber++;
            }
        }

        return schedule.toString();
    }

    public static String getLessonString(JsonObject lesson) {
        StringBuilder schedule = new StringBuilder();

        String name = lesson.get("name").getAsString();
        String subjectType = lesson.get("subjectType").getAsString();
        String teacher = lesson.get("teacher").getAsString();
        String start = lesson.get("start_time").getAsString();
        String end = lesson.get("end_time").getAsString();

        schedule.append("Lesson: ")
                .append(name.replace("\"", ""))
                .append(" (")
                .append(subjectType.replace("\"", ""))
                .append(")")
                .append("\n    Teacher: ")
                .append(teacher.replace("\"", ""))
                .append("\n    Time: ")
                .append(start.replace("\"", ""))
                .append(" - ")
                .append(end.replace("\"", ""));

        return schedule.toString();
    }
}