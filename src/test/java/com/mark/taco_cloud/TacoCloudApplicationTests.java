package com.mark.taco_cloud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class TacoCloudApplicationTests {

	@Test
	void contextLoads() {
	}

	// Создание flux-экземпляров из массива строк
	@Test
	public void createAFlux_fromArray() {
		String[] fruits = new String[] {
				"Apple", "Orange", "Grape", "Banana", "Strawberry" };
		Flux<String> fruitFlux = Flux.fromArray(fruits);
		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();
	}

	// Создание преобразованных flux-элементов
	@Test
	public void createAFlux_range() {
		Flux<Integer> intervalFlux =
				Flux.range(1, 5);
		StepVerifier.create(intervalFlux)
				.expectNext(1)
				.expectNext(2)
				.expectNext(3)
				.expectNext(4)
				.expectNext(5)
				.verifyComplete();
	}

	// Создание преобразованных flux-элементов
	@Test
	public void createAFlux_interval() {
		Flux<Long> intervalFlux =
				Flux.interval(Duration.ofSeconds(1))
						.take(5);
		StepVerifier.create(intervalFlux)
				.expectNext(0L)
				.expectNext(1L)
				.expectNext(2L)
				.expectNext(3L)
				.expectNext(4L)
				.verifyComplete();
	}

	// Объединение двух потоков с помощью кортежа Tuple2
	@Test
	public void zipFluxes() {
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");
		Flux<Tuple2<String, String>> zippedFlux =
				Flux.zip(characterFlux, foodFlux);
		StepVerifier.create(zippedFlux)
				.expectNextMatches(p ->
						p.getT1().equals("Garfield") &&
								p.getT2().equals("Lasagna"))
				.expectNextMatches(p ->
						p.getT1().equals("Kojak") &&
								p.getT2().equals("Lollipops"))
				.expectNextMatches(p ->
						p.getT1().equals("Barbossa") &&
								p.getT2().equals("Apples"))
				.verifyComplete();
	}

	// Объединение двух потоков с помощью кастомной лямбда-функции
	@Test
	public void zipFluxesToObject() {
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");
		Flux<String> zippedFlux =
				Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);
		StepVerifier.create(zippedFlux)
				.expectNext("Garfield eats Lasagna")
				.expectNext("Kojak eats Lollipops")
				.expectNext("Barbossa eats Apples")
				.verifyComplete();
	}

	// Создание нового flux-потока на основе того потока, который первый отправил данные
	@Test
	public void firstWithSignalFlux() {
		Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
				.delaySubscription(Duration.ofMillis(100));
		Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");
		Flux<String> firstFlux = Flux.firstWithSignal(slowFlux, fastFlux);
		StepVerifier.create(firstFlux)
				.expectNext("hare")
				.expectNext("cheetah")
				.expectNext("squirrel")
				.verifyComplete();
	}

	// Фильтрация flux-потока: пропуск первых трех элементов
	@Test
	public void skipAFew() {
		Flux<String> countFlux = Flux.just(
						"one", "two", "skip a few", "ninety nine", "one hundred")
				.skip(3);
		StepVerifier.create(countFlux)
				.expectNext("ninety nine", "one hundred")
				.verifyComplete();
	}

	// Фильтрация flux-потока: пропуск элементов в течение четырех секунд
	@Test
	public void skipAFewSeconds() {
		Flux<String> countFlux = Flux.just(
						"one", "two", "skip a few", "ninety nine", "one hundred")
				.delayElements(Duration.ofSeconds(1))
				.skip(Duration.ofSeconds(4));
		StepVerifier.create(countFlux)
				.expectNext("ninety nine", "one hundred")
				.verifyComplete();
	}

	// Фильтрация flux-потока: передача первых трех элементов
	@Test
	public void take() {
		Flux<String> nationalParkFlux = Flux.just(
						"Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia")
				.take(3);
		StepVerifier.create(nationalParkFlux)
				.expectNext("Yellowstone", "Yosemite", "Grand Canyon")
				.verifyComplete();
	}

	// Фильтрация flux-потока: передача элементов в течение 3.5секунд
	@Test
	public void takeForAwhile() {
		Flux<String> nationalParkFlux = Flux.just(
						"Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
				.delayElements(Duration.ofSeconds(1))
				.take(Duration.ofMillis(3500));
		StepVerifier.create(nationalParkFlux)
				.expectNext("Yellowstone", "Yosemite", "Grand Canyon")
				.verifyComplete();
	}

	// Фильтрация flux-потока: фильтр по условию содержания пробела
	@Test
	public void filter() {
		Flux<String> nationalParkFlux = Flux.just(
						"Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
				.filter(np -> !np.contains(" "));
		StepVerifier.create(nationalParkFlux)
				.expectNext("Yellowstone", "Yosemite", "Zion")
				.verifyComplete();
	}

	// Фильтрация flux-потока: фильтр по уникальности элементов потока
	@Test
	public void distinct() {
		Flux<String> animalFlux = Flux.just(
						"dog", "cat", "bird", "dog", "bird", "anteater")
				.distinct();
		StepVerifier.create(animalFlux)
				.expectNext("dog", "cat", "bird", "anteater")
				.verifyComplete();
	}

	// Преобразование flux-потока: маппинг строк в dto
	@Test
	public void map() {
		Flux<Player> playerFlux = Flux
				.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
				.map(n -> {
					String[] split = n.split("\\s");
					return new Player(split[0], split[1]);
				});
		StepVerifier.create(playerFlux)
				.expectNext(new Player("Michael", "Jordan"))
				.expectNext(new Player("Scottie", "Pippen"))
				.expectNext(new Player("Steve", "Kerr"))
				.verifyComplete();
	}

	@Test
	public void flatMap() {
		Flux<Player> playerFlux = Flux
				.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
				// От каждого элемента flux-потока создаём подпоток mono
				.flatMap(n -> Mono.just(n)
						// Преобразуем элемент mono-потока
						.map(p -> {
							String[] split = p.split("\\s");
							return new Player(split[0], split[1]);
						})
						// Описываем в каком потоке должна осуществиться подписка: Из пула потоков процессора
						.subscribeOn(Schedulers.parallel())
				);
		List<Player> playerList = Arrays.asList(
				new Player("Michael", "Jordan"),
				new Player("Scottie", "Pippen"),
				new Player("Steve", "Kerr"));
		StepVerifier.create(playerFlux)
				.expectNextMatches(playerList::contains)
				.expectNextMatches(playerList::contains)
				.expectNextMatches(playerList::contains)
				.verifyComplete();
	}

	// Буферизация flux-потока: разбиение элементов на две коллекции в выходном потоке
	@Test
	public void buffer() {
		Flux<String> fruitFlux = Flux.just(
				"apple", "orange", "banana", "kiwi", "strawberry");
		Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);
		StepVerifier
				.create(bufferedFlux)
				.expectNext(Arrays.asList("apple", "orange", "banana"))
				.expectNext(Arrays.asList("kiwi", "strawberry"))
				.verifyComplete();
	}

	// Проверка на содержание в потоке символа "a", "k" во всех элементах потока
	@Test
	public void all() {
		Flux<String> animalFlux = Flux.just(
				"aardvark", "elephant", "koala", "eagle", "kangaroo");
		Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));
		StepVerifier.create(hasAMono)
				.expectNext(true)
				.verifyComplete();
		Mono<Boolean> hasKMono = animalFlux.all(a -> a.contains("k"));
		StepVerifier.create(hasKMono)
				.expectNext(false)
				.verifyComplete();
	}

	// Проверка на содержание в потоке символа "t", "z" хотя бы в одном из элементов потока
	@Test
	public void any() {
		Flux<String> animalFlux = Flux.just(
				"aardvark", "elephant", "koala", "eagle", "kangaroo");
		Mono<Boolean> hasTMono = animalFlux.any(a -> a.contains("t"));
		StepVerifier.create(hasTMono)
				.expectNext(true)
				.verifyComplete();
		Mono<Boolean> hasZMono = animalFlux.any(a -> a.contains("z"));
		StepVerifier.create(hasZMono)
				.expectNext(false)
				.verifyComplete();
	}
}
