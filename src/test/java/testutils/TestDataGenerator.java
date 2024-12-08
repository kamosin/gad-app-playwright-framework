package testutils;

import api.models.User;
import com.github.javafaker.Faker;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class TestDataGenerator {

    static Faker faker = new Faker();

    public static String generateEmail(){
        return faker.internet().emailAddress();
    }

    public static String generateFirstName(){
        return faker.name().firstName();
    }

    public static String generateLastName(){
        return faker.name().lastName();
    }

    public static String generateBirthdate(){
        Random random = new Random();
        int year = random.nextInt(1950, 2010);
        int month = random.nextInt(1, 13);
        int day = random.nextInt(1, 29);
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    public static String generatePassword(){
        return faker.internet().password();
    }

    public static User generateUser(){
        return new User(generateFirstName(), generateLastName(), generateEmail(), generateBirthdate(), generatePassword(), "0797eae7-5f95-4985-8ac8-10c58e17c769.jpg");
    }

    public static String generateText(int numberOfCharacters){
        return faker.lorem().characters(numberOfCharacters);
    }

    public static String generateArticleTitle(){
        return faker.book().title();
    }

    public static String currentDate(){
        return Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();
    }

//    public static ArticleRequest generateArticle(){
//        return new ArticleRequest(generateArticleTitle(), generateText(150), currentDate(), ReusableData.articlePictureName);
//    }
}
