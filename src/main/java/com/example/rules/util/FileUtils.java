package com.example.rules.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toSet;

public class FileUtils {

    public static Path getResourcePath(String name) throws URISyntaxException {
        var uri = Optional.ofNullable(FileUtils.class.getResource(name))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Resource %s not found", name))).toURI();
        return Paths.get(uri);
    }

    public static <T> Set<T> readClasspathFile(String resourceName, Function<String, T> mapper) throws URISyntaxException, IOException {
        var lines = Files.readAllLines(FileUtils.getResourcePath(resourceName));
        return lines.stream().map(mapper).collect(toSet());
    }

    public static <T, S> S readClasspathFile(String resourceName, Function<String, T> mapper, Collector<T, ?, S> collector) throws URISyntaxException, IOException {
        var lines = Files.readAllLines(FileUtils.getResourcePath(resourceName));
        return lines.stream().map(mapper).collect(collector);
    }

    public static void writeFile(String content, String pathFirst, String... pathMore) throws IOException {
        var path = Paths.get(pathFirst, pathMore);
        Files.write(path, content.getBytes());
    }
}

