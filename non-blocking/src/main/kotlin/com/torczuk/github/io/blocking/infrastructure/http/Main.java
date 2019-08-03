package com.torczuk.github.io.blocking.infrastructure.http;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) throws IOException {

//    Flux.using(
//            () -> {
//              byte[] bytes = Files.readAllBytes(Paths.get(""));
//              return IntStream.range(0, bytes.length).map(i -> bytes[i]);
//            },
//            Flux::fromStream,
//            Stream::close
//    );

    Function<Stream<Integer>, Publisher<? extends Integer>> fromStream = Flux::fromStream;

    Flux<Byte> using = Flux.using(
            () -> IntStream.range(0, 2).boxed(),
            fromStream,
            BaseStream::close)
            .map(Integer::byteValue);

  }
}