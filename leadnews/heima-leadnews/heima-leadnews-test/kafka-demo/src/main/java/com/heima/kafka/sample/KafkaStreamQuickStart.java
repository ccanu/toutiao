package com.heima.kafka.sample;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * 流式处理
 */
public class KafkaStreamQuickStart {

    public static void main(String[] args) {

        // Kafkaの設定情報
        Properties prop = new Properties();
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        prop.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        prop.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG,"streams-quickstart");

        // ストリームビルダーの作成
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        // ストリーム処理
        streamProcessor(streamsBuilder);

        // KafkaStreamsオブジェクトの作成
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(),prop);
        // ストリーム処理の開始
        kafkaStreams.start();
    }

    /**
            * ストリーム処理
     * メッセージの内容：hello kafka  hello itcast
     * @param streamsBuilder
     */
    private static void streamProcessor(StreamsBuilder streamsBuilder) {
        // KStreamオブジェクトを作成し、どのトピックからメッセージを受信するかを指定します。
        KStream<String, String> stream = streamsBuilder.stream("itcast-topic-input");
        /**
         * メッセージの値を処理する
         */
        stream.flatMapValues(new ValueMapper<String, Iterable<String>>() {
                    @Override
                    public Iterable<String> apply(String value) {
                        return Arrays.asList(value.split(" "));
                    }
                })
                // 値でグループ化して処理します。
                .groupBy((key,value)->value)
                // タイムウィンドウ
                .windowedBy(TimeWindows.of(Duration.ofSeconds(10)))
                // 単語のカウントを行います。
                .count()
                // KStreamに変換
                .toStream()
                .map((key,value)->{
                    System.out.println("key:"+key+",vlaue:"+value);
                    return new KeyValue<>(key.key().toString(),value.toString());
                })
                // メッセージを送信します。
                .to("itcast-topic-out");
    }



}