package com.kai.test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 关于线程池中的操作
 * @author ggk
 * @data 2019年3月26日下午3:19:06
 */
public class ThreadTest {
	
	public String getStringSync() {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "hello,Reactor";
	}
	@Test
	public void testSyncToAsync() throws InterruptedException {
		CountDownLatch countDownLatch=new CountDownLatch(1);
		Mono.fromCallable(()->getStringSync())
		.subscribeOn(Schedulers.elastic())
		.subscribe(System.out::println,null,countDownLatch::countDown);
		countDownLatch.await(10,TimeUnit.SECONDS);
	}
	@Test
	public void testException() {
		Flux.range(1, 6)
		.map(i->10/(i-3))
		.onErrorResume(e->Mono.just(new Random().nextInt(6)))
		.map(i->i*i)
		.subscribe(System.out::println,System.err::println);
	}
	@Test
	public void testBackpressure() {
		Flux.range(1, 6)
		.doOnRequest(n->System.out.println("request"+n+"valuse..........."))
		.subscribe(new BaseSubscriber<Integer>() {
			protected void hookOnSubscribe(Subscription subscription) {
				System.out.println("request-112233");
				request(1);
			}

			protected void hookOnNext(Integer value) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Get value["+value+"]");
				request(1);
			}
			
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
