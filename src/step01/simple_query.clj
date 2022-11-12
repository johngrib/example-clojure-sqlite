(ns step01.simple-query
  "clojure.java.jdbc를 사용해 간단한 sql을 실행하는 예제."
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.pprint :as pp]))

(def db {:classname   "org.sqlite.JDBC"
         :subprotocol "sqlite"
         :subname     "resources/test.db"})

(comment
  ; CREATE TABLE
  (jdbc/db-do-commands db
                       "create table book (
                       id integer primary key autoincrement,
                       title varchar(255),
                       author varchar(255),
                       price integer);")
  ; INSERT
  (jdbc/db-do-commands db
                       "insert into book (title, author, price)
                       values ('The Hobbit', 'J.R.R. Tolkien', 100);")
  ; MULTI INSERT
  (jdbc/db-do-commands db
                       "insert into book (title, author, price)
                       values ('The Lord of the Rings', 'J.R.R. Tolkien', 200),
                              ('The Silmarillion', 'J.R.R. Tolkien', 300);")

  ; SQL LIST
  (jdbc/db-do-commands db
                       ["update book set price = 150 where id = 1;"
                        "update book set price = 250 where id = 2;"])

  ; SELECT
  (jdbc/query db "select * from book;")

  ; SELECT -> TABLE VIEW
  (->> "select * from book;"
       (jdbc/query db)
       pp/print-table)
  ;;
  )

