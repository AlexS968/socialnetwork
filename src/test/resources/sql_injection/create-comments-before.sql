SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE post_comment;
TRUNCATE TABLE notification_type;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO notification_type (id, code, name) VALUES (1, "POST", "новый пост"),
    (2, "POST_COMMENT", "новый комментарий к посту"),
    (3, "COMMENT_COMMENT", "новый комментарий к комментарию");

INSERT INTO post_comment (id, comment_text, is_blocked, time, author_id, parent_id, post_id)
    VALUES (1, "first comment to the post", 0, NOW(), 1, null, 1),
        (2, "second comment to the post", 0, NOW(), 1, 1, 1),
        (3, "third comment to the post", 0, NOW(), 1, null, 2),
        (4, "fouth comment to the post", 0, NOW(), 1, null, 3);