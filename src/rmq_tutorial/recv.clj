(ns rmq-tutorial.recv
  (:import [com.rabbitmq.client ConnectionFactory Connection Channel DefaultConsumer]))

(def queue-name "hello")
(def connection-factory (doto (ConnectionFactory.)
                          (.setHost "localhost")))
(def channel (.. connection-factory
                 newConnection
                 createChannel))

(def declare-queue (.queueDeclare channel queue-name false false false nil))

(def consumer (proxy [DefaultConsumer] [channel]
                (handleDelivery [consumerTag envelope properties body]
                  (let [message (String. body "UTF-8")]
                    (prn " [x] Received " message)))))

(.basicConsume channel queue-name true consumer)
