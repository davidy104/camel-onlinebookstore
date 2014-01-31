package co.nz.onlinebookstore.data.support;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import co.nz.onlinebookstore.data.support.EntityBuilder.EntityBuilderManager;
import co.nz.onlinebookstore.utils.OnlineBookStoreUtils;

public class InitialDataSetup {
	private TransactionTemplate transactionTemplate;

	@PersistenceContext
	private EntityManager entityManager;

	public InitialDataSetup(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void initialize() {
		EntityBuilderManager.setEntityManager(this.entityManager);
		this.transactionTemplate.execute(new TransactionCallback<Void>() {

			@Override
			public Void doInTransaction(TransactionStatus status) {
				// initiate customer
				{
					new CustomerBuilder() {
						{
							create("david", "david.yuan@yellow.co.nz",
									"2983209301");
						}
					}.build();

					new CustomerBuilder() {
						{
							create("jordan", "michael.jordan@yellow.co.nz",
									"4029090902");
						}
					}.build();
				}

				// init items
				{
					new BookBuilder() {
						{
							create("isbn-" + UUID.randomUUID().toString(),
									"Camel in action", new BigDecimal("70.00"),
									"Mike", OnlineBookStoreUtils.strToDate(
											"2007-10-23", "yyyy-MM-dd"), 200);
						}
					}.build();

					new BookBuilder() {
						{
							create("isbn-" + UUID.randomUUID().toString(),
									"Spring in action",
									new BigDecimal("50.00"), "John",
									OnlineBookStoreUtils.strToDate(
											"2009-04-13", "yyyy-MM-dd"), 70);
						}
					}.build();

					new BookBuilder() {
						{
							create("isbn-" + UUID.randomUUID().toString(),
									"Maven definition",
									new BigDecimal("40.00"), "Young",
									OnlineBookStoreUtils.strToDate(
											"2011-01-09", "yyyy-MM-dd"), 20);
						}
					}.build();

					new BookBuilder() {
						{
							create("isbn-" + UUID.randomUUID().toString(),
									"Spring Data in action", new BigDecimal(
											"30.00"), "Li",
									OnlineBookStoreUtils.strToDate(
											"2013-06-08", "yyyy-MM-dd"), 30);
						}
					}.build();
				}

				return null;
			}

		});

		EntityBuilderManager.clearEntityManager();

	}
}
