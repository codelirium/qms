package io.codelirium.unifiedpost.qms.base;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({
				TransactionalTestExecutionListener.class,
				DependencyInjectionTestExecutionListener.class,
				DbUnitTestExecutionListener.class
})
public abstract class BaseDBTest {

	@PersistenceContext
	private EntityManager entityManager;


	protected void flushAndClear() {

		entityManager.flush();

		entityManager.clear();

	}
}
