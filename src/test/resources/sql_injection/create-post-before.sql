DELETE FROM `post`;

INSERT INTO `post` (id, author_id, is_blocked, post_text, time, title)
    VALUES
        (1, 1, 0, "new post's text", NOW(), "Hello, Post One"),
        (2, 1, 0, "new post's text", NOW(), "Hello, Post Two"),
        (3, 1, 0, "new post's text", NOW(), "Hello, Post Three"),
        (4, 1, 0, "new post's text", NOW(), "Hello, Post Four");