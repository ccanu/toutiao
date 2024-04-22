package com.heima.kafka.stream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@Slf4j
public class KafkaStreamHelloListener {
    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
        streamsBuilder.stream("");

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
                .groupBy((key, value) -> value)
                // タイムウィンドウ
                .windowedBy(TimeWindows.of(Duration.ofSeconds(10)))
                // 単語のカウントを行います。
                .count()
                // KStreamに変換
                .toStream()
                .map((key, value) -> {
                    System.out.println("key:" + key + ",vlaue:" + value);
                    return new KeyValue<>(key.key().toString(), value.toString());
                })
                // メッセージを送信します。
                .to("itcast-topic-out");
        return stream;
    }

    }

