package com.kai.test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import net.sf.jsqlparser.parser.StreamProvider;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxXXX {
	
	@Test
	public void test() {
		Flux<String> flux=Flux.just("d","e","f");
		List<String> list = Arrays.asList("a","b","c");
		Flux<String> flux2 = Flux.fromIterable(list);
		for(String st:flux2.toIterable()) {
			System.out.println(st);
		}
		for(String st:flux.toIterable()) {
			System.out.println(st);
		}
		String first = flux.blockFirst();
		String last = flux.blockLast();
		System.out.println("第一条："+first);
		System.out.println("最终一条："+last);
		System.out.println(flux);
		System.out.println(flux2);
	}
	@Test
	public void lambdaTest() {
		Student s1=new Student();
		s1.setHeight(36.5);
		s1.setName("uuid");
		Student s2=new Student();
		s2.setHeight(35.5);
		s2.setName("rt");
		Student s3=new Student();
		s3.setHeight(31.5);
		s3.setName("yy");
		List<Student> list=new ArrayList<Student>();
		list.add(s1);list.add(s2);list.add(s3);
		//list.sort((sq,sw)->Double.compare(sq.getHeight(), sw.getHeight()));
		//list.sort(Comparator.comparingDouble((student)->student.getHeight()));
		list.sort(Comparator.comparingDouble(Student::getHeight));
		System.out.println(list);
	}
	@Test
	public void reactorYY() {
		String a="12314141";
		System.out.println(a);
	}
	@Test
	public void MonoTest() {
		Mono<String> just = Mono.just("a");
		System.out.println(just.block());
		Integer[] a ={1,2,3,4,5,6};
		Flux.just(a).subscribe(System.out::println);
		Flux.just(a).subscribe(System.out::println,System.out::println,()->System.out.println("completed!"));
		Mono.error(new Exception("some error")).subscribe(System.out::println,System.err::println,()->System.out.println("completed!"));
	}
	
	public Flux<Integer> getFluxee(){
		return Flux.just(1,2,3,4,5,6);
	}
	
	public Mono<Integer> getMonoee(){
		return Mono.error(new Exception("some error"));
	}
	
	@Test
	public void testStepVerifier() {
		StepVerifier.create(getFluxee())
		.expectNext(1,2,3,4,5,6)
		.expectComplete()
		.verify();
		StepVerifier.create(getMonoee())
		.expectErrorMessage("some error")
		.verify();
	}
	@Test
	public void fluxTestMap() {
		StepVerifier.create(Flux.range(1, 6)
				.map(i->i*i))
		.expectNext(1,4,9,16,25,36)
		.verifyComplete();
	}
	@Test
	public void flatTestMap() {
		StepVerifier.create(Flux.just("Flux","Mono").flatMap(s->Flux.fromArray(s.split("\\s*")).delayElements(Duration.ofMillis(100)))
				.doOnNext(System.out::println))
		.expectNextCount(8)
		.verifyComplete();
	}
	@Test
	public void filter() {
		StepVerifier.create(Flux.range(1, 6).filter(i->i%2==1).map(i->i*i))
		.expectNext(1,9,25)
		.verifyComplete();
	}
	private Flux<String> getZipData(){
		String data="zip to source together 1 2 3 1 3 2 1 4 6 7 8 9 20 12 23 34 45";
		return Flux.fromArray(data.split("\\s+"));
	}
	@Test
	public void zipTest() throws InterruptedException {
		CountDownLatch countDownLatch=new CountDownLatch(1);
		Flux.zip(getZipData(), Flux.interval(Duration.ofMillis(200)))
		.subscribe(t->System.out.println(t.getT1()),null,countDownLatch::countDown);
		countDownLatch.await(10, TimeUnit.SECONDS);
	}
}
