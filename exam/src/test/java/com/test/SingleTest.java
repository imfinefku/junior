package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SingleTest {

	private static final Logger logger=LoggerFactory.getLogger(SingleTest.class);
	
	@Test
	public void testAdd() {
		logger.warn("111");
	}
}
