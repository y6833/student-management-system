package com.yangy.service;

import java.util.List;

public interface MajorService {

    String getMajorName(String major);

    List<String> getMajorList();

    String getIdByclassName(String value);

}
