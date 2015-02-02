package be.normegil.mylibrary.tools;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomCollectors {

	public <T> Collector<T, ?, Optional<T>> singletonCollector() {
		return Collectors.collectingAndThen(
				Collectors.toList(),
				list -> {
					if (list.size() == 0) {
						return Optional.empty();
					} else if (list.size() == 1) {
						return Optional.of(list.get(0));
					} else {
						throw new IllegalStateException("Found multiple results when expecting only one");
					}
				}
		);
	}

}
