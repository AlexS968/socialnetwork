package main.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
    public static final String PLATFORM_TAG = "Platform";
    public static final String AUTH_TAG = "Auth";
    public static final String DIALOG_TAG = "Dialog";
    public static final String COMMENT_TAG = "Comment";
    public static final String POST_TAG = "Post";
    public static final String STORAGE_TAG = "Storage";
    public static final String SEARCH_TAG = "Search";
    public static final String PROFILE_TAG = "Profile";
    public static final String ACCOUNT_TAG = "Account";
    public static final String FRIEND_TAG = "Friend";
    public static final String NOTIFICATION_TAG = "Notification";
    public static final String TAG_TAG = "Tag";
    public static final String LIKE_TAG = "Like";
    public static final String LOG_TAG = "Log";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .tags(new Tag(PLATFORM_TAG, "Справочные сервисы"),
                        new Tag(AUTH_TAG, "Работа с аутентификацией"),
                        new Tag(DIALOG_TAG, "Работа с диалогами"),
                        new Tag(COMMENT_TAG, "Работа с комментариями"),
                        new Tag(POST_TAG, "Работа с публикациями"),
                        new Tag(STORAGE_TAG, "Работа с хранилищем сервиса"),
                        new Tag(SEARCH_TAG, "Работа с поиском"),
                        new Tag(PROFILE_TAG, "Работа с профилем"),
                        new Tag(ACCOUNT_TAG, "Работа с учетной записью пользователя"),
                        new Tag(FRIEND_TAG, "Работа с друзьями"),
                        new Tag(NOTIFICATION_TAG, "Работа с уведомлениями"),
                        new Tag(TAG_TAG, "Работа с тегами"),
                        new Tag(LIKE_TAG, "Работа с лайками"),
                        new Tag(LOG_TAG, "Работа с логами")
                )
                .apiInfo(new ApiInfoBuilder()
                        .title("Zerone Network API")
                        .description("network api schema")
                        .version("1.0.2")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }
}
