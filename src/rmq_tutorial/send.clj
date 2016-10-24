(ns rmq-tutorial.send
  (:import [com.rabbitmq.client ConnectionFactory Connection Channel DefaultConsumer]))

(def queue-name "hello")
(def connection-factory (doto (ConnectionFactory.)
                          (.setHost "localhost")))
(def channel (.. connection-factory
                 newConnection
                 createChannel))

(def declare-queue (.queueDeclare channel queue-name false false false nil))

(defn basic-publish
  [s]
  (.basicPublish channel
                 ""
                 queue-name
                 nil
                 (.getBytes s)))
