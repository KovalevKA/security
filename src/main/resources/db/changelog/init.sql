CREATE TABLE "users" (
                         "user_id" BIGSERIAL PRIMARY KEY,
                         "username" varchar,
                         "password" varchar,
                         "first_name" varchar,
                         "last_name" varchar,
                         "email" varchar,
                         "status" varchar,
                         "created" date,
                         "updated" date
);

CREATE TABLE "roles" (
                         "role_id" BIGSERIAL PRIMARY KEY,
                         "name" varchar,
                         "status" varchar,
                         "created" date,
                         "updated" date
);

CREATE TABLE "users_roles" (
                               "user_id" BIGINT,
                               "role_id" BIGINT
);

CREATE TABLE "tokens" (
                          "token_id" BIGSERIAL PRIMARY KEY,
                          "refresh_token" varchar,
                          "access_token" varchar,
                          "status" varchar,
                          "created" date,
                          "updated" date
);

CREATE TABLE "users_tokens" (
                                "user_id" BIGINT,
                                "token_id" BIGINT
);

ALTER TABLE "users_roles" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");

ALTER TABLE "users_roles" ADD FOREIGN KEY ("role_id") REFERENCES "roles" ("role_id");

ALTER TABLE "users_tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");

ALTER TABLE "users_tokens" ADD FOREIGN KEY ("token_id") REFERENCES "tokens" ("token_id");
