package com.prodyna.pac.acres.showcase;

import java.text.ParseException;
import java.util.Date;

public interface ShowcaseService {

	void createShowcase();

	void resetDatabase();

	Date getDate(String dateString) throws ParseException;

	Date getDateTime(String dateTimeString) throws ParseException;
}
