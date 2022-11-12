(ns step03.with-hikari-cp
  (:require [hikari-cp.core :as hikari]
            [honey.sql :as honeysql]
            [next.jdbc :as jdbc]))

(def datasource
  "참고: https://github.com/tomekw/hikari-cp#sqlite-example "
  (hikari/make-datasource {:jdbc-url "jdbc:sqlite:resources/test.db"}))

(comment
  (jdbc/execute! datasource (honeysql/format
                             {:insert-into :author
                              :values [{:name "John"
                                        :email "test-email@..."}]}))
  ;; 결과를 vector로 받게 된다.
  (jdbc/execute! datasource (honeysql/format
                             {:select [:*]
                              :from   :author
                              :limit  10}))
  ;; 결과를 한 건만 map으로 받게 된다.
  (jdbc/execute-one! datasource (honeysql/format
                             {:select [:*]
                              :from   :author
                              :where [:= :id 1]}))
  ;;
  )

