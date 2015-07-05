/**
 * Copyright 2010-2015 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.extdirectspring.demo.session;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.NumericFilter;

@Service
public class SessionService {

	private final Map<Integer, Customer> CUSTOMER_DB = new ConcurrentHashMap<>();
	private final AtomicInteger MAX_CUSTOMER_ID = new AtomicInteger(1);

	private final Map<Integer, Order> ORDER_DB = new ConcurrentHashMap<>();
	private final AtomicInteger MAX_ORDER_ID = new AtomicInteger(1);

	SessionService() {
		Customer customer = new Customer(this.MAX_CUSTOMER_ID.getAndIncrement(),
				"Company A", "540-111-1234");
		this.CUSTOMER_DB.put(customer.getId(), customer);

		customer = new Customer(this.MAX_CUSTOMER_ID.getAndIncrement(), "Company B",
				"650-222-2345");
		this.CUSTOMER_DB.put(customer.getId(), customer);

		customer = new Customer(this.MAX_CUSTOMER_ID.getAndIncrement(), "Company C",
				"412-333-3456");
		this.CUSTOMER_DB.put(customer.getId(), customer);

		customer = new Customer(this.MAX_CUSTOMER_ID.getAndIncrement(), "Company D",
				"861-444-4567");
		this.CUSTOMER_DB.put(customer.getId(), customer);

		Order order;
		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 2,
				LocalDate.of(2014, 7, 3), true);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 3,
				LocalDate.of(2014, 7, 5), true);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 3,
				LocalDate.of(2014, 7, 6), false);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 1,
				LocalDate.of(2014, 7, 9), true);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 4,
				LocalDate.of(2014, 7, 13), false);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 4,
				LocalDate.of(2014, 7, 19), false);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 4,
				LocalDate.of(2014, 8, 2), true);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 2,
				LocalDate.of(2014, 8, 6), false);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 3,
				LocalDate.of(2014, 8, 10), false);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 4,
				LocalDate.of(2014, 8, 13), true);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 1,
				LocalDate.of(2014, 8, 17), true);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 1,
				LocalDate.of(2014, 8, 22), true);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 3,
				LocalDate.of(2014, 8, 25), false);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 4,
				LocalDate.of(2014, 9, 1), true);
		this.ORDER_DB.put(order.getId(), order);

		order = new Order(this.MAX_ORDER_ID.getAndIncrement(), 2,
				LocalDate.of(2014, 9, 5), true);
		this.ORDER_DB.put(order.getId(), order);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "session")
	public Collection<Customer> customerRead() {
		return this.CUSTOMER_DB.values();
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "session")
	public List<Customer> customerCreate(List<Customer> newCustomers) {
		newCustomers.stream().forEach(newCustomer -> {
			System.out.println(newCustomer);
			newCustomer.setId(this.MAX_CUSTOMER_ID.getAndIncrement());
			this.CUSTOMER_DB.put(newCustomer.getId(), newCustomer);
		});
		return newCustomers;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "session")
	public List<Customer> customerUpdate(List<Customer> modifiedCustomers) {
		modifiedCustomers.stream().forEach(modifiedCustomer -> {
			System.out.println(modifiedCustomer);
			Customer customer = this.CUSTOMER_DB.get(modifiedCustomer.getId());
			if (modifiedCustomer.getName() != null) {
				customer.setName(modifiedCustomer.getName());
			}
			if (modifiedCustomer.getPhone() != null) {
				customer.setPhone(modifiedCustomer.getPhone());
			}
		});
		return modifiedCustomers;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "session")
	public void customerDestroy(List<Customer> destroyCustomers) {
		destroyCustomers.stream().map(Customer::getId).forEach(this.CUSTOMER_DB::remove);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "session")
	public Collection<Order> orderRead(ExtDirectStoreReadRequest request) {
		NumericFilter customerIdFilter = request.getFirstFilterForField("customerId");
		Number customerId = customerIdFilter.getValue();
		return this.ORDER_DB.values().stream()
				.filter(o -> o.getCustomerId().equals(customerId))
				.collect(Collectors.toList());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "session")
	public List<Order> orderCreate(List<Order> newOrders) {
		newOrders.stream().forEach(newOrder -> {
			System.out.println(newOrder);
			newOrder.setId(this.MAX_ORDER_ID.getAndIncrement());
			this.ORDER_DB.put(newOrder.getId(), newOrder);
		});
		return newOrders;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "session")
	public List<Order> orderUpdate(List<Order> modifiedOrders) {
		modifiedOrders.stream().forEach(modifiedOrder -> {
			System.out.println(modifiedOrder);
			Order order = this.ORDER_DB.get(modifiedOrder.getId());
			if (modifiedOrder.getDate() != null) {
				order.setDate(modifiedOrder.getDate());
			}
			if (modifiedOrder.getShipped() != null) {
				order.setShipped(modifiedOrder.getShipped());
			}
		});
		return modifiedOrders;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "session")
	public void orderDestroy(List<Order> destroyOrders) {
		destroyOrders.stream().map(Order::getId).forEach(this.ORDER_DB::remove);
	}

}
