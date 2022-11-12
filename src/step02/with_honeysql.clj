(ns step02.with-honeysql
  "honeysql을 사용해 간단한 sql을 실행하는 예제."
  (:require [clojure.java.jdbc :as jdbc]
            [honey.sql :as h]))

(def db {:classname   "org.sqlite.JDBC"
         :subprotocol "sqlite"
         :subname     "resources/test.db"})

(comment
  (jdbc/db-do-commands db (h/format {:drop-table [:if-exists :author]}))

  ; CREATE TABLE
  (jdbc/db-do-commands
   db
   "create table author (
   id integer primary key autoincrement,
   name text not null,
   email text not null,
   created_at timestamp default current_timestamp,
   updated_at timestamp default current_timestamp);")

  ; 위와 똑같음
  (jdbc/db-do-commands
   db
   (h/format {:create-table :author
              :with-columns [[:id :integer :primary-key :autoincrement]
                             [:name :text :not-null]
                             [:email :text :not-null]
                             [:created_at :timestamp :default :current_timestamp]
                             [:updated_at :timestamp :default :current_timestamp]]}))

  ; INSERT
  (jdbc/db-do-commands
   db
   (h/format {:insert-into :author
              :values [{:name "John Grib" :email "johngrib82@gmail,com"}
                       {:name "Karl Grib" :email "karlgrib82@gmail,com"}]}
             {:inline true}))

  ; UPDATE
  (jdbc/db-do-commands
   db
   (h/format {:update :author
              :set    {:name "John Grib Lee"}
              :where  [:= :id 1]}
             {:inline true}))

  ; SELECT
  (jdbc/query
   db
   (h/format {:select [:*]
              :from   :author
              :where  [:= :id 1]}
             {:inline true}))
  ;;
  )

