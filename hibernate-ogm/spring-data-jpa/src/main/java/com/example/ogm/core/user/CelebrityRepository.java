package com.example.ogm.core.user;

import java.util.List;

public interface CelebrityRepository {

	Celebrity save(Celebrity user);

	List<Celebrity> all();

	void deleteAll();
}
