/**
 * Copyright 2010-2017 the original author or authors.
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
package ch.rasc.extdirectspring.demo.bigdata;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.extdirectspring.demo.util.YMDLocalDateDeserializer;
import ch.rasc.extdirectspring.demo.util.YMDLocalDateSerializer;

@JsonInclude(Include.NON_NULL)
public class Employee {
	private String employeeNo;

	private int[] rating;

	private BigDecimal salary;

	private String forename;

	private String surname;

	private String email;

	private String department;

	private LocalDate dob;

	private LocalDate joinDate;

	private String noticePeriod;

	private int sickDays;

	private int holidayDays;

	private int holidayAllowance;

	private String avatar;

	public String getEmployeeNo() {
		return this.employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public int[] getRating() {
		return this.rating;
	}

	public void setRating(int[] rating) {
		this.rating = rating;
	}

	public BigDecimal getSalary() {
		return this.salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getForename() {
		return this.forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@JsonSerialize(using = YMDLocalDateSerializer.class)
	public LocalDate getDob() {
		return this.dob;
	}

	@JsonDeserialize(using = YMDLocalDateDeserializer.class)
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	@JsonSerialize(using = YMDLocalDateSerializer.class)
	public LocalDate getJoinDate() {
		return this.joinDate;
	}

	@JsonDeserialize(using = YMDLocalDateDeserializer.class)
	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public String getNoticePeriod() {
		return this.noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public int getSickDays() {
		return this.sickDays;
	}

	public void setSickDays(int sickDays) {
		this.sickDays = sickDays;
	}

	public int getHolidayDays() {
		return this.holidayDays;
	}

	public void setHolidayDays(int holidayDays) {
		this.holidayDays = holidayDays;
	}

	public int getHolidayAllowance() {
		return this.holidayAllowance;
	}

	public void setHolidayAllowance(int holidayAllowance) {
		this.holidayAllowance = holidayAllowance;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void update(Employee modifiedEmployee) {
		this.rating = modifiedEmployee.getRating();
		this.salary = modifiedEmployee.getSalary();
		this.forename = modifiedEmployee.getForename();
		this.surname = modifiedEmployee.getSurname();
		this.email = modifiedEmployee.getEmail();
		this.department = modifiedEmployee.getDepartment();
		this.dob = modifiedEmployee.getDob();
		this.joinDate = modifiedEmployee.getJoinDate();
		this.noticePeriod = modifiedEmployee.getNoticePeriod();
		this.sickDays = modifiedEmployee.getSickDays();
		this.holidayDays = modifiedEmployee.getHolidayDays();
		this.holidayAllowance = modifiedEmployee.getHolidayAllowance();
		this.avatar = modifiedEmployee.getAvatar();
	}

}
