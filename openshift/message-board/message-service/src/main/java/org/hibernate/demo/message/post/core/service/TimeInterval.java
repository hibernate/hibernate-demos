package org.hibernate.demo.message.post.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.ws.rs.PathParam;

public class TimeInterval {

	@PathParam( "sYear" )
	private Integer startYear;

	@PathParam( "sMonth" )
	private Integer startMonth;

	@PathParam( "sDay" )
	private Integer startDay;

	@PathParam( "sHour" )
	private Integer startHour;

	@PathParam( "sMinute" )
	private Integer startMinute;

	@PathParam( "sSecond" )
	private Integer startSecond;

	@PathParam( "eYear" )
	private Integer endYear;

	@PathParam( "eMonth" )
	private Integer endMonth;

	@PathParam( "eDay" )
	private Integer endDay;

	@PathParam( "eHour" )
	private Integer endHour;

	@PathParam( "eMinute" )
	private Integer endMinute;

	@PathParam( "eSecond" )
	private Integer endSecond;

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

	public Integer getStartDay() {
		return startDay;
	}

	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

	public Integer getStartHour() {
		return startHour;
	}

	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	public Integer getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(Integer startMinute) {
		this.startMinute = startMinute;
	}

	public Integer getStartSecond() {
		return startSecond;
	}

	public void setStartSecond(Integer startSecond) {
		this.startSecond = startSecond;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public Integer getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}

	public Integer getEndDay() {
		return endDay;
	}

	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	public Integer getEndHour() {
		return endHour;
	}

	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	public Integer getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(Integer endMinute) {
		this.endMinute = endMinute;
	}

	public Integer getEndSecond() {
		return endSecond;
	}

	public void setEndSecond(Integer endSecond) {
		this.endSecond = endSecond;
	}

	public Date getStart() {
		return getDate(startYear, startMonth, startDay, startHour, startMinute, startSecond);
	}

	public Date getEnd() {
		return getDate(endYear, endMonth, endDay, endHour, endMinute, endSecond);
	}

	private Date getDate(int year, int month, int date, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance( TimeZone.getDefault() );

		// Calendar.JANUARY is 0 in a view calendar is 1
		cal.set( year, month-1, date, hour, minute, second );
		return cal.getTime();
	}
}
