(ns rmq-tutorial.core
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


(def consumer (proxy [DefaultConsumer] [channel]
                (handleDelivery [consumerTag envelope properties body]
                  (let [message (String. body "UTF-8")]
                    (prn " [x] Received '" message "'")))))



(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
